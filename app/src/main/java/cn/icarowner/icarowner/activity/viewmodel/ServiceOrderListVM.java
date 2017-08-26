package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.ServiceOrderListV;
import cn.icarowner.icarowner.adapter.ServiceOrderListAdapter;
import cn.icarowner.icarowner.entity.ServiceOrderItemEntity;
import cn.icarowner.icarowner.httptask.GetServiceOrdersTask;
import cn.icarowner.icarowner.httptask.model.GetServiceOrdersModel;

/**
 * ServiceOrderListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ServiceOrderListVM extends BaseDynamicRecyclerVM<GetServiceOrdersModel> {
    private ServiceOrderListV serviceOrderListV;
    public ToolBarTitleVM toolBarTitleVM;
    private final static int LIMIT = 10;

    public ServiceOrderListVM(ServiceOrderListV serviceOrderListV) {
        super(new LinearLayoutManager(serviceOrderListV.getContext()), new ServiceOrderListAdapter(serviceOrderListV.getContext()));
        this.serviceOrderListV = serviceOrderListV;
        this.initToolBarVM();
        //首次刷新
        load(1);
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("服务单") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                ServiceOrderListVM.this.serviceOrderListV.closePage();
            }
        };
    }

    @Override
    public void load(int page) {
        GetServiceOrdersTask getServiceOrdersTask = new GetServiceOrdersTask(this);
        getServiceOrdersTask.getServiceOrders(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetServiceOrdersModel getServiceOrdersModel) {
        setMaxPage(getServiceOrdersModel.getPages());

        List dataList = new ArrayList();
        for (ServiceOrderItemEntity entity : getServiceOrdersModel.getServiceOrderItemEntities()) {
            dataList.add(new ItemServiceOrderVM(entity, serviceOrderListV));
        }

        return dataList;
    }
}
