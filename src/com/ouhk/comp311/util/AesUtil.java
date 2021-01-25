package com.ouhk.comp311.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

public class AesUtil {

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final int IV_BIT_LENGTH = 128;
    private static Key KEY;

    public static byte[] encrypt(String password, String key) {
        try {
            // 加密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
            byte[] result = cipher.doFinal(password.getBytes());
            return result;
        } catch (Exception e) {
            System.out.println("Encrypt error");
        }
        return null;
    }

    public static String decrypt(byte[] data, String key) {
        try {
            // 解密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            byte[] result = cipher.doFinal(data);
            return new String(result);
        } catch (Exception e) {
            System.out.println("Decrypt error");
        }
        return "";
    }


    private static Key getKey(String key) {
        if (KEY == null) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(key.getBytes());
                keyGenerator.init(IV_BIT_LENGTH, secureRandom);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] byteKey = secretKey.getEncoded();

                // 转换KEY
                KEY = new SecretKeySpec(byteKey, "AES");
                return KEY;
            } catch (Exception e) {
                return null;
            }
        } else {
            return KEY;
        }
    }
}
