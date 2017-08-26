package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.RatingBar;

import cn.icarowner.icarowner.activity.view.EvaluateDetailV;
import cn.icarowner.icarowner.databinding.ActivityEvaluateDetailBinding;
import cn.icarowner.icarowner.httptask.CommitEvaluationTask;
import cn.icarowner.icarowner.httptask.GetCouponMoneyTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetCouponMoneyModel;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * EvaluateDetailVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class EvaluateDetailVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> companyName = new ObservableField<>();
    public final ObservableField<String> advisorName = new ObservableField<>();
    public final ObservableField<String> amount = new ObservableField<>();
    public final ObservableField<String> couponType = new ObservableField<>();
    public final ObservableField<Float> rating = new ObservableField<>();
    public final ObservableField<String> satisfactionLevel = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();


    private String orderId;

    private EvaluateDetailV evaluateDetailV;
    private ActivityEvaluateDetailBinding binding;

    public ToolBarTitleVM toolBarTitleVM;
    public RatingBar.OnRatingBarChangeListener onRatingBarChangeListener;

    public EvaluateDetailVM(EvaluateDetailV detailV, ActivityEvaluateDetailBinding binding, String orderId, String advisor, String dealerFullName) {
        this.evaluateDetailV = detailV;
        this.binding = binding;
        this.orderId = orderId;
        rating.set(0.0f);
        satisfactionLevel.set("请为本次服务评分");
        companyName.set(dealerFullName);
        advisorName.set(advisor);
        initToolBar();
        attemptGetCouponMoney();
        setOnRatingBarChangeListener();
    }


    @BindingAdapter("onRatingBarChangeListener")
    public static void bindRatingBarChangeListener(RatingBar ratingBar, RatingBar.OnRatingBarChangeListener listener) {
        ratingBar.setOnRatingBarChangeListener(listener);
    }


    public void setOnRatingBarChangeListener() {
        onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if ((int) v == 1) {
                    satisfactionLevel.set("非常不满意，各方面都很差");
                    rating.set(v);
                } else if ((int) v == 2) {
                    satisfactionLevel.set("不满意，比较差");
                    rating.set(v);
                } else if ((int) v == 3) {
                    satisfactionLevel.set("一般，需要改善");
                    rating.set(v);
                } else if ((int) v == 4) {
                    satisfactionLevel.set("比较满意，但仍可改善");
                    rating.set(v);
                } else if ((int) v == 5) {
                    satisfactionLevel.set("非常满意，无可挑剔");
                    rating.set(v);
                } else {
                    toastMsg.set("请您评分后再提交");
                    rating.set(0.0f);
                }
            }
        };
    }

    private void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM("评价") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                evaluateDetailV.closePage();
            }
        };
    }

    public void onCommitClick(View view) {
        attemptCommitEvaluationContent();
    }


    private void attemptGetCouponMoney() {
        GetCouponMoneyTask getCouponMoneyTask = new GetCouponMoneyTask(this);
        getCouponMoneyTask.getCouponMoney(orderId, new Callback<GetCouponMoneyModel>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(GetCouponMoneyModel getCouponMoneyModel) {
                super.onDataSuccess(getCouponMoneyModel);
                amount.set(OperationUtils.formatNum(OperationUtils.division(getCouponMoneyModel.getAmount())));
                evaluateDetailV.setCouponMoney(Double.parseDouble(amount.get()));
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

    public void attemptCommitEvaluationContent() {
        CommitEvaluationTask commitEvaluationTask = new CommitEvaluationTask(this);
        if (rating.get() == 0.0) {
            toastMsg.set("请为本次服务评分");
            return;
        }
        commitEvaluationTask.commitEvaluation(orderId, rating.get(), binding.etEvaluateContent.getText().toString(), new Callback() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(Object o) {
                super.onDataSuccess(o);
                evaluateDetailV.showTipDialog(Double.parseDouble(amount.get()));
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }
}
