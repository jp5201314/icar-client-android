package cn.icarowner.icarowner.push;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Random;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.RouteActivity;
import cn.icarowner.icarowner.activity.ScheduleActivity;

/**
 * MQTTPushUtil
 * create by 崔婧
 * create at 2017/5/18 下午1:39
 */
class MQTTPushUtil {

    private static String orderId;

    static boolean showNotification(Context context, String title, String content) {
        try {
            Uri alarmSound = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] pattern = {500, 500, 500};
            if (null == title) {
                title = "我是车主";
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat
                    .Builder(context)
                    .setSmallIcon(R.mipmap.icon_notification)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSound(alarmSound)
                    .setVibrate(pattern)
                    .setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId允许您稍后更新通知。
            Random r = new Random();
            mNotificationManager.notify(r.nextInt(), mBuilder.getNotification());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static boolean showNotification(Context context, String title, String content, JSONObject extra) {
        try {
            Uri alarmSound = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] pattern = {500, 500, 500};
            if (null == title) {
                title = "我是车主";
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat
                    .Builder(context)
                    .setSmallIcon(R.mipmap.icon_notification)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSound(alarmSound)
                    .setVibrate(pattern)
                    .setAutoCancel(true);
            Intent resultIntent = new Intent(context, RouteActivity.class);

            /*服务单状态改变*/
            if (extra.containsKey("service_order_status_movement")) {
                JSONObject serviceOrderStatusMovement = extra.getJSONObject("service_order_status_movement");
                orderId = serviceOrderStatusMovement.getString("order_id");
                resultIntent.putExtra("isFromNotification", true);
                resultIntent.putExtra("orderId", orderId);
                resultIntent.putExtra("dest_class", ScheduleActivity.class);
            }

             /*服务单预计取车时间改变*/
            if (extra.containsKey("service_order_estimated_time_of_taking_car_changed")) {
                JSONObject serviceOrderEstimatedTimeOfTakingCarChanged = extra.getJSONObject("service_order_estimated_time_of_taking_car_changed");
                orderId = serviceOrderEstimatedTimeOfTakingCarChanged.getString("order_id");
                resultIntent.putExtra("isFromNotification", true);
                resultIntent.putExtra("orderId", orderId);
                resultIntent.putExtra("dest_class", ScheduleActivity.class);
            }

                /*服务单超时赔付取消*/
            if (extra.containsKey("service_order_overtime_compensate_canceled")) {
                JSONObject serviceOrderOvertimeCompensateCanceled = extra.getJSONObject("service_order_overtime_compensate_canceled");
                orderId = serviceOrderOvertimeCompensateCanceled.getString("order_id");
                resultIntent.putExtra("isFromNotification", true);
                resultIntent.putExtra("orderId", orderId);
                resultIntent.putExtra("dest_class", ScheduleActivity.class);
            }

            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId允许您稍后更新通知。
            Random r = new Random();
            mNotificationManager.notify(r.nextInt(), mBuilder.getNotification());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static void showToast(final String toast, final Context context) {
        if (!isAppOnForeground(context)) return;
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    private static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //返回在设备上运行的应用程序进程的列表
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            //重要性：
            //系统放置的相对重要性级别
            //在这个过程中。
            //可能是IMPORTANCE_FOREGROUND，IMPORTANCE_VISIBLE之一，
            // IMPORTANCE_SERVICE，IMPORTANCE_BACKGROUND或IMPORTANCE_EMPTY。
            //这些常数被编号，使得“更重要”的值是
            //始终小于“不太重要”的值。
            // processName：
            //此对象关联的进程的名称。
            if (appProcess.processName.equals(context.getPackageName())
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
