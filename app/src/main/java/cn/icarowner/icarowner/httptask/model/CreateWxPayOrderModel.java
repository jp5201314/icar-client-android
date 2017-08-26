package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * CreateWxPayOrderModel
 * create by 崔婧
 * create at 2017/5/18 下午1:27
 */
public class CreateWxPayOrderModel implements Serializable {


    /**
     * sign : 84FDFF249FE4E7E2E5DD8ACD344168C6
     * package : Sign=WXPay
     * partnerid : 1272000801
     * appid : wx83c6b567ad37b372
     * timestamp : 1494902788
     * noncestr : si4s8l1QTysCTvuw
     * prepayid : wx201705161046289ccc8af3830156649749
     */

    private String sign;
    @JSONField(name = "package")
    private String packageValue;
    @JSONField(name = "partnerid")
    private String partnerId;
    @JSONField(name = "appid")
    private String appId;
    private String timestamp;
    @JSONField(name = "noncestr")
    private String nonceStr;
    @JSONField(name = "prepayid")
    private String prepayId;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }
}
