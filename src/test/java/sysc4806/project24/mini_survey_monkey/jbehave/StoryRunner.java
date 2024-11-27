package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.BananaSteps;
import sysc4806.project24.mini_survey_monkey.jbehave.steps.CreateSurveySteps;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@SpringApplicationConfiguration(classes = { MiniSurveyMonkeyApplication.class})
public class StoryRunner extends JUnitStories {

    @Autowired
    public MockMvc mvc;

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(codeLocationFromClass(this.getClass()))
                                .withFormats(Format.CONSOLE, Format.TXT, Format.HTML));
    }

    //@Override
    public List<String> storyPaths() {
        return new StoryFinder().findPaths(
                codeLocationFromClass(this.getClass()),
                "stories/*.story",
                "");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new BananaSteps(mvc), new CreateSurveySteps(mvc));
    }
}

