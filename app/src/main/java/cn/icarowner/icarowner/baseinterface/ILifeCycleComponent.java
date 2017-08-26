package cn.icarowner.icarowner.baseinterface;

/**
 * ILifeCycleComponent
 * create by 崔婧
 * create at 2017/5/18 下午1:16
 */
public interface ILifeCycleComponent {

    /**
     * The UI becomes partially invisible.
     * like {@link android.app.Activity#onPause}
     */
    void onBecomesPartiallyInvisible();

    /**
     * The UI becomes visible from partially or totally invisible.
     * like {@link android.app.Activity#onResume}
     */
    void onBecomesVisible();

    /**
     * The UI becomes totally invisible.
     * like {@link android.app.Activity#onStop}
     */
    void onBecomesTotallyInvisible();

    /**
     * The UI becomes visible from totally invisible.
     * like {@link android.app.Activity#onRestart}
     */
    void onBecomesVisibleFromTotallyInvisible();

    /**
     * like {@link android.app.Activity#onDestroy}
     */
    void onDestroy();
}
