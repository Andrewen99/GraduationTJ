package AndrewS.GraduationTJ.web.admin;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.service.DishService;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.to.DishTo;
import AndrewS.GraduationTJ.to.RestaurantTo;
import AndrewS.GraduationTJ.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static AndrewS.GraduationTJ.util.ValidationUtil.assureIdConsistent;
import static AndrewS.GraduationTJ.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    public static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/history")
    public List<RestaurantTo> getAllHistory(@RequestParam(required = false) String date) {
        if (date== null) {
            return restaurantService.getAllTo();
        } else {
            return restaurantService.getAllWithVotes(DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id, @RequestParam String date) {
        return restaurantService.getInDateWithDishesAndVotes(id, DateTimeUtil.parseLocalDate(date));
    }

    @GetMapping("/dishes/{id}")
    public Dish getDish(@PathVariable int id) {
        return dishService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int id) {
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurantWithLocation(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "{resId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(@RequestBody DishTo dishto, @PathVariable int resId) {
        Dish dish = new Dish(dishto.getName(), dishto.getDate(), dishto.getPrice());
        checkNew(dish);
        Dish created = dishService.create(dish, resId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(value = "{resId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void UpdateDish(@RequestBody Dish dish, @PathVariable int id, @PathVariable int resId) {
        assureIdConsistent(dish, id);
        dishService.update(dish,resId);
    }

    @DeleteMapping("{resId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteDish(@PathVariable int id, @PathVariable int resId) {
        dishService.delete(id);
    }
}
