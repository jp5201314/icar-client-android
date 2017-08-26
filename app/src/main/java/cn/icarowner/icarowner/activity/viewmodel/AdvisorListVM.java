package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.AdvisorListV;
import cn.icarowner.icarowner.adapter.AdvisorListAdapter;
import cn.icarowner.icarowner.entity.AdvisorEntity;
import cn.icarowner.icarowner.httptask.GetAdvisorsTask;
import cn.icarowner.icarowner.httptask.model.GetAdvisorsModel;

/**
 * AdvisorListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:46
 */
public class AdvisorListVM extends BaseDynamicRecyclerVM<GetAdvisorsModel> {

    public ToolBarTitleVM toolBarTitleVM;
    private AdvisorListV advisorListV;

    private final static int LIMIT = 10;

    public AdvisorListVM(AdvisorListV advisorListV) {
        super(new LinearLayoutManager(advisorListV.getContext()), new AdvisorListAdapter(advisorListV.getContext()));
        this.advisorListV = advisorListV;
        initToolBarVM();

        //首次刷新
        load(1);
    }

    @Override
    public void load(int page) {
        GetAdvisorsTask advisorsTask = new GetAdvisorsTask(this);
        advisorsTask.getAdvisor(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetAdvisorsModel getAdvisorModel) {
        setMaxPage(getAdvisorModel.getPages());

        List dataList = new ArrayList();
        for (AdvisorEntity entity : getAdvisorModel.getAdvisorEntities()) {
            dataList.add(new ItemAdvisorVM(entity, advisorListV));
        }

        return dataList;
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("顾问列表") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                advisorListV.closePage();
            }
        };
    }
}
