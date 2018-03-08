package com.cxn.zqsignature.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cxn.zqsignature.model.ZqResultModel;
import com.cxn.zqsignature.service.IZqSignatureService;
import com.cxn.zqsignature.support.EncryptData;
import com.cxn.zqsignature.support.HttpRequest;
import com.cxn.zqsignature.support.ZqSubmit;
import com.google.gson.Gson;


/**
 * ZqSignatureServiceImpl：第三方电子签章接口实现类
 * @author 曹旭楠
 *
 */
public class ZqSignatureServiceImpl implements IZqSignatureService{

	String private_key="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANIwqMKRiZMTMerWYJsp54AoMUcIbgZsdB4FjtGAzabh/NYH9ptNgNBfBo78yShPCP5c0wB0MVqg3wv5ExQRcCA5uj1ajO+FuHy5ESxmDDftxOzQlpHlMdvxCLZwJjy0+Il2AsZcbcSy3HMDN8HGhOG01A9rllbx6JnyC8hFdd+7AgMBAAECgYBztZHRuqjPrGt4ahe4k3L73CR0hDF9m8q4lDqxHoUX76RudufNSvc0vnsvz/01EX1T+em2gECDMbhYMP/NtmPQegoVIsojSGSSF8Q+q7JOCQlDi9JXiRMkoj+uSMeSqa4EbqOdoFAj+F8BlzYJCUCdfdcJRR4Zb8seFNlpUfDToQJBAPMGQt8dWfFGDGlo9Tnif5GIlz09Of7odn/NOyFb6c+fca0ufrg816GWGgLBl0qnj8bO/93P+EY0MWsVF8RytRkCQQDdaZtWGm9YImGT+PKdKapQvt0C5RAfi2OAnRndqCs8bA1K1kPII8hg/t2QFPshx48pqayJ7ve5/dmeig1y0eHzAkAKWnHu32k9hiZxNy97T9LveEo5KaqW2YBy4WNrgGbtmXVWU2zCnJTzJVnmVCkF3S2a4qaz5HBHTWHtlfB1Rg3BAkEA0cpr3fTkRX0mOf/rWhENiL6gSUrjsQ/w8v9ob8cVWIYFPkCxLuUAyy8Snp/SqFofA1n62yMrZPbriTXDsmS+EwJBAOFhYJS/x04TKX3H4iGDXLKLTSaQWoDyHBIZG61HSLVI8UTTre/Efc8jrs6GnYXkXAA0KeAcUQDxdeF0YRFhc2g="; //私钥放置的位置

	@Override
	public ZqResultModel regPerson(Map<String, String> map) {

		String request_url = "http://test.sign.zqsign.com:8081/personReg";

		EncryptData ed = new EncryptData();

		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应

		String sign_val = ed.encrptData(map,private_key);

		map.put("sign_val", sign_val); //请求参数的签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果

		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public ZqResultModel updatePersonInfo(Map<String, String> map) {

		String request_url = "http://test.sign.zqsign.com:8081/personUpdate";

		EncryptData ed = new EncryptData();

		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应

		String sign_val = ed.encrptData(map,private_key);

		map.put("sign_val", sign_val); //请求参数的签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 记录日志操作信息
		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果

		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public ZqResultModel uploadPdfCreateContract(Map<String, String> map) {

		String request_url = "http://test.sign.zqsign.com:8081/uploadPdf";

		EncryptData ed = new EncryptData();
		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应
		
		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密

		map.put("sign_val", sign_val); //请求参数的签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{

		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果
		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public ZqResultModel pdfTemplateCreateContract(Map<String, String> map) {

		String request_url = "http://test.sign.zqsign.com:8081/pdfTemplate";

		EncryptData ed = new EncryptData();

		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应

		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密

		map.put("sign_val", sign_val); //请求参数的签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果
		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public String showSignView(Map<String, String> map) {

		String request_url = "http://test.sign.zqsign.com:8081/signView";

		EncryptData ed = new EncryptData();

		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应

		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密

		map.put("sign_val", sign_val);//添加签名值
		String requestHtmlText = null;
		try {
			//向服务端发送请求，并接收请求结果
			requestHtmlText = ZqSubmit.buildRequest(map,request_url, private_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("请求结果：" + requestHtmlText);//输出服务器响应结果
		return requestHtmlText;
	}

	@Override
	public ZqResultModel getContractImg(String contractNo) {

		String request_url = "http://test.sign.zqsign.com:8081/getImg";

		EncryptData ed = new EncryptData();

		Map<String,String> map = new HashMap<String,String>();
		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应
		map.put("no", contractNo);//已存在的合同编号

		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密

		map.put("sign_val", sign_val);//添加签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果
		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public ZqResultModel getContractPdfUrl(String contractNo) {

		String request_url = "http://test.sign.zqsign.com:8081/getPdfUrl";

		EncryptData ed = new EncryptData();

		Map<String,String> map = new HashMap<String,String>();
		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应
		map.put("no", contractNo);//已存在的合同编号

		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密

		map.put("sign_val", sign_val);//添加签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// TODO Auto-generated catch block
		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果
		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		return resultModel;
	}

	@Override
	public String getContractPdfStream(String contractNo) {

		String request_url = "http://test.sign.zqsign.com:8081/getPdf";

		EncryptData ed = new EncryptData();

		Map<String,String> map = new HashMap<String,String>();
		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应
		map.put("no", contractNo);//已存在的合同编号

		String sign_val = ed.encrptData(map,private_key);//对请求进行签名加密
		map.put("sign_val", sign_val); //请求参数的签名值

		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = ZqSubmit.buildRequest(map,request_url, private_key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// TODO Auto-generated catch block
		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果

		/*       
		//返回合同字节流
        byte[] response_str = HttpRequest..sendPostReturnByte(request_url, map);

        //把pdf文件输出到硬盘，如果需要直接使用文件流，可将下面内容注释。
        FileOutputStream fileOutputStream = new FileOutputStream("D:/test.pdf");
        IOUtils.write(response_str, fileOutputStream);
        System.out.println("当前文件流已转成PDF文件放在  D:/test.pdf");
		*/
		return response_str;
	}

	@Override
	public ZqResultModel completionContract(String contractNo) {

		String request_url = "http://test.sign.zqsign.com:8081/completionContract";

		EncryptData ed = new EncryptData();

		Map<String,String> map = new HashMap<String,String>();

		map.put("zqid", "ZQABA206A379B342FB987B8DCCBA679549");//商户的zqid,该值需要与private_key对应
		map.put("no", contractNo);//已存在的合同编号

		String sign_val = ed.encrptData(map,private_key);

		map.put("sign_val", sign_val); //请求参数的签名值
		String response_str = null;
		try {
			//向服务端发送请求，并接收请求结果
			response_str = HttpRequest.sendPost(request_url, map);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		System.out.println("请求结果：" + response_str);//输出服务器响应结果
		// 将请求结果封装resultModel
		Gson gson = new Gson();		
		ZqResultModel resultModel = gson.fromJson(response_str, ZqResultModel.class);
		
		return resultModel;
	}

}
