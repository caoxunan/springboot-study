package com.cxn.zqsignature.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* *
 *功能：第三方签章接口公用函数类
 *日期：2016-08-16
 */

public class ZqkjCore {

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (key.equalsIgnoreCase("ws_sign_val")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }


	/**
	 * @param sendUrl
	 * @param mapContext
	 * @param myPrivateKey
	 * @return
	 * @throws IOException
	 */
	public static String sendPost(String sendUrl,Map<String, String> mapContext,String myPrivateKey) throws IOException{
		//准备回调
		String formSubmitStrtwo = createLinkString(mapContext);
		String wsSignVal = DecryptData.sign(formSubmitStrtwo, myPrivateKey, "utf8");
		String result = HttpRequest.sendPost(sendUrl,formSubmitStrtwo + "&ws_sign_val=" + wsSignVal);
		return result;
	}
	/**
	 * 验证参数
	 * @param params   oldMap
	 * @param publicKey
	 * @return
	 */
	public static boolean verifyParam(Map<String, String> params,String publicKey){
		Map<String, String> newParams = new HashMap<String, String>();
		String wsSignVal = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			if(key.equals("ws_sign_val")){
				wsSignVal = entry.getValue();
			}else{
				newParams.put(key, entry.getValue());
			}
		}
		String formSubmitStr = createLinkString(newParams);
		return DecryptData.verify(formSubmitStr, wsSignVal,publicKey, "utf8");
	}
	/**
	 * 验证参数
	 * @param params   newMap
	 * @param publicKey
	 * @param wsSignVal
	 * @return
	 */
	public static boolean verifyParam(Map<String, String> params,String publicKey,String wsSignVal){
		String formSubmitStr = createLinkString(params);
		return DecryptData.verify(formSubmitStr, wsSignVal,publicKey, "utf8");
	}
	/**
	 * 验证参数
	 * @param publicKey
	 * @param request
	 * @return
	 */
	 
    
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

}
