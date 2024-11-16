package sysc4806.project24.mini_survey_monkey;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sysc4806.project24.mini_survey_monkey.interceptors.SurveyStateInterceptor;
import sysc4806.project24.mini_survey_monkey.models.State;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final SurveyStateInterceptor draftSurveyInterceptor;
    private final SurveyStateInterceptor nonDraftSurveyInterceptor;

    public WebMvcConfig(SurveyStateInterceptor draftSurveyInterceptor, SurveyStateInterceptor nonDraftSurveyInterceptor) {
        this.draftSurveyInterceptor = draftSurveyInterceptor;
        draftSurveyInterceptor.setStatesToFilterOut(List.of(State.DRAFT));

        nonDraftSurveyInterceptor.setStatesToFilterOut(List.of(State.OPEN, State.CLOSED));
        this.nonDraftSurveyInterceptor = nonDraftSurveyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO: Fix interceptors
        registry.addInterceptor(nonDraftSurveyInterceptor).addPathPatterns("/**/create/**");
        registry.addInterceptor(draftSurveyInterceptor).addPathPatterns("/**/summary/**");
    }
}
