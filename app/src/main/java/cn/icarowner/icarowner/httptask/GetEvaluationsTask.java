package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetEvaluationsModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetEvaluationsTask
 * create by 崔婧
 * create at 2017/5/18 下午1:32
 */
public class GetEvaluationsTask extends BaseTask<GetEvaluationsModel> {

    public GetEvaluationsTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getEvaluations(int page, int size, Callback callback){
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + API.WAIT_EVALUATE_LIST, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetEvaluationsModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetEvaluationsModel.class);
    }
}
