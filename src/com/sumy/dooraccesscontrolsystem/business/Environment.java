package com.sumy.dooraccesscontrolsystem.business;

import java.util.Date;

import com.sumy.dooraccesscontrolsystem.entity.Admin;

/**
 * 环境类，存储系统的各种配置信息
 * 
 * @author sumy
 * 
 */
public class Environment {
    private static boolean isLogined = false;
    private static Date lastLoginDate = null;
    private static Admin loginedAdmin = null;

    private static int currentUserID = 0;
    private static boolean isinitUserID = false;

    public static void setLoginedAdmin(Admin adminuser) {
        if (adminuser != null) {
            isLogined = true;
            lastLoginDate = new Date(System.currentTimeMillis());
            loginedAdmin = adminuser;
        }
    }

    public static Admin getLoginedAdmin() {
        if (isLogined == false)
            return null;
        return loginedAdmin;
    }

    public static int getNextUserID() {
        if (!isinitUserID) {
            return -1;
        }
        currentUserID++;
        return currentUserID;
    }

    public static void initUserID() {
        // TODO 获取最大的编号
    }
}
