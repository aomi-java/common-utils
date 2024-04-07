package tech.aomi.common.utils.time;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

/**
 * @author Sean Create At 2020/3/31
 */
public class DateUtilsTest {

    @Test
    public void weekFirstDay() {
        System.out.println("本周第一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.weekFirstDay().getTime()));
    }

    @Test
    public void weekLastDay() {
        System.out.println("本周最有一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.weekLastDay().getTime()));
    }

    @Test
    public void monthFirstDay() {
        System.out.println("本月第一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.monthFirstDay().getTime()));
    }

    @Test
    public void monthLastDay() {
        System.out.println("本月最后一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.monthLastDay().getTime()));
    }

    @Test
    public void yearFirstDay() {
        System.out.println("本年第一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.yearFirstDay().getTime()));
    }

    @Test
    public void yearLastDay() {
        System.out.println("本年最后一天: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(DateUtils.yearLastDay().getTime()));
    }
}
