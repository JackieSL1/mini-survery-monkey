package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project24.mini_survey_monkey.ResponseForm;
import sysc4806.project24.mini_survey_monkey.ResponseFormInput;
import sysc4806.project24.mini_survey_monkey.models.*;
import sysc4806.project24.mini_survey_monkey.repositories.CommentResponseRepository;
import sysc4806.project24.mini_survey_monkey.repositories.QuestionRepository;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RespondController {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public RespondController(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/r/{surveyID}")
    public String respond(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        model.addAttribute("survey", survey);
        if (survey.getState().equals(State.CLOSED)) {
           return "closed";
        }

        // TODO: Handle form having no questions
        ResponseForm formData = new ResponseForm();
        formData.setResponseInputs(new ArrayList<>());
        for (Question _question: survey.getQuestions()) {
            ResponseFormInput input = new ResponseFormInput();
            input.setResponseText("");
            formData.getResponseInputs().add(input);
        }
        model.addAttribute("formData", formData);

        return "respond";
    }

    @PostMapping("/r/{surveyID}/submit")
    public String submit(@PathVariable("surveyID") int surveyID, @ModelAttribute ResponseForm formData) {
        List<ResponseFormInput> response = formData.getResponseInputs();
        Survey survey = surveyRepository.findById(surveyID);
        if (survey.getState().equals(State.CLOSED)) {
            return "redirect:/r/" + surveyID;
        }

        if (response != null){
            for (int i = 0; i < response.size(); i++) {
                Question question = survey.getQuestions().get(i);
                if (question instanceof CommentQuestion) {
                    CommentResponse commentResponse = new CommentResponse();
                    commentResponse.setText(response.get(i).getResponseText());
                    question.getResponses().add(commentResponse);
                } else if (question instanceof MultipleChoiceQuestion) {
                    // TODO: Implement
                    continue;
                } else if (question instanceof ScaleQuestion) {
                    ScaleResponse scaleResponse = new ScaleResponse();
                    Integer responseValue = response.get(i).getSelectedValue();
                    scaleResponse.setSelectedValue(responseValue);
                    question.getResponses().add(scaleResponse);
                } else {
                    throw new RuntimeException("Unhandled question type: " + question.getClass());
                }

                questionRepository.save(question);
            }
        }

        return "redirect:/r/" + surveyID;
    }
}
