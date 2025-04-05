package com.reverie_unique.reverique.common;

import com.reverie_unique.reverique.constant.ApiStatus;

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

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, ApiStatus.STATUS_OK, ApiStatus.MESSAGE_SUCCESS, data);
    }

    public static <T> ApiResponse<T> success(T data, PageInfo pageInfo) {
        return new ApiResponse<>(ApiStatus.SUCCESS, ApiStatus.STATUS_OK, ApiStatus.MESSAGE_SUCCESS, data, pageInfo);
    }

    public static <T> ApiResponse<T> failure() {
        return new ApiResponse<>(ApiStatus.FAILURE, ApiStatus.STATUS_NOT_FOUND, ApiStatus.NOT_FOUND_MESSAGE, null);
    }

    public static <T> ApiResponse<T> custom(String status, int code, String message, T data) {
        return new ApiResponse<>(status, code, message, data);
    }
}