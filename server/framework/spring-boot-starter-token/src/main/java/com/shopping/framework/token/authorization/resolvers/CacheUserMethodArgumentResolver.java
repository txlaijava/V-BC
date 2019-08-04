package com.shopping.framework.token.authorization.resolvers;

import com.shopping.base.domain.user.User;
import com.shopping.base.utils.BeanUtilEx;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.framework.token.authorization.annotation.CacheUser;
import com.shopping.framework.token.authorization.manager.JwtTokenUtils;
import com.shopping.framework.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 增加方法注入，将含有CacheUser注解的方法参数注入当前缓存的登录User对象
 *
 * @author guofujun
 * @date 2016/10/31
 * @see CacheUser
 */
@Component
public class CacheUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CacheUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Token");
        System.err.println("===============>"+authHeader);
        if (StringUtils.isNotBlank(authHeader)) {
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String key = checkResult.getClaims().getId();
                User user = BeanUtilEx.map2Bean(redisService.hmget(key), User.class);
                return user;
            }
        }
        return null;
    }
}