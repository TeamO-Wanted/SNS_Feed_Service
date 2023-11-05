package com.wanted.sns_feed_service.config;

import com.wanted.sns_feed_service.interceptor.AuthorizationInterceptor;
import com.wanted.sns_feed_service.resolver.LoginUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
  private final AuthorizationInterceptor authorizationInterceptor;
  private final LoginUserResolver loginUserResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(authorizationInterceptor).addPathPatterns("/v1/authorization/**");
  }
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserResolver);
  }
}
