package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.AdvisorDetailV;
import cn.icarowner.icarowner.activity.viewmodel.AdvisorDetailVM;
import cn.icarowner.icarowner.databinding.ActivityAdvisorDetailBinding;

/**
 * AdvisorDetailActivity 服务顾问
 * create by 崔婧
 * create at 2017/5/18 下午12:01
 */
public class AdvisorDetailActivity extends BaseActivity implements AdvisorDetailV {

    private AdvisorDetailVM advisorDetailVM;
    private ActivityAdvisorDetailBinding advisorDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "顾问");

        advisorDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_advisor_detail);
        advisorDetailVM = new AdvisorDetailVM(this, getIntent().getStringExtra("advisorId"));
        advisorDetailBinding.setAdvisorDetail(advisorDetailVM);

        this.setViewModel(advisorDetailVM);
        setObservers();
    }

    @Override
    protected void onDestroy() {
        TCAgent.onPageEnd(this, "顾问");
        super.onDestroy();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        advisorDetailVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (advisorDetailVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        advisorDetailVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(advisorDetailVM.toastMsg.get());
                advisorDetailVM.toastMsg.set(null);
            }
        });
    }

    @Override
    public void callToAdvisor(String phoneNum) {
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
}
