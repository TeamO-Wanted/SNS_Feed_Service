package com.wanted.sns_feed_service.interceptor;

import com.wanted.sns_feed_service.resolver.LoginMember;
import com.wanted.sns_feed_service.resolver.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authorization/test")
public class AuthorizationTestController {
  @GetMapping
  public String authorizationTest(@LoginUser LoginMember loginMember){
    return loginMember.getAccount();
  }
}
