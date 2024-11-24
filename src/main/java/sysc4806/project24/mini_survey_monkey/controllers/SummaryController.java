package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project24.mini_survey_monkey.models.CommentQuestion;
import sysc4806.project24.mini_survey_monkey.models.Question;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.QuestionRepository;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.List;

@Controller
public class SummaryController {

    private final SurveyRepository surveyRepository;

    public SummaryController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/summary/{surveyID}")
    public String viewSurvey(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);

        // Retrieve all CommentQuestions for this survey
        List<CommentQuestion> commentQuestions = survey.getQuestions().stream()
                .filter(question -> question instanceof CommentQuestion)
                .map(question -> (CommentQuestion) question)
                .toList();

        model.addAttribute("survey", survey);

        return "summary";
    }

    @PostMapping("/summary/{surveyID}/close")
    public String closeSurvey(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        survey.setState(State.CLOSED);

        surveyRepository.save(survey);

        return "redirect:/summary/" + surveyID;
    }
}
