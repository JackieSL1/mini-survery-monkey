package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

@Controller
public class LoginController {

    UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login/authenticate")
    public String authenticate(
            Model model,
            @RequestParam("username") String username) {
        if (userRepository.findByUsername(username) == null) {
            model.addAttribute("error", "ERROR: Invalid username.");
            return "login";
        } else {
            return "redirect:/home";
        }
    }
}
