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
 * GetLatestServiceOrderDetailTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetLatestServiceOrderDetailTask extends BaseTask<ServiceOrderDetailEntity> {

    public GetLatestServiceOrderDetailTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getLatestUnFinishedOrder(Callback<ServiceOrderDetailEntity> callback) {
        this.setCallback(callback);
        RequestParams requestParams = new RequestParams(httpCycleContext);
        requestParams.addFormDataPart("type", "unfinished");
        HttpRequest.get(Constant.getHost() + API.LATEST_ORDER_DETAIL, requestParams, getIcarHttpRequestCallBack());
    }

    @Override
    protected ServiceOrderDetailEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), ServiceOrderDetailEntity.class);
    }
}
