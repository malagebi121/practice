import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author embrace
 * @describe
 * @date created in 2020/12/16 16:44
 */
public class Test {
    public static void main(String[] args) throws ParseException {
//        AtomicInteger integer = new AtomicInteger(0);
//        for(int i = 0; i < 1000; i++){
//            integer.incrementAndGet();
//            System.out.println(integer.get());
//        }
        String a = "2020-10-01".substring(5,7);
        SimpleDateFormat datef = new  SimpleDateFormat( "yyyy-MM-dd" );
        Date date= datef.parse("2019-02-01");
//        DateUtil.calendar();
        Calendar cal =  Calendar.getInstance();
        cal.setTime(date);
        // 当前月的最后一天
        // 当前月的最后一天
        cal.set( Calendar.DATE,  1  );
        cal.roll(Calendar.DATE,  -   1  );
        Date endTime = cal.getTime();
        String endTime1 = datef.format(endTime) + "  23:59:59 " ;
        // 当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH,  1 );
        Date beginTime = cal.getTime();
        String beginTime1 = datef.format(beginTime) + "  00:00:00 " ;
//        System.out.println(cal.get(cal.YEAR) + "  "+cal.get(cal.MONTH) + "  "+beginTime1  +  " "  + endTime1);
        System.out.println("2020-10-01".substring(0,7));
    }
}
