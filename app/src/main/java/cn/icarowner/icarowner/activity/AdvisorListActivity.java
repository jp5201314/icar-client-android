package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.AdvisorListV;
import cn.icarowner.icarowner.activity.viewmodel.AdvisorListVM;
import cn.icarowner.icarowner.databinding.ActivityAdvisorListBinding;

/**
 * AdvisorListActivity 顾问列表
 * create by 崔婧
 * create at 2017/5/18 下午12:02
 */
public class AdvisorListActivity extends BaseActivity implements AdvisorListV {

    private ActivityAdvisorListBinding binding;
    private AdvisorListVM advisorListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "顾问列表");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_advisor_list);
        advisorListVM = new AdvisorListVM(this);
        binding.setAdvisorList(advisorListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(advisorListVM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "顾问列表");
    }

    @Override
    public Context getContext() {
        return AdvisorListActivity.this;
    }

    @Override
    public void closePage() {
        this.finish();
    }


    @Override
    public void jumpToDealerDetailActivity(String id, String name) {
        Intent intent = new Intent(this, DealerDetailActivity.class);
        intent.putExtra("dealerId", id);
        intent.putExtra("dealerName", name);
        startActivity(intent);
    }

    @Override
    public void jumpToAdvisorDetailActivity(String id) {
        Intent intent = new Intent(this, AdvisorDetailActivity.class);
        intent.putExtra("advisorId", id);
        startActivity(intent);
    }

    @Override
    public void callToAdvisor(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }
}
