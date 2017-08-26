package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.icarowner.icarowner.entity.ScheduleItemEntity;
import cn.icarowner.icarowner.entity.TypesEntity;
import cn.icarowner.icarowner.event.ReplaceToScheduleDetailFragmentEvent;
import cn.icarowner.icarowner.utils.DateUtil;

/**
 * ItemScheduleVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemScheduleVM extends BaseObservable {

    public final ObservableField<String> plateNumber = new ObservableField<>();
    public final ObservableField<Boolean> joinedOvertimeCompensate = new ObservableField<>();
    public final ObservableField<String> typeNames = new ObservableField<>();
    public final ObservableField<String> estimateTimeOfTakingCar = new ObservableField<>();

    private ScheduleItemEntity scheduleItemEntity;

    public ItemScheduleVM(ScheduleItemEntity scheduleItemEntity) {
        this.scheduleItemEntity = scheduleItemEntity;

        this.morphScheduleItem(scheduleItemEntity);
    }

    private void morphScheduleItem(ScheduleItemEntity entity) {
        plateNumber.set(entity.getPlateNumber());
        joinedOvertimeCompensate.set(1 == entity.getOvertimeCompensate());
        if (null == entity.getTypes()) {
            typeNames.set("");
        } else {
            List<TypesEntity> types = entity.getTypes();
            StringBuilder type = new StringBuilder();
            int size = types.size();
            for (int i = 0; i < size; i++) {
                String name = types.get(i).getName();
                type = type.append(name);
                if (i != size - 1) {
                    type.append('、');
                }
            }
            typeNames.set(type.toString().trim());
        }
        if (TextUtils.isEmpty(entity.getEstimatedTimeOfTakingCar())) {
            estimateTimeOfTakingCar.set("暂无");
        } else {
            estimateTimeOfTakingCar.set(DateUtil.formatTime(entity.getEstimatedTimeOfTakingCar(), "yyyy-MM-dd HH:mm"));
        }

    }

    public void onItemClick(View view) {
        EventBus.getDefault().post(new ReplaceToScheduleDetailFragmentEvent(scheduleItemEntity.getId()));
    }
}
