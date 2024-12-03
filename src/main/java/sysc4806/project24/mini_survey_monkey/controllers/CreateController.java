package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sysc4806.project24.mini_survey_monkey.Constant;
import sysc4806.project24.mini_survey_monkey.models.*;
import sysc4806.project24.mini_survey_monkey.repositories.QuestionRepository;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

@Controller
public class CreateController {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public CreateController(
            SurveyRepository surveyRepository,
            QuestionRepository questionRepository,
            UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String create(@CookieValue(value= Constant.CookieValue.USERNAME, defaultValue=Constant.GUEST_USERNAME) String username) {
        // If cookie is missing, assume that a guest is logged in.
        //
        // Check if guest is creating survey.
        if (username.equals(Constant.GUEST_USERNAME)) {
            // Create guest account if it doesn't exist.
            if (userRepository.findByUsername(username) == null) {
                userRepository.save(new User(Constant.GUEST_USERNAME, Constant.GUEST_USERNAME));
            }
        }

        Survey newSurvey = new Survey();
        newSurvey.setUser(userRepository.findByUsername(username));
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

    @PostMapping("/create/{surveyID}/question/add")
    public String addQuestion(
            @PathVariable("surveyID") int surveyID,
            @RequestParam("type") String type,
            @RequestParam(value = "questionText", required = false) String questionText,
            @RequestParam(value = "options", required = false) List<String> options,
            @RequestParam(value = "minValue", required = false) Integer minValue,
            @RequestParam(value = "maxValue", required = false) Integer maxValue) {

        Survey survey = surveyRepository.findById(surveyID);

        if (survey == null) {
            throw new IllegalArgumentException("Survey not found");
        }

        // Add question based on type
        if ("multiple-choice".equals(type)) {
            MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion();
            mcQuestion.setQuestion(questionText != null ? questionText : "New Multiple Choice Question");
            mcQuestion.setOptions(options != null ? options : Arrays.asList("Option 1", "Option 2"));
            survey.addQuestion(mcQuestion);
        } else if ("scale".equals(type)) {
            ScaleQuestion scaleQuestion = new ScaleQuestion();
            scaleQuestion.setQuestion(questionText != null ? questionText : "New Scale Question");
            scaleQuestion.setMinValue(minValue != null ? minValue : 1);
            scaleQuestion.setMaxValue(maxValue != null ? maxValue : 10);
            survey.addQuestion(scaleQuestion);
        }

        surveyRepository.save(survey);

        return "redirect:/create/" + surveyID;
    }
    @PostMapping("/create/{surveyID}/question/{questionID}/option/update")
    public String updateOption(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("optionIndex") int optionIndex,
            @RequestParam("updatedOption") String updatedOption) {

        // Fetch the question by ID
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionRepository.findById(questionID);

        // Update the specific option
        List<String> options = question.getOptions();
        if (optionIndex >= 0 && optionIndex < options.size()) {
            options.set(optionIndex, updatedOption);
            question.setOptions(options); // Update the question's options
            questionRepository.save(question); // Save changes to the database
        }

        return "redirect:/create/" + surveyID; // Redirect back to the survey edit page
    }
    @PostMapping("/create/{surveyID}/question/{questionID}/option/delete")
    public String deleteOption(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("optionIndex") int optionIndex) {

        // Fetch the question by ID
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionRepository.findById(questionID);

        // Remove the option at the specified index
        List<String> options = question.getOptions();
        if (optionIndex >= 0 && optionIndex < options.size()) {
            options.remove(optionIndex); // Remove the option from the list
            question.setOptions(options); // Update the question's options
            questionRepository.save(question); // Save the changes to the database
        }

        return "redirect:/create/" + surveyID; // Redirect back to the survey edit page
    }
    @PostMapping("/create/{surveyID}/question/{questionID}/option/add")
    public String addOption(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("newOption") String newOption) {

        // Fetch the question by ID
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionRepository.findById(questionID);

        // Add the new option to the list of options
        List<String> options = question.getOptions();
        options.add(newOption); // Add the new option to the list

        question.setOptions(options); // Update the question's options list
        questionRepository.save(question); // Save the changes to the database

        return "redirect:/create/" + surveyID; // Redirect back to the survey edit page
    }

    @PostMapping("/create/{surveyID}/question/{questionID}/update-scale")
    public String editScaleQuestion(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID,
            @RequestParam("newQuestionText") String newQuestionText,
            @RequestParam(value = "minValue", required = false) Integer minValue,
            @RequestParam(value = "maxValue", required = false) Integer maxValue) {

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
