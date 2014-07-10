package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，存储用户的基本信息，包括用户编号和用户名
 * 
 * @author sumy
 * 
 */
public class User {
	private String userid; // 用户ID
	private String name;// 用户名

	public User(String userid, String name) {
		this.userid = userid;
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
