package cn.icarowner.icarowner.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import cn.icarowner.icarowner.event.RefreshBalanceEvent;
import cn.icarowner.icarowner.event.RefreshScheduleDetailEvent;
import cn.icarowner.icarowner.event.RefreshServiceActivityEvent;

/**
 * MQTTPushReceiver 推送消息接受者
 * create by 崔婧
 * create at 2017/5/18 下午1:38
 */
public class MQTTPushReceiver extends BroadcastReceiver {

    private String title;
    private String content;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("mqtt.icarowner.android.PUSH_MESSAGE_RECEIVED_ACTION".equals(intent.getAction())) {
            String topic = intent.getStringExtra("MQTT_TOPIC");
            String msg = intent.getStringExtra("MQTT_MESSAGE");
            //解析message
            JSONObject msgObj = JSONObject.parseObject(msg);
            if (msgObj.containsKey("alert") && !msgObj.containsKey("extra")) {
                JSONObject alert = (JSONObject) msgObj.get("alert");
                if (!alert.isEmpty()) {
                    if (alert.containsKey("title") && alert.containsKey("body")) {
                        String body = alert.getString("body");
                        if (!TextUtils.isEmpty(body)) {
                            title = alert.getString("title");
                            content = alert.getString("body");
                            MQTTPushUtil.showNotification(context, title, content);
                            //MQTTPushUtil.showToast(content, context);
                        }
                    }
                }
            }
            if (msgObj.containsKey("alert") && msgObj.containsKey("extra")) {
                JSONObject alert = (JSONObject) msgObj.get("alert");
                JSONObject extra = (JSONObject) msgObj.get("extra");
                if (!alert.isEmpty()) {
                    if (alert.containsKey("title") && alert.containsKey("body")) {
                        String body = alert.getString("body");
                        if (!TextUtils.isEmpty(body)) {
                            title = alert.getString("title");
                            content = alert.getString("body");
                            MQTTPushUtil.showNotification(context, title, content, extra);
                            //MQTTPushUtil.showToast(content, context);
                        }
                    }
                }
            }
            if (msgObj.containsKey("extra")) {
                JSONObject extra = (JSONObject) msgObj.get("extra");
                String timestamp = extra.getString("timestamp");
                String messageId = extra.getString("message_id");

                  /*服务单取消*/
                if (extra.containsKey("service_order_canceled")) {
                    JSONObject serviceOrderCanceled = extra.getJSONObject("service_order_canceled");
                    EventBus.getDefault().post(new RefreshServiceActivityEvent());
                }

                /*服务单状态改变*/
                if (extra.containsKey("service_order_status_movement")) {
                    JSONObject serviceOrderStatusMovement = extra.getJSONObject("service_order_status_movement");
                    String orderId = serviceOrderStatusMovement.getString("order_id");
                    int status = serviceOrderStatusMovement.getInteger("status");
                    EventBus.getDefault().post(new RefreshServiceActivityEvent());
                    EventBus.getDefault().post(new RefreshBalanceEvent());
                    EventBus.getDefault().post(new RefreshScheduleDetailEvent(orderId));
                }

                /*优惠券数量改变*/
                if (extra.containsKey("coupon_count_changed")) {
                    JSONObject couponCountChanged = extra.getJSONObject("coupon_count_changed");
                    EventBus.getDefault().post(new RefreshServiceActivityEvent());
                }

                /*用户余额变动*/
                if (extra.containsKey("user_balance_changed")) {
                    JSONObject userBalanceChanged = extra.getJSONObject("user_balance_changed");
                    EventBus.getDefault().post(new RefreshBalanceEvent());
                }
            }

        }
    }
}
