package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * UserEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:23
 */
public class UserEntity implements Serializable {


    /**
     * id : 21fe43d1-f6c4-4530-ba13-87932f91dd95
     * mobile : 13800001111
     * avatar_url : http://domain/image.jpg
     * name : My Name
     * gender : 1
     * birth : 1993-10-03
     * balance : 1000
     * status : 0
     */

    private String id;
    private String mobile;
    @JSONField(name = "avatar_url")
    private String avatarUrl;
    private String name;
    private int gender;
    private String birth;
    private int balance;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
