package sysc4806.project24.mini_survey_monkey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

        Question question = questionRepository.findById(questionID).orElse(null);
        if (question != null) {
            question.setQuestion(newQuestionText);
            questionRepository.save(question);
        }

        return "redirect:/create/" + surveyID;
    }
    @PostMapping("/create/{surveyID}/deleteQuestion/{questionID}")
    public String deleteQuestion(
            @PathVariable("surveyID") int surveyID,
            @PathVariable("questionID") int questionID) {

        questionRepository.deleteById(questionID);
        return "redirect:/create/" + surveyID;
    }
}
