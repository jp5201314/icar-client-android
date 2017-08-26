package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;

/**
 * ServiceOrderAnnexGridAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:13
 */
public class ServiceOrderAnnexGridAdapter extends BaseRecyclerVMAdapter<String> {

    public ServiceOrderAnnexGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_service_order_annex;
    }

    @Override
    public int getVariable() {
        return BR.serviceOrderAnnex;
    }
}
