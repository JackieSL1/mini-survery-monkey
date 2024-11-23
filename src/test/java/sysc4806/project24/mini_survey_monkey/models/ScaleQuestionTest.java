package sysc4806.project24.mini_survey_monkey.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sysc4806.project24.mini_survey_monkey.repositories.ScaleQuestionRepository;
import sysc4806.project24.mini_survey_monkey.repositories.ScaleResponseRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScaleQuestionTest {

    @Autowired
    private ScaleQuestionRepository scaleQuestionRepository;

    @Autowired
    private ScaleResponseRepository scaleResponseRepository;

    @Test
    public void testScaleQuestionAndResponse() {
        // Create and save a ScaleQuestion
        ScaleQuestion question = new ScaleQuestion();
        question.setText("How would you rate our service?");
        question.setMinValue(1);
        question.setMaxValue(10);
        scaleQuestionRepository.save(question);

        // Verify the ScaleQuestion is stored
        ScaleQuestion savedQuestion = scaleQuestionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalArgumentException("ScaleQuestion not found"));
        assertEquals("How would you rate our service?", savedQuestion.getText());
        assertEquals(1, savedQuestion.getMinValue());
        assertEquals(10, savedQuestion.getMaxValue());

        // Create and save a ScaleResponse
        ScaleResponse response = new ScaleResponse();
        response.setQuestion(savedQuestion);
        response.setSelectedValue(8);
        scaleResponseRepository.save(response);

        // Verify the ScaleResponse is stored
        List<ScaleResponse> responses = (List<ScaleResponse>) scaleResponseRepository.findAll();
        assertEquals(1, responses.size());
        assertEquals(8, responses.get(0).getSelectedValue());
    }
}