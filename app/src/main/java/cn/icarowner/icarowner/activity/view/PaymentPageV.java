package cn.icarowner.icarowner.activity.view;

/**
 * PaymentPageV
 * create by 崔婧
 * create at 2017/5/18 上午11:39
 */
public interface PaymentPageV extends BaseV {
    void toAliPayTask(String paymentData);

    void toWXPay(String appId, String prepayId,
                 String partnerId, String packageValue,
                 String nonceStr, String timeStamp, String sign);

    void toXinyePay(String tokenId);

    void displayAliPay();
}
