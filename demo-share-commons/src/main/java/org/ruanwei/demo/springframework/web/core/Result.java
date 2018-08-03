package org.ruanwei.demo.springframework.web.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * Created by zhongxianyao.
 * Time 2018/5/24
 * Desc 文件描述
 */
public class Result<T> implements Serializable {

    /**
     * 标识本次调用是否返回
     */
    private boolean success = true;

    /**
     * 本次调用返回code
     */
    private int code;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String message;

    private T data;


    public Result() {
    }


    public Result(int code, String message) {
        this.setSuccess(false);
        this.code = code;
        this.message = message;
    }


    public Result(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }


    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }


    public <R extends Result> R setError(int code, String message) {
        this.setSuccess(false);
        this.setCode(code);
        this.setMessage(message);
        return (R)this;
    }
    public <R extends Result> R setError(ResponseCode responseCode) {
        this.setSuccess(false);
        this.setCode(responseCode.getCode());
        this.setMessage(responseCode.getMessage());
        return (R)this;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("success=").append(success);
        sb.append(", code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }


    public static <T> Result.ResultBuilder<T> builder() {
        return new ResultBuilder<>();
    }


    public static class ResultBuilder<T> {

        protected Result<T> result;


        ResultBuilder() {
            result = new Result<>();
        }


        public ResultBuilder<T> data(T data) {
            result.setData(data);
            return this;
        }


        public ResultBuilder<T> code(int code) {
            result.setCode(code);
            return this;
        }


        public ResultBuilder<T> message(String msg) {
            result.setMessage(msg);
            return this;
        }


        public ResultBuilder<T> success(boolean success) {
            result.setSuccess(success);
            return this;
        }


        public Result<T> build() {
            return result;
        }
    }


    public static void main(String[] args) {
        Result<String> reslt = new Result();
        reslt.setData("ab");
        System.out.println(reslt);
    }
}
