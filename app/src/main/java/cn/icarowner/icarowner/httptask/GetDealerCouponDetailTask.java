package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.CanReceiveCouponEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetDealerCouponDetailTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetDealerCouponDetailTask extends BaseTask<CanReceiveCouponEntity> {

    public GetDealerCouponDetailTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }


    public void receiveGroupCoupon(String dealerId, Callback callback) {
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.CAN_RECEIVE_COUPON_LIST_DEALER, dealerId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected CanReceiveCouponEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), CanReceiveCouponEntity.class);
    }
}
