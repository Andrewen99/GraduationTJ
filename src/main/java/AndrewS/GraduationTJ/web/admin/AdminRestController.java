package AndrewS.GraduationTJ.web.admin;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.service.DishService;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.to.DishTo;
import AndrewS.GraduationTJ.to.RestaurantTo;
import AndrewS.GraduationTJ.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    DishService dishService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("{Admin} getAll ");
        return restaurantService.getAll();
    }

    @GetMapping("/history")
    public List<RestaurantTo> getAllHistory(@RequestParam(required = false) String date) {
        if (date== null) {
            log.info("{Admin} getAllHistory ");
            return restaurantService.getAllTo();
        } else {
            log.info("{Admin} getAllHistory with date: {}", date);
            return restaurantService.getAllWithVotes(DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id, @RequestParam(required = false) String date) {
        if (date==null) {
            log.info("{Admin} getRestaurant with id: {} at date of: {}", id, LocalDate.now().toString());
            return restaurantService.getInDateWithDishesAndVotes(id, LocalDate.now());
        } else {
            log.info("{Admin} getRestaurant with id: {} at date of: {}", id, date);
            return restaurantService.getInDateWithDishesAndVotes(id, DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/dishes/{id}")
    public Dish getDish(@PathVariable int id) {
        log.info("{Admin} getDish ");
        return dishService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int id) {
        log.info("{Admin} delete Restaurant with id: {}", id);
        restaurantService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("{Admin} update Restaurant with id: {}", id);
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
        log.info("{Admin} create Restaurant with id: {}", created.getId());
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
        log.info("{Admin} create Dish with id: {}", created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);

    }

    @PutMapping(value = "{resId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void UpdateDish(@RequestBody Dish dish, @PathVariable int id, @PathVariable int resId) {
        assureIdConsistent(dish, id);
        dishService.update(dish,resId);
        log.info("{Admin} update Dish with id: {}", id);
    }

    @DeleteMapping("{resId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteDish(@PathVariable int id, @PathVariable int resId) {
        dishService.delete(id);
        log.info("{Admin} delete Dish with id: {}", id);
    }
}
