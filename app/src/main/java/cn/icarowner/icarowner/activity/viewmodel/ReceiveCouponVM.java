package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.ReceiveCouponV;
import cn.icarowner.icarowner.entity.CanReceiveCouponEntity;
import cn.icarowner.icarowner.entity.DealerEntity;
import cn.icarowner.icarowner.entity.DonationCouponsEntity;
import cn.icarowner.icarowner.entity.GroupEntity;
import cn.icarowner.icarowner.httptask.GetGroupCouponDetailTask;
import cn.icarowner.icarowner.httptask.GetDealerCouponDetailTask;
import cn.icarowner.icarowner.httptask.ReceiveCouponTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ReceiveCouponVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ReceiveCouponVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> discountedPrice = new ObservableField<>();
    public final ObservableField<String> couponType = new ObservableField<>();
    public final ObservableField<String> companyName = new ObservableField<>();
    public final ObservableField<String> discountType = new ObservableField<>();
    public final ObservableField<Boolean> isCanReceived = new ObservableField<>();

    private String dealerId;
    private String groupId;
    private String key;
    private ReceiveCouponV receiveCouponV;

    public ToolBarTitleVM toolBarTitleVM;

    public ReceiveCouponVM(ReceiveCouponV receiveCouponV, String dealerId, String groupId) {
        this.isLoading.set(false);
        this.receiveCouponV = receiveCouponV;
        this.dealerId = dealerId;
        this.groupId = groupId;
        initToolBar();
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM("领取优惠券") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                receiveCouponV.closePage();
            }
        };
    }

    public void onReceiveCouponClick(View view) {
        attemptReceiveCoupon(key, groupId, dealerId);
    }

    private void morphCanReceiveCoupon(CanReceiveCouponEntity canReceiveCouponEntity) {
        if (canReceiveCouponEntity.getDonationCoupons() != null) {
            DonationCouponsEntity donationCouponsEntity = canReceiveCouponEntity.getDonationCoupons().get(0);
            if (donationCouponsEntity != null) {
                discountedPrice.set(OperationUtils.formatByDecimalPoint(OperationUtils.division(donationCouponsEntity.getDiscount())));
                couponType.set(donationCouponsEntity.getName());
                discountType.set(donationCouponsEntity.getName() + "优惠券" + OperationUtils.formatByDecimalPoint(OperationUtils.division(donationCouponsEntity.getDiscount())) + "元整");
                if (donationCouponsEntity.getCanReceived() == 0) {
                    isCanReceived.set(false);
                } else {
                    isCanReceived.set(true);
                }
                key = donationCouponsEntity.getKey();
            }
        }

        if (canReceiveCouponEntity.getGroup() != null) {
            GroupEntity groupEntity = canReceiveCouponEntity.getGroup();
            if (groupEntity != null) {
                companyName.set(groupEntity.getName());
            }
        }

        if (canReceiveCouponEntity.getDealer() != null) {
            DealerEntity dealerEntity = canReceiveCouponEntity.getDealer();
            if (dealerEntity != null) {
                companyName.set(dealerEntity.getFullName());
            }
        }
    }

    public void attemptGetCouponDetail() {
        if (!TextUtils.isEmpty(groupId)) {
            attemptGetGroupCouponDetail(groupId);
            return;
        }
        if (!TextUtils.isEmpty(dealerId)) {
            attemptGetDealerCouponDetail(dealerId);
        }
    }

    /**
     * 渲染4s店优惠券详情
     */
    public void attemptGetDealerCouponDetail(String dealerId) {
        GetDealerCouponDetailTask getDealerCouponDetailTask = new GetDealerCouponDetailTask(this);
        getDealerCouponDetailTask.receiveGroupCoupon(dealerId, new Callback<CanReceiveCouponEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(CanReceiveCouponEntity canReceiveCouponEntity) {
                super.onDataSuccess(canReceiveCouponEntity);
                morphCanReceiveCoupon(canReceiveCouponEntity);
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
            }
        });
    }

    /**
     * 渲染集团优惠券详情
     */
    public void attemptGetGroupCouponDetail(String groupId) {
        GetGroupCouponDetailTask getGroupCouponDetailTask = new GetGroupCouponDetailTask(this);
        getGroupCouponDetailTask.receiveGroupCoupon(groupId, new Callback<CanReceiveCouponEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(CanReceiveCouponEntity canReceiveCouponEntity) {
                super.onDataSuccess(canReceiveCouponEntity);
                morphCanReceiveCoupon(canReceiveCouponEntity);
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

    /**
     * 领取优惠劵
     */
    public void attemptReceiveCoupon(String key, String groupId, String dealerId) {
        ReceiveCouponTask receiveCouponTask = new ReceiveCouponTask(this);
        if (!TextUtils.isEmpty(groupId)) {
            receiveCouponTask.receiveGroupCoupon(key, groupId, callback);
            return;
        }

        if (!TextUtils.isEmpty(dealerId)) {
            receiveCouponTask.receiveDealerCoupon(key, dealerId, callback);
        }
    }

    private Callback callback = new Callback() {
        @Override
        public void onStart() {
            super.onStart();
            isLoading.set(true);
        }

        @Override
        public void onDataSuccess(Object o) {
            super.onDataSuccess(o);
            isCanReceived.set(false);
            toastMsg.set("领取成功");
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
    };
}
