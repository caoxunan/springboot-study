package com.cxn.common.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * http工具类
 * @author marshal.liu 
 * @since: 2016年3月4日 上午11:22:02
 * @version 1.0
 */
public class HttpClientUtils {

    public static final int HTTP_CONNECTION_TIMEOUT = 5000;
    public static final int HTTP_SOCKET_TIMEOUT = 30000;

	/**
	 * 提交文件内容
	 * @param url 地址
	 * @param requestJson 内容
	 * @param charset 字符集
	 * @return
	 */
	public static String postJson(String url, String requestJson, String charset) {
		try {
			// 创建默认的httpClient实例.
			CloseableHttpClient httpclient = getCloseableHttpClient(url);
			// 创建httppost
			HttpPost httppost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(requestJson, charset);
			httppost.setEntity(requestEntity);
			httppost.addHeader("Content-Type", "application/json");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP_SOCKET_TIMEOUT).setConnectTimeout(HTTP_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			String result = EntityUtils.toString(responseEntity, charset);			
			return result;
		} catch (Exception e) {
			throw new RuntimeException("http接口调用失败：" + e.getMessage(), e);
		}
	}

	/**
	 * get接求
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String get(String url, String charset) {			
		try {
			// 创建默认的httpClient实例.
			CloseableHttpClient httpclient = getCloseableHttpClient(url);
			// 创建httppost
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(HTTP_SOCKET_TIMEOUT).setConnectTimeout(HTTP_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
			httpget.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httpget);
			HttpEntity responseEntity = response.getEntity();
			String result = EntityUtils.toString(responseEntity, charset); 
			return result;
		} catch (Exception e) {
			throw new RuntimeException("http接口调用失败：" + e.getMessage(), e);
		}
	}

	private static CloseableHttpClient getCloseableHttpClient(String url) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https")) {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		}else{
			httpClient = HttpClients.createDefault();
		}		 
		return httpClient;
	}
}
