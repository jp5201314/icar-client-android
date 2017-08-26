package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * TypesEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:23
 */
public class TypesEntity {
    /**
     * id : 4ce94086-8410-4f4d-8e35-60d2803778a5
     * name : A保
     * full_name : A保套餐
     * bill_items : ["套餐","其他"]
     * bill_detail_items : [{"key":"套餐","amount":40000},{"key":"其他","amount":10000}]
     */

    private String id;
    private String name;
    @JSONField(name = "full_name")
    private String fullName;
    @JSONField(name = "bill_items")
    private List<String> billItems;
    @JSONField(name = "bill_detail_items")
    private List<PaymentDetailsEntity> billDetailItems;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<String> billItems) {
        this.billItems = billItems;
    }

    public List<PaymentDetailsEntity> getBillDetailItems() {
        return billDetailItems;
    }

    public void setBillDetailItems(List<PaymentDetailsEntity> billDetailItems) {
        this.billDetailItems = billDetailItems;
    }

}