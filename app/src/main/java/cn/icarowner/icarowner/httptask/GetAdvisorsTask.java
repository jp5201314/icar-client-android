package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetAdvisorsModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetAdvisorsTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class GetAdvisorsTask extends BaseTask<GetAdvisorsModel> {
    public GetAdvisorsTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getAdvisor(int page, int size, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + API.ADVISOR_LIST, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetAdvisorsModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetAdvisorsModel.class);
    }
}
