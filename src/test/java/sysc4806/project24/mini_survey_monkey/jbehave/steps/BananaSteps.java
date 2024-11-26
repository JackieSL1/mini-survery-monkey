package sysc4806.project24.mini_survey_monkey.jbehave.steps;

import org.jbehave.core.annotations.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
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
        //No set up required, mockMvc is already running!
    }

    @When("the user accesses the \"$endpoint\" endpoint")
    public void whenUserAccessesBananaEndpoint(String endpoint) throws Exception {
        response = mockMvc.perform(get(endpoint));
    }

    @Then("the response should be \"$expectedResponse\"")
    public void thenResponseShouldBe(String expectedResponse) throws Exception {
        response.andExpect(content().string(containsString("Banana.")));
    }
}


