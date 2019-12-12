package com.songsmily.petapi.dto;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(CodeMsg cm){
        return new  Result<T>(cm);
    }

    /**
     * 成功时只设置消息
     * */
    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    /**
     * 将返回消息CodeMsg封装到本类中
     * */
    public Result(CodeMsg cm) {
        if(cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public T getData() {
        return data;
    }
}
