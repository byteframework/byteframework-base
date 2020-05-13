package com.byteframework.base.user.util;


import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加解密工具类
 * <p>
 * 注意：
 * 私钥加密： 私钥、公钥都可以解密
 * 公钥加密： 只有私钥可以解密
 * </p>
 *
 * @author sa
 * @date 2020-04-26
 */
public class RSAUtil {

    /**
     * 公钥
     */
    private static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMLO/No3u9SNIFlGraYSZwHZ4kNRiWGJzSNzJJ5Z+HWRzNQCXA7p6LkNQBRdNbZ8Ki/Wvx/XX2wUHpSvk1HtsNECAwEAAQ==";

    /**
     * 私钥
     */
    private static String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAws782je71I0gWUatphJnAdniQ1GJYYnNI3Mknln4dZHM1AJcDunouQ1AFF01tnwqL9a/H9dfbBQelK+TUe2w0QIDAQABAkBomigfLEoJCXZTOaZIRB1XWx0pNIDmBM5HuK3y8TB+P8horP14ZOzrli0+xfQ48y0aylKuelChAQrMtLnvUPf5AiEA+C47Jq9vs154YCgmTkgvONmwy/oGSN37z+e/fwyWGjsCIQDI8kUL+942dLRiD9Zyk5h0ZkJAqX9xO6jM+3rc8D/kYwIgIVrVSFW7xXek5gd/XnYFIrbfNAreXVy4QzD6Y7DlW5ECIQC9BnF/KWnKDhRVHpLa6w6BiGQQUaGJkdZjJLlZtkJUAwIhAJ881zuAI8AOpb04tSNVZyo8W/YIZ2JTBf9nfbNXVDua";


    /**
     * 生成公钥和私钥
     */
    public static void generateKey() {
        // 1.初始化秘钥
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 随机数生成器
            SecureRandom sr = new SecureRandom();
            //设置512位长的秘钥
            keyPairGenerator.initialize(512, sr);
            // 开始创建
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 进行转码
            publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            // 进行转码
            privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
            System.out.println("publicKey: " + publicKey);
            System.out.println("privateKey: " + privateKey);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 私钥匙加密
     *
     * @param content
     * @param privateKeyStr
     * @return
     */
    public static String encryptByPrivateKey(String content, String privateKeyStr) {
        //私钥要用PKCS8进行处理
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        String text = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //还原Key对象
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 加密
            byte[] result = cipher.doFinal(content.getBytes());
            text = Base64.encodeBase64String(result);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }


    /**
     * 私钥匙解密
     *
     * @param content
     * @param privateKeyStr
     * @return
     */
    public static String decryptByPrivateKey(String content, String privateKeyStr) {
        // 私钥要用PKCS8进行处理
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        String text = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 还原Key对象
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 解密
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            text = new String(result, "UTF-8");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }


    /**
     * 公钥匙加密
     *
     * @param content
     * @param publicKeyStr
     * @return
     */
    public static String encryptByPublicKey(String content, String publicKeyStr) {
        // 公钥要用X509进行处理
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        String text = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 还原Key对象
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 加密
            byte[] result = cipher.doFinal(content.getBytes());
            text = Base64.encodeBase64String(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }


    /**
     * 公钥匙解密
     *
     * @param content
     * @param publicKeyStr
     * @return
     */
    public static String decryptByPublicKey(String content, String publicKeyStr) {
        //公钥要用X509进行处理
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        String text = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //还原Key对象
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            //解密
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            text = new String(result, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }


    /**
     * 获取 PublicKey 对象
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey() throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 获取 PrivateKey 对象
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        /**
         * 注意：
         *   私钥加密必须公钥解密
         *   公钥加密必须私钥解密
         */
        System.out.println("-------------生成一对秘钥，分别发送方和接收方保管-------------");
        RSAUtil.generateKey();


        System.out.println();
        System.out.println("-------------第一个栗子，私钥加密私钥、公钥解密-------------");
        String textsr = "私钥加密可以用私钥或者公钥解密！";
        //私钥加密
        String cipherText = RSAUtil.encryptByPrivateKey(textsr, privateKey);
        System.out.println("私钥加密后：" + cipherText);
        //私钥解密
        String text = RSAUtil.decryptByPublicKey(cipherText, publicKey);
        System.out.println("私钥解密后：" + text);
        //公钥解密
        text = RSAUtil.decryptByPublicKey(cipherText, publicKey);
        System.out.println("公钥解密后：" + text);


        System.out.println();
        System.out.println("-------------第二个栗子，公钥加密私钥解密-------------");
        //公钥加密
        textsr = "公钥加密只能用私钥解密";
        cipherText = RSAUtil.encryptByPublicKey(textsr, publicKey);
        System.out.println("公钥加密后：" + cipherText);
        //私钥解密
        text = RSAUtil.decryptByPrivateKey(cipherText, privateKey);
        System.out.print("私钥解密后：" + text);

    }

}
