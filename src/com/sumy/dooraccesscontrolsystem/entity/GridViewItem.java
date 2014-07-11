package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，存放 GridView 的每一项
 * 
 * @author sumy
 * 
 */
public class GridViewItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    private String name;
    private int pic;

    public GridViewItem(String name, int pic) {
        this.name = name;
        this.pic = pic;
    }

}
