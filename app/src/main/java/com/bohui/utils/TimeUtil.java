package com.bohui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangwei on 2017/12/4.
 */
public class TimeUtil {
    private TimeUtil() {
    }

    private static TimeUtil instance;

    public static TimeUtil getInstance() {
        if (instance == null) {
            instance = new TimeUtil();
        }
        return instance;
    }

    private String str = null;

    /**
     * 将long类型的字符串转化成为时间
     */
    public String getTime(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp * 1000));
    }
    /**
     * 将long类型的字符串转化成为时间
     */
    public String getTime2(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
    }
    /**
     * 将long类型的字符串转化成为时间
     */
    public String getCnDate(long timestamp) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(timestamp * 1000));
    }

    /**
     * 将long类型的字符串转化成为时间
     */
    public String getCnTime(long timestamp) {
        return new SimpleDateFormat("HH:mm").format(new Date(timestamp * 1000));
    }

    /**
     * 把日期转换成long
     *
     * @param str
     * @return
     */
    public long getLongDate(String str) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date = formatter.parse(str);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 把日期转换成long
     *
     * @param str
     * @return
     */
    public long getLongDate2(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //这块的效率比较低
    public void getJudgeYesterday() {
        try {
            boolean flag = IsYesterday(str);
            if (flag == true) {//是昨天
                //TODO
            } else {//不是昨天
                //TODO
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //这块的效率比较低暂时不做考虑
    public void getJudgetoDay() {
        try {
            boolean flag = IsToday(str);
            if (flag == true) {//是今天
                //TODO
            } else {//不是今天
                //TODO
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间
     */
    public String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//得到当前的时间
        Date curDate = new Date(System.currentTimeMillis());
        return str = formatter.format(curDate);
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     */
    public boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     */
    public static String getTimeInterval(Date date) {
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if (1 == dayWeek) {
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - 1);*/
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        // 今天是一周中的第几天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (calendar.getFirstDayOfWeek() == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 0);
        }
        // 计算一周开始的日期
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            sb.append(sdf.format(calendar.getTime()));
            if (i != 6) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static String minite2Hour(String time) {
        try {
            int s = Integer.parseInt(time);
            int hour = s / 60;
            String newHour = hour > 99 ? "99" : hour < 10 ? "0" + hour : hour + "";
            int minite = s % 60;
            String newMinite = minite < 10 ? "0" + minite : minite + "";
            return newHour + ":" + newMinite;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "00:00";
        }
    }
}
