package util.Encryp;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

public class EncrypAES {  
      
    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法  
    private KeyGenerator keygen;  
    //SecretKey 负责保存对称密钥  
    private SecretKey deskey;  
    //Cipher负责完成加密或解密工作  
    private Cipher c;  
    //该字节数组负责保存加密的结果  
    private byte[] cipherByte;  
      
    public EncrypAES() throws NoSuchAlgorithmException, NoSuchPaddingException{  
        Security.addProvider(new com.sun.crypto.provider.SunJCE());  
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)  
        keygen = KeyGenerator.getInstance("AES");  
        //生成密钥  
        deskey = keygen.generateKey();  
        //生成Cipher对象,指定其支持的DES算法  
        c = Cipher.getInstance("AES");  
    }  
      
    /** 
     * 对字符串加密 
     *  
     * @param str 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public byte[] Encrytor(String str) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式  
        c.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] src = str.getBytes();  
        // 加密，结果保存进cipherByte  
        cipherByte = c.doFinal(src);  
        return cipherByte;  
    }  
  
    /** 
     * 对字符串解密 
     *  
     * @param buff 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public byte[] Decryptor(byte[] buff) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
        c.init(Cipher.DECRYPT_MODE, deskey);  
        cipherByte = c.doFinal(buff);  
        return cipherByte;  
    }  
  
    /** 
     * @param args 
     * @throws NoSuchPaddingException  
     * @throws NoSuchAlgorithmException  
     * @throws BadPaddingException  
     * @throws IllegalBlockSizeException  
     * @throws InvalidKeyException  
     */  
    public static void main(String[] args) throws Exception {  
        EncrypAES de1 = new EncrypAES();  
        String msg ="1.jpg";  
        byte[] encontent = de1.Encrytor(msg);  
        byte[] decontent = de1.Decryptor(encontent);  
        System.out.println("明文是:" + msg);  
        System.out.println("加密后:" + new String(encontent));  
        System.out.println("解密后:" + new String(decontent));  
    }

    /**
     * AES解密
     *
     * @param data           //密文，被加密的数据
     * @param key            //秘钥
     * @param iv             //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat) throws Exception {
//        initialize();

        //被加密的数据
        byte[] dataByte = Base64.decode(data);
        //加密秘钥
        byte[] keyByte = Base64.decode(key);
        //偏移量
        byte[] ivByte = Base64.decode(iv);


        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));

            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
  
}  