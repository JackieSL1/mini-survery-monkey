package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.BananaSteps;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.CreateSurveySteps;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BananaRunner extends StoryRunner{

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new BananaSteps(mvc));
    }

    @Override
    public List<String> storyPaths() {
        return Arrays.asList("stories/banana.story");
    }
}
