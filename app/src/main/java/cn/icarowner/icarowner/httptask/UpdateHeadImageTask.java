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
 * UpdateHeadImageTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class UpdateHeadImageTask extends BaseTask<UserEntity> {

    public UpdateHeadImageTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void updateHead(String imageUrl, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("avatar", imageUrl);
        HttpRequest.post(Constant.getHost() + API.PERSONAL_INFORMATION, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected UserEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), UserEntity.class);
    }
}
