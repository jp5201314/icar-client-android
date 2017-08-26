package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.net.okhttpfinal.ICarHttpRequestCallBack;
import cn.xiaomeng.httpdog.HttpCycleContext;

/**
 * BaseTask
 * create by 崔婧
 * create at 2017/5/18 下午1:30
 */
abstract class BaseTask<M> {

    protected Callback<M> callback;

    protected ICarHttpRequestCallBack icarHttpRequestCallBack;

    protected HttpCycleContext httpCycleContext;

    public BaseTask(HttpCycleContext httpCycleContext) {
        this.httpCycleContext = httpCycleContext;
        this.initIcarHttpRequestCallBack();
    }

    private void initIcarHttpRequestCallBack() {
        this.icarHttpRequestCallBack = new ICarHttpRequestCallBack() {
            @Override
            public void onStart() {
                super.onStart();
                if (null != callback) {
                    callback.onStart();
                }
            }

            @Override
            protected void onDataSuccess(JSONObject data) {
                super.onDataSuccess(data);
                M model = parseModel(data);

                if (null != callback) {
                    callback.onDataSuccess(model);
                }
            }

            @Override
            protected void onDataError(int status, JSONObject statusInfo) {
                super.onDataError(status, statusInfo);
                if (null != callback) {
                    callback.onDataError(status, statusInfo);
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (null != callback) {
                    callback.onFail(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (null != callback) {
                    callback.onFinish();
                }
            }
        };
    }

    protected void setCallback(Callback callback) {
        this.callback = callback;
    }

    ICarHttpRequestCallBack getIcarHttpRequestCallBack() {
        return this.icarHttpRequestCallBack;
    }

    protected abstract M parseModel(JSONObject data);
}
