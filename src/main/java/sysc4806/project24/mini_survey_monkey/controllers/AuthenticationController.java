package sysc4806.project24.mini_survey_monkey.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sysc4806.project24.mini_survey_monkey.models.RegisterDTO;
import sysc4806.project24.mini_survey_monkey.services.AuthenticationService;

@Controller
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/auth/register")
    public String registerUser(@ModelAttribute RegisterDTO registerDTO) {
        authenticationService.registerUser(registerDTO.getUsername(), registerDTO.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}


