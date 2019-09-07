package AndrewS.GraduationTJ.web.user;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.service.VoteService;
import AndrewS.GraduationTJ.to.RestaurantTo;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static AndrewS.GraduationTJ.VoteTestData.*;
import static AndrewS.GraduationTJ.TestUtil.readFromJson;
import static AndrewS.GraduationTJ.TestUtil.readFromJsonMvcResult;



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
public class userRestControllerTest {
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
                .build();
    }

    @Test
    void getRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RES1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO));

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1, RES2, RES3));
    }

    @Test
    void getRestaurantsWithScore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+RES1_ID));
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "score"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES3_TO, RES1_TO_WITH_VOTE, RES2_TO));
    }

    @Test
    void getScoreWithoutVoting() throws Exception {
        assertThrows(
                AssertionError.class,()->
                        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"score"))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)));
    }

    @Test
    void createVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID));
        Vote v = voteService.get(CREATED_VOTE_ID);
        assertMatch(v, CREATED_VOTE);
    }

    @Test
    void createTwice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID));
        assertThrows(Exception.class, () ->
                mockMvc.perform((MockMvcRequestBuilders.post(REST_URL + RES1_ID))));
    }

    @Test
    void updateVote() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RES1_ID));
        if (LocalTime.now().isAfter(LocalTime.of(11,0))) {
            assertThrows(Exception.class, () ->
                    mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES2.getId() + "/" + CREATED_VOTE_ID)) );
        } else {
            mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + RES2.getId() + "/" + CREATED_VOTE_ID));
            assertMatch(voteService.get(CREATED_VOTE_ID), CREATED_VOTE_UPDATED);
        }
    }
}
