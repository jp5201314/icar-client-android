package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BalanceChangeRecordEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:19
 */
public class BalanceChangeRecordEntity {


    /**
     * id : 9e19a76f-1a86-4135-9b29-968290a96f15
     * user_id : d25ea1b2-e23c-456f-b8cc-fd6a2aadd73d
     * amount : 216200
     * balance : 284300
     * type : expend_service_bill_payment
     * type_name : 支付
     * created_at : 2017-04-06 14:37:33
     */

    private String id;
    @JSONField(name = "user_id")
    private String userId;
    private int amount;
    private int balance;
    private String type;
    @JSONField(name = "type_name")
    private String typeName;
    @JSONField(name = "created_at")
    private String createdAt;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
