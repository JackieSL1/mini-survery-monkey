package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            RedirectAttributes redirectAttributes,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        boolean authenticated = false;

        if (userRepository.findByUsername(username) == null) {
            model.addAttribute("error", "ERROR: Username not found.");
        } else if (!userRepository.findByUsername(username).getPassword().equals(password)) {
            model.addAttribute("error", "ERROR: Invalid password.");
        } else {
            authenticated = true;
        }

        if (authenticated) {
            redirectAttributes.addAttribute("username", username);
            return "redirect:/home";
        } else {
            return "login";
        }
    }
}
