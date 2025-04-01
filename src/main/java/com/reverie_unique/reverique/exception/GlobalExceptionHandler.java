package com.reverie_unique.reverique.exception;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.constant.ApiStatus;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResponse<String> handleAllExceptions(Exception ex) {
        return new ApiResponse<>(ApiStatus.FAILURE, ApiStatus.STATUS_SERVER_ERROR, ApiStatus.MESSAGE_SERVER_ERROR, "An error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleMissingParam(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        String message = "Required request parameter '" + paramName + "' is not present";

        return new ApiResponse<>(ApiStatus.FAILURE, HttpStatus.BAD_REQUEST.value(), message, null);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    @ResponseBody
    public ApiResponse<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiResponse<>(ApiStatus.FAILURE, HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiResponse<>(ApiStatus.FAILURE, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null);
    }
}