package sysc4806.project24.mini_survey_monkey.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SignUpTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testSignUpWhenUserDoesntExist() throws Exception {
        // Should redirect to login page
        mockMvc.perform(post("/signup"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login"));
    }
}
