package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.EvaluationListV;
import cn.icarowner.icarowner.activity.viewmodel.EvaluationListVM;
import cn.icarowner.icarowner.databinding.ActivityEvaluateListBinding;
import cn.icarowner.icarowner.event.RefreshEvaluateListEvent;

/**
 * EvaluateListActivity 评价列表
 * create by 崔婧
 * create at 2017/5/18 下午12:05
 */
public class EvaluateListActivity extends BaseActivity implements EvaluationListV {

    private ActivityEvaluateListBinding binding;
    private EvaluationListVM evaluationListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "待评价");
        EventBus.getDefault().register(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_list);
        evaluationListVM = new EvaluationListVM(this);
        binding.setEvaluateList(evaluationListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        this.setViewModel(evaluationListVM);
        setObservers();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "待评价");
        EventBus.getDefault().unregister(this);
    }

    /**
     * Set observers for fields in view model
     */
    private void setObservers() {
        evaluationListVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (evaluationListVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        evaluationListVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(evaluationListVM.toastMsg.get());
                evaluationListVM.toastMsg.set(null);
            }
        });
    }

    /**
     * 在评价页面评价完成后刷新评价列表
     *
     * @param refreshEvaluateListEvent 刷新页面的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshActivityEvent(RefreshEvaluateListEvent refreshEvaluateListEvent) {
        evaluationListVM.onRefresh();
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
     * 跳转到评价页
     *
     * @param orderId
     * @param advisorName
     * @param dealerFullName
     */
    @Override
    public void jumpToEvaluation(String orderId, String advisorName, String dealerFullName) {
        Intent intent = new Intent(this, EvaluateDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("advisorName", advisorName);
        intent.putExtra("dealerFullName", dealerFullName);
        this.startActivity(intent);
    }
}
