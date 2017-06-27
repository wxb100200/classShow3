package com.hz.school.util;

import java.util.Random;

public class RandomUtil {
    
    static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    static char[] numberSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    
    private static int LENGTH = 8;
    
    /**
     * 默认产生6位 数字随机密码
     * @return
     */
    public static String generateRandomPassword(int... length) {
        Random random = new Random();
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        int len = LENGTH;
        if(length.length > 0){
            len = length[0];
        }
        for (int i = 0; i < len; i++) {
            String rand = String.valueOf(numberSequence[random.nextInt(10)]);
            sRand += rand;
        }
        return sRand;
    }
    
    public static String generateRandomUuid() {
        Random random = new Random();
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        for (int i = 0; i < LENGTH; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(62)]);
            sRand += rand;
        }
        return sRand;
    }
    
    public static void main(String[] args) {
        System.out.println(RandomUtil.generateRandomPassword(8));
    }

}
