package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Role;
import AndrewS.GraduationTJ.model.User;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static AndrewS.GraduationTJ.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
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

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() throws Exception {
        userService.create(new User(null, "duplicate", "user@yandex.ru", "password", Role.ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        userService.delete(1);
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

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }




}
