package run.zykj.app.subject8.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author xiaolin
 * @date 2021/1/6 11:04
 */
public class shortUrlUtils {

    public static  final  String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};



    /**
     * 一个长链接URL转换为4个短KEY
     */
    public static String[] shortUrl(String url) {
        String key = "";
        //对地址进行md5
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        System.out.println(sMD5EncryptResult);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            //取出8位字符串，md5 32位，被切割为4组，每组8个字符
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            //先转换为16进账，然后用0x3FFFFFFF进行位与运算，目的是格式化截取前30位
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

            String outChars = "";
            for (int j = 0; j < 6; j++) {
                //0x0000003D代表什么意思？他的10进制是61，61代表chars数组长度62的0到61的坐标。
                //0x0000003D & lHexLong进行位与运算，就是格式化为6位，即61内的数字
                //保证了index绝对是61以内的值
                long index = 0x0000003D & lHexLong;

                outChars += chars[(int) index];
                //每次循环按位移5位，因为30位的二进制，分6次循环，即每次右移5位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }



}
