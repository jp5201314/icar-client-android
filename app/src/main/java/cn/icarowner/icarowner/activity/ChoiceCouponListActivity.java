package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ChoiceCouponListV;
import cn.icarowner.icarowner.activity.viewmodel.ChoiceCouponListVM;
import cn.icarowner.icarowner.databinding.ActivityChoiceCouponListBinding;

/**
 * ChoiceCouponListActivity 选择优惠券列表
 * create by 崔婧
 * create at 2017/5/18 下午12:03
 */
public class ChoiceCouponListActivity extends BaseActivity implements ChoiceCouponListV {

    private ActivityChoiceCouponListBinding binding;
    private ChoiceCouponListVM choiceCouponListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(this, "选择优惠券");
        this.setStatusBarColor(R.color.color_black_0e1214);
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        String choosedCouponId = null;
        if (intent.hasExtra("couponId")) {
            choosedCouponId = intent.getStringExtra("couponId");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choice_coupon_list);
        choiceCouponListVM = new ChoiceCouponListVM(this, orderId, choosedCouponId);
        binding.setChoiceCouponList(choiceCouponListVM);

        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(choiceCouponListVM);
        this.setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        choiceCouponListVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(choiceCouponListVM.toastMsg.get());
                choiceCouponListVM.toastMsg.set(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "选择优惠券");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void jumpAfterCompletedChoose(String couponId, String couponName, int amount, int discount, int payAmount, boolean isCloseChoiceActivity) {
        Intent intent = new Intent();
        intent.putExtra("couponId", couponId);
        intent.putExtra("couponName", couponName);
        intent.putExtra("amount", amount);
        intent.putExtra("discount", discount);
        intent.putExtra("payAmount", payAmount);
        setResult(RESULT_OK, intent);
        if (isCloseChoiceActivity) {
            finish();
        }
    }
}
