package com.cv.lite.jwtlogin.auth.common.factory;

import com.cv.kb.auth.login.handler.AbstractLoginHandler;
import com.cv.kb.auth.login.pojo.query.LoginQuery;
import com.cv.kb.auth.login.pojo.vo.LoginVO;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * LoginHandlerFactory 类简要描述
 *
 * @author xutu
 * @date 2025-10-11 11:15:42
 */
@Component
public class LoginHandlerFactory {

    private final List<AbstractLoginHandler<?>> handlers;

    public LoginHandlerFactory(List<AbstractLoginHandler<?>> handlers) {
        this.handlers = handlers;
    }

    @SuppressWarnings("unchecked")
    public <R extends LoginVO> AbstractLoginHandler<R> getHandler(LoginQuery request) {
        return (AbstractLoginHandler<R>) handlers.stream()
                .filter(h -> h.supports(request))
                .findFirst()
                .orElseThrow(() -> new AuthenticationServiceException(
                        "Unsupported login type: " + request.getLoginType()));
    }
}

