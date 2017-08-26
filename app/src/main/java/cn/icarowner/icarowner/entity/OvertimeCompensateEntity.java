package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * OvertimeCompensateEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class OvertimeCompensateEntity implements Serializable {

    /**
     * id : 98b016d8-1a3b-4671-987b-ecfe41dde534
     * types : ["98b016d8-1a3b-4671-987b-ecfe41dde534"]
     * basic_items_spend : 60
     * add_items : [{"name":"增项1","spend":1}]
     * total_spend : 66
     * actual_into_factory_at : null
     * actual_out_factory_at : null
     * actual_spend : null
     * overtime : null
     * compensate_fee : null
     */

    private String id;
    @JSONField(name = "basic_items_spend")
    private int basicItemsSpend;
    @JSONField(name = "total_spend")
    private int totalSpend;
    @JSONField(name = "actual_into_factory_at")
    private String actualIntoFactoryAt;
    @JSONField(name = "actual_out_factory_at")
    private String actualOutFactoryAt;
    @JSONField(name = "actual_spend")
    private int actualSpend;
    private int overtime;
    @JSONField(name = "compensate_fee")
    private int compensateFee;
    private List<String> types;
    @JSONField(name = "add_items")
    private List<AddItemsEntity> addItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBasicItemsSpend() {
        return basicItemsSpend;
    }

    public void setBasicItemsSpend(int basicItemsSpend) {
        this.basicItemsSpend = basicItemsSpend;
    }

    public int getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(int totalSpend) {
        this.totalSpend = totalSpend;
    }

    public String getActualIntoFactoryAt() {
        return actualIntoFactoryAt;
    }

    public void setActualIntoFactoryAt(String actualIntoFactoryAt) {
        this.actualIntoFactoryAt = actualIntoFactoryAt;
    }

    public String getActualOutFactoryAt() {
        return actualOutFactoryAt;
    }

    public void setActualOutFactoryAt(String actualOutFactoryAt) {
        this.actualOutFactoryAt = actualOutFactoryAt;
    }

    public int getActualSpend() {
        return actualSpend;
    }

    public void setActualSpend(int actualSpend) {
        this.actualSpend = actualSpend;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getCompensateFee() {
        return compensateFee;
    }

    public void setCompensateFee(int compensateFee) {
        this.compensateFee = compensateFee;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<AddItemsEntity> getAddItems() {
        return addItems;
    }

    public void setAddItems(List<AddItemsEntity> addItems) {
        this.addItems = addItems;
    }
}
