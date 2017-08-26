package cn.icarowner.icarowner.adapter;

import android.content.Context;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;

/**
 * MaterialsItemAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:12
 */
public class MaterialsItemAdapter extends BaseRecyclerVMAdapter {

    public MaterialsItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_schedule_detail_materials;
    }

    @Override
    public int getVariable() {
        return BR.itemScheduleDetailMaterials;
    }
}
