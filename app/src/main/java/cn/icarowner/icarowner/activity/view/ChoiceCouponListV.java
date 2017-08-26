package cn.icarowner.icarowner.activity.view;

/**
 * ChoiceCouponListV
 * create by 崔婧
 * create at 2017/5/18 上午11:22
 */
public interface ChoiceCouponListV extends BaseV {
    void jumpAfterCompletedChoose(String couponId, String couponName, int amount, int discount, int payAmount, boolean isCloseChoiceActivity);
}
