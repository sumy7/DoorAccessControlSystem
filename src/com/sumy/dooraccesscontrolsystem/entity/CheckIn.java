package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，存放签到信息
 * 
 * @author sumy
 * 
 */
public class CheckIn {
    private int id;
    private String name;
    private String date;

    public CheckIn(String name, String date) {
        this.id = 0;
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "id:" + id + " name:" + name + " time=[" + date + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
