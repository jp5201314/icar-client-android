package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.DealerEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * Created by 11608 on 2017/5/5.
 */

public class GetDealerDetailTask extends BaseTask<DealerEntity> {

    public GetDealerDetailTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getDealerDetail(String dealerId, Callback<DealerEntity> callback){
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.DEALER_DETAIL, dealerId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected DealerEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), DealerEntity.class);
    }
}
