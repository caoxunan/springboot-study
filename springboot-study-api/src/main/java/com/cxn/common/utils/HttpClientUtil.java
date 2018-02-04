/**
 * Project Name:midai-system-common
 * File Name:HttpClientUtil.java
 * Package Name:com.midai.common.support
 * Date:2016年6月13日下午2:37:50
 * Copyright (c) 2016, www midaigroup com Technology Co., Ltd. All Rights Reserved.
 *
*/

package com.cxn.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:HttpClientUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年6月13日 下午2:37:50 <br/>
 * @author   陈勋
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class HttpClientUtil {
	
	
	private static final Logger LOG=LoggerFactory.getLogger(HttpClientUtil.class);
	private static SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

	/**
	 *  
	 * ssl:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author 陈勋
	 * @param url
	 * @since JDK 1.7
	 */
    public static void   ssl(String url) {  
        CloseableHttpClient httpclient = null;  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));  
            try {  
                // 加载keyStore d:\\tomcat.keystore    
                trustStore.load(instream, "123456".toCharArray());  
            } catch (CertificateException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    instream.close();  
                } catch (Exception ignore) {  
                }  
            }  
            // 相信自己的CA和所有自签名的证书  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
            // 只允许使用TLSv1协议  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,  
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
            // 创建http请求(get方式)  
            HttpGet httpget = new HttpGet(url);   
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  

                if (entity != null) {  
                    EntityUtils.consume(entity);  
                    
                }  
            } finally {  
                response.close();  
            }  
        } catch (ParseException e) {  
        	LOG.error(e.getMessage());
        } catch (IOException e) {  
        	LOG.error(e.getMessage());
        } catch (KeyManagementException e) {  
        	LOG.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {  
        	LOG.error(e.getMessage());
        } catch (KeyStoreException e) {  
        	LOG.error(e.getMessage()); 
        } finally {  
            if (httpclient != null) {  
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                	LOG.error(e.getMessage());
                }  
            }  
        }  
    }  
  
   /**
   * 
   * postForm:(post提交表单). <br/>
   *
   * @author 陈勋
   * @param url
   * @param params
   * @return
   * @since JDK 1.7
   */
    public static String postForm(String url,Map<String, String> params) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, String> entry : params.entrySet()){
        	formparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    return EntityUtils.toString(entity, "UTF-8");
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
        	LOG.error(e.getMessage());  
        } catch (UnsupportedEncodingException e) {  
        	LOG.error(e.getMessage());
        } catch (IOException e) {  
        	LOG.error(e.getMessage()); 
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	LOG.error(e.getMessage());
            }  
        }
        
        return null;
		
    }  
  
   /**
    * 
    * get:(get方式发送请求). <br/>
    *
    * @author 陈勋
    * @param url
    * @return
    * @since JDK 1.7
    */
    public static String get(String url) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();   
                // 打印响应状态    
                LOG.info(response.getStatusLine()+"");  
                if (entity != null) {  
                	 return EntityUtils.toString(entity, "UTF-8");             	
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
        	LOG.error(e.getMessage());
        } catch (ParseException e) {  
        	LOG.error(e.getMessage());
        } catch (IOException e) {  
        	LOG.error(e.getMessage());
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	LOG.error(e.getMessage());
            }  
        }  
        
        return null;
    }  
  
	/**
	 * postSSL:(https协议,post方式发送K-V参数到指定url,返回响应报文). <br/>
	 * @author 曹旭楠
	 * @param submitUrl 请求地址
	 * @param params 请求参数,以Key-Value的方式组装map
	 * @since JDK 1.7
	 */
	public static String postSSL(String submitUrl,Map<String,Object> params){
		
		HttpsURLConnection conn;
		
		String response = "";
		try {
			String urlString = submitUrl;

			URL url = new URL(urlString);
			// 组织请求参数
			StringBuilder postBody = new StringBuilder();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() == null){
					
					continue;
				}
				postBody.append(entry.getKey()).append("=")
				.append(URLEncoder.encode(entry.getValue().toString(), "utf-8")).append("&");
			}

			if (!params.isEmpty()) {
				postBody.deleteCharAt(postBody.length() - 1);
			}

			conn = (HttpsURLConnection) url.openConnection();
			
			// 设置https
			conn.setSSLSocketFactory(ssf);
			// 设置长链接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置连接超时
			conn.setConnectTimeout(5000);
			// 设置读取超时
			conn.setReadTimeout(5000);
			// 提交参数
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			/**********************************************************************************************/
			// System.out.println("请求地址:" +submitUrl+"?"+ postBody.toString());
			/**********************************************************************************************/
			OutputStream out = conn.getOutputStream();
			
			out.write(postBody.toString().getBytes());
			conn.getOutputStream().flush();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				StringBuilder result = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line).append("\n");
				}
				/**********************************************************************************************/
				// System.out.println("responseResult:" + result);
				/**********************************************************************************************/
				response = result.toString();
				return response;
			}else{
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "utf-8"));
				StringBuilder result = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line).append("\n");
				}
				// System.out.println("responseResult:" + result);
				response = result.toString();
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	

}

