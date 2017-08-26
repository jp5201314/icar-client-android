package cn.icarowner.icarowner.baseinterface;

/**
 * IFragment
 * create by 崔婧
 * create at 2017/5/18 下午1:16
 */
public interface IFragment {

    void onEnter(Object data);

    void onLeave();

    void onBack();

    void onBackWithData(Object data);

    /**
     * process the return back logic
     * return true if back pressed event has been processed and should stay in current view
     *
     * @return
     */
    boolean processBackPressed();
}