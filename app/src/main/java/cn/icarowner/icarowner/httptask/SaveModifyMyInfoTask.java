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
 * SaveModifyMyInfoTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class SaveModifyMyInfoTask extends BaseTask<UserEntity> {
    public static final String MODIFY_NAME = "2";
    public static final String MODIFY_GENDER = "3";
    public static final String MODIFY_BIRTHDAY = "4";

    public SaveModifyMyInfoTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void updateUserInfo(String modifyType, String name, int gender, String birth, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams();
        switch (modifyType) {
            case MODIFY_NAME:
                params.addFormDataPart("name", name);
                break;
            case MODIFY_GENDER:
                params.addFormDataPart("gender", gender);
                break;
            case MODIFY_BIRTHDAY:
                params.addFormDataPart("birth", birth);
                break;
        }
        HttpRequest.post(Constant.getHost() + API.PERSONAL_INFORMATION, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected UserEntity parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), UserEntity.class);
    }
}
