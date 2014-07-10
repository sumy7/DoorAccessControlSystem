package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，用于储存经理的相关信息
 * 
 * @author sumy
 * 
 */
public class Manager extends User {
	private String figure;// 指纹特征码

	public Manager(String userid, String name, String figure) {
		super(userid, name);
		this.figure = figure;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

}
