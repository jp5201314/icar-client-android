package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetBalanceChangeRecordsModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetBalanceChangeRecordsTask
 * create by 崔婧
 * create at 2017/5/18 下午1:31
 */
public class GetBalanceChangeRecordsTask extends BaseTask<GetBalanceChangeRecordsModel> {

    public GetBalanceChangeRecordsTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getBalanceChangeRecords(int page, int size, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + API.BALANCE_CHANGE_RECORD, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetBalanceChangeRecordsModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetBalanceChangeRecordsModel.class);
    }
}
