package cn.icarowner.icarowner.event;

/**
 * ErrorMessageEvent
 * create by 崔婧
 * create at 2017/5/18 下午1:23
 */
public class ErrorMessageEvent {
    private String msg;

    public ErrorMessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
