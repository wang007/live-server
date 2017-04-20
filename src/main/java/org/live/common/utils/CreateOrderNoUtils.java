package org.live.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单号(系统时间+六位随机生成数)
 * @author wzy
 *
 */
public class CreateOrderNoUtils {
	
	/**
	 * 获得当前系统日期的字符串
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd");
		String time=format.format(date);
		return time;
	}

	/**
	 *  获取当前系统时间的字符串
	 * @return
	 */
	public static String getTime() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date);
		return time;
	}
	
	/**
	 * 获得随机数
	 * @param strLength
	 * @return
	 */
	public static String getFixLenthString(int strLength){
		Random rm = new Random();  
	      
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}
	
	/**
	 * 返回当前系统日期+随机数
	 * @return
	 */
	public static String getCreateOrderNo(){
		return CreateOrderNoUtils.getDate()+CreateOrderNoUtils.getFixLenthString(6);//数字代表几位随机数
	}

	/**
	 *  返回当前系统时间+ 随机数
	 * @return
	 */
	public static String getCreateOrderNoByTime() {
		return CreateOrderNoUtils.getTime()+CreateOrderNoUtils.getFixLenthString(3);//数字代表几位随机数
	}

	public static void main(String[] args) {
		// 20170421014800020 20170421014829586
		System.out.println(getCreateOrderNoByTime());
	}

}
