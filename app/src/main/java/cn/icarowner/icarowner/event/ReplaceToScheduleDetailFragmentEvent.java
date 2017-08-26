package cn.icarowner.icarowner.event;

/**
 * ReplaceToScheduleDetailFragmentEvent
 * create by 崔婧
 * create at 2017/5/18 下午1:25
 */
public class ReplaceToScheduleDetailFragmentEvent {
    private String orderId;

    public ReplaceToScheduleDetailFragmentEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
