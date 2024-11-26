package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class BananaSteps {

    @Autowired
    private MockMvc mockMvc;

    private String response;

    @Given("the server is running")
    public void givenTheServerIsRunning() {
        // No explicit action is needed; MockMvc simulates the server.
    }

    @When("the user accesses the \"$endpoint\" endpoint")
    public void whenUserAccessesBananaEndpoint(String endpoint) throws Exception {
        /*
        System.out.println("When endpoint: " + endpoint);
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk()) // Check that the endpoint is reachable
                .andReturn();
        response = result.getResponse().getContentAsString(); // Capture the response body

         */
        response = mockMvc.perform(get(endpoint)).andReturn().getResponse().getContentAsString();
    }

    @Then("the response should be \"$expectedResponse\"")
    public void thenResponseShouldBe(String expectedResponse) {
        assertEquals(expectedResponse, response);
    }
}


