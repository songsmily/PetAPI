package com.songsmily.petapi.enums;


/**
 * redis数据枚举
 */
public enum RedisKey {

    MAP_BLOG_KEY("MAP_BLOG_GOOD", "帖子点赞MAP"),
    MAP_COMMENT_KEY("MAP_COMMENT_GOOD", "帖子点赞MAP");
    private String code;
    private String msg;

    RedisKey(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
