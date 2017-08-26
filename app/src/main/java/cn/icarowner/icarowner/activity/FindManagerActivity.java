package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;

import java.util.List;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.FindManagerV;
import cn.icarowner.icarowner.activity.viewmodel.FindManagerVM;
import cn.icarowner.icarowner.databinding.ActivityFindManagerBinding;
import cn.icarowner.icarowner.entity.OvertimeCompensateEntity;

/**
 * FindManagerActivity 找经理
 * create by 崔婧
 * create at 2017/5/18 下午12:14
 */
public class FindManagerActivity extends BaseActivity implements FindManagerV {

    private ActivityFindManagerBinding binding;
    private FindManagerVM findManagerVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_manager);
        findManagerVM = new FindManagerVM(binding, this,
                getIntent().getStringExtra("orderId"),
                getIntent().getStringExtra("estimatedTimeToPickUp"),
                getIntent().getStringExtra("actualTimeToPickUp"),
                (OvertimeCompensateEntity) getIntent().getSerializableExtra("overtimeCompensate")
        );
        binding.setFindManager(findManagerVM);
        this.setViewModel(findManagerVM);

        this.setObservers();

        this.renderList(findManagerVM.problemTips);
    }

    private void setObservers() {
        findManagerVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (findManagerVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });
        findManagerVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(findManagerVM.toastMsg.get());
                findManagerVM.toastMsg.set(null);
            }
        });
    }

    private void renderList(List<String> tipProblems) {
        CheckedTextView checkedTextView;
        for (String aTipProblem : tipProblems) {
            checkedTextView = (CheckedTextView) LayoutInflater.from(this).inflate(R.layout.item_problem_tip_list, binding.llProblemTipList, false);
            checkedTextView.setText(aTipProblem);
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckedTextView view = (CheckedTextView) v;
                    view.setChecked(!view.isChecked());
                    if (view.isChecked()) {
                        if (!findManagerVM.checkedProblem.contains(view.getText().toString())) {
                            findManagerVM.checkedProblem.add(view.getText().toString());
                        }
                    } else {
                        if (findManagerVM.checkedProblem.contains(view.getText().toString())) {
                            findManagerVM.checkedProblem.remove(view.getText().toString());
                        }
                    }
                }
            });
            binding.llProblemTipList.addView(checkedTextView);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
