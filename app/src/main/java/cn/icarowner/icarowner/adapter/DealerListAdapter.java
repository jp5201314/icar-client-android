package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.viewmodel.ItemDealerVM;

/**
 * DealerListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:12
 */
public class DealerListAdapter extends BaseRecyclerVMAdapter<ItemDealerVM> {
    public DealerListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_dealer;
    }

    @Override
    public int getVariable() {
        return BR.dealer;
    }
}
