package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.ChoiceCouponListV;
import cn.icarowner.icarowner.adapter.ChoiceCouponListAdapter;
import cn.icarowner.icarowner.entity.CouponEntity;
import cn.icarowner.icarowner.httptask.GetCanChoiceCouponsTask;
import cn.icarowner.icarowner.httptask.model.GetCanChoiceCouponsModel;

/**
 * ChoiceCouponListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:52
 */
public class ChoiceCouponListVM extends BaseDynamicRecyclerVM<GetCanChoiceCouponsModel> {
    private final static int LIMIT = 10;

    public ToolBarTitleVM toolBarTitleVM;

    private ChoiceCouponListV choiceCouponListV;

    private String orderId;
    private String choosedCouponId;

    public ChoiceCouponListVM(ChoiceCouponListV choiceCouponListV, String orderId, String choosedCouponId) {
        super(new LinearLayoutManager(choiceCouponListV.getContext()), new ChoiceCouponListAdapter(choiceCouponListV.getContext()));
        this.choiceCouponListV = choiceCouponListV;
        this.orderId = orderId;
        this.choosedCouponId = choosedCouponId;

        this.initToolBarVM();
        //首次刷新
        load(1);
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("选择优惠券") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                ChoiceCouponListVM.this.choiceCouponListV.closePage();
            }
        };
    }

    @Override
    public void load(int page) {
        GetCanChoiceCouponsTask getCanChoiceCouponsTask = new GetCanChoiceCouponsTask(this);
        getCanChoiceCouponsTask.getCoupons(orderId, page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetCanChoiceCouponsModel getCanChoiceCouponsModel) {
        setMaxPage(getCanChoiceCouponsModel.getPages());

        List dataList = new ArrayList();
        for (CouponEntity entity : getCanChoiceCouponsModel.getCoupons()) {
            ItemChoiceCouponVM itemChoiceCouponVM = new ItemChoiceCouponVM(this, choiceCouponListV, entity, orderId);
            itemChoiceCouponVM.notifyChoosedItem(choosedCouponId);
            dataList.add(itemChoiceCouponVM);
        }

        return dataList;
    }

    public String getChoosedCouponId() {
        return choosedCouponId;
    }

    public void chooseCoupon(String couponId) {
        choosedCouponId = couponId;
        for (Object item : getDataList()) {
            ((ItemChoiceCouponVM) item).notifyChoosedItem(couponId);
        }
    }
}
