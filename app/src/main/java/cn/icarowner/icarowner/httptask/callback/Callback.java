package cn.icarowner.icarowner.httptask.callback;

import com.alibaba.fastjson.JSONObject;

/**
 * Callback
 * create by 崔婧
 * create at 2017/5/18 下午1:27
 */
public abstract class Callback<M> {

    public void onStart() {

    }

    /**
     * 响应成功，且数据正常
     *
     * @param m
     */
    public void onDataSuccess(M m) {

    }

    public void onDataError(int status, JSONObject statusInfo) {
        onDataErrorOrFail(statusInfo.getString("message"));
    }

    public void onFail(String msg) {
        onDataErrorOrFail(msg);
    }

    /**
     * 返回数据的异常或者请求响应的异常
     *
     * @param msg
     */
    public void onDataErrorOrFail(String msg) {

    }

    public void onFinish() {

    }

}