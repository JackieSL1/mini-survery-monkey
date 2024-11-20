package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Controller
public class HomeController {

   private final SurveyRepository surveyRepository;

    public HomeController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/home"; // TODO: redirect to login page once implemented
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("surveys", surveyRepository.findAll());
        return "home";
    }
}
