package cn.icarowner.icarowner.datasource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.net.okhttpfinal.ICarHttpRequestCallBack;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * ServiceOrderDetailDataSource
 * create by 崔婧
 * create at 2017/5/18 下午1:18
 */
public class ServiceOrderDetailDataSource implements IAsyncDataSource<ServiceOrderDetailEntity> {
    private HttpCycleContext httpCycleContext;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceOrderDetailDataSource() {
    }

    public ServiceOrderDetailDataSource(HttpCycleContext httpCycleContext) {
        this.httpCycleContext = httpCycleContext;
    }

    public ServiceOrderDetailDataSource(HttpCycleContext httpCycleContext, String id) {
        this.httpCycleContext = httpCycleContext;
        this.id = id;
    }

    @Override
    public RequestHandle refresh(ResponseSender<ServiceOrderDetailEntity> sender) throws Exception {

        return loadServiceOrderDetail(sender);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<ServiceOrderDetailEntity> sender) throws Exception {
        return null;
    }

    @Override
    public boolean hasMore() {
        return false;
    }

    private RequestHandle loadServiceOrderDetail(final ResponseSender<ServiceOrderDetailEntity> sender) {
        RequestParams params = new RequestParams(httpCycleContext);

        HttpRequest.get(Constant.getHost() + String.format(API.SERVICE_ORDER_DETAIL, id), params, new ICarHttpRequestCallBack() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                super.onDataSuccess(data);
                ServiceOrderDetailEntity serviceOrderDetailEntity =
                        JSON.parseObject(data.toJSONString(), ServiceOrderDetailEntity.class);
                sender.sendData(serviceOrderDetailEntity);
            }

            @Override
            protected void onDataError(int status, JSONObject statusInfo) {
                super.onDataError(status, statusInfo);
                sender.sendData(null);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                sender.sendData(null);
            }
        });
        return new OkHttpRequestHandler();
    }
}
