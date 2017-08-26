package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ServiceOrderListV;
import cn.icarowner.icarowner.activity.viewmodel.ServiceOrderListVM;
import cn.icarowner.icarowner.databinding.ActivityServiceOrderListBinding;

/**
 * ServiceOrderListActivity 历史服务单
 * create by 崔婧
 * create at 2017/5/18 下午1:05
 */
public class ServiceOrderListActivity extends BaseActivity implements ServiceOrderListV {


    private ActivityServiceOrderListBinding binding;
    private ServiceOrderListVM serviceOrderListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "服务单");

        serviceOrderListVM = new ServiceOrderListVM(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_order_list);
        binding.setServiceOrderList(serviceOrderListVM);

        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(serviceOrderListVM);
        this.setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        binding.getServiceOrderList().toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(binding.getServiceOrderList().toastMsg.get());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "服务单");
    }

    @Override
    public Context getContext() {
        return ServiceOrderListActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void jumpToServiceOrderDetail(String id) {
        Intent intent = new Intent(this, ServiceOrderDetailActivity.class);
        intent.putExtra("orderId", id);
        startActivity(intent);
    }
}
