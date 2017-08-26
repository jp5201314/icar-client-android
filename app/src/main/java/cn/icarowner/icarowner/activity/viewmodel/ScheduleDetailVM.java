package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.icarowner.icarowner.activity.view.ScheduleDetailV;
import cn.icarowner.icarowner.adapter.BaseRecyclerVMAdapter;
import cn.icarowner.icarowner.adapter.MaintenanceAddCheckItemAdapter;
import cn.icarowner.icarowner.adapter.MaintenanceBaseCheckItemAdapter;
import cn.icarowner.icarowner.adapter.MaterialsItemAdapter;
import cn.icarowner.icarowner.entity.AddItemsEntity;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.entity.ServiceTypesEntity;
import cn.icarowner.icarowner.httptask.GetOvertimeCompensatePreCalculationTask;
import cn.icarowner.icarowner.httptask.GetServiceOrderDetailTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetOvertimeCompensatePreCalculationModel;
import cn.icarowner.icarowner.utils.DateUtil;
import cn.icarowner.icarowner.utils.NumFormatUtils;

/**
 * ScheduleDetailVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ScheduleDetailVM extends BaseVM {

    private final static String serviceTypeAId = "44f9cc2b-1d6c-4074-9486-ffab27b7deb4";
    private final static String serviceTypeBId = "d47cd6d7-b06a-42af-acd3-c4524f9b9149";

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<Boolean> isRefreshing = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<Boolean> displayFindManager = new ObservableField<>();
    public final ObservableField<Boolean> displayOvertimeCompensate = new ObservableField<>();
    public final ObservableField<Integer> overtime = new ObservableField<>();
    public final ObservableField<String> compensate = new ObservableField<>();

    public final ObservableField<Boolean> displayEstimate = new ObservableField<>();
    public final ObservableField<String> leftTip = new ObservableField<>();
    public final ObservableField<String> rightTip = new ObservableField<>();

    public final ObservableField<String> estimateStartWorkingTime = new ObservableField<>();

    public final ObservableField<String> washingCarStatusStr = new ObservableField<>();

    public final ObservableField<Boolean> displayIntoFactoryCircle = new ObservableField<>();
    public final ObservableField<String> intoFactoryLabel = new ObservableField<>();
    public final ObservableField<Boolean> displayWorkCircle = new ObservableField<>();
    public final ObservableField<String> workAtLabel = new ObservableField<>();
    public final ObservableField<Boolean> displayFinalInspectionCircle = new ObservableField<>();
    public final ObservableField<String> finalInspectionLabel = new ObservableField<>();
    public final ObservableField<Boolean> displayOutFactoryCircle = new ObservableField<>();
    public final ObservableField<String> outFactoryLabel = new ObservableField<>();

    public final ObservableField<Boolean> displayOrderDetail = new ObservableField<>();
    // 参与了超时赔付：服务项目，否则 服务类型
    public final ObservableField<String> bigTitle = new ObservableField<>();

    // 参与了超时赔付 且 有增项
    public final ObservableField<Boolean> displayAddItems = new ObservableField<>();
    public final ObservableField<String> addItemCount = new ObservableField<>();
    public final ObservableField<String> addItemTime = new ObservableField<>();
    public final ObservableField<String> basicTitle = new ObservableField<>();
    public final ObservableField<String> basicItemCount = new ObservableField<>();
    public final ObservableField<String> basicItemTime = new ObservableField<>();

    public final ObservableList addCheckItems = new ObservableArrayList<>();
    public final ObservableList baseCheckItems = new ObservableArrayList<>();
    public final ObservableList materialItems = new ObservableArrayList<>();

    public RecyclerView.LayoutManager addCheckLayoutManager, baseCheckLayoutManager, materialLayoutManager;
    public BaseRecyclerVMAdapter addCheckAdapter, baseCheckAdapter, materialAdapter;

    private ScheduleDetailV scheduleDetailV;
    public ToolBarTitleVM toolBarTitleVM;

    private boolean isFromList;
    public String serviceOrderId;
    private ServiceOrderDetailEntity serviceOrderDetailEntity;

    public ScheduleDetailVM(ScheduleDetailV scheduleDetailV, String serviceOrderId, boolean isFromList) {
        this.scheduleDetailV = scheduleDetailV;
        this.serviceOrderId = serviceOrderId;
        this.isFromList = isFromList;

        this.addCheckLayoutManager = new LinearLayoutManager(scheduleDetailV.getContext());
        this.addCheckAdapter = new MaintenanceAddCheckItemAdapter(scheduleDetailV.getContext());
        this.baseCheckLayoutManager = new LinearLayoutManager(scheduleDetailV.getContext());
        this.baseCheckAdapter = new MaintenanceBaseCheckItemAdapter(scheduleDetailV.getContext());
        this.materialLayoutManager = new GridLayoutManager(scheduleDetailV.getContext(), 2);
        this.materialAdapter = new MaterialsItemAdapter(scheduleDetailV.getContext());

        attemptGetServiceOrderDetail();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (!isLoading.get()) {
                attemptGetServiceOrderDetail();
            }
        }
    };

    public void onBackClick(View view) {
        if (isFromList) {
            scheduleDetailV.replaceByScheduleListFragment();
        } else {
            scheduleDetailV.closePage();
        }
    }

    public void onFindManagerClick(View view) {
        scheduleDetailV.jumpToFindManagerPage(serviceOrderId,
                serviceOrderDetailEntity.getEstimatedTimeOfTakingCar(),
                serviceOrderDetailEntity.getOutFactoryAt(),
                serviceOrderDetailEntity.getOvertimeCompensate());
    }

    private void morphServiceOrderDetail(ServiceOrderDetailEntity entity) {
        // ==================================顶部条相关相关===================================
        // 找经理：数据不为空
        displayFindManager.set(null != entity);
        // 超时模块显示：超时时间>0
        // 超时分钟
        // 超时金额
        if (null != entity.getOvertimeCompensate() && entity.getOvertimeCompensate().getOvertime() > 0) {
            displayOvertimeCompensate.set(true);
            overtime.set(entity.getOvertimeCompensate().getOvertime());
            compensate.set(String.valueOf(serviceOrderDetailEntity.getOvertimeCompensate().getCompensateFee() / 100));
        } else {
            displayOvertimeCompensate.set(false);
            overtime.set(null);
            compensate.set(null);
        }
        // 预计模块：超时模块不显示
        displayEstimate.set(null == entity.getOvertimeCompensate() || entity.getOvertimeCompensate().getOvertime() <= 0);
        // 服务单已创建文字 <进厂
        // 车辆待进厂文字 <进厂
        // 预计完工时间文字：>=10
        // 预计完工时间：>=10 && 有预计完成时间 否则暂无
        leftTip.set(entity.getStatus() < ServiceOrderDetailEntity.STATUS_INTO_FACTORY ? "服务单已创建" : "预计完工时间：");
        rightTip.set(entity.getStatus() < ServiceOrderDetailEntity.STATUS_INTO_FACTORY ? "车辆待进厂" :
                ((null == entity.getEstimatedTimeOfTakingCar() ? "暂无" : DateUtil.formatTime(entity.getEstimatedTimeOfTakingCar(), "MM月dd日 HH:mm"))));
        // 等待上钟时间：>=10 && <20 && 等待时间>0 && 进厂时间不为空
        estimateStartWorkingTime.set(entity.getStatus() >= ServiceOrderDetailEntity.STATUS_INTO_FACTORY
                && entity.getStatus() < ServiceOrderDetailEntity.STATUS_WORKING
                && entity.getEstimatedWaitingTime() > 0
                && null != entity.getIntoFactoryAt() ? getEstimatedStartTime(entity) : null);
        // 洗车：==74
        // 洗完车：洗完时间不为空
        if (entity.getStatus() == ServiceOrderDetailEntity.STATUS_WASHING_CAR) {
            washingCarStatusStr.set("洗车中");
        } else if (null != entity.getWashCompletedAt()) {
            washingCarStatusStr.set("已洗车");
        } else {
            washingCarStatusStr.set(null);
        }

        // ==================================圆圈相关===================================
        // 进厂圆圈：有进厂时间亮，否则不亮
        displayIntoFactoryCircle.set(null != entity.getIntoFactoryAt());
        // 进厂时间：有进厂时间显示，否则gone
        intoFactoryLabel.set(null == entity.getIntoFactoryAt() ? null : DateUtil.formatTime(entity.getIntoFactoryAt(), "dd日HH:mm"));
        // 上钟圆圈：(>=上钟 && <洗车) || (>洗完) || (有上钟时间，否则不亮)
        displayWorkCircle.set(
                (entity.getStatus() >= ServiceOrderDetailEntity.STATUS_WORKING && entity.getStatus() < ServiceOrderDetailEntity.STATUS_WASHING_CAR)
                        || (entity.getStatus() > ServiceOrderDetailEntity.STATUS_WASHING_CAR_COMPLETE)
                        || (null != entity.getWorkedAt())
        );
        // 上钟时间：有上钟时间显示，否则不显示
        workAtLabel.set(null == serviceOrderDetailEntity.getWorkedAt() ? null : DateUtil.formatTime(serviceOrderDetailEntity.getWorkedAt(), "dd日HH:mm"));
        // 终检亮：（>=终检 && <洗车）|| (>洗完) || （>=洗车 && <=洗完 && 有终检通过时间)
        displayFinalInspectionCircle.set(
                (entity.getStatus() >= ServiceOrderDetailEntity.STATUS_FINAL_INSPECTION && entity.getStatus() < ServiceOrderDetailEntity.STATUS_WASHING_CAR)
                        || (entity.getStatus() > ServiceOrderDetailEntity.STATUS_WASHING_CAR_COMPLETE)
                        || (entity.getStatus() >= ServiceOrderDetailEntity.STATUS_WASHING_CAR && entity.getStatus() <= ServiceOrderDetailEntity.STATUS_WASHING_CAR_COMPLETE && (null != entity.getFinalInspectionCompletedAt()))
        );
        // 终检时间：（>=终检 && <终检完 && 有终检时间）
        // 终检完成文字：(>=终检完 && <洗车) || (>洗车完) || (>=洗车 && <=洗车完 && 有终检完成时间)
        if (entity.getStatus() >= ServiceOrderDetailEntity.STATUS_FINAL_INSPECTION && entity.getStatus() < ServiceOrderDetailEntity.STATUS_FINAL_INSPECTION_COMPLETE && (null != entity.getFinalInspectionAt())) {
            finalInspectionLabel.set(DateUtil.formatTime(entity.getFinalInspectionAt(), "dd日HH:mm"));
        } else if ((entity.getStatus() >= ServiceOrderDetailEntity.STATUS_FINAL_INSPECTION_COMPLETE && entity.getStatus() < ServiceOrderDetailEntity.STATUS_WASHING_CAR)
                || (entity.getStatus() > ServiceOrderDetailEntity.STATUS_WASHING_CAR_COMPLETE)
                || (entity.getStatus() >= ServiceOrderDetailEntity.STATUS_WASHING_CAR && entity.getStatus() <= ServiceOrderDetailEntity.STATUS_WASHING_CAR_COMPLETE && (null != entity.getFinalInspectionCompletedAt()))) {
            finalInspectionLabel.set("已完成");
        } else {
            finalInspectionLabel.set(null);
        }
        // 出厂圆圈：(>=出厂)
        displayOutFactoryCircle.set(entity.getStatus() >= ServiceOrderDetailEntity.STATUS_OUT_FACTORY);
        // 出厂时间：(>=出厂 && 有出厂时间)
        outFactoryLabel.set((entity.getStatus() >= ServiceOrderDetailEntity.STATUS_OUT_FACTORY && (null != entity.getOutFactoryAt())) ? DateUtil.formatTime(entity.getOutFactoryAt(), "dd日HH:mm") : null);

        // =================================详情=======================================
        ServiceTypesEntity serviceTypeInDealer = null;
        if (null != entity.getDealer() && null != entity.getDealer().getServiceTypes() && entity.getDealer().getServiceTypes().size() > 0) {
            serviceTypeInDealer = entity.getDealer().getServiceTypes().get(0);
        }

        // 服务详情模块：服务类型个数>0 显示 (只有AB保)
        displayOrderDetail.set(null != serviceTypeInDealer);
        // 参与了超时赔付：服务项目，否则（如果服务类型不为空） 服务类型
        bigTitle.set(null != entity.getOvertimeCompensate() ? "服务项目" : (null != serviceTypeInDealer ? serviceTypeInDealer.getName() : ""));

        // 参与了超时赔付 且 有增项
        if (null == entity.getOvertimeCompensate() || null == entity.getOvertimeCompensate().getAddItems() || entity.getOvertimeCompensate().getAddItems().size() <= 0) {
            displayAddItems.set(false);
        } else {
            List<AddItemsEntity> addItems = entity.getOvertimeCompensate().getAddItems();
            displayAddItems.set(true);
            addItemCount.set("(" + addItems.size() + "项)");
            int totalMinute = 0;
            for (AddItemsEntity add : addItems) {
                totalMinute += add.getSpend();
            }
            addItemTime.set("(" + totalMinute + "分钟)");

            addCheckItems.clear();
            for (int i = 0; i < addItems.size(); i++) {
                addCheckItems.add(new ItemScheduleDetailCheckItemVM(i, String.format("%s.%s", NumFormatUtils.formatNumber(i + 1), addItems.get(i).getName())));
            }
        }

        // 超时显示基础保养，否则全面检查项目
        basicTitle.set(null != entity.getOvertimeCompensate() ? getBasicTypeName(serviceTypeInDealer.getId()) : "全面检查项目");
        if (null != serviceTypeInDealer && null != serviceTypeInDealer.getCheckItems()) {
            basicItemCount.set("(" + serviceTypeInDealer.getCheckItems().size() + "项)");
            basicItemTime.set(null == entity.getOvertimeCompensate() ? "" : "(" + entity.getOvertimeCompensate().getBasicItemsSpend() + "分钟)");
            baseCheckItems.clear();
            for (int i = 0; i < serviceTypeInDealer.getCheckItems().size(); i++) {
                baseCheckItems.add(new ItemScheduleDetailCheckItemVM(i, serviceTypeInDealer.getCheckItems().get(i)));
            }
        }

        if (null != serviceTypeInDealer && null != serviceTypeInDealer.getMaterialItems()) {
            materialItems.clear();
            for (int i = 0; i < serviceTypeInDealer.getMaterialItems().size(); i++) {
                materialItems.add(new ItemScheduleDetailMaterialsVM(serviceTypeInDealer.getMaterialItems().get(i)));
            }
        }
    }

    /**
     * 获取到预计开始施工的时间
     */
    private String getEstimatedStartTime(ServiceOrderDetailEntity serviceOrderDetailEntity) {
        int estimatedTime = serviceOrderDetailEntity.getEstimatedWaitingTime() * 1000;
        String intoFactoryAt = serviceOrderDetailEntity.getIntoFactoryAt();
        long intoFactoryTimeStamp = DateUtil.dateToTimestamp(intoFactoryAt);
        long timestamp = (long) estimatedTime + intoFactoryTimeStamp;
        return DateUtil.formatTime(DateUtil.timestampToDate(timestamp), "MM月dd日 HH:mm");
    }

    private String getBasicTypeName(String typeId) {
        if (serviceTypeAId.equals(typeId)) {
            return "基础A保养";
        } else if (serviceTypeBId.equals(typeId)) {
            return "基础B保养";
        }

        return "无";
    }

    public void attemptGetServiceOrderDetail() {
        GetServiceOrderDetailTask getServiceOrderDetailTask = new GetServiceOrderDetailTask(this);
        getServiceOrderDetailTask.getServiceOrderDetail(serviceOrderId, new Callback<ServiceOrderDetailEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isRefreshing.set(true);
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(ServiceOrderDetailEntity entity) {
                super.onDataSuccess(entity);
                serviceOrderDetailEntity = entity;
                morphServiceOrderDetail(entity);
                scheduleDetailV.whetherShowGuideWindow();
                guessOvertimeCompensate();
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
                isRefreshing.set(false);
            }
        });
    }

    public void guessOvertimeCompensate() {
        if (null != serviceOrderDetailEntity
                && null != serviceOrderDetailEntity.getOvertimeCompensate()
                && serviceOrderDetailEntity.getStatus() < ServiceOrderDetailEntity.STATUS_OUT_FACTORY) {
            attemptRefreshOvertimeCompensate();
        }
    }

    public void attemptRefreshOvertimeCompensate() {
        GetOvertimeCompensatePreCalculationTask getOvertimeCompensatePreCalculationTask = new GetOvertimeCompensatePreCalculationTask(this);
        getOvertimeCompensatePreCalculationTask.getOvertimeCompensatePreCalculation(serviceOrderId, new Callback<GetOvertimeCompensatePreCalculationModel>() {
            @Override
            public void onDataSuccess(GetOvertimeCompensatePreCalculationModel getOvertimeCompensatePreCalculationModel) {
                super.onDataSuccess(getOvertimeCompensatePreCalculationModel);
                if (getOvertimeCompensatePreCalculationModel.getOvertime() > 0) {
                    displayOvertimeCompensate.set(true);
                    overtime.set(getOvertimeCompensatePreCalculationModel.getOvertime());
                    compensate.set(String.valueOf(getOvertimeCompensatePreCalculationModel.getCompensateFee() / 100));
                    displayEstimate.set(false);
                }
            }
        });
    }
}
