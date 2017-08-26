package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;

import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ItemAmountOfBalanceVM
 * create by 崔婧
 * create at 2017/5/18 上午11:54
 */
public class ItemAmountOfBalanceVM extends BaseVM {
    public final ObservableField<String> amountOfBalance = new ObservableField<>();

    public ItemAmountOfBalanceVM(UserEntity entity) {
        this.amountOfBalance.set(OperationUtils.formatNum(OperationUtils.division(entity.getBalance())));
    }
}
