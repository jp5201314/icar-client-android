package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.EvaluationListV;
import cn.icarowner.icarowner.adapter.EvaluationListAdapter;
import cn.icarowner.icarowner.entity.EvaluationItemEntity;
import cn.icarowner.icarowner.httptask.GetEvaluationsTask;
import cn.icarowner.icarowner.httptask.model.GetEvaluationsModel;

/**
 * EvaluationListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class EvaluationListVM extends BaseDynamicRecyclerVM<GetEvaluationsModel> {

    private EvaluationListV evaluationListV;
    private static final int LIMIT = 10;
    public ToolBarTitleVM toolBarTitleVM;

    public EvaluationListVM(EvaluationListV evaluationListV) {
        super(new LinearLayoutManager(evaluationListV.getContext()), new EvaluationListAdapter(evaluationListV.getContext()));
        this.evaluationListV = evaluationListV;
        initToolBar();
        load(1);

    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM("待评价") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                evaluationListV.closePage();
            }
        };
    }

    @Override
    public void load(int page) {
        GetEvaluationsTask getEvaluationsTask = new GetEvaluationsTask(this);
        getEvaluationsTask.getEvaluations(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetEvaluationsModel getEvaluationsModel) {
        setMaxPage(getEvaluationsModel.getPages());

        List dataList = new ArrayList();
        for (EvaluationItemEntity evaluationItemEntity : getEvaluationsModel.getEntities()) {
            dataList.add(new ItemEvaluationVM(evaluationItemEntity, evaluationListV));
        }
        return dataList;
    }
}
