package com.wanted.sns_feed_service.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMemberContext {
  private ThreadLocal<LoginMember> loginMemberThreadLocal;
  public LoginMember getLoginMember(){
    return loginMemberThreadLocal.get();
  }
  public void remove(){
    loginMemberThreadLocal.remove();
  }
}
