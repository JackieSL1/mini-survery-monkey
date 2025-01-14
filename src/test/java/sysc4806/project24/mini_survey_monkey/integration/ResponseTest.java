package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sysc4806.project24.mini_survey_monkey.integration.TestHelpers.getQuestionID;

@SpringBootTest
@AutoConfigureMockMvc
public class ResponseTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests responding to a survey
     */
    @Test
    public void testResponseSubmission() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create")).andDo(result -> {
            String location = result.getResponse().getHeader("Location");
            int surveyId = Integer.parseInt(location.split("/")[2]);

            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());

            mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New Survey Title"));

            // /r/1 should throw a 404 while the survey is a Draft
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());

            mockMvc.perform(post("/create/" + surveyId + "/question"));

            String responseHTML =
                    mockMvc.perform(get("/create/" + surveyId)).andReturn().getResponse().getContentAsString();
            int questionID = getQuestionID(surveyId, responseHTML);

            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionID + "/update").param(
                    "newQuestionText", "This is question 1"));
            mockMvc.perform(post("/create/" + surveyId + "/open"));

            // /r/1 should return 200 now that the survey is Open
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().isOk()).andExpect(model().attribute("survey",
                    hasProperty("title", equalTo("New Survey Title")))).andExpect(model().attribute("survey",
                    hasProperty("questions", hasSize(1))));

            mockMvc.perform(post("/r/" + surveyId + "/submit")
                    .param("responseInputs[0].responseText", "Hello world!")
            ).andExpect(status().is3xxRedirection());

            // /r/1 should result in a closed survey page
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId))
                    .andExpect(status().isOk())
                    .andExpect(view().name("closed"));
        });
    }

    /**
     * Tests responding to a survey with multiple comment questions and a scale question
     */
    @Test
    public void testMultipleResponseSubmissions() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create")).andDo(result -> {
            String location = result.getResponse().getHeader("Location");
            int surveyId = Integer.parseInt(location.split("/")[2]);

            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());

            mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "New Survey Title"));

            // /r/1 should throw a 404 while the survey is a Draft
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());

            mockMvc.perform(post("/create/" + surveyId + "/question"));
            mockMvc.perform(post("/create/" + surveyId + "/question"));
            String responseHTML =
                    mockMvc.perform(get("/create/" + surveyId)).andReturn().getResponse().getContentAsString();
            int questionId = getQuestionID(surveyId, responseHTML);

            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionId + "/update").param(
                    "newQuestionText", "This is question 1"));
            mockMvc.perform(post("/create/" + surveyId + "/question"));
            mockMvc.perform(post("/create/" + surveyId + "/question/" + (questionId + 1) + "/update").param(
                    "newQuestionText", "This is question 2"));

            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionId + "/update-scale")
                            .param("newQuestionText", "This is question 3") // Update the question text
                            .param("minValue", "0") // Update the minimum scale value
                            .param("maxValue", "10") // Update the maximum scale value
                    )
                    .andExpect(status().is3xxRedirection()) // Check for redirection status
                    .andExpect(redirectedUrl("/create/" + surveyId)); // Verify redirection URL


            mockMvc.perform(post("/create/" + surveyId + "/open"));

            // /r/1 should return 200 now that the survey is Open
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().isOk()).andExpect(model().attribute("survey",
                    hasProperty("title", equalTo("New Survey Title")))).andExpect(model().attribute("survey",
                    hasProperty("questions", hasSize(3))));

            mockMvc.perform(post("/r/" + surveyId + "/submit")
                    .param("responseInputs[0].responseText", "Hello world!")
                    .param("responseInputs[1].responseText", "Hello world, 2!")
                    .param("responseInputs[2].selectedValue", "5")
            ).andExpect(status().is3xxRedirection());

            // /r/1 should result in a closed survey page
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId))
                    .andExpect(status().isOk())
                    .andExpect(view().name("closed"));

            // /r/1 posting should be redirect to the closed screen
            mockMvc.perform(post("/r/" + surveyId + "/submit")
                    .param("responseInputs[0].responseText", "Hello world!")
                    .param("responseInputs[1].responseText", "Hello world, 2!")
                    .param("responseInputs[2].selectedValue", "5"))
                    .andExpect(status().is3xxRedirection());
        });
    }
    /**
     * Tests responding to a survey with a multiple-choice question
     */
    @Test
    public void testMultipleChoiceResponseSubmission() throws Exception {
        // Create a survey
        mockMvc.perform(post("/create")).andDo(result -> {
            String location = result.getResponse().getHeader("Location");
            int surveyId = Integer.parseInt(location.split("/")[2]);

            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());

            mockMvc.perform(post("/create/" + surveyId + "/update").param("title", "MC Survey Test"));

            mockMvc.perform(post("/create/" + surveyId + "/question"));
            String responseHTML =
                    mockMvc.perform(get("/create/" + surveyId)).andReturn().getResponse().getContentAsString();
            int questionID = getQuestionID(surveyId, responseHTML);
            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionID + "/update")
                    .param("newQuestionText", "Choose an option")
                    .param("newQuestionType", "MultipleChoiceQuestion")
                    .param("options[0]", "Option 1")
                    .param("options[1]", "Option 2")
                    .param("options[2]", "Option 3"));

            mockMvc.perform(post("/create/" + surveyId + "/open"));

            mockMvc.perform(get("/r/" + surveyId))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("survey",
                            hasProperty("title", equalTo("MC Survey Test"))))
                    .andExpect(model().attribute("survey",
                            hasProperty("questions", hasSize(1))))
                    .andExpect(model().attribute("survey",
                            hasProperty("questions", hasItem(
                                    hasProperty("question", equalTo("Choose an option"))))));


            mockMvc.perform(post("/r/" + surveyId + "/submit")
                            .param("responseInputs[0].responseText", "Option 2"))
                    .andExpect(status().is3xxRedirection());
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId))
                    .andExpect(status().isOk())
                    .andExpect(view().name("closed"));
            mockMvc.perform(post("/r/" + surveyId + "/submit")
                            .param("responseInputs[0].responseText", "Option 3"))
                    .andExpect(status().is3xxRedirection());
        });
    }
}
