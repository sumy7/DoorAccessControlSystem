package com.sumy.dooraccesscontrolsystem.validate;

import java.util.List;

import com.sumy.dooraccesscontrolsystem.entity.User;

/**
 * 接口类，用于验证用户权限
 * 
 * @author sumy
 * 
 */
public interface Validate {
	/**
	 * @param users
	 *            用户列表
	 * @return 验证结果 <b>true</b> 验证成功 <b>false</b> 验证失败
	 */
	public boolean checkIn(List<User> users);
}
