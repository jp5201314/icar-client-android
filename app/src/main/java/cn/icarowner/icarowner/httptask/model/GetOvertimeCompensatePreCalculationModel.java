package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * GetOvertimeCompensatePreCalculationModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class GetOvertimeCompensatePreCalculationModel {

    /**
     * actual_spend : 9
     * overtime : 3
     * compensate_fee : 12
     */

    @JSONField(name = "actual_spend")
    private int actualSpend;
    private int overtime;
    @JSONField(name = "compensate_fee")
    private int compensateFee;

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
}
