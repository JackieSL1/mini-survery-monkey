package sysc4806.project24.mini_survey_monkey;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sysc4806.project24.mini_survey_monkey.interceptors.CollectInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.CreateInterceptor;
import sysc4806.project24.mini_survey_monkey.interceptors.SummaryInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CreateInterceptor createInterceptor;
    private final CollectInterceptor collectInterceptor;
    private final SummaryInterceptor summaryInterceptor;

    public WebMvcConfig(CreateInterceptor createInterceptor, CollectInterceptor collectInterceptor, SummaryInterceptor summaryInterceptor) {
        this.createInterceptor = createInterceptor;
        this.collectInterceptor = collectInterceptor;
        this.summaryInterceptor = summaryInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createInterceptor).addPathPatterns("/**/create/**");
        registry.addInterceptor(collectInterceptor).addPathPatterns("/**/collect/**");
        registry.addInterceptor(summaryInterceptor).addPathPatterns("/**/summary/**");
    }
}
