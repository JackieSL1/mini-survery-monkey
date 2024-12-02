package sysc4806.project24.mini_survey_monkey.integration;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.Constant;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.User;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sysc4806.project24.mini_survey_monkey.integration.TestHelpers.getQuestionID;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePageDisplaysSurveys() throws Exception {
        // Perform GET request on /home
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome guest!")))
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
                            .andExpect(model().attribute("survey", hasProperty("title", equalTo("Updated Survey " +
                                    "Title"))));
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

    /**
     * Tests when opening a draft survey
     */
    @Test
    public void testOpeningSurvey() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create")).andDo(result -> {
            String location = result.getResponse().getHeader("Location");
            int surveyId = Integer.parseInt(location.split("/")[2]);

            // /summary/id should initially be invalid
            mockMvc.perform(get("/summary/" + surveyId))
                    .andExpect(status().is4xxClientError());

            // Now, update the title of the created survey
            mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New Survey Title"));

            // Now, open the survey, and confirm a redirect to /summary
            mockMvc.perform(post("/create/" + surveyId + "/open"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", "/collect/" + surveyId));

            // Verify that /create/id and /create/id/update return 404s
            mockMvc.perform(post("/create/" + surveyId))
                    .andExpect(status().is4xxClientError());
            mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New Survey Title"))
                    .andExpect(status().is4xxClientError());

            // /create should still work
            mockMvc.perform(post("/create"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(header().string("Location", startsWith("/create/")));

            // /summary/id should now be valid
            mockMvc.perform(get("/summary/" + surveyId))
                    .andExpect(status().isOk())
                    .andExpect(view().name("summary"))
                    .andExpect(model().attribute("survey", hasProperty("title", equalTo("New Survey Title"))))
                    .andExpect(model().attribute("survey", hasProperty("state", equalTo(State.OPEN))));
        });
    }

    /**
     * Tests closing an open survey
     */
    @Test
    public void testClosingSurvey() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create")).andDo(
                result -> {
                    String location = result.getResponse().getHeader("Location");
                    int surveyId = Integer.parseInt(location.split("/")[2]);

                    // /summary/id/close should initially be invalid
                    mockMvc.perform(post("/summary/" + surveyId + "/close"))
                            .andExpect(status().is4xxClientError());

                    // Now, update the title of the created survey
                    mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New Survey Title"));

                    // Open the survey
                    mockMvc.perform(post("/create/" + surveyId + "/open"));

                    // Close the survey, and confirm a redirect
                    mockMvc.perform(post("/summary/" + surveyId + "/close"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(header().string("Location", "/summary/" + surveyId));

                    // Verify that /create/id and /create/id/update return 404s
                    mockMvc.perform(post("/create/" + surveyId))
                            .andExpect(status().is4xxClientError());
                    mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New New Survey Title"))
                            .andExpect(status().is4xxClientError());

                    // /create should still work
                    mockMvc.perform(post("/create"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(header().string("Location", startsWith("/create/")));

                    // /summary/id should still be valid
                    mockMvc.perform(get("/summary/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("summary"))
                            .andExpect(model().attribute("survey", hasProperty("title", equalTo("New Survey Title"))))
                            .andExpect(model().attribute("survey", hasProperty("state", equalTo(State.CLOSED))));

                    // /summary/id/close should leave the survey in a closed state
                    mockMvc.perform(post("/summary/" + surveyId + "/close"))
                            .andExpect(status().is3xxRedirection());

                    mockMvc.perform(get("/summary/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("summary"))
                            .andExpect(model().attribute("survey", hasProperty("title", equalTo("New Survey Title"))))
                            .andExpect(model().attribute("survey", hasProperty("state", equalTo(State.CLOSED))))
                            .andDo(result1 -> {
                                String linkUrl = "/home";
                                mockMvc.perform(get(linkUrl))
                                        .andExpect(status().isOk())
                                        .andExpect(view().name("home"));
                            });
                });
    }

    @Test
    public void testAddMultipleChoiceQuestion() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", matchesPattern("/create/\\d+")))
                .andDo(result -> {
                    String location = result.getResponse().getHeader("Location");
                    int surveyId = Integer.parseInt(location.split("/")[2]);

                    // Add a multiple-choice question to the survey
                    mockMvc.perform(post("/create/" + surveyId + "/question/add")
                                    .param("questionText", "What is your favorite color?")
                                    .param("options", "Red", "Blue", "Green")
                                    .param("type", "multiple-choice"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(header().string("Location", "/create/" + surveyId));

                    // Verify the question is added with the correct options
                    mockMvc.perform(get("/create/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("create"))
                            .andExpect(model().attributeExists("survey"))
                            .andExpect(model().attribute("survey", hasProperty("questions", hasSize(1))))
                            .andExpect(model().attribute("survey", hasProperty("questions", hasItem(
                                    allOf(
                                            hasProperty("question", equalTo("What is your favorite color?")),
                                            hasProperty("options", containsInAnyOrder("Red", "Blue", "Green"))
                                    )
                            ))));
                });
    }

    @Test
    public void testDeleteMultipleChoiceQuestion() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", matchesPattern("/create/\\d+")))
                .andDo(result -> {
                    String location = result.getResponse().getHeader("Location");
                    int surveyId = Integer.parseInt(location.split("/")[2]);

                    // Add a multiple-choice question
                    mockMvc.perform(post("/create/" + surveyId + "/question/add")
                                    .param("questionText", "What is your favorite color?")
                                    .param("options", "Red", "Blue", "Green")
                                    .param("type", "multiple-choice"))
                            .andExpect(status().is3xxRedirection());

                    // Delete the question
                    String responseHTML =
                            mockMvc.perform(get("/create/" + surveyId)).andReturn().getResponse().getContentAsString();
                    int questionID = getQuestionID(surveyId, responseHTML);

                    mockMvc.perform(post("/create/" + surveyId + "/question/" + questionID + "/delete"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(header().string("Location", "/create/" + surveyId));

                    // Verify the question is deleted
                    mockMvc.perform(get("/create/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(model().attribute("survey", hasProperty("questions", hasSize(0))));
                });
    }

    @Test
    public void testGuestBindsToNewSurvey() throws Exception {
        System.out.println("INFO: Testing if guest binds to new survey ...");

        // Create new survey as guest
        int surveyId = createSurvey();
        System.out.println("INFO: Created survey with ID: " + surveyId);

        String title = updateSurveyTitle(surveyId, null);

        // Check if new survey displays on guest homepage
        htmlContains("/home", Constant.GUEST_USERNAME, null);
        htmlContains("/home", title, null);
    }

    @Test
    public void testUserBindsToNewSurvey() throws Exception {
        // Setup
        User user = new User();
        user.setUsername("jackie");
        user.setPassword("i<3braeden");
        signUpNewUser(user);
        loginExistingUser(user);
        Cookie cookie = new Cookie(Constant.CookieKey.USERNAME, user.getUsername());

        // Create new survey and update title as logged-in user
        int surveyId = createSurvey();
        String title = updateSurveyTitle(surveyId, null);

        // Check if new survey displays on user homepage
        htmlContains("/home", user.getUsername(), cookie);
        htmlContains("/home", title, cookie);
    }
}

