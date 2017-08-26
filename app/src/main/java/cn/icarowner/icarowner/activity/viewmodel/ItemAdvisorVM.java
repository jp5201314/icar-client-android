package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import cn.icarowner.icarowner.activity.view.AdvisorListV;
import cn.icarowner.icarowner.entity.AdvisorEntity;

/**
 * ItemAdvisorVM
 * create by 崔婧
 * create at 2017/5/18 上午11:54
 */
public class ItemAdvisorVM extends BaseObservable {

    public final ObservableField<String> dealerName = new ObservableField<>();
    public final ObservableField<String> advisorName = new ObservableField<>();

    private AdvisorListV advisorListV;

    private AdvisorEntity advisorEntity;

    public ItemAdvisorVM(AdvisorEntity entity, AdvisorListV advisorListV) {
        this.advisorEntity = entity;
        this.advisorListV = advisorListV;

        this.morphAdvisor(entity);
    }

    private void morphAdvisor(AdvisorEntity entity) {
        this.dealerName.set(entity.getDealer().getFullName());
        this.advisorName.set(entity.getName());
    }

    public void showDealerDetailInfo(View view) {
        advisorListV.jumpToDealerDetailActivity(advisorEntity.getDealer().getId(), advisorEntity.getDealer().getName());
    }

    public void showAdvisorDetailInfo(View view) {
        advisorListV.jumpToAdvisorDetailActivity(advisorEntity.getId());
    }

    public void callToAdvisor(View view) {
        if (null == advisorEntity || TextUtils.isEmpty(advisorEntity.getMobile())) {
            return;
        }
        advisorListV.callToAdvisor(advisorEntity.getMobile());
    }
}
