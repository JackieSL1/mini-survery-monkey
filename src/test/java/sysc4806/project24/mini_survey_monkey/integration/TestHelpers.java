package sysc4806.project24.mini_survey_monkey.integration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHelpers {

    public static int getQuestionID(int surveyId, String html) {
        Pattern pattern = Pattern.compile("/create/" + surveyId + "/question/(\\d+)/update");
        Matcher matcher = pattern.matcher(html);
        assert matcher.find();
        return Integer.parseInt(matcher.group(1));
    }
}
