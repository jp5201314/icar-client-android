package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ScheduleListV;
import cn.icarowner.icarowner.adapter.ScheduleListAdapter;
import cn.icarowner.icarowner.entity.ScheduleItemEntity;
import cn.icarowner.icarowner.httptask.GetScheduleListTask;
import cn.icarowner.icarowner.httptask.model.GetScheduleListModel;

/**
 * ScheduleListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ScheduleListVM extends BaseDynamicRecyclerVM<GetScheduleListModel> {

    public ToolBarTitleVM toolBarTitleVM;
    private ScheduleListV scheduleListV;

    private final static int LIMIT = 10;

    public ScheduleListVM(ScheduleListV scheduleListV) {
        super(new LinearLayoutManager(scheduleListV.getContext()), new ScheduleListAdapter(scheduleListV.getContext()));
        this.scheduleListV = scheduleListV;
        initToolBarVM();

        //首次刷新
        load(1);
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM(scheduleListV.getContext().getString(R.string.schedule)) {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                scheduleListV.closePage();
            }
        };
    }

    @Override
    public void load(int page) {
        GetScheduleListTask getScheduleListTask = new GetScheduleListTask(this);
        getScheduleListTask.getScheduleList(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetScheduleListModel getScheduleListModel) {
        setMaxPage(getScheduleListModel.getPages());

        List dataList = new ArrayList();
        for (ScheduleItemEntity entity : getScheduleListModel.getOrders()) {
            dataList.add(new ItemScheduleVM(entity));
        }

        return dataList;
    }
}
