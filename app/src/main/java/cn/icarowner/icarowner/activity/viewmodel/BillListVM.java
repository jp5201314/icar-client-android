package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.BillListV;
import cn.icarowner.icarowner.adapter.BillListAdapter;
import cn.icarowner.icarowner.entity.BillItemEntity;
import cn.icarowner.icarowner.httptask.GetBillsTask;
import cn.icarowner.icarowner.httptask.model.GetBillsModel;

/**
 * BillListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:52
 */
public class BillListVM extends BaseDynamicRecyclerVM<GetBillsModel> {

    public ToolBarTitleVM toolBarTitleVM;
    private BillListV billListV;

    private final static int LIMIT = 10;

    public BillListVM(BillListV billListV) {
        super(new LinearLayoutManager(billListV.getContext()), new BillListAdapter(billListV.getContext()));
        this.billListV = billListV;
        initToolBarVM();

        //首次刷新
        load(1);
    }

    @Override
    public void load(int page) {
        GetBillsTask billsTask = new GetBillsTask(this);
        billsTask.getBills(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetBillsModel getBillsModel) {
        setMaxPage(getBillsModel.getPages());

        List dataList = new ArrayList();
        for (BillItemEntity entity : getBillsModel.getOrders()) {
            dataList.add(new ItemPaymentListVM(entity));
        }

        return dataList;
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM(billListV.getContext().getString(R.string.bill)) {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                billListV.closePage();
            }
        };
    }
}
