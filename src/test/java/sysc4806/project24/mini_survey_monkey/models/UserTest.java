package sysc4806.project24.mini_survey_monkey.models;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testEquals() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");

        assertNotEquals(user1, user2);

        user2.setUsername("user1");

        assertNotEquals(user1, user2);

        user2.setPassword("password1");

        assertEquals(user1, user2);

        user2.setPassword("password2");
    }

    @Test
    void testHashCode() {
        String username = "johndoe";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        assertEquals(Objects.hash(username, password), user.hashCode());
    }
}