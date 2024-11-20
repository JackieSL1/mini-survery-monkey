package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    @GetMapping("/signup")
    public String displayForm(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String createAccount(Model model) {
        return "redirect:/login";
    }
}
