package com.lyy.intellijoj.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lyy.intellijoj.dao.UserRepository;
import com.lyy.intellijoj.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @RequestMapping("login")
    public SaResult login(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            // md5加密
            SaSecureUtil.md5("123456");

            // sha1加密
            SaSecureUtil.sha1("123456");

            // sha256加密
            SaSecureUtil.sha256("123456");

            // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
            StpUtil.checkDisable(10001);
            StpUtil.login(10001, new SaLoginModel().setTimeout(60 * 60 * 24 * 7));
            //在PC端登录

//            StpUtil.login(10001, "PC");
            // 第2步，获取 Token  相关参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            // 第3步，返回给前端
            return SaResult.data(tokenInfo);
        }
        return SaResult.error();
    }

    @RequestMapping("logout")
    public String logout(String username, String password) {
        try {
            // 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException`
            StpUtil.checkLogin();
            //打印token
            System.out.println(SaResult.data(StpUtil.getTokenInfo()));
//            System.out.println(token);
            StpUtil.logout();
        }catch (Exception e){
            e.printStackTrace();
            return "登出失败";
        }
        return "登出成功";
    }

    @RequestMapping("isLogin")
    public String isLogin() {
        // 获取当前会话是否已经登录，返回true=已登录，false=未登录
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @GetMapping(value="/users",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getUsers() {
        Flux<User> users = userRepository.findAll();
//        spring data jpa里面，新增和修改都是save，有id是修改，id为空时新增
        return users;
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") Integer id) {
        // deleteById 没有返回值，不能判断数据是否存在
        // this.userRepository.deleteById(id);

        return this.userRepository.findById(id)
                // 当你要操作数据，并返回一个Mono，这个时候使用flatMap
                // 如果不操作数据，只是转换数据，使用map
                .flatMap(user -> this.userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping(value = "/times", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> times() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(it -> new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody User user
    ) {
        return this.userRepository.findById(id)
                // flatMap: 操作数据
                .flatMap(u -> {
                    u.setName(user.getName());
                    return this.userRepository.save(u);
                })
                // map: 转换数据
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
