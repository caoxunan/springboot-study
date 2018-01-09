package com.cxn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

@Controller
@RequestMapping(path="/oss")
public class OssController {

	@GetMapping(path="/getNameList")
	@ResponseBody
	public List<String> getNameList(){

		String endpoint = "******";
		String accessId = "******";
		String accessKey = "******";
		// 初始化一个OSSClient
		OSSClient client = new OSSClient(endpoint, accessId, accessKey);

		String bucketName = "osstest";

		// 获取指定bucket下的所有Object信息
		ObjectListing listing = client.listObjects(bucketName);
		List<String>nameList = new ArrayList<>();
		// 遍历所有Object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
			nameList.add(objectSummary.getKey());
		}

		return nameList;
	}

}
