package run.zykj.app.subject25.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/6 16:20
 */
public class StbStringUtils {

    /**
     * 目前只支持英文
     *
     * @param source
     * @return
     */
    public static List<String> subStringList(String source){
        // 转小写
        source.toLowerCase();
        List<String> list = new ArrayList<>();
        String temp = null;

        for (int i = 0; i < source.length(); i++){
            if((i+1) < source.length()){
                temp = source.substring(0,i+1);
                list.add(temp);
            }else{
                temp = source + "*";
                list.add(temp);
            }
            temp = null;
        }

        return list;
    }

}
