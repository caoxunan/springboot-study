package com.cxn.zqsignature.support;

import java.util.Map;

public class EncryptData {
	
	
	public String encrptData(Map<String,String> map,String private_key){
		String keyValueStr = ZqkjCore.createLinkString(map);
		String signVal= DecryptData.sign(keyValueStr, private_key, "utf-8");
		return signVal;
	}
	
	public boolean verify(Map<String,String> map,String public_key){
		String sign=map.get("ws_sign_val");
		if(sign==null){
		throw new RuntimeException("签名值为空");	
		}
		map.remove("ws_sign_val");
		String content = ZqkjCore.createLinkString(map);
		return DecryptData.verify(content, sign, public_key, "utf-8");
	}
	

}
