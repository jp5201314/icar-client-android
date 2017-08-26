package cn.icarowner.icarowner.entity;

/**
 * PaymentDetailsEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class PaymentDetailsEntity {
    /**
     * key : 套餐
     * amount : 40000
     */
    private String key;
    private int amount;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
