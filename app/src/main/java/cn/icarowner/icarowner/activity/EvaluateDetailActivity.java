package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.EvaluateDetailV;
import cn.icarowner.icarowner.activity.viewmodel.EvaluateDetailVM;
import cn.icarowner.icarowner.databinding.ActivityEvaluateDetailBinding;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.dialog.SureTipDialog;
import cn.icarowner.icarowner.event.RefreshEvaluateListEvent;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * EvaluateDetailActivity 评价
 * create by 崔婧
 * create at 2017/5/18 下午12:04
 */
public class EvaluateDetailActivity extends BaseActivity implements EvaluateDetailV {

    private ActivityEvaluateDetailBinding binding;
    private EvaluateDetailVM evaluateDetailVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "评价");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_detail);
        evaluateDetailVM = new EvaluateDetailVM(this, binding, getIntent().getStringExtra("orderId"), getIntent().getStringExtra("advisorName"), getIntent().getStringExtra("dealerFullName"));
        binding.setEvaluateDetail(evaluateDetailVM);
        this.setViewModel(evaluateDetailVM);
        setObservers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "评价");
    }

    /**
     * Set observers for fields in view model
     */
    private void setObservers() {
        evaluateDetailVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (evaluateDetailVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        evaluateDetailVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(evaluateDetailVM.toastMsg.get());
                evaluateDetailVM.toastMsg.set(null);
            }
        });
    }

    /**
     * 设置优惠券金额
     */
    public void setCouponMoney(double amount) {
        if (amount <= 0) {
            binding.llCouponMoney.setVisibility(View.GONE);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, (int) (54 * getResources().getDisplayMetrics().density),
                    0, (int) (20 * getResources().getDisplayMetrics().density));
            params.gravity = Gravity.CENTER;
            binding.llCouponMoney.setLayoutParams(params);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) (31 * getResources().getDisplayMetrics().density),
                    0, (int) (76 * getResources().getDisplayMetrics().density));
            layoutParams.gravity = Gravity.CENTER;
            binding.tvSatisfactionLevel.setLayoutParams(layoutParams);
        } else {
            binding.llCouponMoney.setVisibility(View.VISIBLE);
            double division = OperationUtils.division(amount);
            binding.tvMoneyB.setText(new StringBuilder(NumberFormat.getInstance().format(division)).append("元"));
            binding.tvCouponB.setText("保养");
        }
    }


    /**
     * 提示对话框
     *
     * @param tip 提示语
     * @return SureTipDialog
     */
    private SureTipDialog getSureTipDialog(String tip) {
        final SureTipDialog sureTipDialog = DialogCreater.createUpdateVersionDialog(EvaluateDetailActivity.this,
                R.drawable.icon_evaluation,
                tip, "确定", null,
                R.color.color_gray_a5a5a6, R.color.color_gray_a5a5a6,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        EvaluateDetailActivity.this.finish();
                    }
                }, null).create();
        sureTipDialog.findViewById(R.id.btn_negative_button).setVisibility(View.GONE);
        sureTipDialog.findViewById(R.id.line).setVisibility(View.GONE);
        sureTipDialog.setCanceledOnTouchOutside(false);
        sureTipDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (sureTipDialog.isShowing()) {
                        sureTipDialog.dismiss();
                    }
                    EvaluateDetailActivity.this.finish();
                }
                return false;
            }
        });
        return sureTipDialog;
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
     * 显示提示对话框
     */
    @Override
    public void showTipDialog(double amount) {
        SureTipDialog sureTipDialog;
        if (amount <= 0) {
            sureTipDialog = getSureTipDialog("感谢您的评价，期待下一次为您服务。");
        } else {
            sureTipDialog = getSureTipDialog("感谢您的评价，优惠券已到账！");
        }
        sureTipDialog.show();
        EventBus.getDefault().post(new RefreshEvaluateListEvent());
    }
}
