package top.snowphoenix.exp2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.snowphoenix.exp2.aop.CurrentUserHandlerMethodArgumentResolver;
import top.snowphoenix.exp2.aop.UserValidationInterceptor;

import java.util.List;

@Configuration
public class UserValidationConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new UserValidationInterceptor())
                .addPathPatterns("/**")
                ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers
                .add(new CurrentUserHandlerMethodArgumentResolver())
                ;
    }
}
