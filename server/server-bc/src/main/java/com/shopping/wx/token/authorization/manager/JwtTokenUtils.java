package com.shopping.wx.token.authorization.manager;

import com.shopping.base.foundation.util.Base64;
import com.shopping.wx.token.config.Constants;
import com.shopping.wx.token.config.ResultStatus;
import com.shopping.wx.token.model.CheckResult;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtTokenUtils jwt加密和解密的工具类
 *
 * @author guofujun
 * @date 2016/10/31
 */
public class JwtTokenUtils {

    /**
     * 签发JWT
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMillis 失效时间
     * @return String
     */
    public static String createJWT(String id, String subject,long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间戳
        Long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        SecretKey secretKey = generalKey();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String,Object> claims = new HashMap(3);
        claims.put("uid", id);
        //claims.put("wuid", store_id);
        //claims.put("store_name",store_name);
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
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
            // 过期时间
            builder.setExpiration(new Date(currentTimeMillis + ttlMillis * 1000));
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
