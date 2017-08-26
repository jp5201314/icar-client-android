package cn.icarowner.icarowner.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ReceiveCouponV;
import cn.icarowner.icarowner.activity.viewmodel.ReceiveCouponVM;
import cn.icarowner.icarowner.databinding.ActivityReceiveCouponBinding;

/**
 * ReceiveCouponActivity 领取优惠券
 * create by 崔婧
 * create at 2017/5/18 下午1:03
 */
public class ReceiveCouponActivity extends BaseActivity implements ReceiveCouponV {

    private ActivityReceiveCouponBinding binding;
    private ReceiveCouponVM receiveCouponVM;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "领取优惠券");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_receive_coupon);
        receiveCouponVM = new ReceiveCouponVM(this, getIntent().getStringExtra("dealerId"), getIntent().getStringExtra("groupId"));
        binding.setReceiveCoupon(receiveCouponVM);
        this.setViewModel(receiveCouponVM);
        setObservers();

        registerReceiver();
    }


    private void setObservers() {
        receiveCouponVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (receiveCouponVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        receiveCouponVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(receiveCouponVM.toastMsg.get());
                receiveCouponVM.toastMsg.set(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "领取优惠券");
        unRegisterReceive();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }

    /**
     * 注册网络状态改变的广播接收者
     */
    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent service) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (null == mobNetInfo) {
                    if (wifiNetInfo.isConnected()) {
                        if (!receiveCouponVM.isLoading.get()) {
                            receiveCouponVM.attemptGetCouponDetail();
                        }
                    }
                } else {
                    if (wifiNetInfo.isConnected() || mobNetInfo.isConnected()) {
                        if (!receiveCouponVM.isLoading.get()) {
                            receiveCouponVM.attemptGetCouponDetail();
                        }
                    }
                }
            }
        };
        registerReceiver(
                receiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    /**
     * 注销已有的广播
     */
    public void unRegisterReceive() {
        unregisterReceiver(receiver);
    }
}
