package com.shopping.wx;

import com.shopping.base.foundation.result.ActionResult;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotNull;


/**
 * JSR303验证框架统一处理
 */
@Log4j2
@Component
@Aspect
public class JSRValidationAdvice {

    Logger logger = LoggerFactory.getLogger(JSRValidationAdvice.class);

    /**
     * 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
     */
    @Pointcut("execution( * com.shopping.*.controller.*.*(..))")
    public void pointcut() {
    }


    /**
     * 判断验证错误代码是否属于字段为空的情况
     *
     * @param code 验证错误代码
     */
    private boolean isMissingParamsError(String code) {
        if (code.equals(NotNull.class.getSimpleName()) || code.equals(NotBlank.class.getSimpleName()) || code.equals(NotEmpty.class.getSimpleName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 切点处理
     * 定义 advise
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult result = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            for (Object object : args) {
                if (object instanceof BindingResult) {
                    result = (BindingResult) object;
                    break;
                }
            }
        }
        if (result != null && result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            String targetName = joinPoint.getTarget().getClass().getSimpleName();
            String method = joinPoint.getSignature().getName();
            logger.info("验证失败.控制器:{}, 方法:{}, 参数:{}, 属性:{}, 错误:{}, 消息:{}", targetName, method, fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
            String firstCode = fieldError.getCode();
            if (isMissingParamsError(firstCode)) {
                return ActionResult.error(fieldError.getDefaultMessage());
            } else {
                return ActionResult.error("其他错误");
            }
        }
        return joinPoint.proceed();
    }

}

