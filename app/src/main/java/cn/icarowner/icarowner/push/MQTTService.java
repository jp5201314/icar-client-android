package cn.icarowner.icarowner.push;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.entity.UserEntity;

/**
 * MQTTService
 * create by 崔婧
 * create at 2017/5/18 下午1:39
 */
public class MQTTService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;
    public static final String TAG = MQTTService.class.getSimpleName();

    private static MqttAndroidClient client;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("开启mqtt服务");
        //API < 18 ，此方法能有效隐藏Notification上的图标
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        if (null == client) {
            newConnect(Constant.getPushHost(), "1883", "icarowner", "icarowner", this.getAlias());
        } else {
            String alias = this.getAlias();
            if (!this.equal(client.getClientId(), alias)) {
                closeClient();

                newConnect(Constant.getPushHost(), "1883", "icarowner", "icarowner", this.getAlias());
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private String getAlias() {
        //return "aaaaa";
        UserEntity userEntity = UserSharedPreference.getInstance().getUser();
        if (null == userEntity) {
            return null;
        }

        return "alias/" + userEntity.getId();
    }

    private boolean equal(String clientAlias, String alias) {
        if (null == clientAlias && null == alias) {
            return true;
        }

        if (null == clientAlias) {
            return false;
        }

        if (null == alias) {
            return false;
        }

        return clientAlias.equals(alias);
    }

    /**
     * 新建连接
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param alias
     */
    private void newConnect(String host, String port, String username, String password, String alias) {
        client = new MqttAndroidClient(this, String.format("tcp://%s:%s", host, port), alias);

        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        doClientConnection(this.getConOpt(username, password));
    }

    private MqttConnectOptions getConOpt(String username, String password) {
        MqttConnectOptions conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(false);
        // 设置超时时间，单位：秒
        // conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        // conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(username);
        // 密码
        conOpt.setPassword(password.toCharArray());

        return conOpt;
    }

    private void doClientConnection(MqttConnectOptions conOpt) {
        try {
            client.connect(conOpt, null, iMqttActionListener);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void closeClient() {
        if (null != client) {
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //client.close();
        }
    }

    @Override
    public void onDestroy() {
        // 需要关的时候才关
        closeClient();
        super.onDestroy();
    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Logger.d("mqtt连接成功");
            String alias = getAlias();
            if (null != alias) {
                try {
                    client.subscribe(alias, 2, null, new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Logger.d("mqtt订阅成功");
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Logger.d("mqtt订阅失败");
                            exception.printStackTrace();
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            Logger.d("mqtt连接失败");
            arg1.printStackTrace();
            // 连接失败，不需要手动重连
        }
    };

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Logger.d("接收到推送消息");
            String msg = new String(message.getPayload());
            Logger.d(msg);
            Intent intent = new Intent("mqtt.icarowner.android.PUSH_MESSAGE_RECEIVED_ACTION");
            intent.putExtra("MQTT_TOPIC", topic);
            intent.putExtra("MQTT_MESSAGE", new String(message.getPayload()));
            sendBroadcast(intent);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            Logger.d("消息发送完成");
        }

        @Override
        public void connectionLost(Throwable arg0) {
            Logger.d("mqtt失去连接");
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }


}
