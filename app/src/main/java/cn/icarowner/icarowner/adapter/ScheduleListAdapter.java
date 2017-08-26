package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.viewmodel.ItemScheduleVM;

/**
 * ScheduleListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:13
 */
public class ScheduleListAdapter extends BaseRecyclerVMAdapter<ItemScheduleVM> {

    public ScheduleListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_schedule_list;
    }

    @Override
    public int getVariable() {
        return BR.itemSchedule;
    }
}
