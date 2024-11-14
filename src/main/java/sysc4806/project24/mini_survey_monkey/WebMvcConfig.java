package sysc4806.project24.mini_survey_monkey;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sysc4806.project24.mini_survey_monkey.interceptors.DraftSurveyInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final DraftSurveyInterceptor surveyStateInterceptor;

    public WebMvcConfig(DraftSurveyInterceptor surveyStateInterceptor) {
        this.surveyStateInterceptor = surveyStateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(surveyStateInterceptor).addPathPatterns("/**/create/**");
    }
}
