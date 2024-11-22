package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.controllers.BananaController;
import sysc4806.project24.mini_survey_monkey.interceptors.CollectInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.CreateInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.SummaryInterceptor;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BananaController.class)
public class BananaMockTest {

    @Autowired
    private MockMvc mockMvc;

    // This prevents the interceptors from being loaded
    @MockBean
    private CreateInterceptor createInterceptor;
    @MockBean
    private CollectInterceptor collectInterceptor;
    @MockBean
    private SummaryInterceptor summaryInterceptor;

    @Test
    public void shouldReturnBanana() throws Exception {
        this.mockMvc.perform(get("/banana")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Banana.")));
    }

}
