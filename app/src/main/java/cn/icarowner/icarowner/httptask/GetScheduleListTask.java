package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetScheduleListModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetScheduleListTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetScheduleListTask extends BaseTask<GetScheduleListModel> {
    public GetScheduleListTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getScheduleList(int page, int size, Callback callback) {
        this.setCallback(callback);

        RequestParams params = new RequestParams(this.httpCycleContext);
        params.addFormDataPart("page", page);
        params.addFormDataPart("size", size);
        HttpRequest.get(Constant.getHost() + API.SCHEDULE_ORDER_LIST, params, getIcarHttpRequestCallBack());
    }

    @Override
    protected GetScheduleListModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetScheduleListModel.class);
    }
}
