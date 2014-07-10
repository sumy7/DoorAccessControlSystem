package com.sumy.dooraccesscontrolsystem.business;

import java.util.ArrayList;

import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.Door;
import com.sumy.dooraccesscontrolsystem.entity.Ring;
import com.sumy.dooraccesscontrolsystem.entity.User;

/**
 * 业务逻辑类，处理门相关的业务逻辑
 * 
 * @author sumy
 * 
 */
public class DoorSystem {
	private ArrayList<User> userlist; // 用户访问列表
	private Door door; // 门
	private Ring ring; // 门铃

	public DoorSystem() {
		userlist = new ArrayList<User>();
		door = new Door();
		ring = new Ring();

		userlist.add(new Admin("0", "admin", "admin"));
	}

	public ArrayList<User> getUserlist() {
		return userlist;
	}

	public Door getDoor() {
		return door;
	}

	public Ring getRing() {
		return ring;
	}

}
