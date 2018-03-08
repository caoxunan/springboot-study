package com.cxn.zqsignature.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class ZqSubmit {
	
	/**
	 * 根据参数信息生成接口请求；
	 * @param strUserRegInfo 接口参数信息
	 * @return
	 * @throws Exception
	 * String strUserRegInfo
	 */
   public static String buildRequest(Map<String, String>  saveContractInfo,String netURL,String privateKey) throws Exception {
	   //待请求参数数组
	   //私钥
	   //生成签名结果
	   String wsFormInfo = ZqkjCore.createLinkString(saveContractInfo);
	   String wsSignVal=DecryptData.sign(wsFormInfo,privateKey,"utf8");
	   
	   StringBuffer sbHtml = new StringBuffer();
	   sbHtml.append("<form id=\"zqwssubmit\" name=\"zqwssubmit\" action=\"" + netURL + "\" method='post'>");
	   for (Map.Entry<String, String> entry : saveContractInfo.entrySet()) {
		   sbHtml.append("<input type=\"hidden\" name='" + entry.getKey() + "' value=\"" + entry.getValue() + "\"/>");
	   }
	   sbHtml.append("<input type=\"hidden\" name='sign_val' value=\"" + wsSignVal + "\"/>");
	   //submit按钮控件请不要含有name属性
	   sbHtml.append("<input type=\"submit\" value='确认' style=\"display:none;\"></form>");
	   sbHtml.append("<script>document.forms['zqwssubmit'].submit();</script>");
	   
	   return sbHtml.toString();
   }
    /**
     * 输入流转byte字节
     * @param inStream
     * @return
     * @throws IOException
     */
    public static byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}	
