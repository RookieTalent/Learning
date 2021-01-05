package run.zykj.app.subject14.utils;

import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author xiaolin
 * @date 2021/1/5 14:57
 */
public class VerityCodeUtils {

    public static final String VERIFY_CODES = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String NUMBER_VERIFY_CODES = "123456789";

    private static Random random = new SecureRandom();

    public static String generateVerifyCode(int verifySize){
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    public static String generateVerifyCode(int verifySize, String source){
        if(!StringUtils.isNotBlank(source)){
            source = VERIFY_CODES;
        }

        int length = source.length();
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++){
            sb.append(source.charAt(random.nextInt(length - 1)));
        }

        return sb.toString();
    }

}
