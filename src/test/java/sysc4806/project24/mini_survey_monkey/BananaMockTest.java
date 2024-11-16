package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import sysc4806.project24.mini_survey_monkey.controllers.BananaController;
import sysc4806.project24.mini_survey_monkey.interceptors.SurveyStateInterceptor;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@WebMvcTest(BananaController.class)
public class BananaMockTest {

    @Autowired
    private MockMvc mockMvc;

    // This prevents SurveyStateInterceptor from being loaded
    @MockBean
    private SurveyStateInterceptor surveyStateInterceptor;

    @Test
    public void shouldReturnBanana() throws Exception {
       this.mockMvc.perform(get("/banana")).andDo(print()).andExpect(status().isOk())
               .andExpect(content().string(containsString("Banana.")));
    }

}
