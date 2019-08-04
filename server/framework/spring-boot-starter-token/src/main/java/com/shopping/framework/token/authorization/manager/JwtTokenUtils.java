package com.shopping.framework.token.authorization.manager;

import com.shopping.framework.token.config.Constants;
import com.shopping.framework.token.config.ResultStatus;
import com.shopping.framework.token.model.CheckResult;
import com.shopping.base.foundation.util.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.util.Date;

/**
 * JwtTokenUtils jwt加密和解密的工具类
 *
 * @author guofujun
 * @date 2016/10/31
 */
public class JwtTokenUtils {

    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis 失效时间
     * @return String
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                // 主题
                .setSubject(subject)
                // 签发者
                .setIssuer(Constants.JWT_ISSUER)
                // 签发时间
                .setIssuedAt(now)
                // 签名算法以及密匙
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            // 过期时间
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr 验证的Token
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(ResultStatus.JWT_ERRCODE_EXPIRE.getCode());
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(ResultStatus.JWT_ERRCODE_FAIL.getCode());
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(ResultStatus.JWT_ERRCODE_FAIL.getCode());
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 秘钥加密
     *
     * @return
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Constants.JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws InterruptedException {
        //小明失效 10s
        String sc = createJWT("1", "小明", 3000);
        System.out.println(sc);
        System.out.println(validateJWT(sc).getErrCode());
        System.out.println(validateJWT(sc).getClaims().getId());
        System.out.println(validateJWT(sc).getClaims());
    }
}
