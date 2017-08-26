package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetValidCodeModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetValidCodeTask
 * create by 崔婧
 * create at 2017/5/18 下午1:35
 */
public class GetValidCodeTask extends BaseTask<GetValidCodeModel> {

    public GetValidCodeTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getValidCode(String mobile, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("mobile", mobile);
        HttpRequest.post(Constant.getHost() + API.LOGIN_MSG_VALID_CODE, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetValidCodeModel parseModel(JSONObject data) {
        GetValidCodeModel getValidCodeModel = JSON.parseObject(data.toString(), GetValidCodeModel.class);
        return getValidCodeModel;
    }
}
