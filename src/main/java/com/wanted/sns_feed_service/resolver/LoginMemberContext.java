package com.wanted.sns_feed_service.resolver;

import com.wanted.sns_feed_service.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMemberContext {
  private final ThreadLocal<LoginMember> loginMemberThreadLocal = new ThreadLocal<>();
  public void save(Member member){
    loginMemberThreadLocal.set(LoginMember.of(member));
  }
  public LoginMember getLoginMember(){
    return loginMemberThreadLocal.get();
  }
  public void remove(){
    loginMemberThreadLocal.remove();
  }
}
