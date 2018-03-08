package com.cxn.zqsignature.support;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * RSA工具类
 */
public class RsaSign {

	private static Log logger = LogFactory.getLog(RsaSign.class);

	
	//编码集
	public static final String CHARSET_ENCODER = "UTF-8";
	
	/**
	 * <p>map参数排序</p>
	 * @param params
	 * @return
	 * @auther zzk
	 * 2017年1月12日下午4:29:45
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
	
	/**
	 * 签名
	 * @param content
	 * @param privateKey
	 * @return
	 */
	public static String sign(String content, String privateKey) {
		return sign(content, privateKey, null);
	}
	
	public static String sign(String content, String privateKey,String input_charset) {
		String charset = isEmpty(input_charset)?CHARSET_ENCODER : input_charset;
		try {
			String s = RSAUtils.sign(content.getBytes(charset), privateKey);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * 验签
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean verify(String content, String sign, String publicKey) {
		return verify(content, sign, publicKey,null);
	}
	public static boolean verify(String content, String sign, String publicKey,String input_charset) {
		String charset = isEmpty(input_charset)?CHARSET_ENCODER : input_charset;
		try {
			boolean b = RSAUtils.verify(content.getBytes(charset), publicKey, sign);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return false;
	}
	private static boolean isEmpty(String str){
		return str==null||str.trim().length()==0||str.equals("null");
	}
	/*public static String sign(String content, String privateKey,String input_charset) {
		String charset = isEmpty(input_charset)?CHARSET_ENCODER : input_charset;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
	
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
	
			signature.initSign(priKey);
			signature.update(content.getBytes(charset));
	
			byte[] signed = signature.sign();
	
			return Base64.encode(signed);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}*/
	
	/*public static boolean verify(String content, String sign, String publicKey,String input_charset) {
		String charset = isEmpty(input_charset)?CHARSET_ENCODER : input_charset;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(charset));

			boolean bverify = signature.verify(Base64.decode(sign));
			return bverify;

		} catch (Exception e) {
			logger.error(e);
		}

		return false;
	}*/
	public static void main(String[] args) {
		String sign = RsaSign.sign("123zhan张三", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL+xjjeKLMbTWME0LihovLHeJ5UD51nxQbmnuIGG/4HUlPt3bMPeoHOtu+fCqPPsaaN89ChNMxbeGv3jqe2xUotIamvjAGl3jiTUCNVWveURuxT7BjCHDQkgoF92X+EL5tcn+9YQSmgNHs3HFO6gKEwbLj75OgDdwHluz6xDuRilAgMBAAECgYAFTY8moC7u7SfWaHAidAtMTF4B9FKxHUh5L1eeVbK5z7yzXDFpFb6QlKzPE4aDAPZHLIzAlKomJszOWz73MWGcJoeOQFcdNMuRvVEarwLsdEuCDIhuVnt2lCtMHMwdZfOudz5iAv4HqfIMEcekZSwhCW6IXDYgqSdLPK1GiK6ZPQJBAOZw6p/qzyPXNapkZYq7MpWR/w0lspDbfjZ77HSekyoU04fdEjioK7+DJDalUGT0bIUq83U3SSftAFQmCMMf4H8CQQDU9HPczV8I6LQD0nf4Z08MMToSPUfAkJAw5vyGT+QcsMXKFCOGS5X4XNM+TAKnAduxv9Rgf/tynr/Zx0LtBfTbAkEAhs+gMxXnQIxydNBvJw4EtcPHdiWLpXsDB1TQLBlo9sFgTqdiNYsMrOlHkkB8G9NyeSV7cCN7xMO94Xyuu5g2eQJASM/sbaqqu9kU89mau4xXMswCFwps5iKHqrDP1vyp+kVW22lXXCur82eJsts6bO/ttjDo5LXdu6sb3dKLx48p0QJBANx9wJMLPhfhKPBpf3E9RY9/2CimL/eue8BEOLauD+F7LvBPGHqNW/tukbVKU7GxVXq+OYiPEw4o3cjkMt2v+QI=");
		System.out.println(sign);
		boolean b = RsaSign.verify("123zhan张三", sign, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/sY43iizG01jBNC4oaLyx3ieVA+dZ8UG5p7iBhv+B1JT7d2zD3qBzrbvnwqjz7GmjfPQoTTMW3hr946ntsVKLSGpr4wBpd44k1AjVVr3lEbsU+wYwhw0JIKBfdl/hC+bXJ/vWEEpoDR7NxxTuoChMGy4++ToA3cB5bs+sQ7kYpQIDAQAB");
		System.out.println(b);
	}
	
}
