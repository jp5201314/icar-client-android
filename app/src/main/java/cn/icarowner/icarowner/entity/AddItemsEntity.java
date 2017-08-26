package cn.icarowner.icarowner.entity;

import java.io.Serializable;

/**
 * AddItemsEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:19
 */
public class AddItemsEntity implements Serializable {
    /**
     * name : 增项1
     * spend : 1
     */

    private String name;
    private int spend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpend() {
        return spend;
    }

    public void setSpend(int spend) {
        this.spend = spend;
    }
}
