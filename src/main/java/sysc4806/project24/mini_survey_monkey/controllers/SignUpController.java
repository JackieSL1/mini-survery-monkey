package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

@Controller
public class SignUpController {

    private final UserRepository userRepository;

    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String displayForm(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String createAccount(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password
            ) {
        User u = new User(username, password);

        if (userRepository.findByUsername(username) != null) {
            return "signup";
        } else {
            userRepository.save(u);
            return "redirect:/login";
        }
    }
}
