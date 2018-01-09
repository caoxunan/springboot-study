package com.cxn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxn.model.StudyTableModel;
import com.cxn.service.StudyTableService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/redis")
public class RedisController {

	@Reference(version = "1.0.0")
	private StudyTableService studyTableService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping(path="/findByCache/{id}",method={RequestMethod.GET})
	public StudyTableModel findByCache(@PathVariable("id") long id) throws Exception{

		System.out.println("进入方法:findByCache/"+id);
		ValueOperations<String, String>ops = stringRedisTemplate.opsForValue();
		String modelJson = ops.get(String.valueOf(id));
		ObjectMapper mapper = new ObjectMapper();
		if (modelJson == null) {
			System.out.println("缓存中没有值,从数据库中查询~");
			StudyTableModel	model = studyTableService.getByIdUseMapper(id);
			modelJson = mapper.writeValueAsString(model);
			ops.set(String.valueOf(id), modelJson);
			return model;
		}
		System.out.println("从redis中取出缓存~");
		StudyTableModel tableModel = mapper.readValue(modelJson, StudyTableModel.class);
		return tableModel;
	}
	
	@RequestMapping(path="/deleteByCache/{id}",method={RequestMethod.GET})
	public String deleteByCache(@PathVariable("id") long id){

		System.out.println("进入方法:findByCache/"+id);
		try {
			stringRedisTemplate.delete(String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			return"fail";
		}
		return"success";
	}
	
	@RequestMapping(path="/findByCache2/{id}",method={RequestMethod.GET})
	public StudyTableModel findByCache2(@PathVariable("id") long id) throws Exception{

		System.out.println(">>>>>>>>>>>>>>>>>>进入方法:findByCache2/"+id);
		ValueOperations<String, Object>opsForValue = redisTemplate.opsForValue();
		Object object = opsForValue.get(String.valueOf(id));
		if (object == null) {
			System.out.println("缓存中没有值~");
			StudyTableModel	model = studyTableService.getByIdUseMapper(id);
			opsForValue.set(String.valueOf(id), model);
			return model;
		}else{
			System.out.println("从redis中取出缓存~");
			StudyTableModel model1 = (StudyTableModel)object;
			return model1;
		}
	}

	@RequestMapping(path="/deleteByCache2/{id}",method={RequestMethod.GET})
	public String deleteByCache2(@PathVariable("id") long id){

		System.out.println(">>>>>>>>>>>>>>>>>>进入方法:deleteByCache2/"+id);
		try {
			redisTemplate.delete(String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			return"fail";
		}
		return"success";
	}

}
