package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetServiceOrderDetailTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetServiceOrderDetailTask extends BaseTask<ServiceOrderDetailEntity> {

    public GetServiceOrderDetailTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getServiceOrderDetail(String orderId, Callback<ServiceOrderDetailEntity> callback) {
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.SERVICE_ORDER_DETAIL, orderId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected ServiceOrderDetailEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), ServiceOrderDetailEntity.class);
    }
}
