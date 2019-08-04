package com.shopping.framework.token.model;

import io.jsonwebtoken.Claims;

/**
 * CheckResult 登录授权返回信息
 *
 * @author guofujun
 * @date 2016/10/31s
 */

public class CheckResult {

    private int errCode;

    private boolean success;

    private Claims claims;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
