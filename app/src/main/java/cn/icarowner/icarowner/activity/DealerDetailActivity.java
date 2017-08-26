package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.DealerDetailV;
import cn.icarowner.icarowner.activity.viewmodel.DealerDetailVM;
import cn.icarowner.icarowner.databinding.ActivityDealerDetailBinding;

/**
 * DealerDetailActivity 4S店详情
 * create by 崔婧
 * create at 2017/5/18 下午1:06
 */
public class DealerDetailActivity extends BaseActivity implements DealerDetailV {

    private ActivityDealerDetailBinding binding;
    private DealerDetailVM dealerDetailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "4S店");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dealer_detail);
        dealerDetailVM = new DealerDetailVM(this, getIntent().getStringExtra("dealerName"), getIntent().getStringExtra("dealerId"));
        binding.setDealerDetail(dealerDetailVM);

        this.setViewModel(dealerDetailVM);
        setObservers();

        this.initWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "4S店");
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        dealerDetailVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (dealerDetailVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        dealerDetailVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(dealerDetailVM.toastMsg.get());
                dealerDetailVM.toastMsg.set(null);
            }
        });
    }

    @Override
    public void callToDealer(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }

    private void initWebView() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setSupportZoom(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
}
