package com.shopping.wx.token.authorization.resolvers;


import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.service.bc.BcUserService;
import com.shopping.wx.token.authorization.annotation.CurrentBcUser;
import com.shopping.wx.token.authorization.manager.JwtTokenUtils;
import com.shopping.wx.token.model.CheckResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 */

@Component
public class CurrentBcUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private BcUserService bcUserService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentBcUser.class)) {
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
                BcUser bcUser = this.bcUserService.getObjById(CommUtils.null2Long(key));
                return bcUser;
            }
        }
        return null;
    }
}
