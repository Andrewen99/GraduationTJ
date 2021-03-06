package AndrewS.GraduationTJ.web.admin;



import AndrewS.GraduationTJ.DishTestData;
import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.service.DishService;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;

import java.time.LocalDate;


import static AndrewS.GraduationTJ.DishTestData.*;
import static AndrewS.GraduationTJ.TestUtil.userHttpBasic;
import static AndrewS.GraduationTJ.UserTestData.ADMIN;
import static AndrewS.GraduationTJ.UserTestData.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static AndrewS.GraduationTJ.RestaurantTestData.*;
import static AndrewS.GraduationTJ.RestaurantTestData.RES3;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static AndrewS.GraduationTJ.TestUtil.readFromJson;

@SpringJUnitWebConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class AdminRestControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private static final String REST_URL = AdminRestController.REST_URL + "/";

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO, RES2_TO, RES3_TO));
    }

    @Test
    void getAllHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "history").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO, RES2_TO, RES3_TO_WITH_VOTES));
    }

    @Test
    void getRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RES3.getId() + "?date=2019-08-15").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }


    @Test
    void updateRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES2.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RES2_UPDATED)))
                .andExpect(status().isNoContent());

        assertMatch(restaurantService.getAll(), RES1, RES2_UPDATED, RES3);
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RES1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(restaurantService.getAll(), RES2, RES3);
    }

    @Test
    void createRestaurantWithLocation() throws Exception {
        Restaurant created = new Restaurant("Created Restaurant");
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
            .with(userHttpBasic(ADMIN))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(created)));

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        assertMatch(restaurantService.getAll(), RES1, RES2, RES3, created);
    }


    @Test
    void createDish() throws Exception {
        Dish created = new Dish("createdDish", LocalDate.of(2019,9,7),100);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES3.getId() + "/dishes")
            .with(userHttpBasic(ADMIN))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(created)));

        Dish returned = readFromJson(action, Dish.class);
        created.setId(returned.getId());
        assertThat(returned).isEqualToIgnoringGivenFields(created,"restaurant");
    }


    @Test
    void updateDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES3.getId() + "/dishes/" + DISH1_ID)
            .with(userHttpBasic(ADMIN))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(DISH1_UPDATED)))
            .andExpect(status().isNoContent());

        assertThat(dishService.get(DISH1_ID)).isEqualToIgnoringGivenFields(DISH1_UPDATED,"restaurant");
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + RES3.getId() + "/dishes/" + DISH3.getId()).with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent())
                .andDo(print());

        DishTestData.assertMatch(dishService.getAll(RES3.getId()),DISH2, DISH1 );
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

}
