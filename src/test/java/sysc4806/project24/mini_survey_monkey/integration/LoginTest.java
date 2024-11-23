package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginWithExistingUser() throws Exception {
        // Set up
        String username = "janegoodall";
        String password = "primatology4life";

        mockMvc.perform(post("/signup")
                .param("username", username)
                .param("password", password));

        // Should not log in user if password incorrect
        mockMvc.perform(post("/login/authenticate")
                .param("username", username)
                .param("password", "primatology3life"))
                        .andExpect(content().string(containsString("ERROR: Invalid password.")));

        // Should redirect to home page after successful log in
        mockMvc.perform(post("/login/authenticate")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home?username=" + username));
    }

    @Test
    public void testLoginWithNonExistingUser() throws Exception {
        String username = "sarvesh";
        String password = "bluelagoon";

        mockMvc.perform(post("/login/authenticate")
                        .param("username", username)
                        .param("password", password))
                .andExpect(content().string(containsString("ERROR: Username not found.")));
    }
}
