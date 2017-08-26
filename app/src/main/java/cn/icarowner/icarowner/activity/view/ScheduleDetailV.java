package cn.icarowner.icarowner.activity.view;

import cn.icarowner.icarowner.entity.OvertimeCompensateEntity;

/**
 * ScheduleDetailV
 * create by 崔婧
 * create at 2017/5/18 上午11:40
 */
public interface ScheduleDetailV extends BaseV {
    void replaceByScheduleListFragment();

    void jumpToFindManagerPage(String orderId, String estimateTimeToTakingCar, String actOutFactoryAt, OvertimeCompensateEntity overtimeCompensate);

    void whetherShowGuideWindow();
}
