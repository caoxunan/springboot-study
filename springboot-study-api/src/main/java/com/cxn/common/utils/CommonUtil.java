package com.cxn.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * @author caoxunan
 *
 */
public class CommonUtil {

	
	/**
	 * 判断传入的value是否是数值
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value){

		String regex = "^(-?\\d+)(\\.\\d+)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches(); 

	}


}
