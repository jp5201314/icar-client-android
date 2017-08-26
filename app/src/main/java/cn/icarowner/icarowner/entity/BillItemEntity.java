package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;


/**
 * BillItemEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class BillItemEntity implements Serializable {

    /**
     * id : 70007950-e634-4bc3-8ade-8fa5cf48abb0
     * plate_number : 川A20286
     * created_at : 2016-10-12 10:54:11
     * dealer_id : 1a6d0048-6986-4639-8401-3471d395dfaf
     * dealer_user_id : 7a79735c-1db1-4147-8e98-48d9faba0966
     * bill : {"id":"dd1588b9-5b39-447f-a9bd-faf4f5b525db","created_at":"2016-10-08 11:07:02","types":[{"id":"4ce94086-8410-4f4d-8e35-60d2803778a5","name":"A保","full_name":"A保套餐","bill_items":["套餐","其他"],"bill_detail_items":[{"key":"套餐","amount":40000},{"key":"其他","amount":10000}]}],"order":{"id":"6fe4985e-17c1-4361-87d4-44ea270f3ceb","order_no":"2016100812280856100481","user_id":"641dc61c-2915-3e28-8a1b-150366084eae","amount":50000,"discount":0,"pay_amount":50000,"status":1,"pay_channel":null,"payed_at":null,"created_at":"2016-10-08 12:26:08","goods":[{"id":"5e01fbce-8399-47ee-a562-5538a45c1596","name":"A保套餐","describe":"A保套餐","price":0,"pay_price":50000,"number":1}]}}
     */

    private String id;
    @JSONField(name = "plate_number")
    private String plateNumber;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "dealer_id")
    private String dealerId;
    @JSONField(name = "dealer_user_id")
    private String dealerUserId;

    private BillEntity bill;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }

}
