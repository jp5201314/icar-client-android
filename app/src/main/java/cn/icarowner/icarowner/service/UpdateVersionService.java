package cn.icarowner.icarowner.service;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.SettingActivity;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.dialog.UpdateTipDialog;
import cn.icarowner.icarowner.entity.AppVersionEntity;

/**
 * UpdateVersionService 版本更新服务
 * create by 崔婧
 * create at 2017/5/18 下午1:39
 */
public class UpdateVersionService extends Service {

    private UpdateTipDialog updateTipDialog;
    private AppVersionEntity appVersionEntity;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appVersionEntity = (AppVersionEntity) intent.getSerializableExtra("app_version");
        String title = String.format("发现新版本V%1$s(%2$d)", appVersionEntity.getName(), appVersionEntity.getCode());
        UpdateTipDialog.Builder builder = createDialogBuilder(title, appVersionEntity.getDescribe(), appVersionEntity.getDownloadUrl());
        updateTipDialog = builder.create();
        updateTipDialog.getWindow().setType((WindowManager
                .LayoutParams.TYPE_SYSTEM_ALERT));
        if (!updateTipDialog.isShowing()) {
            updateTipDialog.show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private UpdateTipDialog.Builder createDialogBuilder(String title, String versionContent, final String downloadUrl) {
        return DialogCreater.createUpdateTipDialog(this,
                title,
                versionContent,
                "去升级",
                "取消",
                R.color.color_green_3bb4bc,
                R.color.color_gray_a5a5a6,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UpdateVersionService.this, SettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("downloadUrl", downloadUrl);
                        startActivity(intent);
                        updateTipDialog.dismiss();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateTipDialog.dismiss();
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
