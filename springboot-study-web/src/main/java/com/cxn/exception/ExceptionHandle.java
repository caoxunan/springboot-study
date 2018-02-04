package com.cxn.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxn.common.enums.ResultEnum;
import com.cxn.common.pojo.ResponseResult;
import com.cxn.common.pojo.ServiceException;
import com.cxn.common.utils.RestResultUtil;

/**
 * 统一异常处理
 * @author canner
 */
@ControllerAdvice
public class ExceptionHandle {

	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	@ExceptionHandler(value = Exception.class)//表示可处理Exception以内异常
	@ResponseBody
	public ResponseResult<?> handle(Exception e) {
		if (e instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) e;
//			已定义异常一般为业务判断，抛出异常，一般不需要log日志记录
//			logger.error(serviceException.getMessage() + "：{}", e);
			return RestResultUtil.error(serviceException.getErrorCode(), serviceException.getErrorMsg());
		} else {
			logger.error(ResultEnum.UNKONW_ERROR.getMsg() + "：{}", e);
			return RestResultUtil.error(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMsg());
		}
	}
}