package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * CommitEvaluationTask
 * create by 崔婧
 * create at 2017/5/18 下午1:30
 */
public class CommitEvaluationTask extends BaseTask {
    public CommitEvaluationTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void commitEvaluation(String orderId, float rating, String content, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("score", (int) rating);
        params.addFormDataPart("content", content);
        HttpRequest.post(Constant.getHost() + String.format(API.EVALUATION_ORDER, orderId), params, getIcarHttpRequestCallBack());
    }

    @Override
    protected Object parseModel(JSONObject data) {
        return null;
    }
}
