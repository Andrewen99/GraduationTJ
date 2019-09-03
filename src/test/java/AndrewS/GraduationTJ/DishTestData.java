package AndrewS.GraduationTJ;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static AndrewS.GraduationTJ.model.AbstractNamedEntity.*;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

//('steak', 9,'2019-08-29', 100004),
//        ('chiken', 11,'2019-08-29', 100004),
//        ('california roll', 5,'2019-08-29', 100004);

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 5;

    public static final Dish DISH1 = new Dish(DISH1_ID,"steak",LocalDate.of(2019,8,29),9);
    public static final Dish DISH2 = new Dish(DISH1_ID + 1,"chicken",LocalDate.of(2019,8,29),11);
    public static final Dish DISH3 = new Dish(DISH1_ID + 2,"california roll",LocalDate.of(2019,8,15),5);


    public static Dish getCreated() {
        return new Dish(null,"Created Dish", LocalDate.of(2015, 06,12),  15);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID,"Updated Dish", DISH1.getDate(),  15);
    }


    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
