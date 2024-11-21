package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sysc4806.project24.mini_survey_monkey.models.SharingLink;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.SharingLinkRepository;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class CollectController {

    private final SurveyRepository surveyRepository;

    private final SharingLinkRepository sharingLinkRepository;

    public CollectController(SurveyRepository surveyRepository, SharingLinkRepository sharingLinkRepository) {
        this.surveyRepository = surveyRepository;
        this.sharingLinkRepository = sharingLinkRepository;
    }

    @GetMapping("/collect/{surveyID}")
    public String collect(@PathVariable("surveyID") int surveyID, Model model) {
        Survey survey = surveyRepository.findById(surveyID);
        model.addAttribute("survey", survey);

        return "collect";
    }
}
