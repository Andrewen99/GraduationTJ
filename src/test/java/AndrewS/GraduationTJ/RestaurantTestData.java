package AndrewS.GraduationTJ;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.to.RestaurantTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static AndrewS.GraduationTJ.TestUtil.readFromJsonMvcResult;
import static AndrewS.GraduationTJ.TestUtil.readListFromJsonMvcResult;
import static AndrewS.GraduationTJ.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int RES1_ID = START_SEQ + 2;

    public static final Restaurant RES1 = new Restaurant(RES1_ID,"Tokio City Restaurant");
    public static final Restaurant RES2 = new Restaurant(RES1_ID + 1, "Yakitoria Restaurant");
    public static final Restaurant RES3 = new Restaurant(RES1_ID + 2, "Restaurant With Food");

    public static final RestaurantTo RES1_TO = new RestaurantTo(RES1_ID,"Tokio City Restaurant", 0);
    public static final RestaurantTo RES1_TO_WITH_VOTE = new RestaurantTo(RES1_ID,"Tokio City Restaurant",1);
    public static final RestaurantTo RES2_TO = new RestaurantTo(RES1_ID + 1, "Yakitoria Restaurant", 0);
    public static final RestaurantTo RES3_TO = new RestaurantTo(RES1_ID + 2, "Restaurant With Food", 0);


    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "dishes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "dishes").isEqualTo(expected);
    }


    public static void assertMatchTo(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "dishes");
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, RestaurantTo... expected) {
        assertMatchTo(actual, Arrays.asList(expected));
    }

    public static void assertMatchTo(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "dishes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantTo... expected) {
        return result -> assertMatchTo(readListFromJsonMvcResult(result, RestaurantTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static ResultMatcher contentJson(RestaurantTo expected) {
        return result -> assertMatchTo(readFromJsonMvcResult(result, RestaurantTo.class), expected);
    }

}
