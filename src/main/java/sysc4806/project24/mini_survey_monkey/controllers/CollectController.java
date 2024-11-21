package sysc4806.project24.mini_survey_monkey.controllers;

import jakarta.servlet.http.HttpServletRequest;
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
    public String collect(@PathVariable("surveyID") int surveyID, HttpServletRequest request, Model model) {
        // Get the current domain and scheme
        String scheme = request.getScheme(); // http or https
        String serverName = request.getServerName(); // domain or localhost
        int serverPort = request.getServerPort(); // port number
        String contextPath = request.getContextPath(); // app context path, if any

        // Construct the full URL for /r/1
        String fullUrl;
        if (serverPort == 80 || serverPort == 443) {
            fullUrl = scheme + "://" + serverName + contextPath + "/r/1";
        } else {
            fullUrl = scheme + "://" + serverName + ":" + serverPort + contextPath + "/r/1";
        }

        Survey survey = surveyRepository.findById(surveyID);
        model.addAttribute("survey", survey);

        SharingLink sharingLink = new SharingLink();
        sharingLink.setSurvey(survey);
        sharingLink.setLink(fullUrl);
        // sharingLinkRepository.save(sharingLink); for when we want to persist the unique link for each survey in DB

        model.addAttribute("sharingLink", sharingLink);

        return "collect";
    }
}
