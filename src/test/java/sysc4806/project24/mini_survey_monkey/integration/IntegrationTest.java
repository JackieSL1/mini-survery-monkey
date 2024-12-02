package sysc4806.project24.mini_survey_monkey.integration;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.models.User;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * Creates a survey for the currently logged-in user.
     *
     * @return ID of created survey.
     * @throws Exception
     */
    protected int createSurvey() throws Exception {
        // AtomicInteger allows a variable to be set and returned from a lambda expression.
        //
        // Typically, AtomicInteger is used for counters and should not be used to replace
        // the class Integer. However, because our only known implementation of retrieving
        // the surveyId is with a lambda expression, AtomicInteger is the best we can do.
        //
        // @author Braeden
        AtomicInteger surveyId = new AtomicInteger();

        mockMvc.perform(post("/create")).andDo(
                result -> {
                    String location = result.getResponse().getHeader("Location");
                    surveyId.set(Integer.parseInt(location.split("/")[2]));
                }
        );
        return surveyId.get();
    }

    /**
     * Updates survey with a new title. Randomly generates a new title
     * if a null title is passed.
     *
     * @param surveyId ID of survey to update.
     * @param title If null, randomly generates new title.
     * @return Title of updated survey.
     * @throws Exception
     */
    protected String updateSurveyTitle(int surveyId, String title) throws Exception {
        String newTitle = title;

        if (title == null) {
            Random rand = new Random();
            newTitle = Integer.toString(rand.nextInt());
        }

        mockMvc.perform(post("/create/" + surveyId + "/update")
                        .param("title", newTitle))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/create/" + surveyId));

        return newTitle;
    }

    /**
     * Checks if the HTML contains a specified text.
     *
     * @param urlTemplate URL to GET HTML from. (Eg. "/home", "/create/1")
     * @param text Text to search for.
     * @param cookie Cookie to use in request. If null, generates a default cookie.
     * @throws Exception When html does not contain text.
     */
    protected void htmlContains(String urlTemplate, String text, Cookie cookie) throws Exception {
        if (cookie == null) { cookie = createDefaultCookie();}

        mockMvc.perform(get("/home")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(text)));
    }

    protected void signUpNewUser(User user) throws Exception {
        mockMvc.perform(post("/signup")
                .param("username", user.getUsername())
                .param("password", user.getPassword()));
    }

    protected void loginExistingUser(User user) throws Exception {
        mockMvc.perform(post("/login")
                    .param("username", user.getUsername())
                    .param("password", user.getPassword()))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/home"));}

    private Cookie createDefaultCookie() {
        return new Cookie("JSESSIONID", "");
    }
}
