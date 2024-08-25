package com.example.usermanagementsystem.util;

import com.example.usermanagementsystem.model.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String encodedHeader = request.getHeader("Authorization");
        if (encodedHeader != null) {
            String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader));
            UserInfo userInfo = Base64Util.parseUserInfo(decodedHeader);
            request.setAttribute("userInfo", userInfo);
        } else {
            request.setAttribute("userInfo", new UserInfo());  // 未设置header时创建空的UserInfo对象
        }
        return true;
    }
}

