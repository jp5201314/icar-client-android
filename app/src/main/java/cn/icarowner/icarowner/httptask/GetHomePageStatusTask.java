package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.MainActivityStatusEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetHomePageStatusTask
 * create by 崔婧
 * create at 2017/5/18 下午1:32
 */
public class GetHomePageStatusTask extends BaseTask<MainActivityStatusEntity> {

    public GetHomePageStatusTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 获取首页状态
     */
    public void getStatus(Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        HttpRequest.get(Constant.getHost() + API.MAIN_ACTIVITY_DIAPLAY, params, getIcarHttpRequestCallBack());
    }

    @Override
    public MainActivityStatusEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), MainActivityStatusEntity.class);
    }
}
