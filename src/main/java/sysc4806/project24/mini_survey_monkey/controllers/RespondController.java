package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project24.mini_survey_monkey.ResponseFormData;
import sysc4806.project24.mini_survey_monkey.models.CommentResponse;
import sysc4806.project24.mini_survey_monkey.models.Question;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class RespondController {
    private final SurveyRepository surveyRepository;

    public RespondController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/r/{surveyID}")
    public String respond(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        model.addAttribute("survey", survey);
        // TODO: Handle form having no questions
        ResponseFormData formData = new ResponseFormData();
        formData.setQuestion(survey.getQuestions().get(0));
        model.addAttribute("formData", formData);

        return "respond";
    }

    @PostMapping("/r/{surveyID}/submit")
    public String submit(@PathVariable("surveyID") int surveyID, @ModelAttribute ResponseFormData formData) {
        Survey survey = surveyRepository.findById(surveyID);
        CommentResponse response = new CommentResponse();
        response.setText(formData.getResponseText());

        Question question = survey.getQuestions().get(0);
        question.getResponses().add(response);
        response.setQuestion(question);

        surveyRepository.save(survey);

        return "redirect:/r/" + surveyID;
    }
}
