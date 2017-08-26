package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.GroupV;
import cn.icarowner.icarowner.entity.DealerEntity;

/**
 * ItemDealerVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemDealerVM extends BaseObservable {

    public final ObservableField<String> dealerName = new ObservableField<>();

    private GroupV groupV;
    private DealerEntity dealerEntity;

    public ItemDealerVM(DealerEntity entity, GroupV groupV) {
        dealerName.set(entity.getName());
        this.groupV = groupV;
        this.dealerEntity = entity;
    }

    public void jumpToDealerDetail(View view) {
        if (null == dealerEntity || TextUtils.isEmpty(dealerEntity.getId()) || TextUtils.isEmpty(dealerEntity.getName())) {
            return;
        }
        groupV.jumpToDealerDetail(dealerEntity.getId(), dealerEntity.getName());
    }
}
