package org.example.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Log
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor {
    Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        var methodName = ctx.getMethod().getName();
        var className = ctx.getMethod().getDeclaringClass().getSimpleName();

        logger.info("[" + className +  "] " + "Entering method: " + methodName);
        return ctx.proceed();
    }
}
