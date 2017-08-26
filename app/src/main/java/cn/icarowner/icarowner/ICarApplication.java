package cn.icarowner.icarowner;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.icarowner.icarowner.activity.LoginActivity;
import cn.icarowner.icarowner.event.ErrorMessageEvent;
import cn.icarowner.icarowner.event.UnLoginEvent;
import cn.icarowner.icarowner.manager.ACache;
import cn.icarowner.icarowner.push.MQTTService;
import cn.xiaomeng.httpdog.HttpDog;
import cn.xiaomeng.httpdog.HttpDogConfiguration;

/**
 * ICarApplication
 * create by 崔婧
 * create at 2017/5/18 下午1:41
 */
public class ICarApplication extends Application {

    private static ICarApplication INSTANCE;
    private static Context mContext;

    public static synchronized ICarApplication getInstance() {
        return INSTANCE;
    }

    public static synchronized Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mContext = getApplicationContext();
        /*初始化logger*/
        Logger.init("Icarowner---Log").hideThreadInfo();
        /*初始化fresco*/
        Fresco.initialize(this);
        /*开启推送服务*/
        startMQTTService();
        /*初始化HttpDog*/
        ICarApplication.this.initHttpDog();
        /* 初始化talking data*/
        if (!BuildConfig.DEBUG) {
            TCAgent.LOG_ON = true;
            TCAgent.init(this);
            TCAgent.setReportUncaughtExceptions(true);
        }
        EventBus.getDefault().register(mContext);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(mContext);
    }


    public static void startMQTTService() {
        Intent intent = new Intent(getContext(), MQTTService.class);
        getContext().startService(intent);
    }

    /**
     * 初始化HttpDog
     */
    private void initHttpDog() {
        HttpDogConfiguration.Builder builder = new HttpDogConfiguration.Builder();
        builder.setDebug(true);
        // builder.setTimeout(30000);
        HttpDog.getInstance().init(builder.build());

        HttpDog.getInstance().updateCommonHeader("Accept", "application/json");
        String jwtToken = UserSharedPreference.getInstance().getJwtToken();
        // 如果有jwt token，则设置
        if (!TextUtils.isEmpty(jwtToken)) {
            HttpDog.getInstance().updateCommonHeader("Authorization", "Bearer " + jwtToken);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnLoginEvent(UnLoginEvent event) {
        switch (event.getType()) {
            case 40000://NOT_PROVIDED_JWT_TOKEN
                Toast.makeText(mContext, "您还未登录，请登录", Toast.LENGTH_SHORT).show();
                jumpToLogin();
                break;
            case 40001://INVALID_JWT_TOKEN
                Toast.makeText(mContext, "你的账号已在其他设备登录，请重新登录", Toast.LENGTH_SHORT).show();
                jumpToLogin();
                break;
            case 40004://NOT_FOUND_JWT_USER
                Toast.makeText(mContext, "用户信息已过期，请重新登录", Toast.LENGTH_SHORT).show();
                jumpToLogin();
                break;
            case 40005://EXPIRED_JWT_TOKEN
                Toast.makeText(mContext, "令牌已失效，请重新登录", Toast.LENGTH_SHORT).show();
                jumpToLogin();
                break;
            case 40006://EXPIRED_JWT_TOKEN
                Toast.makeText(mContext, "你的账号已在其他设备登录", Toast.LENGTH_SHORT).show();
                jumpToLogin();
                break;
            default:
                break;
        }
    }

    protected boolean allowJumpToLogin() {
        return null == ACache.get(mContext).getAsString("loging");
    }

    protected void jumpToLogin() {
        if (!allowJumpToLogin()) return;
        ACache.get(mContext).put("loging", true, 5);
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorMessageEvent(ErrorMessageEvent event) {
        Toast.makeText(mContext, event.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
