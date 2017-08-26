package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * CancelCouponModel
 * create by 崔婧
 * create at 2017/5/18 下午1:27
 */
public class CancelCouponModel implements Serializable {
    private String id;
    @JSONField(name = "order_no")
    private String orderNo;
    private int amount;
    private int discount;
    @JSONField(name = "pay_amount")
    private int payAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
