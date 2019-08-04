package com.shopping.framework.token.authorization.resolvers;


import com.shopping.base.domain.user.User;
import com.shopping.base.foundation.dao.UserDAO;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.token.authorization.annotation.CurrentUser;
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
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 *
 * @author guofujun
 * @date 2016/10/31
 * @see CurrentUser
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Token");
        if (StringUtils.isNotBlank(authHeader)) {
            CheckResult checkResult = JwtTokenUtils.validateJWT(authHeader);
            if (checkResult.isSuccess()) {
                String key = checkResult.getClaims().getId();
                User user = userDAO.findById(CommUtils.null2Long(key));
                return user;
            }
        }
        return null;
    }
}
