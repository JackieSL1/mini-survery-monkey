package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sysc4806.project24.mini_survey_monkey.models.CommentQuestion;
import sysc4806.project24.mini_survey_monkey.models.Question;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.QuestionRepository;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class CreateController {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public CreateController(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping("/create")
    public String create() {
        Survey newSurvey = new Survey();
        newSurvey.setTitle(Survey.DEFAULT_TITLE);
        newSurvey.setState(State.DRAFT);

        Survey savedSurvey = surveyRepository.save(newSurvey);

        return "redirect:/create/" + savedSurvey.getId();
    }

    @GetMapping("/create/{surveyID}")
    public String edit(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        if (survey == null || survey.getState() != State.DRAFT) {
            // TODO: 404 probably isn't the best choice
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("survey", survey);

        return "create";
    }

    @GetMapping("/survey/{surveyID}")
    public String viewSurvey(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        // Draft surveys shouldn't be viewed
        if (survey == null || survey.getState() == State.DRAFT) {
            // TODO: 404 probably isn't the best choice
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("survey", survey);

        return "survey";
    }

    @PostMapping("/create/{surveyID}/update")
    public String save(@PathVariable("surveyID") int surveyID, @ModelAttribute Survey newSurvey) {
        // TODO: Validate that survey is in a Draft state (may need to do this for other endpoints too)
        // The best option may be to place some sort of validation on ALL /create/ endpoints - not sure how to do this
        // Could be with spring authentication?
        Survey survey = surveyRepository.findById(surveyID);
        survey.setTitle(newSurvey.getTitle());

        surveyRepository.save(survey);

        return "redirect:/create/" + surveyID;
    }

    @PostMapping("/create/{surveyID}/open")
    public String open(@PathVariable("surveyID") int surveyID, @ModelAttribute Survey newSurvey) {
        Survey survey = surveyRepository.findById(surveyID);
        survey.setState(State.OPEN);

        surveyRepository.save(survey);

        return "redirect:/survey/" + surveyID;
    }

    @PostMapping("/create/{surveyID}/question")
    public String addQuestion(@PathVariable("surveyID") int surveyID) {
        Survey survey = surveyRepository.findById(surveyID);
        CommentQuestion newQuestion = new CommentQuestion();
        survey.addQuestion(newQuestion);

        surveyRepository.save(survey);

        return "redirect:/create/" + surveyID;
    }

    @PostMapping("/create/{surveyID}/question/{questionID}/update")
    public String editQuestion(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("newQuestionText") String newQuestionText) {
        Question question = questionRepository.findById(questionID);
        question.setQuestion(newQuestionText);

        questionRepository.save(question);

        return "redirect:/create/" + surveyID;
    }

    @PostMapping("/create/{surveyID}/question/{questionID}/delete")
    public String deleteQuestion(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID) {

        questionRepository.deleteById(questionID);
        return "redirect:/create/" + surveyID;
    }
}
