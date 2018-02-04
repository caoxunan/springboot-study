package com.cxn.common.idno;

import java.util.Date;

/**
 * 身份证
 *
 * Created by DaiLM on 2016/08/30.
 */
public interface IdNo {

    public static final int GENDER_MALE = 1; //男
    public static final String GENDER_MALE_STRING = "男"; //男

    public static final int GENDER_FEMALE = 2; //女
    public static final String GENDER_FEMALE_STRING = "女"; //女

    /**
     * 根据身份证号码计算年龄
     *
     * @return
     */
    public int getAge();

    /**
     * 根据身份证号码判断性别
     *
     * @return
     */
    public int getGender();

    /**
     * 根据身份证计算生日
     *
     * @return
     */
    public Date getBirthDate();

}
