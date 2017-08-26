package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.CancelCouponModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * CancelUseCouponTask
 * create by 崔婧
 * create at 2017/5/18 下午1:30
 */
public class CancelUseCouponTask extends BaseTask<CancelCouponModel> {

    public CancelUseCouponTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 取消使用优惠券
     *
     * @param couponId 优惠券id
     */
    public void cancel(String orderId, String couponId, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("coupon", couponId);
        HttpRequest.post(Constant.getHost() + String.format(API.CANCEL_CHOICE_COUPON, orderId), params, getIcarHttpRequestCallBack());
    }

    @Override
    public CancelCouponModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), CancelCouponModel.class);
    }
}
