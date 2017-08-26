package cn.icarowner.icarowner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.icarowner.icarowner.BR;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.viewmodel.ItemPaymentListVM;
import cn.icarowner.icarowner.entity.GoodsEntity;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * BillListAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:12
 */
public class BillListAdapter extends BaseRecyclerVMAdapter<ItemPaymentListVM> {

    public BillListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.item_payment_list;
    }

    @Override
    public int getVariable() {
        return BR.itemPaymentList;
    }

    @Override
    public void onRenderDataView(BindingViewHolder bindingViewHolder, int position, Object viewModel) {
        super.onRenderDataView(bindingViewHolder, position, viewModel);
        ItemPaymentListVM itemPaymentListVM = (ItemPaymentListVM) viewModel;
        LinearLayout llServiceItem = ((LinearLayout) bindingViewHolder.getBinding().getRoot().findViewById(R.id.ll_service_item));
        llServiceItem.removeAllViews();
        for (GoodsEntity goodEntity : itemPaymentListVM.goodItems) {
            LinearLayout itemOfBill = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_bill_list_detail, llServiceItem, false);
            ((TextView) itemOfBill.findViewById(R.id.tv_service_type_name)).setText(goodEntity.getName());
            ((TextView) itemOfBill.findViewById(R.id.tv_service_type_fee)).setText(OperationUtils.formatNum(OperationUtils.division(goodEntity.getPayPrice())));
            llServiceItem.addView(itemOfBill);
        }
    }
}
