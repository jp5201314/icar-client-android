package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.activity.view.ServiceOrderDetailV;
import cn.icarowner.icarowner.adapter.ServiceOrderAnnexGridAdapter;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.entity.TypesEntity;
import cn.icarowner.icarowner.httptask.GetServiceOrderDetailTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.utils.DateUtil;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * ServiceOrderDetailVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ServiceOrderDetailVM extends BaseVM {

    public final ObservableField<Boolean> isRefreshing = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> companyName = new ObservableField<>();
    public final ObservableField<String> advisorName = new ObservableField<>();
    public final ObservableField<String> plateNumber = new ObservableField<>();
    public final ObservableField<String> intoFactoryMileage = new ObservableField<>();
    public final ObservableField<String> serviceType = new ObservableField<>();
    public final ObservableField<String> startTime = new ObservableField<>();
    public final ObservableField<Boolean> startTimeIsNull = new ObservableField<>();
    public final ObservableField<Boolean> showArrow = new ObservableField<>();
    public final ObservableField<String> endTime = new ObservableField<>();
    public final ObservableField<Boolean> endTimeIsNull = new ObservableField<>();
    public final ObservableField<String> amountOfBill = new ObservableField<>();
    public final ObservableField<Boolean> amountOfBillIsNull = new ObservableField<>();
    public final ObservableField<String> amountOfDiscount = new ObservableField<>();
    public final ObservableField<Boolean> amountOfDiscountIsNull = new ObservableField<>();
    public final ObservableField<String> amountOfPayment = new ObservableField<>();
    public final ObservableField<Boolean> amountOfPaymentIsNull = new ObservableField<>();
    public final ObservableField<Boolean> hasAnnex = new ObservableField<>();
    public final ObservableList<ItemServiceOrderAnnexVM> items = new ObservableArrayList<>();

    public ToolBarTitleVM toolBarTitleVM;

    private ServiceOrderDetailV serviceOrderDetailV;
    private String orderId;
    public ServiceOrderDetailEntity serviceOrderDetailEntity;

    public GridLayoutManager gridLayoutManager;
    public ServiceOrderAnnexGridAdapter adapter;

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            attemptGetServiceOrderDetail();
        }
    };

    public ServiceOrderDetailVM(ServiceOrderDetailV serviceOrderDetailV, String orderId) {
        this.serviceOrderDetailV = serviceOrderDetailV;
        this.orderId = orderId;
        initToolBar();
        gridLayoutManager = new GridLayoutManager(serviceOrderDetailV.getContext(), 3);
        adapter = new ServiceOrderAnnexGridAdapter(serviceOrderDetailV.getContext());
        attemptGetServiceOrderDetail();
    }

    private void attemptGetServiceOrderDetail() {
        GetServiceOrderDetailTask getServiceOrderDetailTask = new GetServiceOrderDetailTask(this);
        getServiceOrderDetailTask.getServiceOrderDetail(orderId, new Callback<ServiceOrderDetailEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isRefreshing.set(true);
            }

            @Override
            public void onDataSuccess(ServiceOrderDetailEntity entity) {
                super.onDataSuccess(entity);
                serviceOrderDetailEntity = entity;
                morphServiceOrderDetail();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isRefreshing.set(false);
            }
        });
    }

    private void morphServiceOrderDetail() {
        List<TypesEntity> types = serviceOrderDetailEntity.getTypes();
        StringBuilder type = new StringBuilder();
        int size = types.size();
        for (int i = 0; i < size; i++) {
            String name = types.get(i).getName();
            type = type.append(name);
            if (i != size - 1) {
                type.append('、');
            }
        }
        companyName.set(serviceOrderDetailEntity.getDealer().getFullName());
        advisorName.set(serviceOrderDetailEntity.getAdvisor().getName());
        plateNumber.set(serviceOrderDetailEntity.getPlateNumber());
        intoFactoryMileage.set(String.valueOf(serviceOrderDetailEntity.getKilometers()));
        serviceType.set(type.toString().trim());
        if (!TextUtils.isEmpty(serviceOrderDetailEntity.getIntoFactoryAt()) && TextUtils.isEmpty(serviceOrderDetailEntity.getOutFactoryAt())) {
            startTime.set(DateUtil.formatTime(serviceOrderDetailEntity.getIntoFactoryAt(), "yyyy-MM-dd HH:mm"));
            startTimeIsNull.set(false);

            showArrow.set(true);

            endTime.set("暂无");
            endTimeIsNull.set(true);
        } else if (!TextUtils.isEmpty(serviceOrderDetailEntity.getIntoFactoryAt()) && !TextUtils.isEmpty(serviceOrderDetailEntity.getOutFactoryAt())) {
            startTime.set(DateUtil.formatTime(serviceOrderDetailEntity.getIntoFactoryAt(), "yyyy-MM-dd HH:mm"));
            startTimeIsNull.set(false);

            showArrow.set(true);

            endTime.set(DateUtil.formatTime(serviceOrderDetailEntity.getOutFactoryAt(), "yyyy-MM-dd HH:mm"));
            endTimeIsNull.set(false);
        } else {
            startTime.set("暂无");
            startTimeIsNull.set(true);

            showArrow.set(false);

            endTime.set("暂无");
            endTimeIsNull.set(true);
        }

        if (serviceOrderDetailEntity.getBill() == null) {
            amountOfBillIsNull.set(true);
            amountOfBill.set("暂无");

            amountOfDiscountIsNull.set(true);
            amountOfDiscount.set("暂无");

            amountOfPaymentIsNull.set(true);
            amountOfPayment.set("暂无");
        } else {
            amountOfBillIsNull.set(false);
            amountOfBill.set(String.format("¥%s", OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getAmount()))));

            amountOfDiscountIsNull.set(false);
            amountOfDiscount.set(String.format("¥%s", OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getDiscount()))));

            amountOfPaymentIsNull.set(false);
            amountOfPayment.set(String.format("¥%s", OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount()))));
        }
        if (null == serviceOrderDetailEntity.getImageUrls() || 0 == serviceOrderDetailEntity.getImageUrls().size()) {
            hasAnnex.set(false);
            items.clear();
        } else {
            hasAnnex.set(true);
            items.clear();
            for (int i = 0; i < serviceOrderDetailEntity.getImageUrls().size(); i++) {
                items.add(new ItemServiceOrderAnnexVM((ArrayList) serviceOrderDetailEntity.getImageUrls(), i, serviceOrderDetailEntity.getImageUrls().get(i), serviceOrderDetailV));
            }
        }
    }

    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM("服务单详情") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                serviceOrderDetailV.closePage();
            }
        };
    }
}
