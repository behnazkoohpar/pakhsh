package com.koohpar.dstrbt.utils;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Shahab on 3/29/2017.
 */

public class TextEncrypter {

    static SecretKeySpec secretKey;
    static String iv = "AAAAAAAA";

//    public static String encrypt(String message , Context context) throws Exception {
//
////        byte[] digestOfPassword = Base64.decode(taxidarbastApp.encryptionSecretKey , 0);
//        byte[] digestOfPassword1 =  context.getResources().StringLanguage.encryption_secret_key).getBytes("UTF-8");
//        SecretKey key = new SecretKeySpec(digestOfPassword1, "DESede");
//        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key , new IvParameterSpec(iv.getBytes("UTF-8")));
//        byte[] plainTextBytes = message.getBytes("UTF-8");
//        byte[] buf = cipher.doFinal(plainTextBytes);
//        String base64EncryptedString = Base64.encodeToString(buf, 0);
//        base64EncryptedString = base64EncryptedString.substring(0, base64EncryptedString.length() - 1);
//        return base64EncryptedString;
//    }
//
//    public static String decrypt(String message , Context context) throws Exception {
//        byte[] digestOfPassword1 = context.getResources().StringLanguage.encryption_secret_key).getBytes("UTF-8");
//
//        SecretKey key = new SecretKeySpec(digestOfPassword1, "DESede");
//        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, key , new IvParameterSpec(iv.getBytes("UTF-8")));
//
//        byte[] plainTextBytes = Base64.decode(message , 0);
//        byte[] buf = cipher.doFinal(plainTextBytes);
//        String base64EncryptedString = new String(buf , 0);
//        return base64EncryptedString;
//    }

    public static String MD5String(String message ){
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(message.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return message;
        }
    }

}
