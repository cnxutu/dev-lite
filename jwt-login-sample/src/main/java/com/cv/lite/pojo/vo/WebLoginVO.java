package com.cv.lite.pojo.vo;

import com.cv.kb.auth.security.common.util.JwtUtils;
import com.cv.kb.auth.security.pojo.dto.UserDetailDTO;
import com.cv.kb.auth.security.pojo.model.JwtUserInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

/**
 * WebLoginVO 类简要描述
 *
 * @author xutu
 * @date 2025-10-11 12:13:24
 */
@Data
@SuperBuilder
public class WebLoginVO extends LoginVO {

    private Long loginTimeMillis; // Web 特有：毫秒时间
    private String loginIp;       // Web 特有：IP


    /**
     * 根据 Spring Security 的 Authentication 构建 WebLoginVO
     */
    public static WebLoginVO fromAuthentication(UserDetailDTO user, JwtUtils jwtUtils, String loginIp) {
        String token = jwtUtils.generateToken(JwtUserInfo.fromUserDetail(user));

        return WebLoginVO.builder()
                .token(token)
                .username(user.getUsername())
                .authorities(user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .loginTimeMillis(System.currentTimeMillis())
                .loginIp(loginIp)
                .build();
    }


}