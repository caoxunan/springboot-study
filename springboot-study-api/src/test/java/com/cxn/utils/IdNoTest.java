package com.cxn.utils;

import org.junit.Test;

import com.cxn.common.idno.IdNo;
import com.cxn.common.idno.IdentityNo;


/**
 * 身份证号码解析 性别 年龄 出生日期
 * @author caoxunan
 *
 */
public class IdNoTest {

	@Test
	public void testParseIdNo(){

		IdentityNo idno = new IdentityNo("232301199202016237");
		// 性别
		if (idno.isMale()) {
			System.out.println("性别:"+IdNo.GENDER_MALE_STRING);
		} else {
			System.out.println(IdNo.GENDER_FEMALE_STRING);
		}
		// 年龄
		System.out.println("年龄:"+idno.getAge());
		// 出生日期
		System.out.println("出生日期:"+idno.getBirthDate().toLocaleString());

	}

}
