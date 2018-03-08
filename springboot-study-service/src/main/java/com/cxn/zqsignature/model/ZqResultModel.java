package com.cxn.zqsignature.model;

import java.util.Arrays;

/**
 * 封装第三方签章 接口的请求结果
 * @author caoxunan
 *
 */
public class ZqResultModel {
	
	// 返回码
	private String code;
	// message信息
	private String msg;
	// 第三方签章标识码
	private String zqid;
	// 合同编号
	private String no;
	// 用户ID
	private String user_code;
	// 签署类型
	private String sign_type;
	// 异步回调地址(在成功后，需要商户返回“success”，否则第三方签章将会一直异步通知商户请求结果)
	private String notify_url;
	// 同步回调地址
	private String return_url;
	// 参数签名值
	private String sign_val;
	// 合同图片地址
	private String imgList[];
	// 合同pdf地址
	private String pdfUrl[];
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getZqid() {
		return zqid;
	}
	public void setZqid(String zqid) {
		this.zqid = zqid;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getSign_val() {
		return sign_val;
	}
	public void setSign_val(String sign_val) {
		this.sign_val = sign_val;
	}
	public String[] getImgList() {
		return imgList;
	}
	public void setImgList(String[] imgList) {
		this.imgList = imgList;
	}
	public String[] getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String[] pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	@Override
	public String toString() {
		return "ZqResultModel [code=" + code + ", msg=" + msg + ", zqid=" + zqid + ", no=" + no + ", user_code="
				+ user_code + ", sign_type=" + sign_type + ", notify_url=" + notify_url + ", return_url=" + return_url
				+ ", sign_val=" + sign_val + ", imglist=" + Arrays.toString(imgList) + ", pdfUrl="
				+ Arrays.toString(pdfUrl) + "]";
	}
	
}
