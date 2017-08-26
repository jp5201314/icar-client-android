package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetServiceOrdersModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetServiceOrdersTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetServiceOrdersTask extends BaseTask<GetServiceOrdersModel> {

    public GetServiceOrdersTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getServiceOrders(int page, int size, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + API.ALREADY_FINISH_ORDER_LIST, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetServiceOrdersModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetServiceOrdersModel.class);
    }
}
