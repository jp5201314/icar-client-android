package cn.icarowner.icarowner.event;

/**
 * ReplaceToBillDetailFragmentEvent
 * create by 崔婧
 * create at 2017/5/18 下午1:24
 */
public class ReplaceToBillDetailFragmentEvent {
    private String orderId;

    public ReplaceToBillDetailFragmentEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
