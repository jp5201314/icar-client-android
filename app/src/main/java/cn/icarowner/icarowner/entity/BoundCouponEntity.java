package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * BoundCouponEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class BoundCouponEntity implements Serializable {
    /**
     * id : 98b016d8-1a3b-4671-987b-ecfe41dde534
     * name : A保
     * discount : 5000
     * effective_at : 2016-10-17 17:12:06
     * expired_at : 2016-10-19 17:12:06
     * status : 0
     */
    private String id;
    private String name;
    private int discount;
    @JSONField(name = "effective_at")
    private String effectiveAt;
    @JSONField(name = "expired_at")
    private String expiredAt;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
