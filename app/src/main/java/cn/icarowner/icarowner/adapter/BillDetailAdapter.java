package cn.icarowner.icarowner.adapter;

import com.shizhefei.mvc.IDataAdapter;

import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;

/**
 * BillDetailAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:11
 */
public class BillDetailAdapter implements IDataAdapter<ServiceOrderDetailEntity> {
    @Override
    public void notifyDataChanged(ServiceOrderDetailEntity serviceOrderDetailEntity, boolean isRefresh) {

    }

    @Override
    public ServiceOrderDetailEntity getData() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
