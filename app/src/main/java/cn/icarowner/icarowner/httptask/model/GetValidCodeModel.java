package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * GetValidCodeModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class GetValidCodeModel {
    @JSONField(name = "send_interval")
    private String sendInterval;
    @JSONField(name = "verification_code")
    private String code;

    public String getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(String sendInterval) {
        this.sendInterval = sendInterval;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
