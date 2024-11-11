package sysc4806.project24.mini_survey_monkey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/**
 * Health check with bananas.
 */
public class BananaController {

    @GetMapping("/banana")
    public String banana(Model model) {
        return "banana";
    }
}
