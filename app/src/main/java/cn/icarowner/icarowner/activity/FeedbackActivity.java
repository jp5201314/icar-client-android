package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.FeedBackV;
import cn.icarowner.icarowner.activity.viewmodel.FeedBackVM;
import cn.icarowner.icarowner.databinding.ActivityFeedbackBinding;

/**
 * FeedbackActivity 意见反馈
 * create by 崔婧
 * create at 2017/5/18 下午12:10
 */
public class FeedbackActivity extends BaseActivity implements FeedBackV {
    //声明Binding和VM变量
    ActivityFeedbackBinding feedbackBinding;
    private FeedBackVM feedBackVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "意见反馈");

        feedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        feedBackVM = new FeedBackVM(feedbackBinding, this);
        feedbackBinding.setFeedback(feedBackVM);
        this.setViewModel(feedBackVM);
        this.setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        feedBackVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (feedBackVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        feedBackVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(feedBackVM.toastMsg.get());
                feedBackVM.toastMsg.set(null);
            }
        });
    }

    @Override
    public Context getContext() {
        return FeedbackActivity.this;
    }

    @Override
    public void closePage() {
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "意见反馈");
    }

}
