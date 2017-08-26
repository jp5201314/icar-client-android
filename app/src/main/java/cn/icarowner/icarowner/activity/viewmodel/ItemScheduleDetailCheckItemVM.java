package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * ItemScheduleDetailCheckItemVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemScheduleDetailCheckItemVM extends BaseObservable {
    public final ObservableField<String> checkItemName = new ObservableField<>();

    public ItemScheduleDetailCheckItemVM(int position, String name) {
        this.checkItemName.set(name);
    }
}
