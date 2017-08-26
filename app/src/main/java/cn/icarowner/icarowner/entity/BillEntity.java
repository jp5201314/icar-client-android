package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * BillEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class BillEntity implements Serializable {
    /**
     * id : dd1588b9-5b39-447f-a9bd-faf4f5b525db
     * created_at : 2016-10-08 11:07:02
     * types : [{"id":"4ce94086-8410-4f4d-8e35-60d2803778a5","name":"A保","full_name":"A保套餐","bill_items":["套餐","其他"],"bill_detail_items":[{"key":"套餐","amount":40000},{"key":"其他","amount":10000}]}]
     * order : {"id":"6fe4985e-17c1-4361-87d4-44ea270f3ceb","order_no":"2016100812280856100481","user_id":"641dc61c-2915-3e28-8a1b-150366084eae","amount":50000,"discount":0,"pay_amount":50000,"status":1,"pay_channel":null,"payed_at":null,"created_at":"2016-10-08 12:26:08","goods":[{"id":"5e01fbce-8399-47ee-a562-5538a45c1596","name":"A保套餐","describe":"A保套餐","price":0,"pay_price":50000,"number":1}]}
     */
    private String id;
    @JSONField(name = "created_at")
    private String createdAt;

    private OrderEntity order;


    private List<TypesEntity> types;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public List<TypesEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypesEntity> types) {
        this.types = types;
    }

}
