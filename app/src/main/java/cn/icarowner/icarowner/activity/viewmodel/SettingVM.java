package cn.icarowner.icarowner.activity.viewmodel;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;

import cn.icarowner.icarowner.ICarApplication;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.SettingV;
import cn.icarowner.icarowner.entity.AppVersionEntity;
import cn.icarowner.icarowner.event.SignOutRefreshEvent;
import cn.icarowner.icarowner.httptask.GetLatestAppVersionTask;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * SettingVM
 * create by 崔婧
 * create at 2017/5/18 下午12:00
 */
public class SettingVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<Integer> versionCode = new ObservableField<>();
    public final ObservableField<String> versionName = new ObservableField<>();
    public final ObservableField<Integer> latestVersionCode = new ObservableField<>();

    public ToolBarTitleVM toolBarTitleVM;

    private SettingV settingV;

    private AppVersionEntity appVersionEntity;

    public SettingVM(SettingV settingV) {
        this.settingV = settingV;
        this.initToolBarVM();

        this.checkVersion();
        this.attemptGetLatestAppVersion();
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("设置") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                SettingVM.this.settingV.closePage();
            }
        };
    }

    public void onUpdateVersionClick(View view) {
        if (!needUpdate()) {
            toastMsg.set("您当前已是最新版本，无需更新");
            return;
        }

        settingV.downloadApk(appVersionEntity.getDownloadUrl());
    }

    public void onFeedbackClick(View view) {
        settingV.jumpToFeedbackPage();
    }

    public void onAboutUsClick(View view) {
        settingV.jumpToAboutUsPage();
    }

    public void onExitLoginClick(View view) {
        UserSharedPreference.getInstance().removeJwtToken();
        UserSharedPreference.getInstance().removeUser();
        ICarApplication.startMQTTService();
        EventBus.getDefault().post(new SignOutRefreshEvent());
        toastMsg.set("您已退出登录");
        settingV.closePage();
    }

    /**
     * 检测当前系统已安装版本
     */
    private void checkVersion() {
        try {
            PackageManager manager = settingV.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(settingV.getContext().getPackageName(), 0);
            versionCode.set(info.versionCode);
            versionName.set(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取最新版
     */
    public void attemptGetLatestAppVersion() {
        GetLatestAppVersionTask getLatestAppVersionTask = new GetLatestAppVersionTask(this);
        getLatestAppVersionTask.getLatestVersion(new Callback<AppVersionEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(AppVersionEntity entity) {
                super.onDataSuccess(entity);
                appVersionEntity = entity;
                latestVersionCode.set(entity.getCode());
            }

            @Override
            public void onDataError(int status, JSONObject statusInfo) {
                super.onDataError(status, statusInfo);
                toastMsg.set(statusInfo.getString("message"));
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }

    /**
     * 是否需要更新
     *
     * @return
     */
    private boolean needUpdate() {
        if (null == latestVersionCode) {
            toastMsg.set("无法获取最新版本");
            return false;
        }

        if (latestVersionCode.get() > versionCode.get()) {
            return true;
        } else {
            return false;
        }
    }
}