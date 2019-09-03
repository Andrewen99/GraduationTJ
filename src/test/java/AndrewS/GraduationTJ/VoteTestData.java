package AndrewS.GraduationTJ;

import AndrewS.GraduationTJ.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static AndrewS.GraduationTJ.model.AbstractBaseEntity.START_SEQ;
import static AndrewS.GraduationTJ.UserTestData.*;
import static AndrewS.GraduationTJ.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 8;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER, RES3, LocalDate.now());
    public static final Vote VOTE1_UPDATED = new Vote(VOTE1_ID, USER, RES3, LocalDate.now());
    public static final Vote VOTE2 = new Vote(VOTE1_ID + 1, ADMIN, RES3, LocalDate.of(2019,8,29));

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "voter","elected");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("voter","elected").isEqualTo(expected);
    }
}
