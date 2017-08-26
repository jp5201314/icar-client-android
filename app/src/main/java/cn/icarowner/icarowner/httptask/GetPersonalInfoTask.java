package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetPersonalInfoTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetPersonalInfoTask extends BaseTask<UserEntity> {

    public GetPersonalInfoTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getPersonalInfo(Callback callback) {
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + API.PERSONAL_INFORMATION, new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected UserEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), UserEntity.class);
    }
}
