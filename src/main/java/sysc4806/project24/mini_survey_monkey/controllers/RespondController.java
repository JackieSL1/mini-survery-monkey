package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class RespondController {
    private final SurveyRepository surveyRepository;

    public RespondController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/r/1")
    public String fillIn() {

        return "respond";
    }
}
