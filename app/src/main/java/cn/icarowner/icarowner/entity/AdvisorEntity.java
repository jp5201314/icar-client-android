package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * AdvisorEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:19
 */
public class AdvisorEntity implements Serializable {

    /**
     * id : 3d318dbe-42c6-407c-8ee8-5e065842250e
     * dealer_id : 2844a0f8-5a5c-4340-8447-d4e1860c3a09
     * mobile : 13011112222
     * role : service_advisor
     * name : rname
     * avatar_url : http://domain/avatar_url.jpg
     * introduction : 个人简介
     * star : 5
     * status : 0
     * dealer : {"id":"2844a0f8-5a5c-4340-8447-d4e1860c3a09","name":"四川华星锦业","full_name":"四川华星锦业羊西店","status":0}
     */

    private String id;
    @JSONField(name = "dealer_id")
    private String dealerId;
    private String mobile;
    private String role;
    private String name;
    @JSONField(name = "avatar_url")
    private String avatarUrl;
    private String introduction;
    private float star;
    private int status;

    private DealerEntity dealer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }
}
