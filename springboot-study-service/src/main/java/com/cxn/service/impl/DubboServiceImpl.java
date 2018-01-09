package com.cxn.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cxn.service.DubboService;

@Service(version="1.0.0",protocol="dubbo")
public class DubboServiceImpl implements DubboService {
	
	@Override
	public String getResult() {
		return "remote success";
	}
	
}