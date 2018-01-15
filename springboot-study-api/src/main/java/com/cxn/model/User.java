package com.cxn.model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 5660765133743381891L;
	
	private String username;
	private String password;
	private int age;
	private double height;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", age=" + age + ", height=" + height + "]";
	}
	
}
