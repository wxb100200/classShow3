package com.hz.school.util;

import cn.hillwind.common.util.BASE64Codec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * 加密工具类。
 * For Mgmt2Resource ONLY .
 */
public class CryptoUtil3 {

    private CryptoUtil3(){}

    private static Cipher aesCipherIn;
    private static Cipher aesCipherOut;
    private final static int BUFFER_SIZE = 100 * 1024;

    private static String testKey = "1d3Jff3839sxjF22";

    private static SecretKeySpec key = null;
    private static IvParameterSpec iv = null;

    static{
        try {
            aesCipherIn = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipherOut = Cipher.getInstance("AES/CBC/PKCS5Padding");

            key = new SecretKeySpec(testKey.getBytes("utf-8"), "AES");
            iv = new IvParameterSpec(testKey.getBytes("utf-8"));

        } catch ( UnsupportedEncodingException e ){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成aes256位的key。
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static byte[] generateAES256Key() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(256); // 192 and 256 bits may not be available
        kgen.init(128);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * aes流加密。
     * @param is
     * @param os
     * @param keyBytes
     * @throws Exception
     */
    /*public static void aesEncrypt(InputStream is,OutputStream os,byte[] keyBytes) throws InvalidKeyException, InvalidAlgorithmParameterException {
        aesCipherIn.init(Cipher.ENCRYPT_MODE, key, iv);
        CipherOutputStream cos = new CipherOutputStream(os,aesCipherIn);
        IOUtil.write(cos, is, BUFFER_SIZE);
        IOUtil.close(cos);
    }*/

    /**
     * aes流解密。
     * @throws InvalidKeyException
     */
    /*public static void aesDecrypt(InputStream is,OutputStream os,byte[] keyBytes) throws InvalidKeyException, InvalidAlgorithmParameterException {
        aesCipherOut.init(Cipher.DECRYPT_MODE, key,iv);
        CipherInputStream cis = new CipherInputStream(is,aesCipherOut);
        IOUtil.write(os, cis, BUFFER_SIZE);
        IOUtil.close(cis);
    }*/

    public static void encryptFile(File in,File out){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(in);
            fos = new FileOutputStream(out);
//            aesEncrypt(fis, fos, testKey.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   /* public static void decryptFile(InputStream fis, File out){
        FileOutputStream fos = null;
        try {
            fos =  new FileOutputStream(out);
            aesDecrypt(fis, fos, testKey.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            IOUtil.close(fos);
        }
    }*/

    public static String encrypt(String plain){
        try {
            plain = plain.trim();
            aesCipherIn.init(Cipher.ENCRYPT_MODE, key,iv);
            byte[] result = aesCipherIn.doFinal(plain.getBytes("utf-8"));
            byte[] b64 = BASE64Codec.encode(result);
            return new String(b64,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 如果在url/cookie/header中传输base64的内容，遇到 +/= 等字符，会出现问题，所以要求前段先escape一下，即用 -@_ 替换掉 +/= 。
    public static String unescapeBase64(String str){
        if(str==null) return null;
        str = str.replaceAll("-", "+").replaceAll("_", "/").replaceAll("~", "=");
        return str;
    }

    public static String decrypt(String cipher){
        if(cipher==null) return null;
        try {
            cipher = cipher.trim();
            cipher = unescapeBase64(cipher);
            aesCipherIn.init(Cipher.DECRYPT_MODE, key,iv);
            byte[] content = BASE64Codec.decode(cipher.getBytes("utf-8"));
            byte[] result = aesCipherIn.doFinal(content);
            return new String(result,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
//        String a="afdsf;dlfjldkfj";
//
//        System.out.println(a.split(";")[0]);

//        File f = new File("d:/haikey.txt");
//        File f2 = new File("d:/haikeyc.txt");
//
//        encryptFile(f,f2);
//        System.out.println(decryptFile(new FileInputStream(f2)));


//        String aa = encrypt("SELECT * FROM am_contract_acp");
        String bb = encrypt("201403272042");

        //String aa = encrypt("delete from am_pro_suigong");
//        String aa = encrypt("update am_person set rs='Invalid' where id in(2741,2742);");
//        System.out.println("result : ["+aa+"]");
        System.out.println("result : ["+bb+"]");
//        System.out.println(b);
//        byte[] keyBytes = generateAES256Key();
//        System.out.println("length : "+keyBytes.length);
//        InputStream fis = new FileInputStream(new File("c:/Setup.exe"));
//        OutputStream fos = new FileOutputStream(new File("d:/temp/dd.exe"));
//
//        aesEncrypt(fis, fos, keyBytes);
//
//        String xx = new String(Base64.encode(keyBytes),"utf-8");
//
//        System.out.println(" key string : "+xx);
//        byte[] keyBytes2 = Base64.decode(xx.getBytes("utf-8"));
//
//        String yy = new String(Base64.encode(keyBytes2),"utf-8");
//        System.out.println(" key string : "+yy);
//
//        InputStream fis2 = new FileInputStream(new File("d:/temp/dd.exe"));
//        OutputStream fos2 = new FileOutputStream(new File("d:/temp/dd2.exe"));
//
//        aesDecrypt(fis2, fos2, keyBytes2);
//

//        byte[] aa = "c:/你好/aba!".getBytes("utf-8");
//        String s64ed = new String(BASE64Codec.encode(aa),"utf-8");
//        System.out.println(s64ed);
//        System.out.println(new String(Base64.decode(s64ed),"utf-8"));

    }


}
