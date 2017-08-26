package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.ChoiceCouponEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * UseCouponTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class UseCouponTask extends BaseTask<ChoiceCouponEntity> {

    public UseCouponTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 使用优惠券
     *
     * @param couponId 优惠券id
     */
    public void use(String orderId, String couponId, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("coupon", couponId);
        HttpRequest.post(Constant.getHost() + String.format(API.CHOICE_COUPON, orderId), params, getIcarHttpRequestCallBack());
    }

    @Override
    public ChoiceCouponEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), ChoiceCouponEntity.class);
    }
}
