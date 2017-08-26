package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetCanChoiceCouponsModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetCanChoiceCouponsTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class GetCanChoiceCouponsTask extends BaseTask<GetCanChoiceCouponsModel> {

    public GetCanChoiceCouponsTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getCoupons(String orderId, int page, int size, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + String.format(API.ORDER_CAN_CHOICE_COUPON_LIST, orderId), params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetCanChoiceCouponsModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetCanChoiceCouponsModel.class);
    }
}
