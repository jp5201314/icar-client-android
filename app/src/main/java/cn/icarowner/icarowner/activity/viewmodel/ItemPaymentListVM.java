package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.icarowner.icarowner.entity.BillItemEntity;
import cn.icarowner.icarowner.entity.GoodsEntity;
import cn.icarowner.icarowner.event.ReplaceToBillDetailFragmentEvent;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ItemPaymentListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemPaymentListVM extends BaseObservable {

    public final ObservableField<String> plateNumber = new ObservableField<>();
    public final ObservableField<String> totalFee = new ObservableField<>();
    public final ObservableList<GoodsEntity> goodItems = new ObservableArrayList<>();

    private BillItemEntity billItemEntity;

    public ItemPaymentListVM(BillItemEntity billItemEntity) {
        this.billItemEntity = billItemEntity;

        this.morphBillItem(billItemEntity);
    }

    private void morphBillItem(BillItemEntity billItemEntity) {
        plateNumber.set(billItemEntity.getPlateNumber());
        if (null == billItemEntity.getBill()) {
            totalFee.set(OperationUtils.formatNum(0));
            return;
        }
        List<GoodsEntity> goods = billItemEntity.getBill().getOrder().getGoods();
        if (goods == null) {
            totalFee.set(OperationUtils.formatNum(0));
            return;
        }
        totalFee.set(OperationUtils.formatNum(OperationUtils.division(billItemEntity.getBill().getOrder().getAmount())));
        goodItems.clear();
        goodItems.addAll(goods);
    }

    public void onToDetailClick(View view) {
        EventBus.getDefault().post(new ReplaceToBillDetailFragmentEvent(billItemEntity.getId()));
    }
}
