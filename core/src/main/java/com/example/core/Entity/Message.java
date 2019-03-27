package com.example.core.Entity;

/**
 * 存储接口回调内容
 */
public class Message {
    private String Message;
    private Boolean IsSuccess;
    private LoginData Data;

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

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData data) {
        Data = data;
    }
}
