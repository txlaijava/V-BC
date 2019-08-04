package com.shopping.wx.token.model;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * CheckResult 登录授权返回信息
 *
 * @author guofujun
 * @date 2016/10/31
 */
@Data
public class CheckResult {

    private int errCode;

    private boolean success;

    private Claims claims;
}
