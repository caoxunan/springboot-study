package com.cxn.utils;

import org.junit.Test;

public class DoubleUtilTest {

	/**
	 * test add method
	 */
	@Test
	public void testAdd(){
		
		double value1 = 5.986234243234;
		double value2 = 6.098334322258;
		
		Double add = DoubleUtil.add(value1, value2);
		System.out.println("add:" + add.doubleValue());
		
	}
	
	/**
	 * test sub method
	 */
	@Test
	public void testSub(){
		
		double value1 = 7.986234243234;
		double value2 = 6.098334322258;
		
		double sub = DoubleUtil.sub(value1, value2);
		// 1.887899920976
		System.out.println("sub:" + sub);

	}
	
	/**
	 * test multiply method
	 */
	@Test
	public void testMul(){
		
		double value1 = 3.2356;
		double value2 = 0.52;
		
		Double mul = DoubleUtil.mul(value1, value2);
		// mul:1.682512
		System.out.println("mul:" + mul);
		
	}
	
	/**
	 * test divide method
	 */
	@Test
	public void testDiv(){
		
		double dividend = 1 ;	// 分母
		double divisor = 0.3;		// 分子
		Integer scale = 10;			// 保留位数
		
		// 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后2位，以后的数字直接进位加1。
		Double divCeiling = DoubleUtil.divCeiling(dividend, divisor);
		// 3.34
		System.out.println("divCeiling:" + divCeiling);
		
		// 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字进位加1
		Double divCeiling2 = DoubleUtil.divCeiling(dividend, divisor, scale);
		// 3.3333333334
		System.out.println("divCeiling:" + divCeiling2);
	
		// 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后2位，以后的数字四舍五入
		Double divHalfUp = DoubleUtil.divHalfUp(dividend, divisor);
		// 3.34
		System.out.println("divHalfUp:" + divHalfUp);
		
		// 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
		Double divHalfUp2 = DoubleUtil.divHalfUp(dividend, divisor, scale);
		// 3.3333333333
		System.out.println("divHalfUp:" + divHalfUp2);
		
	}
	
	@Test
	public void testHalfUp(){
		
		double value = 12.98347;
		Integer scale = 3;
		
		Double round = DoubleUtil.halfUp(value, scale);
		// 12.983
		System.out.println("halfUp:" + round);
		
	}
	
	@Test
	public void testCeiling(){
		
		double value = 12.98347;
		Integer scale = 3;
		
		Double round = DoubleUtil.ceiling(value, scale);
		// 12.984
		System.out.println("ceiling:" + round);
		
	}
	
	
}
