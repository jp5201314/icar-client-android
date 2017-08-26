package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;

import cn.icarowner.icarowner.entity.BalanceChangeRecordEntity;
import cn.icarowner.icarowner.utils.DateUtil;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ItemBalanceChangeRecordVM
 * create by 崔婧
 * create at 2017/5/18 上午11:54
 */
public class ItemBalanceChangeRecordVM extends BaseVM {
    public final ObservableField<String> incomeOrExpenditure = new ObservableField<>();
    public final ObservableField<String> time = new ObservableField<>();
    public final ObservableField<String> amountOfMoneyStr = new ObservableField<>();
    public final ObservableField<Integer> amountOfMoney = new ObservableField<>();

    private BalanceChangeRecordEntity balanceChangeRecordEntity;

    public ItemBalanceChangeRecordVM(BalanceChangeRecordEntity balanceChangeRecordEntity) {
        this.balanceChangeRecordEntity = balanceChangeRecordEntity;
        this.incomeOrExpenditure.set(balanceChangeRecordEntity.getTypeName());
        this.time.set(DateUtil.formatTime(balanceChangeRecordEntity.getCreatedAt(), "yyyy-MM-dd HH:mm"));
        int amount = balanceChangeRecordEntity.getAmount();
        this.amountOfMoney.set(amount);
        this.amountOfMoneyStr.set(amount >= 0
                ? String.format("+%s", OperationUtils.formatNum(OperationUtils.division(amount)))
                : String.format("%s", OperationUtils.formatNum(OperationUtils.division(amount))));
    }
}
