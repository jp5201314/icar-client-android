package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * OrderEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class OrderEntity {
    /**
     * id : 6fe4985e-17c1-4361-87d4-44ea270f3ceb
     * order_no : 2016100812280856100481
     * user_id : 641dc61c-2915-3e28-8a1b-150366084eae
     * amount : 50000
     * discount : 0
     * pay_amount : 50000
     * status : 1
     * pay_channel : null
     * payed_at : null
     * created_at : 2016-10-08 12:26:08
     * goods : [{"id":"5e01fbce-8399-47ee-a562-5538a45c1596","name":"A保套餐","describe":"A保套餐","price":0,"pay_price":50000,"number":1}]
     */


    private String id;
    @JSONField(name = "order_no")
    private String orderNo;
    @JSONField(name = "user_id")
    private String userId;
    private int amount;
    private int discount;
    @JSONField(name = "pay_amount")
    private int payAmount;
    private int status;
    @JSONField(name = "pay_channel")
    private Object payChannel;
    @JSONField(name = "payed_at")
    private Object payedAt;
    @JSONField(name = "created_at")
    private String createdAt;
    private List<GoodsEntity> goods;
    @JSONField(name = "bound_coupon")
    private CouponEntity boundCoupon;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Object payChannel) {
        this.payChannel = payChannel;
    }

    public Object getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Object payedAt) {
        this.payedAt = payedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<GoodsEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsEntity> goods) {
        this.goods = goods;
    }

    public CouponEntity getBoundCoupon() {
        return boundCoupon;
    }

    public void setBoundCoupon(CouponEntity boundCoupon) {
        this.boundCoupon = boundCoupon;
    }
}
