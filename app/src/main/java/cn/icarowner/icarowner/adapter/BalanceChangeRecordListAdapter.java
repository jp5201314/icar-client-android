package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;

/**
 * BalanceChangeRecordListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:11
 */
public class BalanceChangeRecordListAdapter extends BaseRecyclerVMAdapter {

    public BalanceChangeRecordListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.item_amount_of_balance;
    }

    @Override
    public int getHeaderVariable() {
        return BR.amountOfBalance;
    }

    @Override
    public int getLayout() {
        return R.layout.item_balance_change_record;
    }

    @Override
    public int getVariable() {
        return BR.balanceChangeRecord;
    }

}
