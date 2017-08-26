package cn.icarowner.icarowner.utils;

/**
 * ClickUtil
 * create by 崔婧
 * create at 2017/5/18 下午1:39
 */
public class ClickUtil {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
