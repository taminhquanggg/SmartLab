package com.example.smartlab.businessObject;


public class ReferenceInfo<T> {
    private ReferenceStatusEnum status;
    private T data;
    private String message;


    public ReferenceInfo() {

    }

    public ReferenceInfo(ReferenceStatusEnum status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ReferenceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReferenceStatusEnum status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
