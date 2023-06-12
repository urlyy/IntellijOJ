package com.lyy.intellijoj.utils;

import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Post;

import java.util.Map;

public interface HttpClient {
        @Post(
                url = "https://github.com/login/oauth/access_token",
                dataType = "json",
                headers = {
                        "Accept-Charset: utf-8",
                        "Content-Type: application/json"
                }
        )
        String getGithubUserAccessToken(
                @Body("client_id") String clientId,
                @Body("client_secret") String clientSecret,
                @Body("code") String code
        );

        @Get(
                url = "https://api.github.com/user",
                dataType = "json"
        )
        Map<String,String> getGithubUserInfo(
                @Header("Authorization") String accessToken
        );
}
