package com.lyy.intellijoj.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyy.intellijoj.common.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/*
 * 全局异常处理类
 */
@Component
@Order(-1)
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Response r = Response.error();
        //响应对象
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if(ex instanceof BindException e){
            //没有@ResponseBody注解时调用这个
            FieldError fieldError = e.getBindingResult().getFieldError();
            r.msg("参数校验异常");
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            log.error("参数校验异常:\"{}\"", fieldError.getDefaultMessage());
        }else if(ex instanceof MethodArgumentNotValidException e){
            //有@ResponseBody注解时调用这个
            FieldError fieldError = e.getBindingResult().getFieldError();
            r.msg("参数校验异常");
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            log.error("参数校验异常:\"{}\"", fieldError.getDefaultMessage());
        }else if(ex instanceof CustomException e){
            if(e.getData() == null){
                log.error("业务出现异常:message:\"{}\"", e.getMessage());
            }else{
                log.error("业务出现异常:message:\"{}\",data:{}", e.getMessage(),e.getData());
            }
            r.msg("服务器出错");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            log.error("意料外的异常,{}", ex.getMessage());
            r.msg("服务器出错");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ex.printStackTrace();
        //放入响应体内容
        String json = objectMapper.writeValueAsString(r);
        byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(responseBytes);
        // 返回响应体内容
        return response.writeWith(Mono.just(buffer));

    }
}
