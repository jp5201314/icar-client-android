package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.viewmodel.ItemServiceOrderVM;

/**
 * ServiceOrderListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:13
 */
public class ServiceOrderListAdapter extends BaseRecyclerVMAdapter<ItemServiceOrderVM> {
    public ServiceOrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_service_order;
    }

    @Override
    public int getVariable() {
        return BR.serviceOrder;
    }
}
