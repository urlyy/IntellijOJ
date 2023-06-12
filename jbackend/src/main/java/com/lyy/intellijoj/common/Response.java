package com.lyy.intellijoj.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static com.lyy.intellijoj.common.ResultCode.*;


/**
 * @author :  CodeScholar
 * @description : <p> 统一返回结果类 </p>
 */
@ApiModel(value = "统一返回结果类")
@Data
public class Response {
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "响应消息", required = false)
    private String message;

    /**
     * 响应码：参考`ResultCode`
     */
    @ApiModelProperty(value = "响应码", required = true)
    private Integer code;

    /**
     * 响应中的数据
     */
    @ApiModelProperty(value = "响应数据", required = false)
    private Object data;

    public static Response error(String message) {
        return new Response(FAILURE.getCode(), message, null);
    }

    public static Response error() {
        return new Response(FAILURE.getCode(), ERROR.getDesc(), null);
    }

    public static Response error(Integer code, String message) {
        return new Response(code, message, null);
    }

    public static Response error(String message,Object data) {
        return new Response(FAILURE.getCode(), message, data);
    }

    public static Response success() {
        return new Response(SUCCESS.getCode(), SUCCESS.getDesc(), null);
    }

    public static Response success(Object data) {
        return new Response(SUCCESS.getCode(), SUCCESS.getDesc(), data);
    }

    public static Response success(String message, Object data) {
        return new Response(SUCCESS.getCode(), message, data);
    }

    public static Response success(Integer code, String message, Object data) {
        return new Response(code, message, data);
    }

    public static Response success(Integer code, String message) {
        return new Response(code, message, null);
    }

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public Response(){};

    public Response code(Integer code){
        this.code = code;
        return this;
    }

    public Response msg(String msg){
        this.message = msg;
        return this;
    }

    public Response data(Object data){
        this.data = data;
        return this;
    }
}
