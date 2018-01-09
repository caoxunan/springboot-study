package com.cxn.controller;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxn.model.User;
import com.mongodb.WriteResult;

@Controller
public class MongoController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostMapping(path="/saveUser")
	@ResponseBody
	public String saveUser(@RequestBody User user){

		System.out.println(user);

		mongoTemplate.save(user);

		return"success";
	}

	@PostMapping(path="/removeUserByName")
	@ResponseBody
	public WriteResult  removeUserByName(@RequestParam String username){

		System.out.println("username:"+username);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("username").is(username));

		WriteResult result = mongoTemplate.remove(new Query(criteria), User.class);
		System.out.println("WriteResult:"+result);

		return result;

	}

	@PostMapping(path="/updateUserByName")
	@ResponseBody
	public WriteResult  updateUserByName(@RequestParam("username") String username, @RequestParam("updateName") String updateName){

		System.out.println("username:"+username);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("username").is(username));

		Update update = new Update();
		update.set("username", updateName);

		WriteResult result = mongoTemplate.updateFirst(new Query(criteria), update, User.class);
		System.out.println("WriteResult:"+result);

		// mongoTemplate.updateMulti(query, update, entityClass);
		return result;

	}

	@GetMapping(path="/getUserListByName")
	@ResponseBody
	public List<User> getUserListByName(@RequestParam String username){

		System.out.println("username:"+username);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("username").is(username));
		Query query = new Query(criteria);

		List<User>userList = mongoTemplate.find(query, User.class);

		return userList;

	}

	@GetMapping(path="/getOneUserByName")
	@ResponseBody
	public User getOneUserByName(@RequestParam String username){

		System.out.println("username:"+username);
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("username").is(username));

		User user = mongoTemplate.findOne(new Query(criteria), User.class);
		System.out.println("User:"+user);

		return user;

	}

	// 模糊查询
	@GetMapping(path="/getUserByNameSample")
	@ResponseBody
	public List<User> getUserByNameSample(@RequestParam String username){

		System.out.println("username:"+username);
		Criteria criteria = new Criteria();
		Pattern pattern = Pattern.compile("^.*" + username + ".*$", Pattern.CASE_INSENSITIVE);
		criteria.orOperator(Criteria.where("username").regex(pattern));
		Query query = new Query(criteria);
		query.skip(0);	// 从哪条记录开始(第一条数据是0)
		query.limit(12);// 取多少条记录
		List<User>userList = mongoTemplate.find(query, User.class);

		long count = mongoTemplate.count(query, User.class);
		System.out.println(count);

		return userList;

	}

}
