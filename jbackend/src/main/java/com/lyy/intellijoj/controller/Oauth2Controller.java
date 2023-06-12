package com.lyy.intellijoj.controller;
import com.lyy.intellijoj.exception.CustomException;
import com.lyy.intellijoj.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final HttpClient myClient;

    @Value("${oauth2.github.clientId}")
    private String clientId;

    @Value("${oauth2.github.clientSecret}")
    private String clientSecret;

    @GetMapping("/github")
    public Mono<Map<String,String>> githubAuth(@RequestParam("code") String code) {
        try {
            String tokenInfoStr= myClient.getGithubUserAccessToken(clientId,clientSecret,code);
            Map<String,String> tokenInfo = new HashMap<>();
            for (String kvStr : tokenInfoStr.split("&")) {
                String[] kv = kvStr.split("=");
                String key = kv[0];
                String val = kv.length<2?"":kv[1];
                tokenInfo.put(key,val);
            }
            String authorization = String.join(
                    StringUtils.SPACE, tokenInfo.get("token_type"), tokenInfo.get("access_token"));
            System.out.println(authorization);
            Map<String, String> githubUserInfo = myClient.getGithubUserInfo(authorization);
            String githubId = githubUserInfo.get("id");
            String githubAvatar = githubUserInfo.get("avatar_url");
            String githubName = githubUserInfo.get("name");
            Map<String,String> data = new HashMap<>();
            data.put("githubId",githubId);
            data.put("githubAvatar",githubAvatar);
            data.put("githubName",githubName);
            return Mono.just(data);
        }catch (RuntimeException e){
            throw new CustomException("github登录异常，请稍后再试或换用邮箱登录");
        }
    }
}
