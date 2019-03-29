package com.example.core.Entity.message;

/**
 * 存储接口回调内容
 */
public abstract class Message<T> {
    private String Message;
    private Boolean IsSuccess;
    private T Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
