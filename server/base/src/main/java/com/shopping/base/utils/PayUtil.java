package com.shopping.base.utils;

import org.apache.commons.codec.binary.Base64;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * 支付工具类
 *
 * @author 邹
 */
public class PayUtil {

    /**
     * 签名验证
     *
     * @param request
     * @param dataString
     * @throws Exception
     */
    public static boolean signatureVerify(HttpServletRequest request, String dataString) throws Exception {
        // 签名
        String signatureString = request.getHeader("x-pingplusplus-signature");
        // 密钥
        String pingPP_public_key = request.getRealPath("") + "/WEB-INF/classes/pingpp_rsa_public_key.pem";
        PublicKey publicKey = getPubKey(pingPP_public_key);
        // 原始数据
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        boolean b = signature.verify(signatureBytes);
        return b;
    }

    public static boolean signatureVerifyHbPay(HttpServletRequest request, String dataString) throws Exception {
        // 签名
        String signatureString = request.getHeader("hbpay-signature");
        // 获得密钥
        String pingPP_public_key = request.getRealPath("") + "/WEB-INF/classes/hbpay_rsa_public_key.pem";
        PublicKey publicKey = getPubKey(pingPP_public_key);
        // 原始数据
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        boolean b = signature.verify(signatureBytes);
        return b;
    }

    public static String getDataString(HttpServletRequest request) throws Exception {
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            //logger.info(key+" "+value);
        }
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        return buffer.toString();
    }


    /**
     * 获得公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey(String pubKeyPath) throws Exception {
        String pubKeyString = getStringFromFile(pubKeyPath);
        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    /**
     * @param var0
     * @param privateKeyPath
     * @return
     * @throws IOException
     */
    private static String generateSign(String var0, String privateKeyPath) throws IOException {
        byte[] var2;
        String privateKey;

        if (privateKeyPath == null) {
            return null;
        }

        FileInputStream var1 = new FileInputStream(privateKeyPath);
        var2 = new byte[var1.available()];
        var1.read(var2);
        var1.close();
        String var3 = new String(var2, "UTF-8");
        privateKey = var3;


        String var22 = privateKey.replaceAll("(-+BEGIN (RSA )?PRIVATE KEY-+\\r?\\n|-+END (RSA )?PRIVATE KEY-+\\r?\\n?)", "");
        var2 = Base64.decodeBase64(var22);
        DerInputStream var23 = new DerInputStream(var2);
        DerValue[] var4 = var23.getSequence(0);
        if (var4.length < 9) {
            System.out.println("Could not parse a PKCS1 private key.");
            return null;
        } else {
            BigInteger var5 = var4[1].getBigInteger();
            BigInteger var6 = var4[2].getBigInteger();
            BigInteger var7 = var4[3].getBigInteger();
            BigInteger var8 = var4[4].getBigInteger();
            BigInteger var9 = var4[5].getBigInteger();
            BigInteger var10 = var4[6].getBigInteger();
            BigInteger var11 = var4[7].getBigInteger();
            BigInteger var12 = var4[8].getBigInteger();
            RSAPrivateCrtKeySpec var13 = new RSAPrivateCrtKeySpec(var5, var6, var7, var8, var9, var10, var11, var12);

            try {
                KeyFactory var14 = KeyFactory.getInstance("RSA");
                PrivateKey var15 = var14.generatePrivate(var13);
                Signature var16 = Signature.getInstance("SHA256withRSA");
                var16.initSign(var15);
                var16.update(var0.getBytes("UTF-8"));
                byte[] var17 = var16.sign();
                return Base64.encodeBase64String(var17).replaceAll("\n|\r", "");
            } catch (NoSuchAlgorithmException var18) {
                var18.printStackTrace();
            } catch (InvalidKeySpecException var19) {
                var19.printStackTrace();
            } catch (InvalidKeyException var20) {
                var20.printStackTrace();
            } catch (SignatureException var21) {
                var21.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 读取文件, 部署 web 程序的时候, 签名和验签内容需要从 request 中获得
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }

    public static void main(String[] args) {

        String signatureString = "";


    }
}
