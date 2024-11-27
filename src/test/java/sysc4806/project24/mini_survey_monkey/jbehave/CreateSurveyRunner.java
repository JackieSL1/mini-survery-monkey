package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.BananaSteps;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.CreateSurveySteps;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateSurveyRunner extends StoryRunner{

    @Override
    public InjectableStepsFactory stepsFactory() {
        // connects the runner to the steps
        return new InstanceStepsFactory(configuration(), new CreateSurveySteps(mvc));
    }

    @Override
    public List<String> storyPaths() {
        // connects the runner to the story
        return Arrays.asList("stories/create-survey.story");
    }
}
