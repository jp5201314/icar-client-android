package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * DonationCouponsEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class DonationCouponsEntity implements Serializable {

    /**
     * key : service_type_common
     * name : 通用
     * discount : 10000
     * can_received : 1
     */
    private String key;
    private String name;
    private int discount;
    @JSONField(name = "can_received")
    private int canReceived;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public int getCanReceived() {
        return canReceived;
    }

    public void setCanReceived(int canReceived) {
        this.canReceived = canReceived;
    }
}
