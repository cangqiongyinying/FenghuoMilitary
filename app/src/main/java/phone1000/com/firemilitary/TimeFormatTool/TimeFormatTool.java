package phone1000.com.firemilitary.TimeFormatTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shiwei on 2016/9/8.
 */
public class TimeFormatTool {
    public static long getTime(int totaltime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String times = format.format(new Date(totaltime* 1000L));//得到服务器时间
        //得到系统时间
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = format.format(curDate);
        try {
            //得到服务器的时间
            Date parse = format.parse(times);
            //得到系统的时间
            Date parse1 = format.parse(str);
            long time = parse1.getTime() - parse.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
