package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.viewmodel.ItemAdvisorVM;

/**
 * AdvisorListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:10
 */
public class AdvisorListAdapter extends BaseRecyclerVMAdapter<ItemAdvisorVM> {

    public AdvisorListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_advisor_list;
    }

    @Override
    public int getVariable() {
        return BR.advisor;
    }

}
