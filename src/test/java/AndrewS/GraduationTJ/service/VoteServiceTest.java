package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.util.exception.VoteExpiredException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static AndrewS.GraduationTJ.RestaurantTestData.*;
import static AndrewS.GraduationTJ.UserTestData.*;
import static AndrewS.GraduationTJ.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() throws Exception {
        LocalDate now = LocalDate.now();
        Vote created = voteService.create(USER.getId(), RES1.getId());
        assertMatch(voteService.getInDateByUser(now,USER.getId()), created);
    }

    @Test
    void createTwice() throws Exception {
        LocalDate now = LocalDate.now();
        voteService.create(USER.getId(), RES1.getId());
        assertThrows(VoteExpiredException.class, () ->
                voteService.create(USER.getId(), RES2.getId()));

    }

    @Test
    void update() throws Exception {
        LocalTime now = LocalTime.now();

        try {

            if (now.isAfter(LocalTime.of(11,0))) {
                throw new VoteExpiredException("You can't change your mind after 11");
            }

            voteService.update(VOTE1, VOTE1.getVoter().getId(), RES1.getId());
            assertMatch(voteService.get(VOTE1_ID), VOTE1_UPDATED);

        } catch (VoteExpiredException v) {
            assertThrows(VoteExpiredException.class, () ->
                    voteService.update(VOTE1, VOTE1.getVoter().getId(), RES1.getId()));
        }
    }

    @Test
    void getInDate() throws Exception {
        List<Vote> votes = voteService.getInDate(LocalDate.of(2019,8,29));
        assertMatch(votes,VOTE1,VOTE2);
    }

    @Test
    void getInDateByUser() throws Exception {
        Vote result = voteService.getInDateByUser(LocalDate.of(2019,8,29), 100000);
        assertMatch(result,VOTE1);
    }
}
