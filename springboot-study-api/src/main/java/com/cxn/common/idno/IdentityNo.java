package com.cxn.common.idno;

import java.util.Date;

/**
 * 身份证号码类
 *
 * Created by DaiLM on 2016/08/30.
 */
public class IdentityNo {

    private IdNo identityno;

    public IdentityNo(String idno) {

        if (idno == null) {
            return;
        }

        if (idno.length() == 15) {
            this.identityno = new IdNo15(idno);
        }

        if (idno.length() == 18) {
            this.identityno = new IdNo18(idno);
        }

    }

    public int getAge() {
        return this.identityno.getAge();
    }

    public boolean isMale() {
        if (this.identityno.getGender() == IdNo.GENDER_MALE) {
            return true;
        } else {
            return false;
        }
    }

    public Date getBirthDate() {
        return this.identityno.getBirthDate();
    }
}
