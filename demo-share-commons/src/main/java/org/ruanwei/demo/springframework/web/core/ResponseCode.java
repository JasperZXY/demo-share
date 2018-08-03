package org.ruanwei.demo.springframework.web.core;

public enum ResponseCode {
    SESSION_INVALID(1000, "登录态过期，请重新登录！"),
    ERROR(1001, "系统开小差，请稍后重试！"),
    PARAM_ERROR(1002, "参数错误"),
    URL_NO_FOUND(1003, "请确认访问的地址是否正确！"),
    DATA_NO_FOUND(1004, "数据没找到！"),
    ;

    private final String message;

    private final int code;


    ResponseCode(int code, String message) {
        this.message = message;
        this.code = code;
    }


    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
