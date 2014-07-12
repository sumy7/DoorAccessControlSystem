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
}
