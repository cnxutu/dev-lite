package com.cv.devlite.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取Authorization头
        String header = request.getHeader(jwtUtils.getHeader());
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 提取token
        String token = header.substring(7);
        if (!jwtUtils.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // 从token中获取用户名
        String username = jwtUtils.getUsernameFromToken(token);
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }

        // 创建UserDetails对象
        UserDetails userDetails = User.withUsername(username)
                .password("password") // 密码在实际应用中应该从数据库获取
                .roles("USER")
                .build();

        // 创建认证令牌
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 设置认证信息到SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}