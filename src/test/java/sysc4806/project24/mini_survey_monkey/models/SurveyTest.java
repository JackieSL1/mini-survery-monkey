package sysc4806.project24.mini_survey_monkey.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SurveyTest {

    @Test
    void testToString() {
        Survey survey = new Survey();

        survey.setId(1);
        survey.setTitle("Test Survey");
        User user = new User();
        survey.setUser(user);
        List<Question> questions = new ArrayList<>();
        survey.setQuestions(questions);
        survey.setState(State.OPEN);

        assertEquals("Survey{id=1, title='Test Survey', user=" + user.toString() + ", questions=" +
                questions.toString() + ", state=" + State.OPEN.toString() + "}", survey.toString());
    }
}