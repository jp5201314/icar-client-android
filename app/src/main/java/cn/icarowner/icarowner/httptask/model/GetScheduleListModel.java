package cn.icarowner.icarowner.httptask.model;

import java.io.Serializable;
import java.util.List;

import cn.icarowner.icarowner.entity.ScheduleItemEntity;

/**
 * GetScheduleListModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class GetScheduleListModel implements Serializable {

    private int pages;
    private int total;
    private List<ScheduleItemEntity> orders;

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

    public List<ScheduleItemEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<ScheduleItemEntity> orders) {
        this.orders = orders;
    }
}
