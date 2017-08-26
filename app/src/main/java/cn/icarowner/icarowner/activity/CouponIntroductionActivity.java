package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.CouponIntroductionV;
import cn.icarowner.icarowner.activity.viewmodel.CouponIntroductionVM;
import cn.icarowner.icarowner.databinding.ActivityCouponIntroductionBinding;

/**
 * CouponIntroductionActivity 评价领取优惠券
 * create by 崔婧
 * create at 2017/5/18 下午12:04
 */
public class CouponIntroductionActivity extends BaseActivity implements CouponIntroductionV {

    private ActivityCouponIntroductionBinding binding;
    private CouponIntroductionVM couponIntroductionVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon_introduction);
        couponIntroductionVM = new CouponIntroductionVM(this);
        binding.setCouponIntroduction(couponIntroductionVM);
        this.setViewModel(couponIntroductionVM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
