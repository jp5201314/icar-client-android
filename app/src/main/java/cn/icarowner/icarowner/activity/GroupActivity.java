package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.GroupV;
import cn.icarowner.icarowner.activity.viewmodel.GroupVM;
import cn.icarowner.icarowner.databinding.ActivityGroupBinding;

/**
 * GroupActivity 集团简介
 * create by 崔婧
 * create at 2017/5/18 下午1:00
 */
public class
GroupActivity extends BaseActivity implements GroupV {

    private ActivityGroupBinding binding;
    private GroupVM groupVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group);
        groupVM = new GroupVM(this, getIntent().getStringExtra("groupId"), getIntent().getStringExtra("groupName"));
        binding.setDealerList(groupVM);
        this.setViewModel(groupVM);
        setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    private void setObservers() {
        groupVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (groupVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        groupVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(groupVM.toastMsg.get());
                groupVM.toastMsg.set(null);
            }
        });
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
     * 跳转到经销商详情
     *
     * @param dealerId
     * @param dealerName
     */
    @Override
    public void jumpToDealerDetail(String dealerId, String dealerName) {
        Intent intent = new Intent(this, DealerDetailActivity.class);
        intent.putExtra("dealerId", dealerId);
        intent.putExtra("dealerName", dealerName);
        startActivity(intent);
    }
}
