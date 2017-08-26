package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetUserInfoTask
 * create by 崔婧
 * create at 2017/5/18 下午1:35
 */
public class GetUserInfoTask extends BaseTask<UserEntity> {

    public GetUserInfoTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    /**
     * 获取个人资料
     */
    public void getInfo(Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        HttpRequest.get(Constant.getHost() + API.PERSONAL_INFORMATION, params, getIcarHttpRequestCallBack());
    }

    @Override
    public UserEntity parseModel(JSONObject data) {
        UserEntity userEntity = JSON.parseObject(data.toString(), UserEntity.class);
        UserSharedPreference.getInstance().setUser(userEntity);
        return userEntity;
    }
}
