package sysc4806.project24.mini_survey_monkey.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurveyStateInterceptor implements HandlerInterceptor {

    private final SurveyRepository surveyRepository;
    private List<State> statesToFilterOut = new ArrayList<>();

    public SurveyStateInterceptor(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object surveyIDAttribute = request.getAttribute("surveyID");
        // Not all endpoints have a surveyID, so it's valid for the surveyID attribute to not be present
        if (surveyIDAttribute == null) {
            return true;
        }

        int surveyID = Integer.parseInt(surveyIDAttribute.toString());
        Survey survey = surveyRepository.findById(surveyID);
        // An invalid surveyID should be handled by the calling class, so this is valid
        if (survey == null) {
            return true;
        }

        // Filter out surveys of specified states
        if (statesToFilterOut.contains(survey.getState())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot be visited while survey is in state: " + survey.getState().toString());
            return false;
        }

        return true;
    }

    public List<State> getStatesToFilterOut() {
        return statesToFilterOut;
    }

    public void setStatesToFilterOut(List<State> stateToFilter) {
        this.statesToFilterOut = stateToFilter;
    }
}