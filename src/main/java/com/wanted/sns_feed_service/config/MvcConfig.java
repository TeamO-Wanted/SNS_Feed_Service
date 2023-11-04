package com.wanted.sns_feed_service.config;

import com.wanted.sns_feed_service.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
  private final AuthorizationInterceptor authorizationInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(authorizationInterceptor).addPathPatterns("/v1/authorization/**");
  }
}
