package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

/**
 * ToolBarTitleVM
 * create by 崔婧
 * create at 2017/5/18 下午12:00
 */
public class ToolBarTitleVM extends BaseVM {

    public final ObservableField<String> title = new ObservableField<>();

    public ToolBarTitleVM(String title) {
        this.title.set(title);
    }

    /**
     * On back click listener
     */
    public void onBackClick(View view) {

    }
}