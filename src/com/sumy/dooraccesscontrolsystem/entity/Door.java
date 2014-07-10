package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，门
 * 
 * @author sumy
 * 
 */
public class Door {
	private boolean isOpen = false;

	/**
	 * 开门操作
	 */
	public void toOpen() {
		if (!isOpen()) {
			isOpen = true;
		}
	}

	/**
	 * 关门操作
	 */
	public void toClose() {
		if (isOpen()) {
			isOpen = false;
		}
	}

	/**
	 * 返回门的状态
	 * 
	 * @return <b>true</b> 门已打开 <b>false</b> 门关闭
	 */
	public boolean isOpen() {
		return this.isOpen;
	}
}
