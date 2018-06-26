package com.xxgames.util;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static long startOfDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static int startOfDay(int time) {
        return (int) (startOfDay(time * 1000L) / 1000);
    }

    /**
     * 获取两天之间的时间差（自然天），用dayTwo减去dayOne。
     * 例如，dayOne（1月1日）与dayTwo（1月2日）的返回值为1。
     * 参数相反为-1；
     *
     * @param dayOne
     * @param dayTwo
     *
     * @return
     */
    public static int getDiffOfDay(int dayOne, int dayTwo) {
        int startOne = startOfDay(dayOne);
        int startTwo = startOfDay(dayTwo);
        return (startTwo - startOne) / 86400;
    }

    public static int getDiffOfDay2(int dayOne, int dayTwo) {
        int startOne = startOfDay(dayOne) - (24 - 3) * 60 * 60;
        ;
        int startTwo = startOfDay(dayTwo) - (24 - 3) * 60 * 60;
        ;
        return (startTwo - startOne) / 86400;
    }

    /**
     * 获取今天是周几
     *
     * @return 0为周日，1为周一，6为周六
     */
    public static int getWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 指定时间是周几
     *
     * @param time
     *
     * @return
     */
    public static int getWeek(int time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到当前时间是一年中的第几周
     *
     * @return
     */
    public static int getWeakOfYear() {
        return getWeakOfYear(nowInt());
    }

    /**
     * 得到指定时间中的周
     *
     * @param time
     *
     * @return
     */
    public static int getWeakOfYear(int time) {
        Calendar c = getCalendar(time);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取本周指定星期数的时间戳，如周一，给定的参数为Calendar.MONDAY
     * <strong>注意，每周开始为周一，周日为最后一天</strong>
     *
     * @param week
     *
     * @return
     */
    public static int getDayBeginForWeek(int week) {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            c.set(Calendar.DAY_OF_WEEK, week);
            return (int) (startOfDay(c.getTimeInMillis()) / 1000) - 86400 * 7;
        }
        c.set(Calendar.DAY_OF_WEEK, week);
        return (int) (startOfDay(c.getTimeInMillis()) / 1000);
    }

    /**
     * 获取今天是在几月份,此类中，返回的month加了一，
     * 如果更改此方法，请注意，用到此方法的其他代码数值会减少一
     *
     * @return 一月返回1，二月返回2，以此类推
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间是在几月份，注意，此处给定的时间为int值。
     *
     * @param time
     *
     * @return 月份，1月返回1，二月返回2，以此类推。
     */
    public static int getMonth(int time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定日期的int值。
     *
     * @param year 2012
     * @param month 给定指定月份记得，如1月给1
     * @param day 天数 周日是1
     *
     * @return
     */
    public static int getIntForDay(int year, int month, int day) {
        return (int) (getCalendar(year, month - 1, day).getTimeInMillis() / 1000);
    }

    /**
     * @param hour 小时数
     * @param day 在本周的第几天
     *
     * @return
     */
    public static int getTimeForWeek(int hour, int day) {
        Calendar cal = Calendar.getInstance();
        if (day == -1 && hour == -1) {
            return -1;
        }

        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (day > 0) {
            cal.set(Calendar.DAY_OF_WEEK, day);
        }
        if (hour > -1) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }

        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static int getTimeForDay(int year, int month, int day, int hour) {
        Calendar cal = Calendar.getInstance();
        if (month == -1 && day == -1 && hour == -1) {
            return -1;
        }
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (year > 0) {
            cal.set(Calendar.YEAR, year);
        }
        if (month > 0) {
            cal.set(Calendar.MONTH, month - 1);
        }
        if (day > 0) {
            cal.set(Calendar.DAY_OF_MONTH, day);
        }
        if (hour > -1) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 本方法按照给定的月日时返回时间戳。
     *
     * @param month -1为本月,参数为自然月，即无0月
     * @param day -1为本日，参数为自然日，即无0日
     * @param hour -1为本时
     *
     * @return 返回时间戳，如果参数均为-1，则返回-1。
     */
    public static int getTimeForDay(int month, int day, int hour) {
        return getTimeForDay(-1, month, day, hour);
    }

    /**
     * 获取指定时间的年份，注意，此处给定的时间为int值。
     *
     * @param time
     *
     * @return
     */
    public static int getYear(int time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的日期，注意，此处给定的时间为int值。
     *
     * @param time
     *
     * @return
     */
    public static int getDay(final int time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar getCalendar(final int time) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(new Date(time * 1000L));
        return c;
    }

    public static Calendar getCalendar(final long time) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(new Date(time));
        return c;
    }

    public static Calendar getCalendar(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month, day);
        return c;
    }

    /**
     * 获取指定年月的最大天数
     *
     * @param year 指定年份
     * @param month 指定月份，一月则给定1即可
     *
     * @return 返回指定月份的最大天数，注意，如果给定参数错误，如month非1到12.则返回-1
     */
    public static int getMaxDay(int year, int month) {
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        case 2:
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                return 29;
            }
            return 28;
        default:
            return -1;
        }
    }

    /**
     * 获取当前月份的最大天数
     *
     * @return
     */
    public static int getMaxDayForTodayThisMonth() {
        return getMaxDay(getYear(), getMonth());
    }

    /**
     * 获取今天的年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取今天的年份,我的家任务专用
     *
     * @return
     */
    public static int getYearForHomeTask() {
        int year = getYear();
        int month = getMonth();
        int weak = getWeakOfYear();
        if (weak == 1 && month == 12) {
            return year + 1;
        }
        return year;
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前的小时
     *
     * @return
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得指定时间的小时
     */
    public static int getHour(int time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getHour(long time) {
        Calendar c = getCalendar(time);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获得当前的分钟
     *
     * @return
     */
    public static int getMin() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取指定时分的时间
     */
    public static int getTime(int day, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        cal.set(year, month, day, hour, min, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取当天0点时的int值
     *
     * @return
     */
    public static int startOfDay() {
        Calendar cal = Calendar.getInstance();
        return (int) (startOfDay(cal.getTimeInMillis()) / 1000);
    }

    public static boolean sameDay(long time1, long time2) {
        return startOfDay(time1) == startOfDay(time2);
    }

    public static boolean sameDay(int time1, int time2) {
        return startOfDay(time1 * 1000L) == startOfDay(time2 * 1000L);
    }

    public static String toString(int time) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH点mm分");
        return sdf.format(cal.getTime());
    }

    public static String toFormatString(int time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeInMillis(time * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());

    }

    public static int fromFormatString(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d;
        try {
            d = sdf.parse(time);
            int l = (int) (d.getTime() / 1000L);
            return l;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return 0;
        }

    }

    public static String formatString(int second, String sign) {
        SimpleDateFormat sdf = new SimpleDateFormat(sign);
        return sdf.format(new Date(second * 1000L));
    }

    public static int getDateStr(String date, String sign) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(sign);
        return (int) (sdf.parse(date).getTime() / 1000);
    }

    public static long nowLong() {
        return System.currentTimeMillis();
    }

    public static int nowInt() {
        return (int) (nowLong() / 1000);
    }

    /**
     * 取两个时间段的交集，要求，参数end大于begin，否则会返回-1
     *
     * @param aBegin 第一段时间的开始时间戳
     * @param aEnd 第一段时间的结束时间戳
     * @param bBegin 第二段时间的开始时间戳
     * @param bEnd 第二段时间的结束时间戳
     *
     * @return 重合的时间，单位秒，如果没有交集，则返回-1
     */
    public static int timeIntersection(int aBegin, int aEnd, int bBegin, int bEnd) {
        if (aBegin > aEnd || bBegin > bEnd) {
            // 结束时间，小于开始时间，错误数据
            return -1;
        } else if (aBegin == aEnd || bBegin == bEnd) {
            // 非时间段，错误数据
            return -1;
        } else if (aEnd < bBegin || bEnd < aBegin) {
            // 一个时间段的结束时间，小于另一个开始时间，无交集
            return -1;
        }
        if (aBegin == bBegin) {
            // 两个时间段的开始时间相等
            if (aEnd >= bEnd) {
                return bEnd - aBegin;
            }
            return aEnd - aBegin;
        } else if (aEnd == bEnd) {
            if (aBegin >= bBegin) {
                return bEnd - aBegin;
            }
            return bEnd - aBegin;
        }
        if (aBegin > bBegin) {
            if (aEnd >= bEnd) {
                return bEnd - aBegin;
            }
            return aEnd - aBegin;
        }
        if (aEnd >= bEnd) {

            return bEnd - bBegin;
        }
        return aEnd - bBegin;
    }

    /**
     * <p>Formats a date/time into a specific pattern.</p>
     *
     * @param date the date to format
     * @param pattern the pattern to use to format the date
     *
     * @return the formatted date
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * <p>Formats a date/time into a specific pattern.</p>
     *
     * @param millis the date to format expressed in milliseconds
     * @param pattern the pattern to use to format the date
     *
     * @return the formatted date
     */
    public static String format(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }

    /**
     * <p>Formats a calendar into a specific pattern.</p>
     *
     * @param calendar the calendar to format
     * @param pattern the pattern to use to format the calendar
     *
     * @return the formatted calendar
     */
    public static String format(Calendar calendar, String pattern) {
        return DateFormatUtils.format(calendar, pattern);
    }

    /**
     * <p>Parses a string representing a date.</p>
     *
     * @param str the date to parse, not null
     * @param parsePattern the date format patterns to use, see SimpleDateFormat, not null
     *
     * @return the parsed date
     *
     * @throws ParseException if none of the date patterns were suitable
     */
    public static Date parse(String str, String parsePattern) throws ParseException {
        return DateUtils.parseDate(str, new String[] { parsePattern });
    }

    /**
     * 根据日期格式创建格式化器<br>
     * SimpleDateFormat并不支持多线程访问，所以尽可能使用format和parse接口
     *
     * @param pattern
     *
     * @return
     */
    public static DateFormat getDateFormatter(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static Date nowDate() {
        return new Date();
    }

    public static boolean between(int beginTime, int endTime) {
        int nowInt = TimeUtil.nowInt();
        if (nowInt >= beginTime && nowInt <= endTime) {
            return true;
        }
        return false;
    }

    public static boolean crossDay(int time1, int time2) {
        if (time1 == 0 || time2 == 0) {
            return true;
        }
        int hour1 = TimeUtil.getHour(time1);
        int dayBegin1;
        int dayEnd1;
        if (hour1 >= 3) {
            dayBegin1 = TimeUtil.startOfDay(time1) + 3 * 60 * 60;
            dayEnd1 = TimeUtil.startOfDay(time1) + (24 + 3) * 60 * 60;
        } else {
            dayBegin1 = TimeUtil.startOfDay(time1) - (24 - 3) * 60 * 60;
            dayEnd1 = TimeUtil.startOfDay(time1) + 3 * 60 * 60;
        }
        if ((time2 > dayEnd1) || time2 < dayBegin1) {
            return true;
        }
        return false;
    }

    public static boolean crossMonth(int time1, int time2) {
        if (time1 == 0 || time2 == 0) {
            return true;
        }
        return getMonth((time1 - 3 * 60 * 60)) != getMonth((time2 - 3 * 60 * 60));
    }

    /**
     * 是否在几点到几点之间
     *
     * @param hour1
     * @param hour2
     *
     * @return
     */
    public static boolean betweenHour(int hour1, int hour2) {
        int nowHour = TimeUtil.getHour();
        if (nowHour >= hour1 && nowHour <= hour1) {
            return true;
        }
        return false;
    }

    public static boolean betweenHour(int nowHour, int hour1, int hour2) {
        if (nowHour >= hour1 && nowHour <= hour1) {
            return true;
        }
        return false;
    }

    public static boolean betweenHour(int nowHour, String hourStr) {
        String[] split = hourStr.split("~");
        if (split[1].equals("0")) {
            split[1] = "24";
        }
        int beginHour = Integer.parseInt(split[0]);
        int endHour = Integer.parseInt(split[1]);
        if (nowHour >= beginHour && nowHour < endHour) {
            return true;
        }
        return false;
    }

    public static boolean betweenHour(String hourStr) {
        return betweenHour(TimeUtil.getHour(), hourStr);
    }

    public static int getWeekBeginTime(final int time) {
        Calendar c = getCalendar(time);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static void main(String[] args) throws ParseException {
//		TimeUtil.parse("2012-02-01 14:32:32", "yyyy-MM-dd HH:mm:ss");
//		int day1 = fromFormatString("2012-02-01 14:32:32", "yyyy-MM-dd HH:mm:ss");
//		int day2 = fromFormatString("2012-02-07 12:32:32", "yyyy-MM-dd HH:mm:ss");
//		System.out.println(getDiffOfDay(day1, day2));
//		System.out.println(TimeUtil.parse("2012-02-01 14:32:32", "yyyy-MM-dd HH:mm:ss"));
//		System.out.println("nowLong=" + nowLong());
//
//		int hour = TimeUtil.getHour();
//		System.out.println(hour);
//		int hour2 = TimeUtil.getHour(1000L);
//		System.out.println(hour2);
//
//		String format = TimeUtil.format(nowDate(), "yyyyMMddHHmmss");
//		System.out.println(format);
//
//		int nowInt = TimeUtil.nowInt();
//		System.out.println("nowInt=" + nowInt);
//		int eone = 1410332932;
//		System.out.println(nowInt - eone);
//
//		int startOfDay = TimeUtil.startOfDay(eone);
//		System.out.println(startOfDay);
//		
//		int fromFormatString = TimeUtil.fromFormatString("20140922151515", "yyyyMMddHHmmss");
//		System.out.println(fromFormatString);
//		
//		int secondsOneday = 24 * 60 * 60;
//		System.err.println(secondsOneday);
//		
////		int t = Integer.parseInt("");
////		System.err.println("t="+t);
//		System.err.println("hour="+TimeUtil.getHour());
//		
//		int dayBeginForWeek = NowTime.getWeekDay();
//		System.err.println(dayBeginForWeek);
//		
//		System.err.println("wb:"+getWeekBeginTime(TimeUtil.nowInt()));
//		
//		int month = TimeUtil.getMonth();
//		System.err.println("month="+month);
//		int month2 = TimeUtil.getMonth(1433174400);
//		System.err.println("month2="+month2);
//		
//		boolean sameMonth = TimeUtil.sameMonth(TimeUtil.nowInt(), 1433174400);
//		System.err.println("sameMonth="+sameMonth);

//		int nowInt = TimeUtil.nowInt();
//		boolean crossDay = TimeUtil.crossDay(nowInt, 1440703830);
//		System.err.println("crossDay="+crossDay);
//		
//		boolean crossMonth = TimeUtil.crossMonth(1441045830, 1441056830);
//		System.err.println("crossMonth="+crossMonth);
//		
//		int month = TimeUtil.getMonth();
//		System.err.println("month="+month);

//		System.err.println(TimeUtil.getHour());

//		boolean crossDay = TimeUtil.crossDay(1442034418, 1442196930);
//		System.err.println("crossDay="+crossDay);
//		boolean crossDay2 = TimeUtil.crossDay(1442170799, 1442174399);
//		System.err.println("crossDay2="+crossDay2);
//		boolean crossDay3 = TimeUtil.crossDay(1442087999, 1441915199);
//		System.err.println("crossDay3="+crossDay3);
//		
//		int day = TimeUtil.getDay();
//		int month = TimeUtil.getMonth();
//		int year = TimeUtil.getYear();
//		int hour = TimeUtil.getHour();
//		System.err.println(year+"-"+month+"-"+day+"-"+hour);
//		
//		System.err.println(TimeUtil.getHour(1442199857));

        boolean crossDay = TimeUtil.crossDay(0, TimeUtil.nowInt());
        System.err.println(crossDay);

        int min = TimeUtil.getMin();
        System.err.println(min);
    }

}
