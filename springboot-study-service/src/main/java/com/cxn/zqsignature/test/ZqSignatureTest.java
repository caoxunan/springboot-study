package com.cxn.zqsignature.test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.cxn.zqsignature.impl.ZqSignatureServiceImpl;
import com.cxn.zqsignature.model.ZqResultModel;
import com.cxn.zqsignature.support.Base64Utils;


public class ZqSignatureTest {

	/**
	 * 测试：个人用户颁发数字证书
	 * 测试结果：成功
	 */
	@Test
	public void testRegPerson() {
		Map<String, String> map = new HashMap<>();
		map.put("user_code", "201803061101320001");//用户唯一标示，该值不能重复
		map.put("name", "曹循安");//平台方用户姓名
		map.put("id_card_no", "232301199302016237");//身份证号
		map.put("mobile", "15326463525");//联系电话（手机号码）

		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();

		// 测试：个人用户颁发数字证书
		ZqResultModel resultModel = service.regPerson(map);
		System.out.println(resultModel);
	}

	/**
	 * 测试：个人用户更新数字证书
	 * 测试结果：成功
	 */
	@Test
	public void testUpdatePersonInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("user_code", "201803061101320001");//用户唯一标示，该值不能重复
		map.put("name", "曹丕");//平台方用户姓名
		map.put("id_card_no", "232301199302016537");//身份证号
		map.put("mobile", "15326463525");//联系电话（手机号码）

		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();

		// 测试：个人用户颁发数字证书
		ZqResultModel resultModel = service.updatePersonInfo(map);
		System.out.println(resultModel);
	}

	/**
	 * 测试：上传PDF文件创建合同
	 * 测试结果1：{"code":"100000","msg":"缺少zqid参数"}
	 * 测试结果2：成功
	 * 分析原因:可能是文件过大,仅换了一个小一些的文件,速度快很多并且返回成功
	 */
	@Test
	public void testUploadPdfCreateContract() {

		Map<String, String> map = new HashMap<>();
		map.put("user_code", "201803061101320001");//用户唯一标示，该值不能重复

		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();
		// 测试：上传PDF文件，创建合同
		map.put("no", "contract_no_201803060001");//自行创建合同编号，该值不可重复使用
		map.put("name", "购车合同");//合同名称
		File file2 = new File("D:\\test\\2018Test.pdf");
		String pdfBase64String = null;
		try {
			pdfBase64String = Base64Utils.encodeFile(file2.getPath() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("pdfBase64String:" + pdfBase64String);
		map.put("contract", pdfBase64String);//合同文件的base64
		ZqResultModel resultModel = service.uploadPdfCreateContract(map);

		System.out.println(resultModel);
	}

	/**
	 * 测试：PDF模板文件创建合同
	 * 请求结果：{"code":"130006","msg":"t_no不存在"}
	 */
	@Test
	public void testPdfTemplateCreateContract() {
		Map<String, String> map = new HashMap<>();
		map.put("no", "contract_no_201803060002");	// 合同编号
		map.put("name", "购车合同2");				// 合同名称
		map.put("t_no", "template001");				// 模板编号
		String jsonFill = "{\"jsonVal\":[{\"year\":\"2016\",\"month\":\"02\",\"day\":\"25,\"signer1\":\"user001\",\"signer2\":\"user002\"}]}";
		map.put("contract_val", jsonFill);			// 模板填充值

		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();

		// 测试：个人用户颁发数字证书
		ZqResultModel resultModel = service.pdfTemplateCreateContract(map);
		System.out.println(resultModel);
	}

	/**
	 * 测试：显示页面 签署
	 * 测试结果：成功
	 */
	@Test
	public void testShowSignView() {
		Map<String, String> map = new HashMap<>();
		map.put("no", "contract_no_201803060001");	//已存在的合同编号
		map.put("user_code", "201803061101320001");	//签署人的user_code
		map.put("sign_type", "SIGNATURE");			//签章不验证签署
		map.put("return_url", "http://oa2.midairen.com/returnUrl1");	//同步回调地址
		map.put("notify_url", "http://oa2.midairen.com/returnUrl2");	//异步回调地址

		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();

		// 测试：个人用户颁发数字证书
		String htmlStr = service.showSignView(map);
		System.out.println(htmlStr);

	}

	/**
	 * 测试：获取合同图片地址
	 * 测试结果：成功
	 * 地址：http://test.sign.zqsign.com:8081//contracts/img/4b80174be8e63104c5a2d8ff9a073811/contract_no_201803060001/1
	 */
	@Test
	public void testGetContractImg() {
		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();
		String contractNo = "contract_no_201803060001";
		ZqResultModel result = service.getContractImg(contractNo);
		String[] imglist = result.getImgList();
		System.out.println(Arrays.toString(imglist));
	}

	/**
	 * 测试：获取合同Url地址
	 * 测试结果：成功
	 */
	@Test
	public void testGetContractPdfUrl() {
		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();
		String contractNo = "contract_no_201803060001";
		ZqResultModel result = service.getContractPdfUrl(contractNo);
		System.out.println(result);
	}

	/**
	 * 测试：获取pdf文件
	 * 测试结果：成功(返回的是html页面，加载完成后自动提交隐藏表单数据完成下载)
	 */
	@Test
	public void testGetContractPdfStream() {
		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();
		String contractNo = "contract_no_201803060001";
		String contractPdfStream = service.getContractPdfStream(contractNo);
		System.out.println(contractPdfStream);
	}

	/**
	 * 测试：合同生效标记
	 * 请求结果:成功
	 * 请求结果：{"code":"600017","msg":"该合同已标记为生效状态，请勿重复操作"}
	 */
	@Test
	public void testCompletionContract() {
		ZqSignatureServiceImpl service = new ZqSignatureServiceImpl();
		String contractNo = "contract_no_201803060001";
		ZqResultModel resultModel = service.completionContract(contractNo);
		System.out.println(resultModel);
	}

	@Test
	public void testLowSpeedHash() {
		
		
		
	}
	
}
