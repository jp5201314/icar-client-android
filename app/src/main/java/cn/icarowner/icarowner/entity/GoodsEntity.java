package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * GoodsEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class GoodsEntity {
    /**
     * id : 5e01fbce-8399-47ee-a562-5538a45c1596
     * name : A保套餐
     * describe : A保套餐
     * price : 0
     * pay_price : 50000
     * number : 1
     */

    private String id;
    private String name;
    private String describe;
    private int price;
    @JSONField(name = "pay_price")
    private int payPrice;
    private int number;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(int payPrice) {
        this.payPrice = payPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
