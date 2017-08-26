package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * CommitFeedBackTask
 * create by 崔婧
 * create at 2017/5/18 下午1:30
 */
public class CommitFeedBackTask extends BaseTask<Object> {
    public CommitFeedBackTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void commit(String content, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("content", content);
        HttpRequest.post(Constant.getHost() + API.FEED_BACK, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected Object parseModel(JSONObject data) {
        return null;
    }
}
