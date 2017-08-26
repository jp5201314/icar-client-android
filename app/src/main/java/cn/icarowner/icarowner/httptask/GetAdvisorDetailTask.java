package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.AdvisorEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetAdvisorDetailTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class GetAdvisorDetailTask extends BaseTask<AdvisorEntity> {

    public GetAdvisorDetailTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getAdvisorDetailInfo(String advisorId, Callback<AdvisorEntity> callback) {
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.ADVISOR_INFORMATION, advisorId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected AdvisorEntity parseModel(JSONObject data) {

        return JSON.parseObject(data.toString(), AdvisorEntity.class);
    }
}
