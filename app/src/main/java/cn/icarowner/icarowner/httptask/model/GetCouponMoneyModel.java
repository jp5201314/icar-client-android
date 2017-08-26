package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * GetCouponMoneyModel
 * create by 崔婧
 * create at 2017/5/18 下午1:28
 */
public class GetCouponMoneyModel {
    @JSONField(name = "amount")
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
