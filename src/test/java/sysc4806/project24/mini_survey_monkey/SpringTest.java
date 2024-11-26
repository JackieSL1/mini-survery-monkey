package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.controllers.BananaController;
import sysc4806.project24.mini_survey_monkey.interceptors.CollectInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.CreateInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.ResponseInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.SummaryInterceptor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(BananaController.class)
public class SpringTest {

    @Autowired
    MockMvc mvc;

    // This prevents the interceptors from being loaded
    @MockBean
    private CreateInterceptor createInterceptor;
    @MockBean
    private CollectInterceptor collectInterceptor;
    @MockBean
    private ResponseInterceptor responseInterceptor;
    @MockBean
    private SummaryInterceptor summaryInterceptor;

    @Test
    public void test() throws Exception {
        assertNotNull(mvc);
    }
}
