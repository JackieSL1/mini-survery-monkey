package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class RespondController {
    private final SurveyRepository surveyRepository;

    public FillInController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/r/1")
    public String fillIn() {

        return "respond";
    }
}
