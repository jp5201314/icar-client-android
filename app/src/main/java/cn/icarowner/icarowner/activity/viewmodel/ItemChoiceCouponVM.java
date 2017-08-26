package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.ChoiceCouponListV;
import cn.icarowner.icarowner.entity.ChoiceCouponEntity;
import cn.icarowner.icarowner.entity.CouponEntity;
import cn.icarowner.icarowner.httptask.CancelUseCouponTask;
import cn.icarowner.icarowner.httptask.UseCouponTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.CancelCouponModel;
import cn.icarowner.icarowner.utils.DateUtil;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ItemChoiceCouponVM
 * create by 崔婧
 * create at 2017/5/18 上午11:56
 */
public class ItemChoiceCouponVM extends BaseVM {
    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> money = new ObservableField<>();
    public final ObservableField<String> couponType = new ObservableField<>();
    public final ObservableField<String> startTime = new ObservableField<>();
    public final ObservableField<String> endTime = new ObservableField<>();
    public final ObservableField<String> companyName = new ObservableField<>();
    public final ObservableField<Boolean> choosed = new ObservableField<>();

    private ChoiceCouponListVM choiceCouponListVM;
    private ChoiceCouponListV choiceCouponListV;
    private CouponEntity couponEntity;
    private String orderId;

    public ItemChoiceCouponVM(ChoiceCouponListVM choiceCouponListVM, ChoiceCouponListV choiceCouponListV, CouponEntity couponEntity, String orderId) {
        this.choiceCouponListVM = choiceCouponListVM;
        this.choiceCouponListV = choiceCouponListV;
        this.couponEntity = couponEntity;
        this.orderId = orderId;

        this.money.set(OperationUtils.formatByDecimalPoint(OperationUtils.division(couponEntity.getDiscount())));
        this.couponType.set(couponEntity.getName());
        this.startTime.set(DateUtil.formatTime(couponEntity.getEffectiveAt(), "yyyy.MM.dd"));
        this.endTime.set(DateUtil.formatTime(couponEntity.getExpiredAt(), "yyyy.MM.dd"));
        this.companyName.set(null == couponEntity.getDealer() ? "多店通用" : couponEntity.getDealer().getFullName());
        this.choosed.set(false);
    }

    public void notifyChoosedItem(String choosedCouponId) {
        if (TextUtils.isEmpty(choosedCouponId)) {
            choosed.set(false);
            return;
        }
        choosed.set(couponEntity.getId().equals(choosedCouponId));
    }

    public void onItemClick(View view) {
        if (choosed.get()) {
            // cancel
            cancelUseCoupon(orderId, couponEntity.getId());
            return;
        }

        if (TextUtils.isEmpty(choiceCouponListVM.getChoosedCouponId())) {
            // choose
            useCoupon(orderId, couponEntity.getId());
            return;
        }

        // cancel and choose
        useCouponAfterCancelCoupon(orderId, choiceCouponListVM.getChoosedCouponId(), couponEntity.getId());
    }

    private void cancelUseCoupon(String orderId, final String cancelCouponId) {
        cancelUseCoupon(orderId, cancelCouponId, null);
    }

    private void useCouponAfterCancelCoupon(String orderId, String cancelCouponId, String couponId) {
        cancelUseCoupon(orderId, cancelCouponId, couponId);
    }

    private void cancelUseCoupon(final String orderId, final String cancelCouponId, final String chooseCouponId) {
        CancelUseCouponTask cancelUseCouponTask = new CancelUseCouponTask(this);
        cancelUseCouponTask.cancel(orderId, cancelCouponId, new Callback<CancelCouponModel>() {
            boolean isSuccess = false;

            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(CancelCouponModel o) {
                super.onDataSuccess(o);
                choosed.set(false);
                choiceCouponListVM.chooseCoupon(null);
                choiceCouponListV.jumpAfterCompletedChoose(
                        "",
                        "",
                        o.getAmount(),
                        o.getDiscount(),
                        o.getPayAmount(),
                        false
                );
                isSuccess = true;
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                //toastMsg.set(msg);
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
                if (isSuccess && null != chooseCouponId) {
                    useCoupon(orderId, chooseCouponId);
                }
            }
        });
    }

    private void useCoupon(String orderId, final String couponId) {
        UseCouponTask useCouponTask = new UseCouponTask(this);
        useCouponTask.use(orderId, couponId, new Callback<ChoiceCouponEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(ChoiceCouponEntity o) {
                super.onDataSuccess(o);
                choosed.set(true);
                choiceCouponListVM.chooseCoupon(couponId);
                choiceCouponListV.jumpAfterCompletedChoose(
                        o.getBoundCoupon().getId(),
                        o.getBoundCoupon().getName(),
                        o.getAmount(),
                        o.getDiscount(),
                        o.getPayAmount(),
                        true
                );
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }
}
