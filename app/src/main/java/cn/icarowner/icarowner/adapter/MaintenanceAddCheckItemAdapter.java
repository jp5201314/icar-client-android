package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;

/**
 * MaintenanceAddCheckItemAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:12
 */
public class MaintenanceAddCheckItemAdapter extends BaseRecyclerVMAdapter {

    public MaintenanceAddCheckItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_schedule_detail_check_item;
    }

    @Override
    public int getVariable() {
        return BR.itemScheduleDetailCheckItem;
    }
}
