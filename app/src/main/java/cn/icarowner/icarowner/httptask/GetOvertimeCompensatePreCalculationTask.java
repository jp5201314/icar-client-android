package cn.icarowner.icarowner.httptask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetOvertimeCompensatePreCalculationModel;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * GetOvertimeCompensatePreCalculationTask
 * create by 崔婧
 * create at 2017/5/18 下午1:33
 */
public class GetOvertimeCompensatePreCalculationTask extends BaseTask<GetOvertimeCompensatePreCalculationModel> {

    public GetOvertimeCompensatePreCalculationTask(HttpCycleContext httpCycleContext) {
        super(httpCycleContext);
    }

    public void getOvertimeCompensatePreCalculation(String orderId, Callback<GetOvertimeCompensatePreCalculationModel> callback) {
        this.setCallback(callback);
        HttpRequest.get(Constant.getHost() + String.format(API.OVERTIME_COMPENSATE_PRE_CALCULATION, orderId), new RequestParams(httpCycleContext), getIcarHttpRequestCallBack());
    }

    @Override
    protected GetOvertimeCompensatePreCalculationModel parseModel(JSONObject data) {
        return JSON.parseObject(data.toString(), GetOvertimeCompensatePreCalculationModel.class);
    }
}
