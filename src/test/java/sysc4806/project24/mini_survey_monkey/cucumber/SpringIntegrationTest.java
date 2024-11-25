package sysc4806.project24.mini_survey_monkey.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Common test functions to be reused in other test classes.
 *
 * Examples functions could be:
 * - execute get
 * - create survey
 * - log in
 * - sign up new user
 */
@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class SpringIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    protected void executeGet(String url) throws Exception {
        mockMvc.perform(get(url));
    }
}
