package com.cxn.common.idno;

import java.util.Calendar;
import java.util.Date;

import com.cxn.common.utils.DateUtil;


/**
 * 15位身份证
 *
 * Created by DaiLM on 2016/08/30.
 */
public class IdNo15 implements IdNo {

    private int age = 0;

    private int gender = 0;

    private Date birthdate = null;

    public IdNo15(String idno) {

        if (idno == null || idno.length() != 15) {
            // 身份证号码无效
            return;
        }

        this.age = getAge(idno);
        this.gender = getGender(idno);
        this.birthdate = getBirthDate(idno);

    }

    private int getAge(String idno) {
        String strYear = "19" + idno.substring(6, 8);
        Calendar cal = Calendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        return curYear - Integer.valueOf(strYear);
    }

    private int getGender(String idno) {
        String strGender = idno.substring(14);
        if (Integer.valueOf(strGender) % 2 == 0) {
            return GENDER_FEMALE;
        } else {
            return GENDER_MALE;
        }
    }

    private Date getBirthDate(String idno) {
        String strYear = "19" + idno.substring(6, 8);
        String strMonth = idno.substring(8, 10);
        String strDay = idno.substring(10, 12);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(strYear));
        cal.set(Calendar.MONTH, Integer.valueOf(strMonth) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(strDay));
        return DateUtil.getDate(cal.getTime(), DateUtil.DateFormat.DATE.toString());
    }

    /**
     * 根据身份证号码计算年龄
     *
     * @return
     */
    @Override
    public int getAge() {
        return this.age;
    }

    /**
     * 根据身份证号码判断性别
     *
     * @return
     */
    @Override
    public int getGender() {
        return this.gender;
    }

    /**
     * 根据身份证计算生日
     *
     * @return
     */
    @Override
    public Date getBirthDate() {
        return this.birthdate;
    }
}
