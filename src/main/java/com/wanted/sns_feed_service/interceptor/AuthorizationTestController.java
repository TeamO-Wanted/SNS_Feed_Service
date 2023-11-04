package com.wanted.sns_feed_service.interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authorization/test")
public class AuthorizationTestController {
  @GetMapping
  public String authorizationTest(){
    return "ok";
  }
}
