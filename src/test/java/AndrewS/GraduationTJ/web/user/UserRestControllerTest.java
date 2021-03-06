package AndrewS.GraduationTJ.web.user;


import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static AndrewS.GraduationTJ.TestUtil.*;
import static AndrewS.GraduationTJ.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static AndrewS.GraduationTJ.VoteTestData.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


import static AndrewS.GraduationTJ.RestaurantTestData.*;



import javax.annotation.PostConstruct;
import java.time.LocalTime;

@SpringJUnitWebConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserRestControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private static final String REST_URL = UserRestController.REST_URL + "/";

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VoteService voteService;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RES1_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO));

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO, RES2_TO, RES3_TO));
    }

    @Test
    void getRestaurantsWithScore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+RES1_ID).with(userHttpBasic(USER)));
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "score").with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson( RES1_TO_WITH_VOTE, RES2_TO, RES3_TO));
    }

//    @Test
//    void getScoreWithoutVoting() throws Exception {
//        assertThrows(
//                AssertionError.class,()->
//                        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"score").with(userHttpBasic(USER)))
//                                .andDo(print())
//                                .andExpect(status().isOk())
//                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)));
//    }

    @Test
    void createVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID).with(userHttpBasic(USER)));
        Vote v = voteService.get(CREATED_VOTE_ID);
        assertMatch(v, CREATED_VOTE);
    }

    @Test
    void createTwice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID).with(userHttpBasic(USER)));
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID).with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());

    }

    @Test
    void updateVote() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID).with(userHttpBasic(USER)));
        if (LocalTime.now().isAfter(LocalTime.of(11,0))) {
                    mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES2.getId() + "/" + CREATED_VOTE_ID).with(userHttpBasic(USER)))
                    .andExpect(status().isForbidden());
        } else {
            mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES2.getId() + "/" + CREATED_VOTE_ID).with(userHttpBasic(USER)));
            assertMatch(voteService.get(CREATED_VOTE_ID), CREATED_VOTE_UPDATED);
        }
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}
