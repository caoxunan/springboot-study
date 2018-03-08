package com.cxn.zqsignature.support;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DecryptData {

	//private static String my_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkAgFING3fSl6vw5Uo7qKC5ZXDD0662ebagAVOD0rC1uoM8nWFTJVPpgpTpw1XKaaJmACq/cb9X3UD+eLA89F/q44bzG53C6nwQxZscys7V75DCPU2/c8oHwYYDJ0x/Wxcjttbfoo2y5u4YDjNQsprZnCWsr1hMjcYsAGEHvXPfQIDAQAB";
	//private static String my_private_key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKQCAUg0bd9KXq/DlSjuooLllcMPTrrZ5tqABU4PSsLW6gzydYVMlU+mClOnDVcppomYAKr9xv1fdQP54sDz0X+rjhvMbncLqfBDFmxzKztXvkMI9Tb9zygfBhgMnTH9bFyO21t+ijbLm7hgOM1CymtmcJayvWEyNxiwAYQe9c99AgMBAAECgYBXwirldAiGvsjDzCf4axjSaf8lDP/Xw9lfeblc95nNgCplGyf/V+ddYz+LW4wr48GVRpy04m6kDXwT0JahINYsWwZ8VZocVU2BsizYiAN9NXyIeKMBUUR4CqfhX2AwovZejWP+aCVbxh0hbQS4gTHtIRkdvrBNOpwt/cgxW/fqAQJBANhsw8eCMcJD8uE3gU9CnZT9bPIYTrxTEhrKd6ZgkcK1du//7XRtPRdyJS0Vn9BphwFYaV+5lRUayDC1EewGHaECQQDB/4FWUNoYpkRmaFDeQ/m0qnvSgjJOWVtuq0JwGFvgm/aO+5S6oaIqLxFq3YX/ZhKnzQ2GxbBLLbanualx6YxdAkA7OjCt1ZxmbI/4QGdb2IAleOEqs6pAcX9f446w8naRUXXWIjTKiuaQFwXDe8p7j5WJsbdMog4Bc43bfoIEFHxhAkB8YDRqw4FgZlDaGy0low0X4GlsaB6ajr2I8XGx7lwRX8yulOsg0Hnoc67GBepf+PAi9tsiOeIyAMfnNqhFKQfdAkEAzQbCQlup864lq26VCRx2E3d+BieUFMZPmPSJdsD+et1IbZCQxbtf+L2YYGbtRqI8WgHVEBYw0u3xdRH8a6BU/g==";
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/**
	 * 公钥解密
	 * @param data 需要解密数据
	 * @param public_key 公钥
	 * 用公钥解密接口商的数据
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decryptPublicData(String data, String public_key) throws Exception {
		RSAPublicKey pubKey = (RSAPublicKey) RasUtils.getPublicKey(public_key);
        String mi = RasUtils.decryptByPrivateKey(data,pubKey);
		return mi;
	}
	
	public static void main(String[] args) throws Exception {
		//InputStream in = new FileInputStream("d:\\test\\test.pdf");
		//byte b [] =Base64Utils.fileToByte("d:\\test\\123.pdf");
		//OutputStream out = new FileOutputStream(new File("d:\\test\\1.pdf"));
		//out.write(b);
		//out.close();
		//public static String sign(String content, String privateKey, String input_charset){
		String private_key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAItsUhnERbC3WChkxfDnIu+nOGc1IJNNwVHjq68wGr24HeNRIBndyV0mXSpFbO8bhHMb8WWP3bVU+ll4rVeMeXO+bEle6YidkPhefNyq6WBFc8fTs2GmMkiE1/QXEaURootfry8WwoMNl01PXzpNTLdaP4KVjlpdiYhudonM8B2RAgMBAAECgYAVj6fWskKwxHnOrV7rFWOSukJ/u3iRLyohVtsbGY5ZHpsIp2gWd/UHAcLQobHp/FBsWLrpIOmBfGXG5mGwM1oosC2AlmIVfwMqWwd1p6fybms89nMBGF/FcPcrj7YDrB2djQ+hvwHP3YMNcSiW+mSfviK4SlRJu3nHpOjDBL6wwQJBANeZu65tY/NGq7kSojZvuKMuxVP161V9RYeUXiUPvNSN+T/eSADSWtnAYc91r4uFmeM42FHCB0njIswp5rWiG9kCQQCljGPQF52NZwjr/keCzp+38zyaQ0u+iV5ohgdi83/3VT4FHNYLhG48zv1gsPvREc1zU5fgAq4qrFroIVhPxBR5AkB4pE2hDRUgzCrLJOIgE+P9a+5/TNyiubZuZ1dG5ceEKO2QD5G64/pyXnRc4j2YlOnHe8eP+EKb1b1jAQ9YE54BAkA/YCRh6Hh/Rn+K4Lh9oh5Q7IQ5xiN8GNiUiSLQxckqZW6txGFI3XTaQJ+NYZO1cOxHByxwLHaCjaLrlMBbIQR5AkEAnzv6qEx6hBAItvO8l8vcY4NHPUVnXl3BRbKTKCV43pn7pkJ1e46PjVmvi4vEsevY5D6Ups+VdcoRE/b+RJl3cA==";
		String content="address=北京市海淀区&certificate=65411265422&city=北京市&id_card=612401198503205575&id_card_scan_file1=1&id_card_scan_file2=1&notify_url=test3&principal=曹羽&province=北京&return_url=&scan_file=1&telephone_num=13271928495&user_code=zhxy-tst01&user_name=第三方签章测试企业3&user_type=0&zqid=ZQ6B528586FE8C22CE7B76872C0A321BA6";
		
		String str = sign(content,private_key,"utf-8");
		String s1=URLEncoder.encode(str, "utf-8");
		//String str1 = new String(str.getBytes(),Encoder.UTF_8);
		System.out.println(s1);
		
	}
	/**
	 * 私钥解密
	 * @param data 需要解密数据
	 * @param private_key 私钥
	 * @return  解密后的字符串
	 * @throws Exception 
	 */
	public static String decryptPrivateData(String data, String private_key) throws Exception {
		RSAPrivateKey priKey = (RSAPrivateKey) RasUtils.getPrivateKey(private_key);
        String mi = RasUtils.decryptByPrivateKey(data,priKey);
		return mi;
	}
	
	/**
	 * 公钥加密
	 * @param data  需要加密的字符串
	 * @param public_key  公钥
	 * @return  加密后的字符串
	 * @throws Exception 
	 */
	public static String encryptedPublicData(String data,String public_key) throws Exception{
		RSAPublicKey pubKey = (RSAPublicKey) RasUtils.getPublicKey(public_key);  
        String mi = RasUtils.encryptByPublicKey(data,pubKey);
		return mi;
	}
	/**
	 * 私钥加密
	 * @param data 需要加密的字符串
	 * @param private_key  私钥
	 * @return  加密的字符串
	 * @throws Exception
	 */
	public static String encryptedPrivateData(String data,String private_key) throws Exception{
        RSAPrivateKey priKey = (RSAPrivateKey) RasUtils.getPrivateKey(private_key);
        String mi = RasUtils.encryptByPublicKey(data,priKey);
		return mi;
	}
	
	/**
	 * 私钥签名签名
	 * @param content  待签名字符串
	 * @param privateKey  私钥
	 * @param input_charset  字符集
	 * @return  签名后的数据
	 */
	public static String sign(String content, String privateKey, String input_charset){
        try{
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            String sign= Base64.encode(signed);
            return sign;
            
        }catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param public_key 第三方签章公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String public_key, String input_charset){
		try{
			sign = sign.replaceAll("_zq", "+");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 私钥加密【文件】
	 * @param data 需要加密的文件字节流
	 * @param private_key  私钥
	 * @return  加密后的字节流
	 * @author chy
	 * @throws Exception
	 */
	public static byte[] encryptedPrivateDataFile(byte[] data,String private_key) throws Exception{
		//将文件字节流转成base64
		sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
		String contractFilebase64 = base64Encoder.encode(data);
        RSAPrivateKey priKey = (RSAPrivateKey) RasUtils.getPrivateKey(private_key);
        String mi = RasUtils.encryptByPublicKey(contractFilebase64,priKey);
		return mi.getBytes();
	}
	/**
	 * 公钥解密【文件】
	 * @param data 需要解密文件字节流
	 * @param public_key  公钥
	 * @return  加密后的字节流
	 * @author chy
	 * @throws Exception
	 */
	public static byte[] decryptedPrivateDataFile(byte[] data,String public_key) throws Exception{
		//将文件字节流转成base64
		sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
		String contractFilebase64 = base64Encoder.encode(data);
		RSAPublicKey pubKey = (RSAPublicKey) RasUtils.getPublicKey(public_key);  
        String mi = RasUtils.decryptByPrivateKey(contractFilebase64,pubKey);
		return mi.getBytes();
	}
}
