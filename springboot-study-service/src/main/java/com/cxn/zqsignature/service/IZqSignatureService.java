package com.cxn.zqsignature.service;

import java.util.Map;

import com.cxn.zqsignature.model.ZqResultModel;

/**
 * 第三方签章接口对接
 * @author caoxunan
 *
 */
public interface IZqSignatureService {

	/**
	 * RegPerson:(个人用户颁发数字证书). <br/>
	 * @author 曹旭楠  
	 * @param map:
	 * 		user_code	用户唯一标示，该值不能重复
	 * 		name 		平台方用户姓名
	 * 		id_card_no 	平台方身份证号
	 * 		mobile		平台方联系电话（手机号码）
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel regPerson(Map<String, String> map);
		
	/**
	 * updatePersonInfo:(个人用户更新数字证书). <br/>
	 * @author 曹旭楠  
	 * @param map:
	 * 		user_code	用户唯一标示,只更新该用户的信息
	 * 		name 		平台方用户姓名
	 * 		id_card_no 	平台方身份证号
	 * 		mobile		平台方联系电话（手机号码）
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel updatePersonInfo(Map<String, String> map);

	/**
	 * uploadPdfCreateContract:(上传PDF创建合同). <br/>
	 * @author 曹旭楠  
	 * @param map:
	 * 		user_code	用户唯一标示,只更新该用户的信息
	 * 		no 			自行创建合同编号，该值不可重复使用
	 * 		name 		合同名称
	 * 		contract	合同文件的base64
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel uploadPdfCreateContract(Map<String, String> map);
	
	/**
	 * pdfTemplateCreateContract:(PDF模板文件创建合同). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		user_code		用户唯一标示,只更新该用户的信息
	 * 		no 				自行创建合同编号，该值不可重复使用
	 * 		name 			合同名称
	 * 		t_no 			合同模板编号
	 * 		contract_val	表单的json串
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel pdfTemplateCreateContract(Map<String, String> map);
	
	/**
	 * showSignView:(显示页面签署). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		user_code					签署人的user_code
	 * 		no 							已存在的合同编号
	 * 		sign_type:"SIGNATURECODE"	签章验证签署
	 * 		sign_type:"SIGNATURE"		签章不验证签署
	 * 		return_url 					同步回调地址
	 * 		notify_url					异步回调地址
	 * @return String		返回html页面
	 * @since JDK 1.7
	 */
	public String showSignView(Map<String, String> map);
	
	/**
	 * getContractImg:(获取合同图片地址). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		contractNo		已存在的合同编号
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel getContractImg(String contractNo);
	
	/**
	 * getContractPdf:(获取合同pdf地址). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		contractNo		已存在的合同编号
	 * @return ZqResultModel
	 * @since JDK 1.7
	 */
	public ZqResultModel getContractPdfUrl(String contractNo);
	
	/**
	 * getContractPdfStream:(获取合同文件流). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		contractNo		已存在的合同编号
	 * @return String
	 * @since JDK 1.7
	 */
	public String getContractPdfStream(String contractNo);
	
	/**
	 * completionContract:(合同生效标记(必调接口)). <br/>
	 * @author 曹旭楠
	 * @param map:
	 * 		contractNo		已存在的合同编号
	 * @return ZqResultBean
	 * @since JDK 1.7
	 */
	public ZqResultModel completionContract(String contractNo);
	
	
}
