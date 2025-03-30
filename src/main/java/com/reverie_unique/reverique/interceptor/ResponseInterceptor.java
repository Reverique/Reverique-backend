package com.reverie_unique.reverique.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reverie_unique.reverique.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

public class ResponseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 처리 전 로직 (필요 시)
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (!response.isCommitted()) {
            if (response.getStatus() == HttpStatus.OK.value()) {
                // 성공적인 응답
                ApiResponse<Object> apiResponse = new ApiResponse<>(
                        "success", 200, "성공적으로 처리되었습니다.", modelAndView != null ? modelAndView.getModel() : null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            } else {
                // 실패한 응답
                ApiResponse<Object> apiResponse = new ApiResponse<>(
                        "failure", response.getStatus(), "처리 중 오류가 발생했습니다.", null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
            }
        }
    }
}