package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，继承自User，用于存储管理员的相关信息
 * 
 * @author sumy
 * 
 */
public class Admin extends User {
	private String password; // 用户密码

	public Admin(String userid, String name, String password) {
		super(userid, name);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
