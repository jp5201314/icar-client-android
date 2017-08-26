package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ChoiceCouponEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class ChoiceCouponEntity implements Serializable {


    /**
     * bound_coupon : {"id":"98b016d8-1a3b-4671-987b-ecfe41dde534","name":"A保","discount":5000,"effective_at":"2016-10-17 17:12:06","expired_at":"2016-10-19 17:12:06","status":0}
     * amount : 10000
     * discount : 5000
     * pay_amount : 5000
     */

    @JSONField(name = "bound_coupon")
    private BoundCouponEntity boundCoupon;
    private int amount;
    private int discount;
    @JSONField(name = "pay_amount")
    private int payAmount;

    public BoundCouponEntity getBoundCoupon() {
        return boundCoupon;
    }

    public void setBoundCoupon(BoundCouponEntity boundCoupon) {
        this.boundCoupon = boundCoupon;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }


}
