package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.AboutUsV;
import cn.icarowner.icarowner.activity.viewmodel.AboutUsVM;
import cn.icarowner.icarowner.databinding.ActivityAboutUsBinding;

/**
 * AboutUsActivity 关于我们
 * create by 崔婧
 * create at 2017/5/18 下午12:01
 */
public class AboutUsActivity extends BaseActivity implements AboutUsV {

    private ActivityAboutUsBinding binding;
    private AboutUsVM aboutUsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "关于我们");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        aboutUsVM = new AboutUsVM(this);
        binding.setAboutUs(aboutUsVM);
        this.setViewModel(aboutUsVM);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "关于我们");
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
