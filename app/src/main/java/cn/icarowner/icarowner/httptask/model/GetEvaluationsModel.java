package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import cn.icarowner.icarowner.entity.EvaluationItemEntity;

/**
 * GetEvaluationsModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class GetEvaluationsModel {
    private int pages;
    private int total;
    @JSONField(name = "orders")
    private List<EvaluationItemEntity> entities;

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

    public List<EvaluationItemEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<EvaluationItemEntity> entities) {
        this.entities = entities;
    }
}
