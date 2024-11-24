package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
            Pattern pattern = Pattern.compile("/create/" + surveyId + "/question/(\\d)/update");
            Matcher matcher = pattern.matcher(responseHTML);
            assert matcher.find();
            String questionID = matcher.group(1);

            mockMvc.perform(post("/create/" + surveyId + "/question/" + questionID + "/update").param("newQuestionText",
                    "This is question 1"));
            mockMvc.perform(post("/create/" + surveyId + "/open"));

            // /r/1 should return 200 now that the survey is Open
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().isOk()).andExpect(model().attribute("survey",
                    hasProperty("title", equalTo("New Survey Title")))).andExpect(model().attribute("survey",
                    hasProperty("questions", hasSize(1))));

            mockMvc.perform(post("/r/" + surveyId + "/submit").param("text", "This is my response")).andExpect(status().is3xxRedirection());

            // /r/1 should throw a 404 when the survey is Closed
            mockMvc.perform(post("/summary/" + surveyId + "/close"));
            mockMvc.perform(get("/r/" + surveyId)).andExpect(status().is4xxClientError());
        });
    }
}