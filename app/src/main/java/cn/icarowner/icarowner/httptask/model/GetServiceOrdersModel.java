package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

import cn.icarowner.icarowner.entity.ServiceOrderItemEntity;

/**
 * GetServiceOrdersModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class GetServiceOrdersModel implements Serializable {
    private int pages;
    private int total;
    @JSONField(name = "orders")
    private List<ServiceOrderItemEntity> serviceOrderItemEntities;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ServiceOrderItemEntity> getServiceOrderItemEntities() {
        return serviceOrderItemEntities;
    }

    public void setServiceOrderItemEntities(List<ServiceOrderItemEntity> serviceOrderItemEntities) {
        this.serviceOrderItemEntities = serviceOrderItemEntities;
    }
}
