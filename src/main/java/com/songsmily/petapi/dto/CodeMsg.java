package com.songsmily.petapi.dto;


public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(100, "success");//请求成功
    public static CodeMsg  SERVERERROR = new CodeMsg(500, "serverError");//服务器异常
    public static CodeMsg  AUTHENERROR = new CodeMsg(600,"unlogin");//未登录
    public static CodeMsg  AUTHORERROR = new CodeMsg(700,"unauthen");//未授权

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
