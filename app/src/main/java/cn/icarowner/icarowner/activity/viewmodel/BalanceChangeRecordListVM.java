package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.BalanceChangeRecordListV;
import cn.icarowner.icarowner.adapter.BalanceChangeRecordListAdapter;
import cn.icarowner.icarowner.entity.BalanceChangeRecordEntity;
import cn.icarowner.icarowner.httptask.GetBalanceChangeRecordsTask;
import cn.icarowner.icarowner.httptask.model.GetBalanceChangeRecordsModel;

/**
 * BalanceChangeRecordListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:46
 */
public class BalanceChangeRecordListVM extends BaseDynamicRecyclerVM<GetBalanceChangeRecordsModel> {

    public ToolBarTitleVM toolBarTitleVM;
    public ItemAmountOfBalanceVM itemAmountOfBalanceVM;

    public BalanceChangeRecordListV balanceChangeRecordListV;
    private final static int LIMIT = 10;

    public BalanceChangeRecordListVM(BalanceChangeRecordListV balanceChangeRecordListV) {
        super(new LinearLayoutManager(balanceChangeRecordListV.getContext()), new BalanceChangeRecordListAdapter(balanceChangeRecordListV.getContext()));
        this.balanceChangeRecordListV = balanceChangeRecordListV;
        this.initToolBarVM();
        itemAmountOfBalanceVM = new ItemAmountOfBalanceVM(UserSharedPreference.getInstance().getUser());
        load(1);
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("余额") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                BalanceChangeRecordListVM.this.balanceChangeRecordListV.closePage();
            }
        };
    }

    @Override
    public BaseVM getHeaderVM() {
        return itemAmountOfBalanceVM;
    }

    @Override
    public BaseVM getFooterVM() {
        return null;
    }

    @Override
    public void load(int page) {
        GetBalanceChangeRecordsTask getBalanceChangeRecordsTask = new GetBalanceChangeRecordsTask(this);
        getBalanceChangeRecordsTask.getBalanceChangeRecords(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetBalanceChangeRecordsModel getBalanceChangeRecordsModel) {
        setMaxPage(getBalanceChangeRecordsModel.getPages());

        List<ItemBalanceChangeRecordVM> itemBalanceChangeRecordVMs = new ArrayList<>();
        for (BalanceChangeRecordEntity entity : getBalanceChangeRecordsModel.getBalanceChangeRecordEntities()) {
            itemBalanceChangeRecordVMs.add(new ItemBalanceChangeRecordVM(entity));
        }

        return itemBalanceChangeRecordVMs;
    }
}
