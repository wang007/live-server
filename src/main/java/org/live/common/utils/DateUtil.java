package org.live.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期/时间工具(1.获得当前日期后X天的时间；2.获得当天的日期；3.计算两个时间的间隔（毫秒、小时、天、周、月、年）；)
 * @author qinhuan
 * @version 2013-02-26 11:53:22
 */
public class DateUtil {


	/**
	 * 获取当前日期后aDate天的时间（格式："yyyy-MM-dd HH:mm:ss"）
	 * @param aDate
	 * @return 时间字符串
	 */
	public static String nowDateAddDays(Integer aDate) {
		SimpleDateFormat dateFormatf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, aDate);
		String d = dateFormatf.format(calendar.getTime());
		return d;
	}
	
	/**
	 * 获得指定时间后若干年的时间对象
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date afterAFewYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	/**
	 * 获得指定时间后若干天的时间对象。
	 * @param date
	 * @param days
	 * @return Date对象
	 */
	public static Date afterAFewDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	/**
	 * 获得指定时间后若干小时对象
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date afterAFewHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}
	
	/**
	 * 获得指定时间后若干分钟对象
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date afterAFewMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	/**
	 * 获得当前系统时间后指定天数的时间对象。
	 * @param days
	 * @return Date对象
	 */
	public static Date afterAFewDays(int days) {
		Date date = new Date();
		return afterAFewDays(date, days);
	}
	
	/**
	 * 获取当前日期（格式："YYYY-MM-DD"）
	 * @return 日期字符串
	 */
	public static String nowDate() {
		String result = nowDateAddDays(0);
		return result.substring(0,10);
	}
	
	/**
	 * 获取当前日期（格式："YYYY-MM-DD"）
	 * @return 日期字符串
	 */
	public static String nowDate(String pattern) {
		return nowDateAddDays4Format(0, pattern);
	}


	/**
	 * 获得当前日期后N天的日期。
	 * @param aDate 天数
	 * @param pattern 日期格式
	 * @return 日期字符串
	 */
	public static String nowDateAddDays4Format(Integer aDate, String pattern) {
		SimpleDateFormat dateFormatf = new SimpleDateFormat(pattern);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, aDate);
		String d = dateFormatf.format(calendar.getTime());
		return d;
	}
	
	/**
	 * 获得当前时间字符串（格式："yyyy-MM-dd HH:mm:ss"）
	 * @return 时间字符串
	 */
	public static String nowTime() {
		return nowDateAddDays(0);
	}
	
	/**
	 * 返回两个时间相差的毫秒（）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Long differenceMilliseconds(Date time1, Date time2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		Long mm1 = calendar.getTimeInMillis();
		calendar.setTime(time2);
		Long mm2 = calendar.getTimeInMillis();
		Long dm = mm1 - mm2;
		return dm;
	}

	/**
	 * 返回两个时间相差的毫秒（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 毫秒数
	 * @throws ParseException
	 */
	public static Long differenceMilliseconds(String s1, String s2, String dateFormat) throws ParseException {
		SimpleDateFormat dateFormatf = new SimpleDateFormat(dateFormat);
		Date time1 = dateFormatf.parse(s1);
		Date time2 = dateFormatf.parse(s2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time1);
		Long mm1 = calendar.getTimeInMillis();
		calendar.setTime(time2);
		Long mm2 = calendar.getTimeInMillis();
		Long dm = mm1 - mm2;
		return dm;
	}
	
	/**
	 * 返回两个时间相差的小时（）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Double differenceHour(Date time1, Date time2) {
		Double dm = differenceMilliseconds(time1, time2).doubleValue();
		dm = dm / (1000*60*60);
		return dm;
	}
	
	/**
	 * 返回两个时间相差的小时（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 小时数
	 * @throws ParseException
	 */
	public static Double differenceHour(String s1, String s2, String dateFormat) throws ParseException {
		Double dm = differenceMilliseconds(s1, s2, dateFormat).doubleValue();
		dm = dm / (1000*60*60);
		return dm;
	}
	
	/**
	 * 返回两个时间相差的天（）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Double differenceDay(Date time1, Date time2) {
		Double dm = differenceMilliseconds(time1, time2).doubleValue();
		dm = dm / (1000*60*60*24);
		return dm;
	}


	/**
	 * 返回两个时间相差的天（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 天数
	 * @throws ParseException
	 */
	public static Double differenceDay(String s1, String s2, String dateFormat) throws ParseException {
		Double dm = differenceMilliseconds(s1, s2, dateFormat).doubleValue();
		dm = dm / (1000*60*60*24);
		return dm;
	}
	
	/**
	 * 返回两个时间相差的周（）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Double differenceWeek(Date time1, Date time2) {
		Double dm = differenceDay(time1, time2).doubleValue();
		dm = dm / 7;
		return dm;
	}

	/**
	 * 返回两个时间相差的周（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 周数
	 * @throws ParseException
	 */
	public static Double differenceWeek(String s1, String s2, String dateFormat) throws ParseException {
		Double dm = differenceDay(s1, s2, dateFormat).doubleValue();
		dm = dm / 7;
		return dm;
	}
	
	/**
	 * 返回两个时间相差的月（30天为一月）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Double differenceMonth(Date time1, Date time2) {
		Double dm = differenceDay(time1, time2).doubleValue();
		dm = dm / 30;
		return dm;
	}


	/**
	 * 返回两个时间相差的月（30天为一月）（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 月数
	 * @throws ParseException
	 */
	public static Double differenceMonth(String s1, String s2, String dateFormat) throws ParseException {
		Double dm = differenceDay(s1, s2, dateFormat).doubleValue();
		dm = dm / 30;
		return dm;
	}
	
	/**
	 * 返回两个时间相差的年（30天为一月）
	 * @param time1
	 * @param time2
	 * @return time1 - time2
	 */
	public static Double differenceYears(Date time1, Date time2) {
		Double dm = differenceMonth(time1, time2).doubleValue();
		dm = dm / 12;
		return dm;
	}

	/**
	 * 返回两个时间相差的年（30天为一月）（按照指定的dataFormat将字符串转换为Date）
	 * @param s1
	 * @param s2
	 * @param dateFormat
	 * @return 年数
	 * @throws ParseException
	 */
	public static Double differenceYears(String s1, String s2, String dateFormat) throws ParseException {
		Double dm = differenceMonth(s1, s2, dateFormat).doubleValue();
		dm = dm / 12;
		return dm;
	}
	
	/**
	 * 将String格式表示的日期转换为Date对象
	 * @param source
	 * @param format 默认"yyyy-MM-dd"
	 * @return Date对象
	 */
	public static Date string2Date(String source, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将Date对象转换成指定格式的String(format:"yyyy-MM-dd")
	 * @param source
	 * @return
	 */
	public static Date string2Date(String source) {
		return string2Date(source, "yyyy-MM-dd");
	}
	
	/**
	 * 将Date对象转换成指定格式的String
	 * @param date
	 * @param format 默认"yyyy-MM-dd"
	 * @return 日期字符串
	 */
	public static String date2String(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将Date对象转换为日期String，格式为"yyyy-MM-dd"
	 * @param date
	 * @return 日期字符串
	 */
	public static String date2DateString(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}
	
	/**
	 * 将Date对象转换为时间String，格式为"yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @return 时间字符串
	 */
	public static String date2TimeString(Date date) {
		return date2String(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**  
	*  
	* 描述:此类用于取得当前日期相对应的月初，月末，季初，季末，年初，年末，返回值均为String字符串  
	*      1、得到当前日期         today()  
	*      2、得到当前月份月初      thisMonth()  
	*      3、得到当前月份月底      thisMonthEnd()  
	*      4、得到当前季度季初      thisSeason()  
	*      5、得到当前季度季末      thisSeasonEnd()  
	*      6、得到当前年份年初      thisYear()  
	*      7、得到当前年份年底      thisYearEnd()  
	*      8、判断输入年份是否为闰年 leapYear    
	*/  

	private int x;                  // 日期属性：年   
	private int y;                  // 日期属性：月   
	public Calendar getLocalTime() {
		return localTime;
	}

	public void setLocalTime(Calendar localTime) {
		this.localTime = localTime;
	}
	//private int z;                  // 日期属性：日   
	private Calendar localTime;     // 当前日期   

	/**  
	* 功能：得到当前月份月初 格式为：yyyy-MM-dd (eg: 2007-12-01)  
	* @return String   
	*/  
	public  String thisMonth() {   
	String strY = null;   
	x = localTime.get(Calendar.YEAR);   
	y = localTime.get(Calendar.MONTH) + 1;   
	strY = y >= 10 ? String.valueOf(y) : ("0" + y);return x + "-" + strY + "-01";   
	}   

	public static void main(String args[]) {
		//System.out.println(nowDate());
		//System.out.println(afterAFewYear(new Date(), 1));
		System.out.println(nowTime());
		System.out.println(afterAFewHour(new Date(), 0));
		System.out.println(afterAFewMinute(new Date(), 61));
	}
	/**  
	 * 功能：得到当前年份年初 格式为：yyyy-MM-dd (eg: 2007-01-01)<br>  
	 * @return String  
	 */  

	public String thisYear() {
		x = localTime.get(Calendar.YEAR);   
		return x + "-01" + "-01";   
	} 
	
	/**
	 * 获得指定时间的季度标识（年份和季度序号组合，如20131、20132、20133、20134）
	 * @param date
	 * @return
	 */
	public static String getJdbs(Date date) {
		String result = DateUtil.date2String(date, "yyyy");
		int yf = Integer.parseInt(DateUtil.date2String(date, "MM"));
		switch (yf) {
		case 1:
		case 2:
		case 3:
			result += "1";
			break;
		case 4:
		case 5:
		case 6:
			result += "2";
			break;
		case 7:
		case 8:
		case 9:
			result += "3";
			break;
		case 10:
		case 11:
		case 12:
			result += "4";
			break;
		}
		return result;
	}
}
