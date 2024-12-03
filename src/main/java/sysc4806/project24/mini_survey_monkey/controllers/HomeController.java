package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import sysc4806.project24.mini_survey_monkey.Constant;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

@Controller
public class HomeController {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public HomeController(SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Model model, @CookieValue(value = Constant.CookieValue.USERNAME, defaultValue =
            Constant.GUEST_USERNAME) String username) {
        model.addAttribute("welcome", "Welcome " + username + "!");

        User user = userRepository.findByUsername(username);
        model.addAttribute("surveys", surveyRepository.findAllByUser(user));

        return "home";
    }
}
