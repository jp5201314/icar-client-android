package cn.icarowner.icarowner.httptask.model;

import java.io.Serializable;
import java.util.List;

import cn.icarowner.icarowner.entity.BillItemEntity;

/**
 * GetBillsModel
 * create by 崔婧
 * create at 2017/5/18 下午1:28
 */
public class GetBillsModel implements Serializable {

    private int pages;
    private int total;

    private List<BillItemEntity> orders;

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

    public List<BillItemEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<BillItemEntity> orders) {
        this.orders = orders;
    }
}
