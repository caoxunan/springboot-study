package com.cxn.wkhtml;

/**
 *	html相关功能枚举
 */
public enum WkhtmltoxEnum {
	SUCCESS("success"),FAIL("fail"),PDF("pdf"),JPG("image");
	private String type;
	private WkhtmltoxEnum(String type) {
		this.type = type;
	}
	public String getType(){
		return type;
	}
}
