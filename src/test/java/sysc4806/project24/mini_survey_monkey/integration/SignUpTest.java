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
public class SignUpTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testSignUpForNewUser() throws Exception {
        String username = "Ann Darrow";
        String password = "i<3kingkong";

        // Should redirect to login page
        mockMvc.perform(post("/signup").
                        param("username", username).
                        param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login"));

        // Should not allow a duplicate user to be created
        // and send user back to sign up page
        mockMvc.perform(post("/signup").
                        param("username", username).
                        param("password", password))
                .andExpect(content().string(containsString("ERROR: Username is already taken")));
    }
}
