package sysc4806.project24.mini_survey_monkey.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

@Component
public class DraftSurveyInterceptor implements HandlerInterceptor {

    private final SurveyRepository surveyRepository;

    public DraftSurveyInterceptor(SurveyRepository surveyRepository) {
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
        if (survey != null && survey.getState() != State.DRAFT) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Survey is " + survey.getState().toString().toLowerCase());
            return false;
        }
        return true;
    }
}