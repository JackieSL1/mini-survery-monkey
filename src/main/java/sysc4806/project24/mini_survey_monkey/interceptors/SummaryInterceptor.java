package sysc4806.project24.mini_survey_monkey.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SummaryInterceptor implements HandlerInterceptor {

    private final SurveyRepository surveyRepository;

    public SummaryInterceptor(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Pattern pattern = Pattern.compile("/summary/([0-9]+)");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        // Not all endpoints have a surveyID, so it's valid for the surveyID to not be present
        if (!matcher.find()) {
            return true;
        }
        String surveyID = matcher.group(1);

        Survey survey = surveyRepository.findById(Integer.parseInt(surveyID));
        // An invalid surveyID should be handled by the calling class, so this is valid
        if (survey == null) {
            return true;
        }

        if (survey.getState() == State.DRAFT) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot be visited while survey is in state: " + survey.getState().toString());
            return false;
        }

        return true;
    }
}