package sysc4806.project24.mini_survey_monkey.jbehave.steps;

import org.jbehave.core.annotations.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


public class CreateSurveySteps {

    private MockMvc mockMvc;

    private ResultActions response;

    private int surveyId;

    public CreateSurveySteps(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Given("the user is on the home page")
    public void givenUserOnPage() throws Exception {
        // gets the user on the home page
        mockMvc.perform(get("/home"));
    }

    @When("the user clicks on the \"Create Survey\" button")
    public void whenUserClicksButton() throws Exception {
        // simulates the user clicking a button
        response = mockMvc.perform(post("/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", matchesPattern("/create/\\d+")))
                .andDo(result -> {
                    String location = result.getResponse().getHeader("Location");
                    surveyId = Integer.parseInt(location.split("/")[2]);

                    // Verify that the updated title is saved
                    mockMvc.perform(get("/create/" + surveyId))
                            .andExpect(status().isOk())
                            .andExpect(view().name("create"));
                });
    }

    @Then("the system sends them to the \"/create\" page for a new survey")
    public void thenResponseShouldBe() throws Exception {
        // checks to make sure that the user is on the create page
        response.andExpect(header().string("Location", startsWith("/create/")));
    }
}