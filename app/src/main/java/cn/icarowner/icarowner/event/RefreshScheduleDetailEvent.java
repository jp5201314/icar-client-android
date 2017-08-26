package cn.icarowner.icarowner.event;

/**
 * RefreshScheduleDetailEvent
 * create by 崔婧
 * create at 2017/5/18 下午1:24
 */
public class RefreshScheduleDetailEvent {
    private String serviceOrderId;

    public RefreshScheduleDetailEvent(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public String getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(String serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }
}
