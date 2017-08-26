package cn.icarowner.icarowner.event;

/**
 * UnLoginEvent
 * create by 崔婧
 * create at 2017/5/18 下午1:25
 */
public class UnLoginEvent {
    private int type;

    public UnLoginEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
