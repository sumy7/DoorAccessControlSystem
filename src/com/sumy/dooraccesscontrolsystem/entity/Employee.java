package com.sumy.dooraccesscontrolsystem.entity;

/**
 * 实体类，继承自 User，用于保存雇员的相关信息
 * 
 * @author sumy
 * 
 */
public class Employee extends User {
    private String cardid;
    private String photo;
    private boolean hasCard;

    public Employee(String userid, String name, String cardid, String photo,
            boolean hascard) {
        super(userid, name);
        this.cardid = cardid;
        this.photo = photo;
        this.hasCard = hascard;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isHasCard() {
        return this.hasCard;
    }

    public void makeCard() {
        this.hasCard = true;
    }

}
