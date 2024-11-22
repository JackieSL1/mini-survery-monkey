package sysc4806.project24.mini_survey_monkey.interceptors;

import org.springframework.stereotype.Component;
import sysc4806.project24.mini_survey_monkey.models.State;
import sysc4806.project24.mini_survey_monkey.repositories.SurveyRepository;

import java.util.HashSet;

@Component
public class CollectInterceptor extends PageInterceptor{
    public CollectInterceptor(SurveyRepository surveyRepository) {
        super(surveyRepository);
        regex = "/collect/([0-9]+)";
        surveyStatesToFilter = new HashSet<>();
        surveyStatesToFilter.add(State.DRAFT);
        surveyStatesToFilter.add(State.CLOSED);
    }
}
