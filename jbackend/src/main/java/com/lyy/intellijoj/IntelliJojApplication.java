package com.lyy.intellijoj;

import cn.dev33.satoken.SaManager;
import com.dtflys.forest.springboot.annotation.ForestScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;



 /**
 * webflux对元素的操作
 * map操作可以将数据元素进行转换/映射，得到一个新元素。
 * flatMap操作可以将每个数据元素转换/映射为一个流，然后将这些流合并为一个大的数据流
 * filter操作可以对数据元素进行筛选。
 * 看到zip这个词可能会联想到拉链，它能够将多个流一对一的合并起来。zip有多个方法变体，我们介绍一个最常见的二合一的。
 *
 * reactor和java8 stream区别
 * 1、形似而神不似
 * 2、reactor：push模式，服务端推送数据给客户端
 * 3、java8 stream：pull模式，客户端主动向服务端请求数据
 *
 * 默认基于的web容器是 Netty
 * Netty是一个高性能 NIO（异步非阻塞的框架）区别的就是BIO（同步阻塞的）
 */
@SpringBootApplication
@Slf4j
@ForestScan(basePackages = "com.lyy.intellijoj.utils")
public class IntelliJojApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(IntelliJojApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
        ip = "localhost";
        System.out.println("\n----------------------------------------------------------\n\t" +
                "IntelliJ-OJ is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port  + "/\n\t" +
                "External: \thttp://" + ip + ":" + port  + "/\n\t" +
                "Knife4j-ui: \thttp://" + ip + ":" + port  + "/doc.html\n" +
                "----------------------------------------------------------");

    }
}
