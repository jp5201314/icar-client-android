package cn.icarowner.icarowner.activity;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.viewmodel.BaseVM;
import cn.icarowner.icarowner.baseinterface.IActivityFinish;
import cn.icarowner.icarowner.baseinterface.IComponentContainer;
import cn.icarowner.icarowner.baseinterface.ILifeCycleComponent;
import cn.icarowner.icarowner.customizeview.RefreshHeaderView;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.manager.AppManage;
import cn.icarowner.icarowner.manager.LifeCycleComponentManager;
import cn.icarowner.icarowner.manager.PhoneManager;
import cn.icarowner.icarowner.manager.SystemBarTintManager;
import cn.xiaomeng.httpdog.HttpCycleContext;
import cn.xiaomeng.httpdog.HttpTaskHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * BaseActivity
 * create by 崔婧
 * create at 2017/5/18 下午12:03
 */
public class BaseActivity extends AppCompatActivity implements IComponentContainer, IActivityFinish, HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();
    protected NormalDialog noticeDialog;
    protected ACProgressFlower waitingDialog;
    private BaseVM baseVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManage.getInstance().addActivity(this);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void finish() {
        AppManage.getInstance().finishActivity(this);
    }

    @Override
    public void finishActivity() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mComponentContainer.onBecomesPartiallyInvisible();
    }

    @Override
    public void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpTaskHandler.getInstance().removeTask(getHttpTaskKey());
        if (null != this.baseVM) this.baseVM.closeHttpTask();
        mComponentContainer.onDestroy();
    }

    public void setViewModel(BaseVM baseVM) {
        this.baseVM = baseVM;
    }

    /**
     * 开始执行contentView动画
     */
    protected void startContentViewAnimation(View contentView, AnimatorListenerAdapter onAnimationEnd) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(contentView, "alpha", 1),
                ObjectAnimator.ofFloat(contentView, "translationY", 0)
        );
        set.setDuration(400).start();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(onAnimationEnd);
    }

    /**
     * toast message
     *
     * @param text
     */
    protected void toast(String text) {
        if (null == text) return;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast message
     *
     * @param resource
     */
    protected void toast(int resource) {
        Toast.makeText(this, resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addComponent(ILifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    /**
     * Override this function when you need control whether you will cancel OkHttpFinal after Destroy
     *
     * @return boolean
     */
    protected boolean cancelOkHttpFinalAfterDestory() {
        return true;
    }

    protected void showNoticeDialog(String content) {
        showNoticeDialog(getString(R.string.kindly_reminder), content, getString(R.string.sure), null);
    }

    protected void showNoticeDialog(String titleNotice, String content) {
        showNoticeDialog(titleNotice, content, getString(R.string.sure), null);
    }

    protected void showNoticeDialog(String titleNotice, String content, String btnTxt, OnBtnClickL l) {
        noticeDialog = DialogCreater.createTipsDialog(this, titleNotice, content, btnTxt, false, l);
        noticeDialog.show();
    }

    protected void showWaitingDialog(boolean show) {
        showWaitingDialog(show, getString(R.string.please_wait));
    }

    protected void showWaitingDialog(boolean show, String waitingNotice) {
        if (null == waitingDialog) {
            waitingDialog = DialogCreater.createProgressDialog(BaseActivity.this, waitingNotice);
        }

        if (show) {
            waitingDialog.show();
        } else {
            waitingDialog.dismiss();
        }
    }

    /**
     * 从本地获取持久化的entity
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T getEntityFromLocalStorage(String storageKey, String key, Class<T> clazz) {
        SharedPreferences share = getSharedPreferences(storageKey, Context.MODE_PRIVATE);
        if (share.contains(key)) {
            String dealerJson = share.getString(key, null);
            return null == dealerJson ? null : JSON.parseObject(dealerJson, clazz);
        }
        return null;
    }

    /**
     * 将entity持久化存储到本地(异步,非及时)
     *
     * @param key
     * @param entity
     */
    protected void putEntityToLocalStorage(String storageKey, String key, Object entity) {
        putEntityToLocalStorage(storageKey, key, entity, false);
    }

    /**
     * 将entity持久化存储到本地(及时)
     *
     * @param key
     * @param entity
     */
    protected void putEntityToLocalStorageNow(String storageKey, String key, Object entity) {
        putEntityToLocalStorage(storageKey, key, entity, true);
    }

    private void putEntityToLocalStorage(String storageKey, String key, Object entity, boolean isNow) {
        SharedPreferences share = getSharedPreferences(storageKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        if (null == entity) {
            if (share.contains(key)) {
                editor.remove(key);
                return;
            }
            return;
        }
        editor.putString(key, JSON.toJSONString(entity));
        if (isNow) {
            editor.commit();
        } else {
            editor.apply();
        }
    }


    protected void setMaterialHeader(PtrClassicFrameLayout ptr) {
        RefreshHeaderView ptrHeader = new RefreshHeaderView(getApplicationContext());
        ptrHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        ptrHeader.setPtrFrameLayout(ptr);

        ptr.setLoadingMinTime(800);
        ptr.setDurationToCloseHeader(800);
        ptr.setHeaderView(ptrHeader);
        ptr.addPtrUIHandler(ptrHeader);
    }

    /**
     * 验证是否新版,如果新版,则跳转引导页
     *
     * @return
     */
    protected boolean validNewVersion() {
        int nowVersionCode = PhoneManager.getVersionInfo().versionCode;

        UserSharedPreference userSharedPreference = UserSharedPreference.getInstance();
        if (userSharedPreference.isNewVersionCode(nowVersionCode)) {
            startActivity(new Intent(BaseActivity.this, GuideActivity.class));
            userSharedPreference.setLatestVersionCode(nowVersionCode);
            return true;
        }
        return false;
    }

    protected String getRunningActivityName() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName(); //类名
        String className = info.topActivity.getClassName(); //完整类名
        String packageName = info.topActivity.getPackageName();//包名

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return shortClassName;
    }
}