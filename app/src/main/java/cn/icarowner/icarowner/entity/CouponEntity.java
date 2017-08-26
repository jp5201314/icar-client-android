package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * CouponEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class CouponEntity implements Serializable {


    /**
     * id : b53506a0-0d6d-4ff0-8c3c-750ecd44905c
     * user_id : eceeeaa3-14de-46f4-8f17-39ec84bd75c6
     * dealer_id : 37737208-28ba-4a16-bc3d-738ebbd9ff09
     * dealer : {"id":"37737208-28ba-4a16-bc3d-738ebbd9ff09","name":"羊西店","full_name":"四川华星锦业羊西店","status":0}
     * name : A保
     * discount : 10000
     * effective_at : 2016-06-07 00:00:00
     * expired_at : 2016-08-08 23:59:59
     */

    private String id;
    @JSONField(name = "user_id")
    private String userId;
    @JSONField(name = "dealer_id")
    private String dealerId;

    private DealerEntity dealer;
    private String name;
    private int discount;
    @JSONField(name = "effective_at")
    private String effectiveAt;
    @JSONField(name = "expired_at")
    private String expiredAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEffectiveAt() {
        return effectiveAt;
    }

    public void setEffectiveAt(String effectiveAt) {
        this.effectiveAt = effectiveAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}
