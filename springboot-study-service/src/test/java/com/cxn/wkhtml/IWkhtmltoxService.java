package com.cxn.wkhtml;

import java.util.Map;

/**
 * wkhtmltox工具service
 * 根据模板生成html然后转换成pdf or jpg
 * @version 1.0 
 */
public interface IWkhtmltoxService {

	/**
	 * html转换Pdf或jpg
	 * @param map 
	 * 
	 * @return TranscodeResult
	 */
	public TranscodeResult convertToX (Map<String, Object> map,WkhtmltoxEnum wkEnum);
	
	public TranscodeResult convertToX (Map<String, Object> map,WkhtmltoxEnum wkEnum,boolean excelFlag);
}
