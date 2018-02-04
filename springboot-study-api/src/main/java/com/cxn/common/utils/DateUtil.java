package com.cxn.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import com.cxn.common.pojo.ServiceException;

/**
 * 时间工具类
 */
public class DateUtil {
    
    public static final String DATE_FORMATE_STRING_DEFAULT = "yyyyMMddHHmmss";
    public static final String DATE_FORMATE_STRING_A = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATE_STRING_B = "yyyy-MM-dd";
    public static final String DATE_FORMATE_STRING_C = "MM/dd/yyyy HH:mm:ss a";
    public static final String DATE_FORMATE_STRING_D = "yyyy-MM-dd HH:mm:ss a";
    public static final String DATE_FORMATE_STRING_E = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMATE_STRING_F = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DATE_FORMATE_STRING_G = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String DATE_FORMATE_STRING_H = "yyyyMMdd";
    public static final String DATE_FORMATE_STRING_I = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMATE_STRING_J = "yyyyMMddHHmmss.SSS";
    public static final String DATE_FORMATE_STRING_K = "yyyyMM";
    public static final String DATE_FORMATE_STRING_L = "yyyy-MM-dd-HH";
    public static final String DATE_FORMATE_STRING_M = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_FORMATE_STRING_N = "yyyy/MM/dd HH:mm";
    public static final String PARTTEN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PARTTEN_yyyy_MM_dd_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PARTTEN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String PARTTEN_HH_mm_SS = "HH:mm:ss";

    public static final String FORMATE_TIMESTAMP = DATE_FORMATE_STRING_I;
    public static final String FORMATE_DATETIME = DATE_FORMATE_STRING_A;
    public static final String FORMATE_DATE = DATE_FORMATE_STRING_B;
 
    /**
     * 将Date转换为 pattern 格式的字符串，格式为：
     * yyyyMMddHHmmss
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * MM/dd/yyyy HH:mm:ss a
     * yyyy-MM-dd HH:mm:ss a
     * yyyy-MM-dd'T'HH:mm:ss'Z'
     * yyyy-MM-dd'T'HH:mm:ssZ
     * yyyy-MM-dd'T'HH:mm:ssz
     * yyyyMM
     * yyyy-MM-dd-HH
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return String --格式化的日期字符串
     * @see Date
     */
    public static String getFormatTimeString(Date date, String pattern) {
        SimpleDateFormat sDateFormat = getDateFormat(pattern);
        return sDateFormat.format(date);
    }

    public static Date getDate(Date date, String pattern) {
        String str = getFormatTimeString(date, DateFormat.DATETIME.toString());

        try {
            return getDateFromString(str, pattern);
        } catch (ServiceException e) {
            return date;
        }
    }

    /**
     * 将Date转换为默认的YYYYMMDDHHMMSS 格式的字符串
     *
     * @param date
     * @return
     */
    public static String getDefaultFormateTimeString(Date date) {
        return getFormatTimeString(date, DATE_FORMATE_STRING_DEFAULT);
    }

    /**
     * 根据pattern取得的date format
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat;
    }

    /**
     * 将格式将日期字符串转换为Date对象
     *
     * @param date    字符串
     * @param pattern 格式如下：
     *                yyyyMMddHHmmss
     *                yyyy-MM-dd HH:mm:ss
     *                yyyy-MM-dd
     *                MM/dd/yyyy HH:mm:ss a
     *                yyyy-MM-dd HH:mm:ss a
     *                yyyy-MM-dd'T'HH:mm:ss'Z'
     *                yyyy-MM-dd'T'HH:mm:ssZ
     *                yyyy-MM-dd'T'HH:mm:ssz
     * @return 日期Date对象
     * @throws ParseException
     * @see Date
     */
    public static Date getDateFromString(String date, String pattern){
        SimpleDateFormat sDateFormat = getDateFormat(pattern);
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
			return sDateFormat.parse(date);
		} catch (ParseException e) {
			throw new ServiceException(50,"日期格式转换异常:"+date+"->"+pattern,e);
		}
    }

    /**
     * 将日期字符串转化成默认格式YYYYMMDDHHMMSS的Date对象
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getDefaultDateFromString(String date){
        return getDateFromString(date, DATE_FORMATE_STRING_DEFAULT);
    }

    /**
     * 取当前时间,格式为YYYYMMDDHHMMSS的日期字符串
     *
     * @return 当前日期字符串
     * @throws ParseException
     * @see Date
     */
    public static String getNowDefault() {
        return getNow(DATE_FORMATE_STRING_DEFAULT);
    }

    /**
     * 按照pattern格式取当前日期字符串
     *
     * @param pattern 日期字符串格式
     * @return 格式化后的当前日期字符串
     */
    public static String getNow(String pattern) {
        return getFormatTimeString(new Date(), pattern);
    }

    /**
     * 取当前时间,格式为YYYYMMDD
     *
     * @return 当前日期字符串
     * @throws ParseException
     * @see Date
     */
    public static String getNowII() {
        return getFormatTimeString(new Date(), DATE_FORMATE_STRING_H);
    }

    /**
     * 将输入pattern格式的日期字符串转换为取时间的毫秒数 since 1976
     *
     * @return 时间毫秒数
     * @throws ParseException
     * @see Date
     */
    public static long dateString2Long(String str, String pattern){
        return getDateFromString(str, pattern).getTime();
    }

    /**
     * 把since1976的毫秒数转成默认格式yyyyMMddHHmmss的String日期字符串
     *
     * @param time
     * @return
     */
    public static String longToDateStringDefault(long time) {
        return getFormatTimeString(new Date(time), DATE_FORMATE_STRING_DEFAULT);
    }

    /**
     * 将时间的毫秒数 since 1976转换为按照pattern格式的日期字符串
     *
     * @return 日期字符串
     * @see Date
     */
    public static String longToDateString(long time, String pattern) {
        return getFormatTimeString(new Date(time), pattern);
    }

    /**
     * 将Date对象转成since 1976的毫秒数
     *
     * @param date
     * @return since1976的毫秒数
     */
    public static long date2Long(Date date) {
        return date.getTime();
    }

    /**
     * 将since1976毫秒数转成Date对象
     *
     * @param time
     * @return
     */
    public static Date longToDate(Long time) {
        if (time == null) {
            return null;
        }
        return new Date(time);
    }

    /**
     * 自动适配两种格式的日期字符串转换为date对象
     * A格式	:	yyyy-MM-dd HH:mm:ss
     * B格式	:	yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getDateFromStringAdaptTwoPattern(String date){
        try {
            return getDateFromString(date, DATE_FORMATE_STRING_A);
        } catch (ServiceException e) {
            return getDateFromString(date, DATE_FORMATE_STRING_B);
        }
    }

    /**
     * 计算调用时间
     *
     * @param baseTime
     * @return
     */
    public static String calcInterval(Date baseTime) {
        String temp = String.valueOf(System.currentTimeMillis() - baseTime.getTime());
        baseTime.setTime(System.currentTimeMillis());
        return temp;
    }

    /**
     * 获取当前时间前一天【格式为：yyyy-MM-dd】
     *
     * @return
     */
    public static String getDayOfBefore() {
        return getDayOfBefore(DATE_FORMATE_STRING_B);
    }

    /**
     * 获取当前时间前一天
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayOfBefore(String pattern) {
        return getDayByOffset(pattern,-1);
    }

    /**
     * 获取当前时间后一天
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayOfAfter(String pattern) {
        return getDayByOffset(pattern,1);
    }

    /**
     * 获取当前时间前/后 offset 天
     * @param pattern 日期格式
     * @param offset 偏移量<strong>【注】大于0是后 offset 天，小于0是前 offset 天</strong>
     * @return 结果
     */
    public static String getDayByOffset(String pattern,int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return getFormatTimeString(calendar.getTime(), pattern);
    }

    /**
     * 获取指定时间前/后 offset 天
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @param offset 偏移量<strong>【注】大于0是后 offset 天，小于0是前 offset 天</strong>
     * @return 结果
     */
    public static String getDayByOffset(String dateStr, String pattern,int offset){
        Date toDate = getDateFromString(dateStr, pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(toDate);
        calendar.add(GregorianCalendar.DATE, offset);
        toDate = calendar.getTime();
        return getFormatTimeString(toDate, pattern);
    }
    /**
     * 获取指定时间前/后 offset 天
     * @param date 日期
     * @param pattern 日期格式
     * @param offset 偏移量<strong>【注】大于0是后 offset 天，小于0是前 offset 天</strong>
     * @return 结果
     */
    public static Date getDayByOffsetDate(Date date, String pattern,int offset){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DATE, offset);
        date = calendar.getTime();
        return getDate(date, pattern);
    }

    /**
     * 获取指定时间前一天
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayOfBefore(String dateStr, String pattern){
        return getDayByOffset(dateStr, pattern,-1);
    }

    /**
     * 获取指定时间后一天
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayOfAfter(String dateStr, String pattern){
    	return getDayByOffset(dateStr, pattern,-1);
    }

    /**
     * 获取当前时间前两天【格式为：yyyy-MM-dd】
     * @return 结果
     */
    public static String getDayBeforeYesterday() {
        return getDayBeforeYesterday(DATE_FORMATE_STRING_B);
    }

    /**
     * 获取当前时间前两天
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayBeforeYesterday(String pattern) {
        return getDayByOffset(pattern,-2);
    }

    /**
     * 获取指定时间前两天
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 结果
     */
    public static String getDayBeforeYesterday(String dateStr, String pattern) {
        return getDayByOffset(dateStr, pattern,-2);
    }

    /**
     * 获得当月第一天【格式：yyyy-MM-dd】
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        return getFormatTimeString(lastDate.getTime(), DATE_FORMATE_STRING_B);
    }

    /**
     * 获得指定日期月的第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth(String dateStr,String pattern) {
        Date toDate = getDateFromString(dateStr, pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(toDate);
        calendar.set(Calendar.DATE, 1);// 设为当前月的1号
        return getFormatTimeString(calendar.getTime(), pattern);
    }

    /**
     * 获得当月最后一天【格式：yyyy-MM-dd】
     *
     * @return
     */
    public static String getLastDayOfMonthy() {
        return getLastDayOfMonthy(DATE_FORMATE_STRING_B);
    }

    /**
     * 获得当月最后一天
     *
     * @param pattern
     * @return
     */
    public static String getLastDayOfMonthy(String pattern) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        return getFormatTimeString(lastDate.getTime(), pattern);
    }

    /**
     * 获得次月第一天【格式：yyyy-MM-dd】
     *
     * @return
     */
    public static String getNextMonthFirstDay() {
        return getNextMonthFirstDay(DATE_FORMATE_STRING_B);
    }

    /**
     * 获得次月第一天
     *
     * @param pattern
     * @return
     */
    public static String getNextMonthFirstDay(String pattern) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        return getFormatTimeString(lastDate.getTime(), pattern);
    }

    /**
     * 获得次月最后一天【格式：yyyy-MM-dd】
     *
     * @return
     */
    public static String getNextMonthLastDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        return getFormatTimeString(lastDate.getTime(), DATE_FORMATE_STRING_B);
    }

    /**
     * 获取当前时间上一月【格式为：yyyy-MM-dd】
     *
     * @return
     */
    public static String getMonthOfBefore() {
        return getMonthOfBefore(DATE_FORMATE_STRING_B);
    }

    /**
     * 获取当前时间上一月
     *
     * @return
     */
    public static String getMonthOfBefore(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return getFormatTimeString(calendar.getTime(), pattern);
    }

    /**
     * 获取当前时间上N月
     * num 为负数 -1表示上个月，-2表示上两个月
     * @return
     */
   public static String getMonthOfBefore(String pattern,int num) {
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.MONTH, num);
       return getFormatTimeString(calendar.getTime(), pattern);
   }
    
    /**
     * 获取指定时间上一月
     *
     * @return
     * @throws Exception
     */
    public static String getMonthOfBefore(String dateStr, String pattern){
        Date toDate = getDateFromString(dateStr, pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(toDate);
        calendar.add(GregorianCalendar.MONTH, -1);
        toDate = calendar.getTime();
        return getFormatTimeString(toDate, pattern);
    }

    /**
     * 获得当前15分钟前的时间(时间格式为yyyy-MM-dd-HH-mm)
     *
     * @param dateStr
     * @return
     * @throws Exception
     */
    public static String getMinuteOfBefore(String dateStr, String pattern, int frequency){
        Date toDate = getDateFromString(dateStr, pattern);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(toDate);
        calendar.add(GregorianCalendar.MINUTE, -frequency);
        toDate = calendar.getTime();
        return getFormatTimeString(toDate, pattern);
    }

    /**
     * 获得当前年份的最后一天
     *
     * @return
     */
    public static String getYearOfEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 11); //到12月
        int day = calendar.getMaximum(Calendar.DAY_OF_MONTH); //获得12月的总天数
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return getFormatTimeString(calendar.getTime(), "yyyyMMdd");
    }

    /**
     * 计算比较日和基准日之间相差多少天
     *
     * @param d1 基准日
     * @param d2 比较日
     * @return
     */
    public static int getDayInterval(Date d1, Date d2) {
        Calendar c = Calendar.getInstance();

        // 获取基准日的值（去除时分秒）
        c.setTime(d1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long l1 = c.getTimeInMillis();

        // 获取比较日的值（去除时分秒）
        c.setTime(d2);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long l2 = c.getTimeInMillis();

        return (int) ((l2 - l1) / (1000 * 3600 * 24));
    }

    /**
     * 返回基准日前若干天
     *
     * @param d        基准日
     * @param interval 间隔天数
     * @return
     */
    public static Date getDateSub(Date d, int interval) {
        return getDateAdd(d, -1 * interval);
    }

    /**
     * 返回基准日后若干天
     *
     * @param d        基准日
     * @param interval 间隔天数
     * @return
     */
    public static Date getDateAdd(Date d, int interval) {
        Calendar c = Calendar.getInstance();

        // 获取基准日的值（去除时分秒）
        c.setTime(d);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, interval);

        return c.getTime();
    }

    /**
     * 日期格式
     */
    public enum DateFormat {
        DATE, DATETIME, SLASHDATE, SLASHDATETIME, CHINESEDATE, CHINESEDATETIME;

        public String toString() {
            switch (this) {
                case DATE:
                    return DATE_FORMATE_STRING_B;
                case DATETIME:
                    return DATE_FORMATE_STRING_A;
                case SLASHDATE:
                    return "yyyy/MM/dd";
                case SLASHDATETIME:
                    return "yyyy/MM/dd HH:mm:ss";
                case CHINESEDATE:
                    return "yyyy年MM月dd日";
                case CHINESEDATETIME:
                    return "yyyy年MM月dd日 HH时mm分ss秒";
                default:
                    return null;
            }
        }
    }

    /**
     * 获取指定日期的周六
     */
    public static Date getSaturday(Date date) {
        return getWeekday(date,Calendar.SATURDAY);
    }

    /**
     * 获取指定日期的周日
     */
    public static Date getSunday(Date date) {
        return getWeekday(date,Calendar.SUNDAY);
    }

    /**
     * 获取指定日期的周x
     */
    private static Date getWeekday(Date date,int wd) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        if (day != wd) {
            c.add(Calendar.DAY_OF_MONTH, wd - day);
        }
        return c.getTime();
    }

    /**
     * 自定义时间格式，如果出错则返回null
     *
     * @param date   时间
     * @param format 转换格式
     * @return String or null if   failed transfer
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            return null;
        }
        java.text.DateFormat fmat = new SimpleDateFormat(format);
        try {
            return fmat.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 将year和week转换为周日或周六（默认周日为一周开始）
     *
     * @param yearWeek yyyyMM
     * @param weekDay  Calendar.SUNDAY | Calendar.SATURDAY
     * @return Date
     */
    public static Date convertYearWeek(String yearWeek, int weekDay) {
        Calendar c = Calendar.getInstance();
        /**
         * 这里加1是因为 s_data存储和java转换测试出来的
         */
        c.setWeekDate(Integer.valueOf(yearWeek.substring(0, 4)), Integer.valueOf(yearWeek.substring(4)) + 1, weekDay);
        return c.getTime();
    }

    public static Date stringToDate(String date, String partten) {
        if (date == null || date.trim().equals("")) {
            return null;
        }

        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat(partten).parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static Integer stringToInteger(String value) {
        if (value == null || value.equals("")) {
            return null;
        }

        return Integer.parseInt(value);
    }

    public static Long stringToLong(String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        }
        return Long.valueOf(value.trim());
    }

    /**
     * 获取两个时间差 结束时间-开始时间
     *
     * @param from  开始时间
     * @param to    结束时间
     * @param filed 类别 Calendar： MINUTE, HOUR, DATE (other: 秒)
     */
    public static long getDiffTime(Date from, Date to, int filed) {
        if (from == null && to == null) {
            return 0;
        } else {
            Date date = new Date();
            from = from == null ? date : from;
            to = to == null ? date : to;
            long diff = (to.getTime() - from.getTime()) / 1000;
            if (filed == Calendar.MINUTE) {
                return diff / 60;
            }
            if (filed == Calendar.HOUR) {
                return diff / 60 / 60;
            }
            if (filed == Calendar.DATE) {
                return diff / 60 / 60 / 24;
            }
            return diff;
        }
    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param seconds
     * @return String
     */
    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }

    /**
     * 获取指定日期在一周中的第几天
     */
    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断指定日期是否是一周中的某一天
     * @return
     */
    private static boolean isSpecifyDayOfWeek(Date date,int dayOfWeek){
        boolean b = false;
        b = dayOfWeek==getDayOfWeek(date);
        return b;
    }

    /**
     * 判断指定日期是否是周一
     * @param date
     * @return
     */
    public static boolean isMonday(Date date){
        return isSpecifyDayOfWeek(date,Calendar.MONDAY);
    }

    /**
     * 判断现在是否是周一
     * @param
     * @return
     */
    public static boolean isMonday(){
        return isMonday(new Date());
    }
    
    /**
     * 获取指定日期的前一天
     * @param date
     * @return Date
     */
    public static Date getBeforeDay(Date date){
    	Date dBefore = new Date();
    	
    	Calendar calendar = Calendar.getInstance(); //得到日历
    	calendar.setTime(date);//把当前时间赋给日历
    	calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
    	dBefore = calendar.getTime();   //得到前一天的时间
    	
		return dBefore;
    }
    
    
	/**
	 * 获取过去第past天的日期
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

    /**
     * 毫秒值转时分秒
     * @param val 待转的值
     * @return 转换结果
     */
    public static String millisecond2HmsConverter(Integer val) {
        if (null==val || val < 0) {
            return "-";
        } else if (val == 0) {
            return "00:00:00";
        }
        return millisecond2HmsConverter(val*1.0);
    }

    /**
     * 毫秒值转时分秒
     * @param val 待转的值
     * @return 转换结果
     */
    public static String millisecond2HmsConverter(Double val) {
        if (null==val || val < 0) {
            return "-";
        } else if (val == 0) {
            return "00:00:00";
        }

        int hour = (int)(val / 1000 / 60 / 60);
        int minute = (int)(val / 1000 / 60 % 60);
        int second = (int)(val / 1000 % 60);

        String h = "";
        String m = "";
        String s = "";

        if (hour < 10) {
            h = "0";
        }
        h += hour;
        if (minute < 10) {
            m = "0";
        }
        m += minute;
        if (second < 10) {
            s += "0";
        }
        s += second;
        return h+":"+m+":"+s;
    }
    
    /**
     * 获取给定日期未来一个月
     * @param date
     * @return
     */
    public static Date getFutureOneMonthday(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH,1);
    	
    	return cal.getTime();
    }
    
    /**
     * 获取给定日期两周后日期
     * @param date
     * @return
     */
    public static Date getFutureTwoWeeksday(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE,14);
    	
    	return cal.getTime();
    }
    
    public static int getOverdueDays(int financePartyId,Date borrowTime,Date nowTime){
    	borrowTime = DateUtil.stringToDate(DateUtil.getFormatTimeString(borrowTime,DateUtil.FORMATE_DATE),DateUtil.FORMATE_DATE);
    	nowTime = DateUtil.stringToDate(DateUtil.getFormatTimeString(nowTime,DateUtil.FORMATE_DATE),DateUtil.FORMATE_DATE);
    	long num = 0;
		if(financePartyId == 10002) {
			num = DateUtil.getDiffTime(DateUtil.getFutureTwoWeeksday(borrowTime),nowTime, Calendar.DATE);
		}else {
			num = DateUtil.getDiffTime(DateUtil.getFutureOneMonthday(borrowTime),nowTime, Calendar.DATE);					
		}		
		return (int)num;
    }
    
}
