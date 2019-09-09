package AndrewS.GraduationTJ.service;

import static AndrewS.GraduationTJ.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.List;

import static AndrewS.GraduationTJ.DishTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringJUnitConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void create() throws Exception  {
        Dish newDish = getCreated();
        Dish created = dishService.create(newDish, RES3.getId());
        newDish.setId(created.getId());
        assertMatch(newDish, created);
        assertMatch( dishService.getAll( RES3.getId() ), DISH1, DISH2, DISH3, newDish);
    }

    @Test
    void update() throws Exception  {
        dishService.update(DISH1_UPDATED,RES3.getId());
        assertMatch(dishService.get(DISH1_ID),DISH1_UPDATED);
    }

    @Test
    void delete() throws Exception {
        dishService.delete(DISH1_ID);
        assertMatch(dishService.getAll(RES3.getId()), DISH2, DISH3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                dishService.delete(1));
    }

    @Test
    void get() throws Exception {
        Dish actual = dishService.get(DISH1_ID);
        assertMatch(actual, DISH1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                dishService.get(1));
    }

//    @Test
//    void getInDateByRestaurant() {
//        List<Dish> dishes = dishService.getInDateByRestaurant(LocalDate.of(2019,8,15), RES3.getId());
//        assertMatch(dishes, DISH3);
//    }

    @Test
    void getAll() throws Exception {
        List<Dish> actual = dishService.getAll(RES3.getId());
        assertMatch(actual, DISH1,DISH2,DISH3);
    }


}