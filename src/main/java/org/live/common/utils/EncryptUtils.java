package org.live.common.utils;


import org.live.common.constants.Constants;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 封装一些常用的加密工具
 * @author Mr.wang
 *
 */
public final class EncryptUtils {
	
	/**
	 * md5加密后，转成16进制的字符串
	 * @return
	 */
	public static String encryptToHex(String source) {
		 char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	        try {
	            byte[] btInput = source.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) { 
	        	throw new RuntimeException(e) ;       
	        }
	}
	
	/**
	 * 
	 *
	 * md5之后再进行base64加密
	 * @param str
	 * @return
	 */
	public static String encryptToBase64(String str) {
		String encryptionStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64Encoder = new BASE64Encoder();
			encryptionStr = base64Encoder.encode(md5.digest(str.getBytes(Constants.DEFAULT_CHARSET)));
		} catch (NoSuchAlgorithmException e) {
			
			throw new RuntimeException(e) ;  
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e) ;  
		}
		return encryptionStr;
	}
	
}
