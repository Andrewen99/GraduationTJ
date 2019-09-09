package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.DishTestData;
import AndrewS.GraduationTJ.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import AndrewS.GraduationTJ.util.exception.NotFoundException;

import java.util.List;

import static AndrewS.GraduationTJ.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;
    @Test
    void create() throws Exception {
          Restaurant newRes = new Restaurant(null, "NewRes");
          Restaurant created = service.create(newRes);
          newRes.setId(created.getId());
          assertMatch(newRes, created);
          assertMatch(service.getAll(), RES1, RES2,RES3, newRes);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(RES1);
        updated.setName("Updated Name");
        service.update(updated);
        assertMatch(service.get(RES1_ID),updated);
    }

    @Test
    void delete() throws Exception {
        service.delete(RES1_ID);
        assertMatch(service.getAll(),RES2,RES3);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows( NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant actual = service.get(RES1_ID);
        assertMatch(actual, RES1);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all,RES1, RES2,RES3);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getWithDishes() throws Exception {
        Restaurant actualRes = service.getWithDishes(RES3.getId());
        assertMatch(actualRes, RES3);
        DishTestData.assertMatch(actualRes.getDishes(), DishTestData.DISH1,  DishTestData.DISH3, DishTestData.DISH2);
    }
}