package sysc4806.project24.mini_survey_monkey.jbehave;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

@SpringBootTest
public class BananaStoryRunner extends JUnitStories {

    @Override
    public Configuration configuration() {
        return new org.jbehave.core.configuration.MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(codeLocationFromClass(this.getClass()))
                                .withFormats(Format.CONSOLE, Format.TXT, Format.HTML));
    }

    @Override
    public List<String> storyPaths() {
        return new StoryFinder().findPaths(
                codeLocationFromClass(this.getClass()),
                "stories/*.story",
                "");
    }

    @Override
    public InstanceStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new BananaSteps());
    }
}

