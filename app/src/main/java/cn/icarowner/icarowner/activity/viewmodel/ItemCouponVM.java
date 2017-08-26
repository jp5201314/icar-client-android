package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import cn.icarowner.icarowner.entity.CouponEntity;
import cn.icarowner.icarowner.utils.DateUtil;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ItemCouponVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemCouponVM extends BaseObservable {

    public final ObservableField<String> money = new ObservableField<>();
    public final ObservableField<String> couponType = new ObservableField<>();
    public final ObservableField<String> startTime = new ObservableField<>();
    public final ObservableField<String> endTime = new ObservableField<>();
    public final ObservableField<String> companyName = new ObservableField<>();

    public ItemCouponVM(CouponEntity couponEntity) {
        this.money.set(OperationUtils.formatByDecimalPoint(OperationUtils.division(couponEntity.getDiscount())));
        this.couponType.set(couponEntity.getName());
        this.startTime.set(DateUtil.formatTime(couponEntity.getEffectiveAt(), "yyyy.MM.dd"));
        this.endTime.set(DateUtil.formatTime(couponEntity.getExpiredAt(), "yyyy.MM.dd"));
        this.companyName.set(null == couponEntity.getDealer() ? "多店通用" : couponEntity.getDealer().getFullName());
    }
}
