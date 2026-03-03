package com.cv.devlite.jwtlogin.auth.service;

/**
 * @author: xutu
 * @since: 2025/10/10 17:34
 */
/**
 * 登录流程契约定义
 */
public interface ILoginFlow {

    /** 校验参数 */
    void validateLoginParams(Object query);

    /** 前置校验（验证码、签名验证等，可选） */
    default void preLoginCheck(Object query) {}

    /** 核心认证逻辑（由子类具体实现） */
    Object authenticate(Object query);

    /** 认证成功后的后置操作（可选，如记录日志、来源等） */
    void afterAuthenticated(Object user, Object query);

    /** 登录类型标识（用于工厂选择） */
    String getLoginType();
}
