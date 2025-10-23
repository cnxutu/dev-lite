package com.cv.lite.jwtlogin.auth.common.handler;

import com.cv.lite.jwtlogin.auth.common.util.JwtUtils;
import com.cv.lite.jwtlogin.auth.pojo.vo.WebLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: xutu
 * @since: 2025/10/10 17:39
 */
@Slf4j
@Component
public class WebLoginHandler extends AbstractLoginHandler<WebLoginVO> {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    public WebLoginHandler(CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 构建 Spring Security Authentication 对象
     */
    @Override
    public Authentication buildAuthentication(LoginQuery request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // 返回你自定义的 UserDetailDTO
        UserDetailDTO user = customUserDetailsService.loadUserByUsername(username);

        // 若密码前端传输过来非明文，则可以在此处先解码，解码完成后再构建 Authentication 对象，方便 security 后续进行框架对比
//        byte[] decoded = Base64.getDecoder().decode(loginQuery.getPassword());
//        String rawPassword = new String(decoded, StandardCharsets.UTF_8);
//
//        Authentication authRequest = new UsernamePasswordAuthenticationToken(
//                loginQuery.getUsername(),
//                rawPassword
//        );


        // UsernamePasswordAuthenticationToken 需要 principal 和 credentials
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    /**
     *
     * 通用前置校验（等保策略、账号状态等）
     * <p>
     * Java 方法覆盖规则：
     * 当子类 重写（Override） 父类方法时，子类的方法会完全覆盖父类方法
     * 父类的方法不会自动执行，除非在子类中显式调用 super.method(...)
     * 通用前置校验（可选，等保或限制策略）
     *
     */
    @Override
    public void preAuthenticationCheck(LoginQuery request) {
        // 调用父类通用登录校验方法
        super.preAuthenticationCheck(request);
        // 执行子类特有的登录校验方法
        log.debug("Web login pre-check for username: {}", request.getUsername());
        // 可以做失败次数检查、IP 限制等
    }

    /**
     * 登录失败处理
     */
    @Override
    public void onLoginFailure(LoginQuery request, AuthenticationException exception) {
        // 记录失败次数或触发锁定策略
        super.recordLoginFail(request);
        log.warn("Web login failed for username {}: {}", request.getUsername(), exception.getMessage());

    }

    /**
     * 构建返回给前端的 WebLoginVO
     */
    @Override
    public WebLoginVO buildLoginResponse(Authentication authentication) {
        UserDetailDTO user = (UserDetailDTO) authentication.getPrincipal();
        // 构建 VO
        WebLoginVO webLoginVO = WebLoginVO.fromAuthentication(user, jwtUtils, null);
        log.info("Web login success: {}", webLoginVO.getUsername());
        return webLoginVO;
    }

    /**
     * 判断是否支持当前请求类型
     */
    @Override
    public boolean supports(LoginQuery request) {
        return LoginTypeEnum.WEB.getCode().equals(request.getLoginType());
    }

    /**
     * 获取客户端 IP（示例，可根据网关或负载均衡调整）
     */
    private String getCurrentIp() {
        // 简单示例，生产环境可从 RequestContext 获取
        return "127.0.0.1";
    }
}



