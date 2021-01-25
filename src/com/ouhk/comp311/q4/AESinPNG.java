package com.ouhk.comp311.q4;

import com.ouhk.comp311.util.AesUtil;
import com.ouhk.comp311.util.ImageUtility;
import com.ouhk.comp311.util.MD5Util;

public class AESinPNG {

    public static void main(String[] args) throws Exception {
        if (args == null) {
            System.out.println("Please input arguments.");
            return;
        }
        String action = args[0];
        String key = args[1];

        switch (action) {
            case "-encrypt":
                String data = args[2];
                String encryptKey = MD5Util.md5Encrypt(key);
                byte[] encryptData = AesUtil.encrypt(data, encryptKey);
                String inputFile = args[3];
                String outputFile = args[4];
                ImageUtility.writePNGCustomData(inputFile, outputFile, encryptKey, encryptData);
                System.out.println("Encrypt success");
                break;
            case "-decrypt":
                String decryptKey = MD5Util.md5Encrypt(key);
                String decryptFile = args[2];
                byte[] imageData = ImageUtility.readPNGCustomData(decryptFile, decryptKey);
                String decrypt = AesUtil.decrypt(imageData, decryptKey);
                System.out.println(decrypt);
                break;
            default:
                System.out.println("Error Action");
                break;
        }
    }
}
