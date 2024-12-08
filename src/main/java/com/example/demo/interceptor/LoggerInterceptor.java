package com.example.demo.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import lombok.extern.java.Log;

import java.io.Serializable;

@Interceptor
@LoggerInt
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class LoggerInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    private final SecurityContext securityContext;

    @Inject
    public LoggerInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        log.info(context.getMethod().getName() + securityContext.getCallerPrincipal());
        return context.proceed();
    }
}
