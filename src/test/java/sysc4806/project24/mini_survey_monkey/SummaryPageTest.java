package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.controllers.SummaryController;
import sysc4806.project24.mini_survey_monkey.models.CommentQuestion;
import sysc4806.project24.mini_survey_monkey.models.CommentResponse;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SummaryController.class)
public class SummaryPageTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyRepository surveyRepository;

    @Test
    public void testSummaryPageDisplaysCollectedResponses() throws Exception {
        Survey survey = new Survey();
        survey.setId(1);
        survey.setTitle("Sample Survey");
        survey.setState(State.OPEN);

        // Question 1
        CommentQuestion question = new CommentQuestion();
        question.setId(1);
        question.setQuestion("What is your favorite color?");
        question.setSurvey(survey);

        CommentResponse response1 = new CommentResponse();
        response1.setId(1);
        response1.setText("Blue");
        response1.setQuestion(question);

        CommentResponse response2 = new CommentResponse();
        response2.setId(2);
        response2.setText("Green");
        response2.setQuestion(question);

        question.setCommentResponses(Arrays.asList(response1, response2));
        survey.setQuestions(List.of(question));

        // Mock repository behavior
        when(surveyRepository.findById(1)).thenReturn(survey);

        // Perform the GET request and verify the HTML content
        mockMvc.perform(get("/summary/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("summary"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("What is your favorite color?")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Blue")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Green")));
    }

}
