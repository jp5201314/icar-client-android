package cn.icarowner.icarowner.net.okhttpfinal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.event.ErrorMessageEvent;
import cn.icarowner.icarowner.event.UnLoginEvent;
import cn.xiaomeng.httpdog.JsonHttpRequestCallback;
import okhttp3.Headers;

/**
 * ICarHttpRequestCallBack
 * create by 崔婧
 * create at 2017/5/18 下午1:38
 */
public class ICarHttpRequestCallBack extends JsonHttpRequestCallback {

    protected boolean autoToastErrorMessage = true;

    public ICarHttpRequestCallBack() {
        this.autoToastErrorMessage = true;
    }

    public ICarHttpRequestCallBack(boolean autoToastErrorMessage) {
        this.autoToastErrorMessage = autoToastErrorMessage;
    }

    @Override
    protected void onSuccess(Headers headers, JSONObject rstJson) {
        Logger.i(JSON.toJSONString(rstJson));
        refreshLocalJwtTokenFromResponseHeader(headers);
        int status = rstJson.getInteger("status");

        JSONObject data = null;
        if (rstJson.containsKey("data")) {
            data = rstJson.getJSONObject("data");
        }

        JSONObject statusInfo = null;
        if (rstJson.containsKey("statusInfo")) {
            statusInfo = rstJson.getJSONObject("statusInfo");
        }

        switch (status) {
            case 0:
                onDataSuccess(data);
                break;
            case 10000:
                onDataError(status, statusInfo);
                if (autoToastErrorMessage) {
                    EventBus.getDefault().post(new ErrorMessageEvent(statusInfo.getString("message")));
                }
                break;
            case 40000://JWT_TOKEN不存在
            case 40001://JWT_TOKEN不可用
            case 40004://JWT_USER未找到
            case 40005://JWT_TOKEN过期失效
            case 40006://USER_KICKED
                unAvailableLogin(status);
            default:
                onDataError(status, statusInfo);
                break;
        }
    }

    protected void onDataSuccess(JSONObject data) {
        Logger.i(JSON.toJSONString(data, true));
    }

    protected void onDataError(int status, JSONObject statusInfo) {
        Logger.e("status:" + status + "\nstatusInfo:\n" + JSON.toJSONString(statusInfo, true));
    }

    private void unAvailableLogin(int code) {
        UserSharedPreference.getInstance().removeJwtToken();
        UserSharedPreference.getInstance().removeUser();
        EventBus.getDefault().post(new UnLoginEvent(code));
    }

    private void refreshLocalJwtTokenFromResponseHeader(Headers headers) {
        String authorization = headers.get("Authorization");
        if (null != authorization && authorization.startsWith("Bearer ")) {
            String jwtToken = authorization.substring("Bearer ".length());
            UserSharedPreference.getInstance().setJwtToken(jwtToken);
        }
    }
}
