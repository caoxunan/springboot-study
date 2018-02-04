package com.cxn.common.pojo;

import java.io.Serializable;

/**
 * http请求返回信息包装类
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前的数据版本
     */
    protected final String version = "1.0";

    /** 
     * 错误码
     */
    private int errorCode = 0;

    /** 
     * 结果的json串
     */
    private String errorMsg = null;

    /** 
     * 具体的内容
     */
    private T data = null;

    public ResponseResult() {
    	super();
    }
    public ResponseResult(int errorCode, String errorMsg, T data) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * 获取静态方法实例
     */
    public static ResponseResult<Object> newInstance(int errorCode, String errorMsg, Object data) {
        return new ResponseResult<>(errorCode, errorMsg, data);
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public String getVersion() {
        return version;
    }

}
