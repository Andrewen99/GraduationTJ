package AndrewS.GraduationTJ.util.restaurant;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.to.DishTo;
import AndrewS.GraduationTJ.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public RestaurantTo getWithDishesInDate(Restaurant restaurant, LocalDate date) {
        RestaurantTo restaurantTo = new RestaurantTo(restaurant.getId(), restaurant.getName());

        restaurantTo.setDishes(restaurant.getDishes()
        .stream()
        .filter(dish -> dish.getDate().equals(date))
        .map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice()))
        .collect(Collectors.toList())
        );

        return restaurantTo;
    }

    public static RestaurantTo getWithFilteredCountOfVotes(Restaurant restaurant, LocalDate date) {
        RestaurantTo restaurantTo = new RestaurantTo(restaurant.getId(), restaurant.getName());
        restaurantTo.setCountOfVotes(
                (int)restaurant.getVotes().stream().filter(vote -> vote.getDate().equals(date)).count()
        );
        return restaurantTo;
    }

    public static List<RestaurantTo> getAllWithFilteredCountOfVotes(List<Restaurant> restaurants, LocalDate date) {
        return restaurants.stream()
                .map(restaurant -> {
                    RestaurantTo restaurantTo = new RestaurantTo(restaurant.getId(), restaurant.getName());
                    restaurantTo.setCountOfVotes( (int)restaurant.getVotes().stream().filter(vote -> vote.getDate().equals(date)).count() );
                    return restaurantTo; })
                .collect(Collectors.toList());

    }
}
