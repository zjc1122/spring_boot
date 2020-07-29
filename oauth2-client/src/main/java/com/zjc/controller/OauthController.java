package com.zjc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author : zhangjiacheng
 * @ClassName : UserController
 * @date : 2018/6/13
 */
@RestController
@Slf4j
public class OauthController {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    /**
     *
     * @return
     */
    @GetMapping("/personInfo")
    public String person() {
//        ModelAndView mav = new ModelAndView("personinfo");
        String personResourceUrl = "http://localhost:8035/user";
        OAuth2ProtectedResourceDetails resource = oAuth2RestTemplate.getResource();
        String forObject = oAuth2RestTemplate.getForObject(personResourceUrl, String.class);
        System.out.println(forObject);
        return forObject;
    }

    @GetMapping("/oauth/callback")
    public void callback() {
        String personResourceUrl = "http://localhost:8035/user";
        OAuth2ClientContext oAuth2ClientContext = oAuth2RestTemplate.getOAuth2ClientContext();
//        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();
        OAuth2ProtectedResourceDetails resource = oAuth2RestTemplate.getResource();
        ResponseEntity<String> forEntity = oAuth2RestTemplate.getForEntity(personResourceUrl, String.class);
        System.out.println("11111111111111111111L");
    }

    @GetMapping("/")
    public String main() {
        return "Welcome to the main!";
    }

    @GetMapping("/user")
    public Principal principal(Principal user) {
        return user;
    }

}
