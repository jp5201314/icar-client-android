package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ServiceOrderDetailV;
import cn.icarowner.icarowner.activity.viewmodel.ServiceOrderDetailVM;
import cn.icarowner.icarowner.databinding.ActivityServiceOrderDetailBinding;

/**
 * ServiceOrderDetailActivity 服务单详情
 * create by 崔婧
 * create at 2017/5/18 下午1:05
 */
public class ServiceOrderDetailActivity extends BaseActivity implements ServiceOrderDetailV {

    private ActivityServiceOrderDetailBinding binding;
    private ServiceOrderDetailVM serviceOrderDetailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "服务单详情");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_order_detail);
        serviceOrderDetailVM = new ServiceOrderDetailVM(this, getIntent().getStringExtra("orderId"));
        binding.setServiceDetail(serviceOrderDetailVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(serviceOrderDetailVM);
        setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        serviceOrderDetailVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(serviceOrderDetailVM.toastMsg.get());
                serviceOrderDetailVM.toastMsg.set(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "服务单详情");
    }

    @Override
    public Context getContext() {
        return ServiceOrderDetailActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void jumpToBigImageScan(ArrayList<String> list, int position) {
        Intent intent = new Intent(this, PhotoGalleryActivity.class);
        intent.putExtra("LIST", list);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }
}
