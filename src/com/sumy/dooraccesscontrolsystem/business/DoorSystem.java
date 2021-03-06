package com.sumy.dooraccesscontrolsystem.business;

import java.util.ArrayList;

import android.os.Environment;

import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.Door;
import com.sumy.dooraccesscontrolsystem.entity.Ring;
import com.sumy.dooraccesscontrolsystem.entity.User;
import com.sumy.dooraccesscontrolsystem.utils.XMLTools;
import com.sumy.dooraccesscontrolsystem.validate.Validate;

/**
 * 业务逻辑类，处理门相关的业务逻辑 </br>
 * 
 * 保证唯一，使用单例模式
 * 
 * @author sumy
 * 
 */
public class DoorSystem {
    private static DoorSystem singleInstance = null; // 单例模式对象

    public static String AUTOSAVE_PATH = Environment
            .getExternalStorageDirectory() + "/doorsystemautosave.xml";

    private ArrayList<User> userlist; // 用户访问列表
    private Door door; // 门
    private Ring ring; // 门铃

    private int currentIDNumber = -1;

    private DoorSystem() {
        userlist = XMLTools.readXML(AUTOSAVE_PATH);
        if (userlist == null) {
            userlist = new ArrayList<User>();
            userlist.add(new Admin("0", "admin", "123456"));
        }
        door = new Door();
        ring = new Ring();

        // 遍历用户列表，存储最大的用户ID
        int idnumber = 0;
        for (User user : userlist) {
            int userid = Integer.parseInt(user.getUserid());
            if (userid > idnumber) {
                idnumber = userid;
            }
        }
        currentIDNumber = idnumber;
    }

    /**
     * 取得单例对象
     * 
     * @return 验证系统的对象
     */
    public static DoorSystem getInstance() {
        if (singleInstance == null) {
            singleInstance = new DoorSystem();
        }
        return singleInstance;
    }

    /**
     * 验证用户权限
     * 
     * @param pValidate
     *            验证器接口
     * @return <b>true</b>验证成功 <b>false</b>验证失败
     */
    public boolean Check(Validate pValidate) {
        // TODO 用户不同，验证的方式不同
        return pValidate.checkIn(userlist);
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

    public void setUserlist(ArrayList<User> userlist) {
        this.userlist = userlist;
    }

    public int getNextUserNumber() {
        currentIDNumber++;
        return currentIDNumber;
    }

}
