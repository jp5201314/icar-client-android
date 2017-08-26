package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.LoginModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpDog;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * LoginTask
 * create by 崔婧
 * create at 2017/5/18 下午1:35
 */
public class LoginTask extends BaseTask<LoginModel> {

    public LoginTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * User login
     *
     * @param mobile   手机号
     * @param password 密码
     */
    public void login(String mobile, String password, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("mobile", mobile);
        params.addFormDataPart("code", password);
        HttpRequest.post(Constant.getHost() + API.LOGIN_WITHOUT_REGISTRATION, params, getIcarHttpRequestCallBack());
    }

    @Override
    public LoginModel parseModel(JSONObject data) {
        LoginModel loginModel = JSON.parseObject(data.toString(), LoginModel.class);
        HttpDog.getInstance().updateCommonHeader("Authorization", "Bearer " + loginModel.getToken());
        return loginModel;
    }
}
