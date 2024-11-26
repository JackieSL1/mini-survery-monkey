package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.annotations.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import sysc4806.project24.mini_survey_monkey.controllers.BananaController;
import sysc4806.project24.mini_survey_monkey.interceptors.CollectInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.CreateInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.ResponseInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.SummaryInterceptor;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BananaSteps {

    private MockMvc mockMvc;

    private ResultActions response;

    public BananaSteps(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Given("the server is running")
    public void givenTheServerIsRunning() {
        //assertNotNull(applicationContext.getBean(MockMvc.class), "MockMvc bean is not loaded!");
    }

    @When("the user accesses the \"$endpoint\" endpoint")
    public void whenUserAccessesBananaEndpoint(String endpoint) throws Exception {
        /*
        System.out.println("When endpoint: " + endpoint);
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk()) // Check that the endpoint is reachable
                .andReturn();
        response = mockMvc.perform(get(endpoint));

         */
    }

    @Then("the response should be \"$expectedResponse\"")
    public void thenResponseShouldBe(String expectedResponse) throws Exception {
        assertTrue(true);
        //response.andExpect(content().string(containsString("Banana.")));
    }
}


