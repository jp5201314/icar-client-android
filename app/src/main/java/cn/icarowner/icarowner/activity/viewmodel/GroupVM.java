package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.GroupV;
import cn.icarowner.icarowner.adapter.DealerListAdapter;
import cn.icarowner.icarowner.entity.DealerEntity;
import cn.icarowner.icarowner.httptask.GetDealersTask;
import cn.icarowner.icarowner.httptask.model.GetDealersModel;

/**
 * GroupVM
 * create by 崔婧
 * create at 2017/5/18 上午11:54
 */
public class GroupVM extends BaseDynamicRecyclerVM<GetDealersModel> {
    private String groupId;
    private String groupName;
    private GroupV groupV;

    public ToolBarTitleVM toolBarTitleVM;

    private final static int LIMIT = 20;

    public GroupVM(GroupV groupV, String groupId, String groupName) {
        super(new GridLayoutManager(groupV.getContext(), 2), new DealerListAdapter(groupV.getContext()));
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupV = groupV;
        initToolBar();
        load(1);
    }

    @Override
    public void load(int page) {
        GetDealersTask getDealersTask = new GetDealersTask(this);
        getDealersTask.getDealers(groupId, page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetDealersModel getDealersModel) {
        setMaxPage(getDealersModel.getPages());

        List dataList = new ArrayList();
        for (DealerEntity entity : getDealersModel.getDealers()) {
            dataList.add(new ItemDealerVM(entity, groupV));
        }

        return dataList;
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM(groupName) {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                groupV.closePage();
            }
        };
    }

}
