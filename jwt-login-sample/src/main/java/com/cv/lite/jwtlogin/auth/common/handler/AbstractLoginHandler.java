package com.cv.lite.jwtlogin.auth.common.handler;

import com.cv.kb.auth.login.pojo.query.LoginQuery;
import com.cv.kb.auth.login.pojo.vo.LoginVO;
import com.cv.kb.auth.login.service.ILoginFlow;
import com.cv.kb.auth.login.service.impl.RedissonLoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author: xutu
 * @since: 2025/10/10 17:36
 * <p>
 * ● 设计边界：
 * Handler: AbstractLoginHandler
 * ├─ 前置校验 preAuthenticationCheck
 * ├─ 构建 Authentication 对象 buildAuthentication
 * └─ 可选：登录方式特定逻辑（多端/多方式）
 *
 */
public abstract class AbstractLoginHandler<R extends LoginVO> implements ILoginFlow<LoginQuery> {

    /**
     * required = false : 如果 Bean 未被注入不会报错
     */
    @Autowired(required = false)
    protected RedissonLoginAttemptService loginAttemptService;


    /**
     * 模板方法，security 前置登录校验，确认登录前 至 构建 Authentication 的骨架流程
     *
     * @param request
     * @return
     */
    public final Authentication preAuthenticate(LoginQuery request) {
        // 1️⃣ 通用前置校验
        preAuthenticationCheck(request);

        // 2️⃣ 等保验证（失败次数、锁定）
        checkAccountLocked(request);

        // 3️⃣ 构建认证对象
        return buildAuthentication(request);
    }


    @Override
    public void preAuthenticationCheck(LoginQuery request) {
    }


    /**
     * 模板方法设计模式
     * 控制登录成功后的主要骨架流程
     * <p>
     * 父类控制“什么时候执行校验、什么时候调用子类逻辑”，
     * 整个登录流程的模板已经固定下来，
     * 子类无法破坏流程，只能在指定点实现差异逻辑。
     * <p>
     * 这正是 模板方法模式的核心特征。
     * <p>
     * 总结：
     * 🔹“父类提供共用方法”是工具复用。
     * 🔹“父类控制流程骨架，子类填充细节”才是模板方法模式。
     *
     * @param request
     * @param authentication
     * @return
     */
    public R onLoginSuccess(LoginQuery request, Authentication authentication) {
        // 1. 登录成功，清除失败计数
        clearLoginFailCount(request);
        // 2. 构建返回结果（子类实现）
        // 由子类构建具体返回
        return buildLoginResponse(authentication);
    }


    // =============== 🔹 模板方法中的步骤定义 🔹 ===============

    /**
     * 登录校验
     */
    protected void checkAccountLocked(LoginQuery request) {
        if (loginAttemptService.isBlocked(request.getUsername())) {
            throw new LockedException("账户已被锁定，请稍后再试");
        }
    }

    /**
     * 登录成功清除失败次数
     */
    protected void clearLoginFailCount(LoginQuery request) {
        loginAttemptService.loginSucceeded(request.getUsername());
    }

    /**
     * 登录失败累计次数 +1
     */
    protected void recordLoginFail(LoginQuery request) {
        loginAttemptService.loginFailed(request.getUsername());
    }


    // =============== 子类各自的实现 ===============

    /**
     * 子类实现：构建 Authentication
     */
    public abstract Authentication buildAuthentication(LoginQuery request);

    /**
     * 子类实现：构建泛型返回对象
     */
    public abstract R buildLoginResponse(Authentication authentication);

    @Override
    public abstract boolean supports(LoginQuery request);

    @Override
    public abstract void onLoginFailure(LoginQuery request, AuthenticationException exception);


}

