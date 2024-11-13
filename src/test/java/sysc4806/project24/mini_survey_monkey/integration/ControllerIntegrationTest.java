package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.models.State;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePageDisplaysSurveys() throws Exception {
        // Perform GET request on /home
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome home!")))
                .andExpect(content().string(containsString("My Surveys")))
                .andExpect(content().string(containsString("TITLE")))
                .andExpect(content().string(containsString("STATUS")));
    }

    @Test
    public void testCreateSurveyAndRedirectToEditPage() throws Exception {
        // Simulate creating a survey
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())  // Expect a redirection status
                .andExpect(header().string("Location", startsWith("/create/")));  // Redirect to /create/{id}
    }

    @Test
    public void testEditSurveyPageDisplaysCorrectSurvey() throws Exception {
        // First, create a survey to ensure it exists in the repository
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", matchesPattern("/create/\\d+")))
                .andDo(result -> {
                    String location = result.getResponse().getHeader("Location");
                    int surveyId = Integer.parseInt(location.split("/")[2]);

                    // Then, access the edit page for the created survey
                    mockMvc.perform(get("/create/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("create"))
                            .andExpect(model().attributeExists("survey"))
                            .andExpect(model().attribute("survey", hasProperty("title", equalTo("Untitled"))))
                            .andExpect(model().attribute("survey", hasProperty("state", equalTo(State.DRAFT))));
                });
    }

    @Test
    public void testSaveUpdatedSurveyTitle() throws Exception {
        // First, create a survey to have a survey ID to work with
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", matchesPattern("/create/\\d+")))
                .andDo(result -> {
                    String location = result.getResponse().getHeader("Location");
                    int surveyId = Integer.parseInt(location.split("/")[2]);

                    // Now, update the title of the created survey
                    mockMvc.perform(post("/create/" + surveyId + "/update")
                                    .param("title", "Updated Survey Title"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(header().string("Location", "/create/" + surveyId));

                    // Verify that the updated title is saved
                    mockMvc.perform(get("/create/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("create"))
                            .andExpect(model().attribute("survey", hasProperty("title", equalTo("Updated Survey Title"))));
                });
    }

    /**
     * Tests if root url redirects to correct page.
     */
    @Test
    public void testValidRootRedirection() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", startsWith("/home")));
    }
}

