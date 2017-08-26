package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.FeedBackV;
import cn.icarowner.icarowner.databinding.ActivityFeedbackBinding;
import cn.icarowner.icarowner.httptask.CommitFeedBackTask;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * FeedBackVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class FeedBackVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();

    private ActivityFeedbackBinding binding;
    private FeedBackV feedBackV;

    public FeedBackVM(ActivityFeedbackBinding binding, FeedBackV feedBackV) {
        this.binding = binding;
        this.feedBackV = feedBackV;
        this.title.set("意见反馈");
    }

    public void onCommitClick(View view) {
        String content = binding.etFeedbackContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            toastMsg.set("请输入内容后提交");
            return;
        }
        attemptCommitFeedBack(content);
    }

    private void attemptCommitFeedBack(String content) {
        CommitFeedBackTask feedBackTask = new CommitFeedBackTask(this);
        feedBackTask.commit(content, new Callback() {

            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(Object o) {
                super.onDataSuccess(o);
                toastMsg.set("感谢你的反馈，我们将及时改进");
                feedBackV.closePage();
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                //toastMsg.set(msg);
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });

    }

    public void onBackClick(View view) {
        feedBackV.closePage();
    }
}
