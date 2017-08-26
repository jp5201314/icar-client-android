package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.CouponListV;
import cn.icarowner.icarowner.activity.viewmodel.CouponListVM;
import cn.icarowner.icarowner.databinding.ActivityCouponListBinding;

/**
 * CouponListActivity 优惠券列表
 * create by 崔婧
 * create at 2017/5/18 下午12:04
 */
public class CouponListActivity extends BaseActivity implements CouponListV {


    private ActivityCouponListBinding binding;
    private CouponListVM couponListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(this, "优惠券");
        this.setStatusBarColor(R.color.color_black_0e1214);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon_list);
        couponListVM = new CouponListVM(this);
        binding.setCouponList(couponListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(couponListVM);
        this.setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        binding.getCouponList().toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(binding.getCouponList().toastMsg.get());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "优惠券");
    }

    @Override
    public Context getContext() {
        return CouponListActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
