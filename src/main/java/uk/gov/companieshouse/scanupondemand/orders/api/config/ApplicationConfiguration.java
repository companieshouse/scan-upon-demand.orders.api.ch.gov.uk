package uk.gov.companieshouse.scanupondemand.orders.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.companieshouse.scanupondemand.orders.api.interceptor.UserAuthenticationInterceptor;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Value("${uk.gov.companieshouse.scanupondemand.orders.api.home}")
    private String SCAN_UPON_DEMAND_HOME;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new UserAuthenticationInterceptor()).addPathPatterns(SCAN_UPON_DEMAND_HOME + "/**")
                .excludePathPatterns(SCAN_UPON_DEMAND_HOME + "/healthcheck");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setPropertyNamingStrategy(SNAKE_CASE)
                .findAndRegisterModules();
    }

}
