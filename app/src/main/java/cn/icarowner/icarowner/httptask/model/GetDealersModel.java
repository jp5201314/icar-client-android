package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import cn.icarowner.icarowner.entity.DealerEntity;

/**
 * GetDealersModel
 * create by 崔婧
 * create at 2017/5/18 下午1:28
 */
public class GetDealersModel {
    private int pages;
    private int total;
    @JSONField(name = "dealers")
    private List<DealerEntity> dealers;

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

    public List<DealerEntity> getDealers() {
        return dealers;
    }

    public void setDealers(List<DealerEntity> dealers) {
        this.dealers = dealers;
    }
}
