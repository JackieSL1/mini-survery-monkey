package sysc4806.project24.mini_survey_monkey.integration;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.Constant;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.models.State;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    protected int createSurvey(Cookie cookie, String title, State state) throws Exception {
        if (cookie == null) { cookie = createDefaultCookie();}

        // AtomicInteger allows a variable to be set and returned from a lambda expression.
        //
        // Typically, AtomicInteger is used for counters and should not be used to replace
        // the class Integer. However, because our only known implementation of retrieving
        // the surveyId is with a lambda expression, AtomicInteger is the best we can do.
        //
        // @author Braeden
        AtomicInteger surveyId = new AtomicInteger();

        mockMvc.perform(post("/create").cookie(cookie)).andDo(
                result -> {
                    String location = result.getResponse().getHeader("Location");
                    surveyId.set(Integer.parseInt(location.split("/")[2]));
                }
        );

        // Set the title if provided
        if (title != null) {
            mockMvc.perform(post("/create/" + surveyId.get() + "/update")
                            .param("title", title)
                            .cookie(cookie))
                    .andExpect(status().is3xxRedirection());
        }

        // Set the state if provided
        if (state == State.OPEN) {
            mockMvc.perform(post("/create/" + surveyId.get() + "/open").cookie(cookie))
                    .andExpect(status().is3xxRedirection());
        } else if (state == State.CLOSED) {
            mockMvc.perform(post("/create/" + surveyId.get() + "/open").cookie(cookie));
            mockMvc.perform(post("/summary/" + surveyId.get() + "/close").cookie(cookie))
                    .andExpect(status().is3xxRedirection());
        }

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
    protected String updateSurveyTitle(int surveyId, String title, Cookie cookie) throws Exception {
        if (cookie == null) { cookie = createDefaultCookie();}
        String newTitle = title;

        if (title == null) {
            Random rand = new Random();
            newTitle = Integer.toString(rand.nextInt());
        }

        mockMvc.perform(post("/create/" + surveyId + "/update")
                        .param("title", newTitle)
                        .cookie(cookie))
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

        mockMvc.perform(get(urlTemplate)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(text)));
    }

    /**
     * Checks if the HTML does not contain a specified text.
     *
     * @param urlTemplate URL to GET HTML from. (Eg. "/home", "/create/1")
     * @param text Text to search for.
     * @param cookie Cookie to use in request. If null, generates a default cookie.
     * @throws Exception When html does contain the text.
     */
    protected void htmlNotContains(String urlTemplate, String text, Cookie cookie) throws Exception {
        if (cookie == null) { cookie = createDefaultCookie();}

        mockMvc.perform(get(urlTemplate)
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString(text))));
    }

    protected void signUpNewUser(User user) throws Exception {
        mockMvc.perform(post("/signup")
                .param("username", user.getUsername())
                .param("password", user.getPassword()));
    }

    /**
     * Logs existing user into system.
     *
     * @param user Existing user to log in.
     * @return Cookie of user.
     * @throws Exception
     */
    protected Cookie loginExistingUser(User user) throws Exception {
        mockMvc.perform(post("/login")
                    .param("username", user.getUsername())
                    .param("password", user.getPassword()))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/home"));

        // Successful login
        return new Cookie(Constant.CookieValue.USERNAME, user.getUsername());
    }

    /**
     * Opens a survey by transitioning its state from DRAFT to OPEN.
     * Sends a POST request to the `/create/{surveyId}/open` endpoint.
     *
     * @param surveyId the ID of the survey to open.
     * @param cookie   the authentication cookie to include in the request.
     * @throws Exception if the request fails or the expected redirection does not occur.
     */
    protected void openSurvey(int surveyId, Cookie cookie) throws Exception {
        mockMvc.perform(post("/create/" + surveyId + "/open").cookie(cookie))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/collect/" + surveyId));
    }

    /**
     * Asserts that accessing the specified endpoint results in a client error (4xx status code).
     * Sends a GET request to the provided endpoint and checks for an error response.
     *
     * @param endpoint the URL endpoint to test.
     * @param cookie   the authentication cookie to include in the request.
     * @throws Exception if the request does not result in a 4xx error or fails to execute.
     */
    protected void assertEndpointReturnsError(String endpoint, Cookie cookie) throws Exception {
        mockMvc.perform(get(endpoint).cookie(cookie))
                .andExpect(status().is4xxClientError());
    }



    private Cookie createDefaultCookie() {
        return new Cookie("JSESSIONID", "");
    }
}
