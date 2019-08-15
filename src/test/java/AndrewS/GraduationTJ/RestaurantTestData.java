package AndrewS.GraduationTJ;

import AndrewS.GraduationTJ.model.Restaurant;

import java.util.Arrays;

import static AndrewS.GraduationTJ.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int RES1_ID = START_SEQ + 2;

    public static final Restaurant RES1 = new Restaurant(RES1_ID,"Tokio City Restaurant");
    public static final Restaurant RES2 = new Restaurant(RES1_ID + 1, "Yakitoria Restaurant");
    public static final Restaurant RES3 = new Restaurant(RES1_ID + 2, "Restaurant With Food");


    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "dishes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "dishes").isEqualTo(expected);
    }

}
