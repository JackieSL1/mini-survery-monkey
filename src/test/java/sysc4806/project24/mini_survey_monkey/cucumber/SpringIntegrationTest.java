package sysc4806.project24.mini_survey_monkey.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    protected void createSurveyWithId(int n) throws Exception {
        // WARNING: This method is partially implemented.

        // If survey with id n already exists,
        // then don't create a new survey.
        //
        // Not yet implemented.

        // Else, create a survey with id n.
        //
        // NOTE: The system is not capable of creating surveys
        //   with specified ids at this time.
        //   Testing with Cucumber has revealed this deficiency.
        mockMvc.perform(post("http://localhost:8080/create"))
                .andExpect(status().is3xxRedirection());
    }

    protected void updateSurveyTitle(int surveyID, String title) throws Exception {
        mockMvc.perform(post("/create/" + surveyID + "/update")
                        .param("title", title));
    }

    protected void homepageShouldDisplaySurveyWithTitle(String title) throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(content().string(containsString(title)));
    }
}
