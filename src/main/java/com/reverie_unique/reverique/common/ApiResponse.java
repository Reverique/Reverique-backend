package com.reverie_unique.reverique.common;

import java.util.Map;

public class ApiResponse<T> {
    private String status;
    private int code;
    private String message;
    private T data;
    private PageInfo pageInfo;

    public ApiResponse(String status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String status, int code, String message, T data, PageInfo pageInfo) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageInfo = pageInfo;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}