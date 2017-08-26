package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import cn.icarowner.icarowner.ICarApplication;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.LoginV;
import cn.icarowner.icarowner.databinding.ActivityLoginBinding;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.event.LoginRefreshUserInfoEvent;
import cn.icarowner.icarowner.httptask.GetValidCodeTask;
import cn.icarowner.icarowner.httptask.LoginTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetValidCodeModel;
import cn.icarowner.icarowner.httptask.model.LoginModel;
import cn.icarowner.icarowner.utils.TimeCountUtil;
import cn.xiaomeng.httpdog.HttpDog;

/**
 * LoginVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class LoginVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    private ActivityLoginBinding binding;
    private LoginV loginV;

    public LoginVM(ActivityLoginBinding binding, LoginV loginV) {
        this.binding = binding;
        this.loginV = loginV;

        if (UserSharedPreference.getInstance().hasLogined()) {
            loginV.closePage();
            return;
        }

        String phoneAndPassword = UserSharedPreference.getInstance().getPhoneAndPassword();
        if (!TextUtils.isEmpty(phoneAndPassword) && phoneAndPassword.length() > 11) {
            this.phoneNum.set(phoneAndPassword.substring(0, 11));
        }
    }

    /**
     * OnBackClick Listener
     *
     * @param view
     */
    public void onBackClick(View view) {
        loginV.closePage();
    }

    /**
     * onObtainShortMessageClick Listener
     *
     * @param view
     */
    public void onObtainShortMessageClick(View view) {
        final String mobile = binding.cetPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            toastMsg.set("请填写手机号");
            return;
        }

        GetValidCodeTask getValidCodeTask = new GetValidCodeTask(this);
        getValidCodeTask.getValidCode(mobile, new Callback<GetValidCodeModel>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(GetValidCodeModel getValidCodeModel) {
                super.onDataSuccess(getValidCodeModel);
                String send_interval = getValidCodeModel.getSendInterval();
                //toast(send_interval + "秒后重发");
                String verificationCode = getValidCodeModel.getCode();
                if (null != verificationCode) {
                    //toast("测试验证码" + verificationCode);
                    binding.etValidCode.setText(verificationCode);
                }
                long time = Long.parseLong(send_interval);
                TimeCountUtil timeCountUtil = new
                        TimeCountUtil(loginV.getContext(), time * 1000, 1000, binding.btnObtainMessage);
                timeCountUtil.start();
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


    /**
     * OnLogin Click
     *
     * @param view
     */
    public void onLoginClick(View view) {
        final String mobile = binding.cetPhoneNumber.getText().toString();
        final String password = binding.etValidCode.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            toastMsg.set("请填写手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            toastMsg.set("请填写密码");
            return;
        }

        LoginTask loginTask = new LoginTask(this);
        loginTask.login(mobile, password, new Callback<LoginModel>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(LoginModel loginModel) {
                String token = loginModel.getToken();
                UserSharedPreference.getInstance().setJwtToken(token);
                UserSharedPreference.getInstance().setPhoneAndPassword(mobile, password);
                HttpDog.getInstance().updateCommonHeader("Authorization", "Bearer " + token);
                String userId = loginModel.getId();
                String mobile = loginModel.getMobile();
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userId);
                userEntity.setMobile(mobile);
                UserSharedPreference.getInstance().setUser(userEntity);
                ICarApplication.startMQTTService();
                EventBus.getDefault().post(new LoginRefreshUserInfoEvent());
                binding.btnLogin.requestFocus();
                toastMsg.set("登录成功");
                loginV.closePage();
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