package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetCouponMoneyModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetCouponMoneyTask
 * create by 崔婧
 * create at 2017/5/18 下午1:32
 */
public class GetCouponMoneyTask extends BaseTask <GetCouponMoneyModel>{

    public GetCouponMoneyTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getCouponMoney(String orderId , Callback callback){
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.EVALUATION_COUPON_MONEY, orderId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }
    @Override
    protected GetCouponMoneyModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(),GetCouponMoneyModel.class);
    }
}
