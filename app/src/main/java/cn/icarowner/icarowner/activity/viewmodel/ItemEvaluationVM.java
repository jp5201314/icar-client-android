package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import cn.icarowner.icarowner.activity.view.EvaluationListV;
import cn.icarowner.icarowner.entity.EvaluationItemEntity;
import cn.icarowner.icarowner.entity.TypesEntity;
import cn.icarowner.icarowner.utils.DateUtil;

/**
 * ItemEvaluationVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemEvaluationVM extends BaseObservable {

    public ObservableField<String> companyName = new ObservableField<>();
    public ObservableField<String> advisorName = new ObservableField<>();
    public ObservableField<String> licensePlate = new ObservableField<>();
    public ObservableField<String> serviceType = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();

    private EvaluationItemEntity evaluationItemEntity;
    private EvaluationListV evaluationListV;

    public ItemEvaluationVM(EvaluationItemEntity entity, EvaluationListV evaluationListV) {
        this.evaluationItemEntity = entity;
        this.evaluationListV = evaluationListV;
        companyName.set(entity.getDealer().getFullName());
        advisorName.set(entity.getDealerUser().getName());
        licensePlate.set(entity.getPlateNumber());
        getServiceType(entity.getTypes());
        time.set(DateUtil.formatTime(entity.getCreatedAt(), "yyyy-MM-dd"));

    }

    private void getServiceType(List<TypesEntity> entities) {
        StringBuilder type = new StringBuilder();
        for (int i = 0; i < entities.size(); i++) {
            String name = entities.get(i).getName();
            type = type.append(name);
            if (i != entities.size() - 1) {
                type.append('、');
            }
        }
        serviceType.set(type.toString().trim());
    }


    public void jumpToEvaluation(View view) {
        if (null == evaluationItemEntity || TextUtils.isEmpty(evaluationItemEntity.getId()) || TextUtils.isEmpty(evaluationItemEntity.getDealerUser().getName()) || TextUtils.isEmpty(evaluationItemEntity.getDealer().getFullName())) {
            return;
        }
        evaluationListV.jumpToEvaluation(evaluationItemEntity.getId(), evaluationItemEntity.getDealerUser().getName(), evaluationItemEntity.getDealer().getFullName());
    }
}
