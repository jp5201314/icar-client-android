package cn.icarowner.icarowner.activity.viewmodel;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.CouponListV;
import cn.icarowner.icarowner.adapter.CouponListAdapter;
import cn.icarowner.icarowner.entity.CouponEntity;
import cn.icarowner.icarowner.httptask.GetCouponsTask;
import cn.icarowner.icarowner.httptask.model.GetCouponsModel;

/**
 * CouponListVM
 * create by 崔婧
 * create at 2017/5/18 上午11:53
 */
public class CouponListVM extends BaseDynamicRecyclerVM<GetCouponsModel> {
    private CouponListV couponListV;
    public ToolBarTitleVM toolBarTitleVM;
    private final static int LIMIT = 10;

    public CouponListVM(CouponListV couponListV) {
        super(new LinearLayoutManager(couponListV.getContext()), new CouponListAdapter(couponListV.getContext()));
        this.couponListV = couponListV;
        this.initToolBarVM();
        //首次刷新
        load(1);
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("优惠券") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                CouponListVM.this.couponListV.closePage();
            }
        };
    }

    @Override
    public void load(int page) {
        GetCouponsTask getCouponsTask = new GetCouponsTask(this);
        getCouponsTask.getCoupons(page, LIMIT, getCallback(page));
    }

    @Override
    public List onDataSuccess(GetCouponsModel getCouponsModel) {
        setMaxPage(getCouponsModel.getPages());

        List dataList = new ArrayList();
        for (CouponEntity entity : getCouponsModel.getCoupons()) {
            dataList.add(new ItemCouponVM(entity));
        }

        return dataList;
    }
}
