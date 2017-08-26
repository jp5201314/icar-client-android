package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

import cn.icarowner.icarowner.entity.BalanceChangeRecordEntity;

/**
 * GetBalanceChangeRecordsModel
 * create by 崔婧
 * create at 2017/5/18 下午1:28
 */
public class GetBalanceChangeRecordsModel implements Serializable {

    private int pages;
    private int total;
    @JSONField(name = "movements")
    private List<BalanceChangeRecordEntity> balanceChangeRecordEntities;

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

    public List<BalanceChangeRecordEntity> getBalanceChangeRecordEntities() {
        return balanceChangeRecordEntities;
    }

    public void setBalanceChangeRecordEntities(List<BalanceChangeRecordEntity> balanceChangeRecordEntities) {
        this.balanceChangeRecordEntities = balanceChangeRecordEntities;
    }
}
