package sysc4806.project24.mini_survey_monkey.cucumber;

import io.cucumber.java.en.When;

public class StepDefinitions extends SpringIntegrationTest {

    @When("^the client calls /banana$")
    public void the_client_issues_GET_banana() throws Throwable{
        executeGet("http://localhost:8080/banana");
    }
}
