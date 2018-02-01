package com.cxn.utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 非对称加密算法RSA工具类
 * @author caoxunan
 *
 */
public class RSACryptoUtil {
	/**
	 * 得到公钥
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * 得到私钥
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = Base64.encodeBase64String(keyBytes);
		return s;
	}

	/**
	 * 将密钥对象写入密钥文件
	 * @param path 生成密钥文件的路径
	 * @param key  密钥对象
	 * @throws Exception
	 */
	public void writeKey(String path, Key key) throws Exception {  
		FileOutputStream fos = new FileOutputStream(path);  
		ObjectOutputStream oos = new ObjectOutputStream(fos);  
		oos.writeObject(key);  
		oos.close();  
	}  
	
	/**
	 * 从密钥文件中读出密钥对象
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Key readKey(String path) throws Exception {  
		FileInputStream fis = new FileInputStream(path);  
		ObjectInputStream bis = new ObjectInputStream(fis);  
		Object object = bis.readObject();  
		bis.close();  
		return (Key)object;  
	}  

	

}
