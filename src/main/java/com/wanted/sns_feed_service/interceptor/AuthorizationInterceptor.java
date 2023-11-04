package com.wanted.sns_feed_service.interceptor;

import com.wanted.sns_feed_service.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
  private final JwtProvider jwtProvider;
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = extractToken(request);
    if(jwtProvider.verify(token))
      return false;
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  private String extractToken(HttpServletRequest request){
    return request.getHeader("Authorization");
  }
}
