package com.cxn.utils;

import org.junit.Test;

import com.cxn.common.utils.CommonUtil;

public class CommonUtilTest {

	@Test
	public void testIsNumber(){
		
		String value1 = "-123.034";
		boolean result1 = CommonUtil.isNumber(value1);
		
		String value2 = "1234";
		boolean result2 = CommonUtil.isNumber(value2);
		
		String value3 = "33.034a";
		boolean result3 = CommonUtil.isNumber(value3);
		
		System.out.println("value1:" + result1);	// true
		System.out.println("value2:" + result2);	// true
		System.out.println("value3:" + result3);	// false
		
	}
	
	
}
