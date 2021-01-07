package run.zykj.app.subject29;

import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author xiaolin
 * @date 2021/1/7 9:32
 */
public class UserLoginStatusService {
    private static final String host="47.98.178.22";

    private static final int port=6379;

    private static final int BIT_AMOUNT_IN_ONE_BYTE =8;

    private static final Jedis jedis=new Jedis(host,port);

    //日期的初始值（也可以理解为用户的注册时间），
    //下文需要使用日期的偏移量作为redis位图的offset，
    //因此需要将要保存登录状态的日期减去该初始日期。
    //这里使用了Java 8的新日期API
    private static final LocalDate beginDate=LocalDate.of(2021,1,6);

    static {
        jedis.connect();
    }

    public void setLoginStatus(String userId, LocalDate date,boolean isLogin){
        long offset = getDateDuration(beginDate, date);
        jedis.setbit(userId,offset,isLogin);
    }

    public boolean getLoginStatus(String userId,LocalDate date){
        long offset = getDateDuration(beginDate, date);
        return jedis.getbit(userId,offset);
    }

    private long getDateDuration(LocalDate start ,LocalDate end){
        return start.until(end, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        UserLoginStatusService userLoginStatusService=new UserLoginStatusService();
        String userId="user_1";
        LocalDate today = LocalDate.now();
        userLoginStatusService.setLoginStatus(userId,today,true);
        boolean todayLoginStatus = userLoginStatusService.getLoginStatus(userId, today);
        System.out.println(String.format("The loginStatus of %s in %s is %s",userId,today,todayLoginStatus));
        LocalDate yesterday = LocalDate.now().minusDays(1);
        boolean yesterdayLoginStatus = userLoginStatusService.getLoginStatus(userId, yesterday);
        System.out.println(String.format("The loginStatus of %s in %s is %s",userId,yesterday,yesterdayLoginStatus));
    }

    public int bitCountByBitIndex(String key, long startBitIndex, long endBitIndex) {
        int startByteIndex = getByteIndexInTheBytes(startBitIndex);
        int endByteIndex = getByteIndexInTheBytes(endBitIndex);
        byte[] bytes = jedis.getrange(key.getBytes(), startByteIndex, endByteIndex);
        int totalBitInBytes = getTotalBitInBytes(bytes);
        int startBitIndexInFirstByte = getBitIndexInTheByte(startBitIndex);
        int endBitIndexInLastByte = getBitIndexInTheByte(endBitIndex);
        byte firstByte = bytes[0];
        byte lastByte = bytes[bytes.length-1];
        for(int i=7;i>(BIT_AMOUNT_IN_ONE_BYTE-1-startBitIndexInFirstByte);i--){
            if(((firstByte>>i)&1)==1){
                totalBitInBytes--;
            }
        }
        for(int i=0;i<(BIT_AMOUNT_IN_ONE_BYTE-1-endBitIndexInLastByte);i++){
            if(((lastByte>>i)&1)==1){
                totalBitInBytes--;
            }
        }

        return totalBitInBytes;
    }

    private int getTotalBitInBytes(byte[] bytes){
        int count=0;
        for(byte b:bytes){
            for(int i = 0; i< BIT_AMOUNT_IN_ONE_BYTE; i++){
                if(((b>>i)&1)==1){
                    count++;
                }
            }
        }
        return count;
    }

    private int getByteIndexInTheBytes(long offset){
        return (int) offset/ BIT_AMOUNT_IN_ONE_BYTE;
    }

    private int getBitIndexInTheByte(long offset){
        return (int)(offset-offset/ BIT_AMOUNT_IN_ONE_BYTE * BIT_AMOUNT_IN_ONE_BYTE);
    }

}
