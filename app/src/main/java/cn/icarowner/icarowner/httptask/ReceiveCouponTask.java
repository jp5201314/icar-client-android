package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * ReceiveCouponTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class ReceiveCouponTask extends BaseTask {
    public ReceiveCouponTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void receiveGroupCoupon(String key, String groupId, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("key", key);
        HttpRequest.post(Constant.getHost() + String.format(API.RECEIVE_COUPON_GROUP, groupId), params, getIcarHttpRequestCallBack());
    }

    public void receiveDealerCoupon(String key, String dealerId, Callback callBack) {
        this.setCallback(callBack);

        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("key", key);
        HttpRequest.post(Constant.getHost() + String.format(API.RECEIVE_COUPON_DEALER, dealerId), params, getIcarHttpRequestCallBack());
    }

    @Override
    protected Object parseModel(JSONObject data) {
        return null;
    }
}
