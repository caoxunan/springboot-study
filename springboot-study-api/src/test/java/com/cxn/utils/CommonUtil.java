package com.cxn.utils;

import java.util.regex.Pattern;

/**
 * 常用工具类
 * @author caoxunan
 *
 */
public class CommonUtil {

	public static boolean isNumber(String value){
		
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		
		return pattern.matcher(value).matches(); 
		
	}
	
}
