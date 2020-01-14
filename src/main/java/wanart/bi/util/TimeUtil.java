package wanart.bi.util;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String getTimeNowString(){
        SimpleDateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(new Date());
    }

    public static boolean validTimeFormat(String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        boolean result = true;
        try{
            LocalDateTime.parse(time, df);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
