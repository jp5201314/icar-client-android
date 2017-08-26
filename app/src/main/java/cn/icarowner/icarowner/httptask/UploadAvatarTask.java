package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetAvatarModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * UploadAvatarTask
 * create by 崔婧
 * create at 2017/5/18 下午1:36
 */
public class UploadAvatarTask extends BaseTask<GetAvatarModel> {

    public UploadAvatarTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void uploadAvatar(File avatar, Callback callback) {
        this.setCallback(callback);
        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("image", avatar);
        HttpRequest.post(Constant.getHost() + API.UPLOAD_IMAGE, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetAvatarModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetAvatarModel.class);
    }
}
