package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project24.mini_survey_monkey.models.*;
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
        model.addAttribute("survey", survey);

        return "create";
    }

    @PostMapping("/create/{surveyID}/update")
    public String save(@PathVariable("surveyID") int surveyID, @ModelAttribute Survey newSurvey) {
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

        return "redirect:/collect/" + surveyID;
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

    @PostMapping("/create/{surveyID}/scale-question")
    public String addScaleQuestion(@PathVariable("surveyID") int surveyID) {
        // Retrieve the survey by ID
        Survey survey = surveyRepository.findById(surveyID);

        if (survey == null) {
            throw new IllegalArgumentException("Survey not found");
        }

        // Create a new ScaleQuestion with default values
        ScaleQuestion newScaleQuestion = new ScaleQuestion();
        newScaleQuestion.setQuestion("New Scale Question"); // Default question text
        newScaleQuestion.setMinValue(1); // Default minimum scale value
        newScaleQuestion.setMaxValue(10); // Default maximum scale value

        // Associate the question with the survey
        survey.addQuestion(newScaleQuestion);

        // Save the survey (cascade saves the new question)
        surveyRepository.save(survey);

        return "redirect:/create/" + surveyID;
    }

    @PostMapping("/create/{surveyID}/question/{questionID}/update-scale")
    public String editScaleQuestion(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("newQuestionText") String newQuestionText,
            @RequestParam(value = "minValue", required = false) Integer minValue,
            @RequestParam(value = "maxValue", required = false) Integer maxValue,
            @RequestParam(value = "minLabel", required = false) String minLabel,
            @RequestParam(value = "maxLabel", required = false) String maxLabel) {

        Question question = questionRepository.findById(questionID);

        if (question instanceof ScaleQuestion) {
            ScaleQuestion scaleQuestion = (ScaleQuestion) question;
            scaleQuestion.setQuestion(newQuestionText);
            if (minValue != null) scaleQuestion.setMinValue(minValue);
            if (maxValue != null) scaleQuestion.setMaxValue(maxValue);
        } else {
            question.setQuestion(newQuestionText);
        }

        questionRepository.save(question);

        return "redirect:/create/" + surveyID;
    }
}
