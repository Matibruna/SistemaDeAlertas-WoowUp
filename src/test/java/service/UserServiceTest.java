package service;

import entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;
    @Before
    public void setUp(){
        userService = new UserService();
    }

    @Test
    public void should_register_user(){
        try {
            userService.add(new User("matias", "password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertTrue(userService.contains(new User("matias", "password")));
    }

    @Test(expected = Exception.class)
    public void should_not_register_user_with_same_username(){
        try {
            userService.add(new User("matias2", "password"));
            userService.add(new User("matias2", "password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}