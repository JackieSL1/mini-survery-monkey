package sysc4806.project24.mini_survey_monkey.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project24.mini_survey_monkey.Constant;
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

    @PostMapping("/login")
    public String authenticate(
            Model model,
            HttpServletResponse response,
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
            Cookie cookie = new Cookie(Constant.CookieValue.USERNAME, username);
            cookie.setPath("/"); // sends cookie to specified URL and all its subdirectories
            response.addCookie(cookie);
            return "redirect:/home";
        } else {
            return "login";
        }
    }
}
