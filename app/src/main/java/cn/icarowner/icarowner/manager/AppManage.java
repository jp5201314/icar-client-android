package cn.icarowner.icarowner.manager;

import android.content.Context;

import java.util.Stack;

import cn.icarowner.icarowner.baseinterface.IActivityFinish;

/**
 * AppManage
 * create by 崔婧
 * create at 2017/5/18 下午1:37
 */
public class AppManage {

    private static final AppManage instance = new AppManage();

    public static AppManage getInstance() {
        synchronized (instance) {
            return instance;
        }
    }

    private Stack<IActivityFinish> activities;

    private AppManage() {
        activities = new Stack<IActivityFinish>();
    }

    public void addActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }


    public void removeActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    public void finishOther() {
        IActivityFinish activity = getCurrentActivity();
        while (!activities.isEmpty()) {
            IActivityFinish act = activities.pop();
            if (act != activity && act != null) {
                act.finishActivity();
            }
        }
        addActivity(activity);
    }

    public void finishActivity(IActivityFinish activity) {
        if (activity != null) {
            activities.remove(activity);
        }
        activity.finishActivity();
    }

    public IActivityFinish getCurrentActivity() {
        if (activities.size() > 0) {
            return activities.lastElement();
        } else {
            return null;
        }
    }


    @SuppressWarnings("deprecation")
    public void exit(Context context) {
        clearEverything();

        System.exit(0);
    }

    public void clearEverything() {
        while (!activities.isEmpty()) {
            IActivityFinish act = activities.pop();
            try {
                if (act != null) {
                    act.finishActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isAppAlive() {
        AppManage appManage = getInstance();
        if (null != appManage.activities && appManage.activities.size() > 0) {
            return true;
        }

        return false;
    }

//    /**
//     * 判断应用是否已经启动
//     * @param context 一个context
//     * @param packageName 要判断应用的包名
//     * @return boolean
//     */
//    public static boolean isAppAlive(Context context, String packageName){
//        ActivityManager activityManager =
//                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> processInfos
//                = activityManager.getRunningAppProcesses();
//        for(int i = 0; i < processInfos.size(); i++){
//            if(processInfos.get(i).processName.equals(packageName)){
//                Log.i("NotificationLaunch",
//                        String.format("the %s is running, isAppAlive return true", packageName));
//                return true;
//            }
//        }
//        Log.i("NotificationLaunch",
//                String.format("the %s is not running, isAppAlive return false", packageName));
//        return false;
//    }
}
