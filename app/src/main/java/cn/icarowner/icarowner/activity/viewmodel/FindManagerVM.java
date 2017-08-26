package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.FindManagerV;
import cn.icarowner.icarowner.databinding.ActivityFindManagerBinding;
import cn.icarowner.icarowner.entity.OvertimeCompensateEntity;
import cn.icarowner.icarowner.httptask.ServiceOrderFeedbackTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.utils.DateUtil;

/**
 * FindManagerVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class FindManagerVM extends BaseVM {

    public ToolBarTitleVM toolBarTitleVM;
    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> estimateFinishTime = new ObservableField<>();
    public final ObservableField<String> actualFinishTime = new ObservableField<>();
    public final ObservableList<String> problemTips = new ObservableArrayList<>();

    public final ObservableList<String> checkedProblem = new ObservableArrayList<>();

    private ActivityFindManagerBinding binding;
    private FindManagerV findManagerV;

    private String orderId;


    public FindManagerVM(ActivityFindManagerBinding binding, FindManagerV findManagerV, String orderId, String estimateFinishTime, String actualFinishTime, OvertimeCompensateEntity overtimeCompensateEntity) {
        this.binding = binding;
        this.findManagerV = findManagerV;
        this.initToolBar();
        this.orderId = orderId;
        this.estimateFinishTime.set(DateUtil.formatTime(estimateFinishTime, "MM月dd日 HH:mm"));
        this.actualFinishTime.set(DateUtil.formatTime(actualFinishTime, "MM月dd日 HH:mm"));

        this.morphProblemTips(null != overtimeCompensateEntity);
    }

    private void morphProblemTips(boolean joinedOvertimeCompensate) {
        String[] problemTipArr = new String[]{"延时了，而且还未完工！", "对服务人员不满意！", "对维修质量不满意！"};
        for (String str : problemTipArr) {
            this.problemTips.add(str);
        }

        if (!joinedOvertimeCompensate) {
            return;
        }

        this.problemTips.add("对赔付金额有问题！");
    }

    public void onCommitClick(View view) {
        String content = getContent();
        if (TextUtils.isEmpty(content)) {
            toastMsg.set("请选择或输入您遇到的问题");
            return;
        }

        attemptCommitContent(orderId, content);
    }

    /**
     * 获取评价内容
     */
    private String getContent() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < checkedProblem.size(); i++) {
            stringBuilder.append(checkedProblem.get(i));
        }
        return stringBuilder.append(binding.etSupplementaryContent.getText().toString()).toString();
    }

    /**
     * 提交内容
     */
    private void attemptCommitContent(String orderId, String content) {
        ServiceOrderFeedbackTask serviceOrderFeedbackTask = new ServiceOrderFeedbackTask(this);
        serviceOrderFeedbackTask.commit(orderId, content, new Callback() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(Object o) {
                super.onDataSuccess(o);
                toastMsg.set("我们会将您遇到的问题及时反馈给经理");
                findManagerV.closePage();
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


    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM("找经理") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                findManagerV.closePage();
            }
        };
    }
}
