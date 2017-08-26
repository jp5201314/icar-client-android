package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * ServiceOrderFeedbackTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class ServiceOrderFeedbackTask extends BaseTask<Object> {
    public ServiceOrderFeedbackTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void commit(String orderId, String content, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("content", content);
        HttpRequest.post(Constant.getHost() + String.format(API.SERVICE_ORDER_FEED_BACK, orderId), params, getIcarHttpRequestCallBack());
    }

    @Override
    protected Object parseModel(JSONObject data) {
        return null;
    }
}
