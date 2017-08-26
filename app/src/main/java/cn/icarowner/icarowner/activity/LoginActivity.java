package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.LoginV;
import cn.icarowner.icarowner.activity.viewmodel.LoginVM;
import cn.icarowner.icarowner.databinding.ActivityLoginBinding;

/**
 * LoginActivity 登录
 * create by 崔婧
 * create at 2017/5/18 下午1:00
 */
public class LoginActivity extends BaseActivity implements LoginV {

    public ActivityLoginBinding binding;
    private LoginVM loginVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_transparent);
        TCAgent.onPageStart(this, "登录");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginVM = new LoginVM(binding, this);
        binding.setLogin(loginVM);

        this.setViewModel(loginVM);
        this.setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        binding.getLogin().isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (binding.getLogin().isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        binding.getLogin().toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(binding.getLogin().toastMsg.get());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "登录");
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public Context getContext() {
        return LoginActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
