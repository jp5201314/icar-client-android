package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ButtonStatusEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class ButtonStatusEntity implements Serializable {

    /**
     * un_finished : 0
     * pending_pay : 0
     * pending_evaluated : 0
     * finished : 0
     */
    @JSONField(name = "un_finished")
    private int unFinished;
    @JSONField(name = "pending_pay")
    private int pendingPay;
    @JSONField(name = "pending_evaluated")
    private int pendingEvaluated;
    private int finished;

    public int getUnFinished() {
        return unFinished;
    }

    public void setUnFinished(int unFinished) {
        this.unFinished = unFinished;
    }

    public int getPendingPay() {
        return pendingPay;
    }

    public void setPendingPay(int pendingPay) {
        this.pendingPay = pendingPay;
    }

    public int getPendingEvaluated() {
        return pendingEvaluated;
    }

    public void setPendingEvaluated(int pendingEvaluated) {
        this.pendingEvaluated = pendingEvaluated;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
