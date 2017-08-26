package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import cn.icarowner.icarowner.activity.view.ServiceOrderListV;
import cn.icarowner.icarowner.entity.ServiceOrderItemEntity;
import cn.icarowner.icarowner.entity.TypesEntity;
import cn.icarowner.icarowner.utils.DateUtil;

/**
 * ItemServiceOrderVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class ItemServiceOrderVM extends BaseVM {

    public final ObservableField<String> companyFullName = new ObservableField<>();
    public final ObservableField<String> advisorName = new ObservableField<>();
    public final ObservableField<String> plateNumber = new ObservableField<>();
    public final ObservableField<String> serviceType = new ObservableField<>();
    public final ObservableField<String> createTime = new ObservableField<>();

    private ServiceOrderItemEntity serviceOrderItemEntity;
    private ServiceOrderListV serviceOrderListV;

    public ItemServiceOrderVM(ServiceOrderItemEntity serviceOrderItemEntity, ServiceOrderListV serviceOrderListV) {
        List<TypesEntity> types = serviceOrderItemEntity.getTypes();
        StringBuilder type = new StringBuilder();
        int size = types.size();
        for (int i = 0; i < size; i++) {
            String name = types.get(i).getName();
            type = type.append(name);
            if (i != size - 1) {
                type.append('、');
            }
        }
        this.serviceOrderItemEntity = serviceOrderItemEntity;
        this.serviceOrderListV = serviceOrderListV;
        this.companyFullName.set((serviceOrderItemEntity.getDealer().getFullName()));
        this.advisorName.set(serviceOrderItemEntity.getAdvisor().getName());
        this.plateNumber.set(serviceOrderItemEntity.getPlateNumber());
        this.serviceType.set(type.toString().trim());
        this.createTime.set(DateUtil.formatTime(serviceOrderItemEntity.getCreatedAt(), "yyyy-MM-dd"));
    }

    public void onItemClick(View view) {
        if (null == serviceOrderItemEntity || TextUtils.isEmpty(serviceOrderItemEntity.getId())) {
            return;
        }
        serviceOrderListV.jumpToServiceOrderDetail(serviceOrderItemEntity.getId());
    }
}
