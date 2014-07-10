package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，继承自 User，用于保存雇员的相关信息
 * 
 * @author sumy
 * 
 */
public class Employee extends User {
	private String cardid;

	public Employee(String userid, String name, String cardid) {
		super(userid, name);
		this.cardid = cardid;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

}
