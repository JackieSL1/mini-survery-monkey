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
    private List<State> stateToFilter = new ArrayList<>();

    public SurveyStateInterceptor(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object surveyIDAttribute = request.getAttribute("surveyID");
        if (surveyIDAttribute == null) {
            return true;
        }

        int surveyID = Integer.parseInt(surveyIDAttribute.toString());
        Survey survey = surveyRepository.findById(surveyID);

        // Filter out non-draft surveys
        if (survey != null && stateToFilter.contains(survey.getState())) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot be visited while survey is in state: " + survey.getState().toString());
            return false;
        }
        return true;
    }

    public List<State> getStateToFilter() {
        return stateToFilter;
    }

    public void setStateToFilter(List<State> stateToFilter) {
        this.stateToFilter = stateToFilter;
    }
}