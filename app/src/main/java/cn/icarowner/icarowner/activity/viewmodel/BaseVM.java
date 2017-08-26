package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;

import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpTaskHandler;

/**
 * BaseVM
 * create by 崔婧
 * create at 2017/5/18 上午11:52
 */
public class BaseVM extends BaseObservable implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    public void closeHttpTask() {
        HttpTaskHandler.getInstance().removeTask(getHttpTaskKey());
    }
}