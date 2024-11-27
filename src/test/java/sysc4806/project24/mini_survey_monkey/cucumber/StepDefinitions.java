package sysc4806.project24.mini_survey_monkey.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends SpringIntegrationTest {

    @When("the client calls {string}")
    public void the_client_issues_GET(String url) throws Throwable{
        executeGet("http://localhost:8080" + url);
    }

    @Given("the client has a survey with id {int}")
    public void create_survey_with_id(int n) throws Throwable{
        createSurveyWithId(n);
    }

    @When("the client updates the title of survey {int} to {string}")
    public void update_survey_title(int id, String title) throws Throwable{
        updateSurveyTitle(id, title);
    }

    @Then("a survey with title {string} is displayed on homepage")
    public void test_survey_is_displayed_with(String title) throws Throwable {
        homepageShouldDisplaySurveyWithTitle(title);
    }
}
