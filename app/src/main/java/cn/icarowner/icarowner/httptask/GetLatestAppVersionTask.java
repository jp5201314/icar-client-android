package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.AppVersionEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetLatestAppVersionTask
 * create by 崔婧
 * create at 2017/5/18 下午1:32
 */
public class GetLatestAppVersionTask extends BaseTask<AppVersionEntity> {

    public GetLatestAppVersionTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 获取App最新版本
     */
    public void getLatestVersion(Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("platform", "android");
        params.addFormDataPart("type", "user");
        HttpRequest.get(Constant.getHost() + API.UPDATE_VERSION, params, getIcarHttpRequestCallBack());
    }

    @Override
    public AppVersionEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), AppVersionEntity.class);
    }
}
