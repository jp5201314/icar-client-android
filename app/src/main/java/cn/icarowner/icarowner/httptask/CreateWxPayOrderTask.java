package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.CreateWxPayOrderModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * CreateWxPayOrderTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class CreateWxPayOrderTask extends BaseTask<CreateWxPayOrderModel> {

    public CreateWxPayOrderTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void createOrder(String orderId, int balance, int amount, Callback callback) {
        this.setCallback(callback);
        RequestParams requestParams = new RequestParams(httpCycleContext);
        requestParams.addFormDataPart("order", orderId);
        requestParams.addFormDataPart("balance", balance);
        requestParams.addFormDataPart("amount", amount);
        HttpRequest.post(Constant.getHost() + API.WXPAY_PREPARE_ORDER, requestParams, getIcarHttpRequestCallBack());
    }

    @Override
    protected CreateWxPayOrderModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), CreateWxPayOrderModel.class);
    }
}
