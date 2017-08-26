package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * CreateAliPayOrderTask
 * create by 崔婧
 * create at 2017/5/18 下午1:30
 */
public class CreateAliPayOrderTask extends BaseTask<String> {


    public CreateAliPayOrderTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void createOrder(String orderId, int balance, int amount, Callback callback) {
        this.setCallback(callback);
        RequestParams requestParams = new RequestParams(httpCycleContext);
        requestParams.addFormDataPart("order", orderId);
        requestParams.addFormDataPart("balance", balance);
        requestParams.addFormDataPart("amount", amount);
        HttpRequest.post(Constant.getHost() + API.ALIPAY_PREPARE_ORDER, requestParams, getIcarHttpRequestCallBack());
    }

    @Override
    protected String parseModel(JSONObject data) {
        return data.getString("pay_string");
    }
}
