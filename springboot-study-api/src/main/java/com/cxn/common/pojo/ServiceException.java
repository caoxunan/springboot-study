package com.cxn.common.pojo;

/**
 * 自定义异常
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/**
	 * 错误码
	 */
	private int errorCode;
	/**
	 * 错误信息
	 */
	private String errorMsg;

	public ServiceException(int errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ServiceException(int errorCode, String errorMsg, Exception e) {
		super(e);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	@Override
	public String getMessage() {
		return "错误码:" + errorCode + ",错误描述:" + errorMsg;
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
}
