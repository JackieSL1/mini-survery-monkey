package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DatabaseTest {

    @Autowired
    private UserRepository repository;

    @Test
    void greetingShouldReturnDefaultMessage() {
        assertThat(repository.findAll()).isEmpty();

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        repository.save(user);
        assertThat(repository.findAll()).contains(user);
    }
}