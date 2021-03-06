package tech.aomi.common.utils.time;

import java.util.Calendar;
import java.util.Date;

/**
 * Date Util
 *
 * @author 田尘殇Sean(sean.snow @ live.com) createAt 2016/12/16
 */
public class DateUtils {

    /**
     * 一周的开始是星期几
     * 默认为星期一
     */
    private static int WEEK_START_AT = 2;

    /**
     * 获取昨天
     */
    public static Calendar yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    /**
     * @return 一天的开始时间
     */
    public static Calendar dayStartTime() {
        return dayStartTime((Calendar) null);
    }

    public static Calendar dayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dayStartTime(calendar);
    }

    /**
     * @param calendar 时间信息
     * @return 一天的开始时间
     */
    public static Calendar dayStartTime(Calendar calendar) {
        if (null == calendar)
            calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static Calendar dayEndTime() {
        return dayEndTime((Calendar) null);
    }

    public static Calendar dayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dayEndTime(calendar);
    }

    /**
     * @param calendar 时间
     * @return 一天的结束时间
     */
    public static Calendar dayEndTime(Calendar calendar) {
        if (null == calendar)
            calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    /**
     * 获取一周的第一天
     *
     * @return 本周第一天的时间信息
     */
    public static Calendar weekFirstDay() {
        return weekFirstDay(new Date());
    }

    /**
     * @param date 时间
     * @return 一周的第一天
     */
    public static Calendar weekFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, WEEK_START_AT);
        dayStartTime(calendar);
        return calendar;
    }

    public static Calendar weekLastDay() {
        return weekLastDay(new Date());
    }

    public static Calendar weekLastDay(Date date) {
        Calendar calendar = weekFirstDay(date);
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        dayEndTime(calendar);
        return calendar;
    }

    /**
     * @return 获取本月的第一天时间
     */
    public static Calendar monthFirstDay() {
        return monthFirstDay(new Date());
    }

    /**
     * 获取指定时间月的第一天时间
     *
     * @param date time
     * @return date 的第一天
     */
    public static Calendar monthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        dayStartTime(calendar);
        return calendar;
    }

    /**
     * 获取本月最后一天
     */
    public static Calendar monthLastDay() {
        return monthLastDay(new Date());
    }

    public static Calendar monthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayEndTime(calendar);
        return calendar;
    }

    public static Calendar yearFirstDay() {
        return yearFirstDay(new Date());
    }

    public static Calendar yearFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        dayStartTime(calendar);
        return calendar;
    }

    public static Calendar yearLastDay() {
        return yearLastDay(new Date());
    }

    public static Calendar yearLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        dayEndTime(calendar);
        return calendar;
    }

    /**
     * 判断date是否在start 和 end 之间
     */
    public static boolean inRange(Date start, Date end, Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.after(start) && calendar.before(end);
    }

    /**
     * 判断date是否在start 和 end 之间
     */
    public static boolean inTimeRange(Date start, Date end, Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.set(1993, Calendar.SEPTEMBER, 22);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        endCalendar.set(1993, Calendar.SEPTEMBER, 22);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.after(start) && calendar.before(end);
    }

    /**
     * 设置一周开始的时间
     *
     * @param startAt 一周开始的值
     */
    public static void setWeekStartAt(int startAt) {
        WEEK_START_AT = startAt;
    }

}
