package com.cxn.common.enums;

/**
 * 返回结果枚举
 * 0：成功
 * 正数：业务异常
 * 负数：系统异常
 */
public enum ResultEnum {
    /**
     * 统一异常返回结果
     */
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
	SERVICE_ERROR(1, "业务处理错误");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
