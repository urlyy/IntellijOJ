package com.lyy.intellijoj.controller;

import com.lyy.intellijoj.utils.IPUtils;
import com.lyy.intellijoj.utils.SensitiveUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

    @RequestMapping("test/{text}")
    public String test(@PathVariable("text") String text, ServerHttpRequest request){
        String ip = IPUtils.getIP(request);
        String filter = SensitiveUtil.filter(text);
        return filter;
    }
}
