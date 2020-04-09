package com.songsmily.petapi.enums;

import lombok.Getter;

/**
 * 返回结果枚举
 * @Author: Songsmily

 */
@Getter
public enum ResultEnum {
    /**
     * 返回结果枚举，每个枚举代表着一个返回状态
     */
    SUCCESS(100, "操作成功！"),
    ERROR(40000, "操作失败！"),
    DATA_NOT_FOUND(40001, "查询失败！"),
    PARAMS_NULL(40002, "参数不能为空！"),
    PARAMS_ERROR(40005, "参数不合法！"),
    NOT_AUTH(600, "当前账号未授权！"),
    NOT_LOGIN(700, "当前账号未登录！");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
