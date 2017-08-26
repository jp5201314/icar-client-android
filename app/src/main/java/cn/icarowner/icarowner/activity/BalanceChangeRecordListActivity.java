package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.BalanceChangeRecordListV;
import cn.icarowner.icarowner.activity.viewmodel.BalanceChangeRecordListVM;
import cn.icarowner.icarowner.databinding.ActivityBalanceChangeRecordBinding;

/**
 * BalanceChangeRecordListActivity 余额变动记录
 * create by 崔婧
 * create at 2017/5/18 下午12:02
 */
public class BalanceChangeRecordListActivity extends BaseActivity implements BalanceChangeRecordListV {


    private ActivityBalanceChangeRecordBinding binding;
    private BalanceChangeRecordListVM balanceChangeRecordListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balance_change_record);
        balanceChangeRecordListVM = new BalanceChangeRecordListVM(this);
        binding.setBalanceChangeRecordList(balanceChangeRecordListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(balanceChangeRecordListVM);
        this.setObservers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        binding.getBalanceChangeRecordList().toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(binding.getBalanceChangeRecordList().toastMsg.get());
            }
        });
    }


    @Override
    public Context getContext() {
        return BalanceChangeRecordListActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
