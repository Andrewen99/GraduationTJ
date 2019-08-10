package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Role;
import AndrewS.GraduationTJ.model.User;
import AndrewS.GraduationTJ.util.exception.NotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static AndrewS.GraduationTJ.UserTestData.*;

@SpringJUnitConfig( locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "New@mail.ru", "password", false,new Date(),Collections.singleton(Role.ROLE_USER));
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertMatch(userService.getAll(), ADMIN, newUser, USER);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("updatedName");
        userService.update(updated);
        assertMatch(userService.get(USER_ID),updated);
    }

    @Test
    public void duplicateMailCreate() throws Exception {
        assertThrows( DataAccessException.class, () ->
        userService.create(new User(null, "duplicate", "user@yandex.ru", "password", Role.ROLE_USER)));
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.delete(1));
    }

    @Test
    public void get() throws Exception {
        User user = userService.get(USER_ID);
        assertMatch(user,USER);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = userService.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.get(1));
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }




}
