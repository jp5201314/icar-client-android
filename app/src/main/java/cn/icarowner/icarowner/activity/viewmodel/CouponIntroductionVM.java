package cn.icarowner.icarowner.activity.viewmodel;

import android.view.View;

import cn.icarowner.icarowner.activity.view.CouponIntroductionV;

/**
 * CouponIntroductionVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class CouponIntroductionVM extends BaseVM {

    public ToolBarTitleVM toolBarTitleVM;
    private CouponIntroductionV couponIntroductionV;

    public CouponIntroductionVM(CouponIntroductionV couponIntroductionV) {
        this.couponIntroductionV = couponIntroductionV;
        initToolBar();
    }

    /**
     * 初始化ToolBar
     */
    public void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM("优惠券说明") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                couponIntroductionV.closePage();
            }
        };
    }
}
