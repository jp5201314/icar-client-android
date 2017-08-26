package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetDealersModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetDealersTask
 * create by 崔婧
 * create at 2017/5/18 下午1:32
 */
public class GetDealersTask extends BaseTask<GetDealersModel> {

    public GetDealersTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getDealers(String groupId, int page, int size, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + String.format(API.DEALER_LIST, groupId), params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetDealersModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetDealersModel.class);
    }
}
