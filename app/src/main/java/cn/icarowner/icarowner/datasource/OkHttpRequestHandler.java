package cn.icarowner.icarowner.datasource;

import com.shizhefei.mvc.RequestHandle;

/**
 * OkHttpRequestHandler
 * create by 崔婧
 * create at 2017/5/18 下午1:18
 */
public class OkHttpRequestHandler implements RequestHandle {
    @Override
    public void cancle() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
