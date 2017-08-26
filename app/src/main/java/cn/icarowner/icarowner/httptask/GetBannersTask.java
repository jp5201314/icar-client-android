package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.BannerEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetBannersTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class GetBannersTask extends BaseTask<BannerEntity> {

    public GetBannersTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 获取Banners
     */
    public void getBanners(Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        HttpRequest.get(Constant.getHost() + API.BANNER, params, getIcarHttpRequestCallBack());
    }

    @Override
    public BannerEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), BannerEntity.class);
    }
}
