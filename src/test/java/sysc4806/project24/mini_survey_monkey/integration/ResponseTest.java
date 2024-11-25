package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

            // /r/1 should throw a 404 when the survey is Closed
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());
        });
    }

    /**
     * Tests responding to a survey with multiple comment questions
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
            String responseHTML =
                    mockMvc.perform(get("/create/" + surveyId)).andReturn().getResponse().getContentAsString();
            int questionId = getQuestionID(surveyId, responseHTML);

            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionId + "/update").param(
                    "newQuestionText", "This is question 1"));
            mockMvc.perform(post("/create/" + surveyId + "/question"));
            mockMvc.perform(post("/create/" + surveyId + "/question/" + (questionId + 1) + "/update").param(
                    "newQuestionText", "This is question 2"));

            mockMvc.perform(post("/create/" + surveyId + "/open"));

            // /r/1 should return 200 now that the survey is Open
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().isOk()).andExpect(model().attribute("survey",
                    hasProperty("title", equalTo("New Survey Title")))).andExpect(model().attribute("survey",
                    hasProperty("questions", hasSize(2))));

            mockMvc.perform(post("/r/" + surveyId + "/submit")
                    .param("responseInputs[0].responseText", "Hello world!")
                    .param("responseInputs[1].responseText", "Hello world, 2!")
            ).andExpect(status().is3xxRedirection());

            // /r/1 should throw a 404 when the survey is Closed
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());
        });
    }
}
