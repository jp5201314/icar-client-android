package cn.icarowner.icarowner.datasource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.List;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.entity.BillItemEntity;
import cn.icarowner.icarowner.net.okhttpfinal.ICarHttpRequestCallBack;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * BillListDataSource
 * create by 崔婧
 * create at 2017/5/18 下午1:18
 */
public class BillListDataSource implements IAsyncDataSource<List<BillItemEntity>> {
    private HttpCycleContext httpCycleContext;
    private int page = 1;
    private int maxPage = 5;

    public BillListDataSource(HttpCycleContext httpCycleContext) {
        this.httpCycleContext = httpCycleContext;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<BillItemEntity>> sender) throws Exception {
        return loadPaymentOrder(sender, 1);
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<BillItemEntity>> sender) throws Exception {
        return loadPaymentOrder(sender, page + 1);
    }

    @Override
    public boolean hasMore() {
        return page < maxPage;
    }

    private RequestHandle loadPaymentOrder(final ResponseSender<List<BillItemEntity>> sender, final int page) {

        RequestParams params = new RequestParams(httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", 10);
        HttpRequest.get(Constant.getHost() + API.WAIT_PAYMENT_ORDER_LIST, params, new ICarHttpRequestCallBack() {
            @Override
            protected void onDataSuccess(JSONObject data) {
                super.onDataSuccess(data);
                maxPage = data.getIntValue("pages");
                BillListDataSource.this.page = page;
                List<BillItemEntity> billItemEntityList =
                        JSON.parseArray(data.getString("orders"), BillItemEntity.class);
                sender.sendData(billItemEntityList);
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
