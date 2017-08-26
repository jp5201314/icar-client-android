package cn.icarowner.icarowner.event;

/**
 * WeChatPayEvent 微信支付是否成功
 * create by 崔婧
 * create at 2017/5/18 下午1:25
 */
public class WeChatPayEvent {

    public int success;

    public WeChatPayEvent(int success) {
        this.success = success;
    }
}
