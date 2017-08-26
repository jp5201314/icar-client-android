package cn.icarowner.icarowner.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;

import com.orhanobut.logger.Logger;
import com.tendcloud.tenddata.TCAgent;

import java.io.File;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.SettingV;
import cn.icarowner.icarowner.activity.viewmodel.SettingVM;
import cn.icarowner.icarowner.databinding.ActivitySettingBinding;

/**
 * SettingActivity 设置
 * create by 崔婧
 * create at 2017/5/18 下午1:05
 */
public class SettingActivity extends BaseActivity implements SettingV {

    private BroadcastReceiver checkVersionReceiver;
    private BroadcastReceiver downloadManagerReceiver;

    private long downloadId = -1;
    private ProgressDialog progressDialog;
    private boolean onRequest = false;

    private ActivitySettingBinding binding;
    private SettingVM settingVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "设置");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        settingVM = new SettingVM(this);
        binding.setSetting(settingVM);
        this.setViewModel(settingVM);
        this.setObservers();

        this.registerCheckVersionReceiver();
        this.registerDownLoadManagerReceiver();

        Intent intent = getIntent();
        if (intent.hasExtra("downloadUrl")) {
            downloadApk(intent.getStringExtra("downloadUrl"));
        } else {
            settingVM.attemptGetLatestAppVersion();
        }
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        settingVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (settingVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        settingVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(settingVM.toastMsg.get());
                settingVM.toastMsg.set(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (downloadId != -1) {
            closeDownload(downloadId);
            downloadId = -1;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "设置");
        if (null != downloadManagerReceiver) {
            unregisterReceiver(downloadManagerReceiver);
        }
        if (null != checkVersionReceiver) {
            unregisterReceiver(checkVersionReceiver);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void jumpToFeedbackPage() {
        startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
    }

    @Override
    public void jumpToAboutUsPage() {
        startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
    }

    /**
     * 下载安装包
     *
     * @param downloadUrl 版本更新地址
     */
    @Override
    public void downloadApk(String downloadUrl) {
        openDownload(downloadUrl);
        progressDialog = new ProgressDialog(SettingActivity.this);
        progressDialog.setTitle("正在下载...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        DownloadObserver mDownloadObserver = new DownloadObserver(mHandler, SettingActivity.this, downloadId);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadObserver);
    }

    /**
     * 获取下载管理器
     *
     * @return
     */
    private DownloadManager getManager() {
        return (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 打开下载
     *
     * @param downloadUrl 版本更新地址
     * @return
     */
    private long openDownload(String downloadUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setDescription("安装包正在下载中");
        request.setTitle("我是车主");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath() + "/icar.apk");
        Logger.d(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        if (file.exists()) {
            Logger.d("文件已经存在");
            file.delete();
            Logger.d("旧文件已删除");
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "icar.apk");
        request.setMimeType("application/vnd.android.package-archive");
        downloadId = getManager().enqueue(request);
        return downloadId;
    }

    /**
     * 移除下载
     *
     * @param downloadId
     */
    private void closeDownload(long downloadId) {
        getManager().remove(downloadId);
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setProgress(msg.what);
                }
            });
        }
    };

    /**
     * 注册下载完成的广播接收者
     */
    private void registerDownLoadManagerReceiver() {
        downloadManagerReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent service) {
                long id = service.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == -1) {
                    return;
                }
                if (id != downloadId) {
                    return;
                }
                downloadId = -1;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Intent intent = new Intent();
                /**
                 * 6.0 ,7.0无法安装
                 * intents.setDataAndType(uri, "application/vnd.android.package-archive");
                 * 以前这个uri是通过DownloadManager里面的getUriForDownloadedFile(longid)获取的！
                 * 而现在上面的这行代码没有获取uri对应的文件的真实路径，
                 * 没能成功解析这个uri（具体原因，看源码！）
                 * 所以直接找到下载的安装包获取文件的Uri
                 *
                 */
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/icar.apk");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uriForDownloadedFile = FileProvider.getUriForFile(context, "cn.icarowner.icarowner.fileprovider", file);
                    intent.setDataAndType(uriForDownloadedFile, "application/vnd.android.package-archive");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            }
        };
        //注册下载完成的广播
        SettingActivity.this.registerReceiver(
                downloadManagerReceiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    /**
     * 注册网络状态改变的广播接收者
     */
    private void registerCheckVersionReceiver() {
        checkVersionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent service) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mobNetInfo == null) {
                    if (wifiNetInfo.isConnected()) {
                        if (!onRequest) {
                            settingVM.attemptGetLatestAppVersion();
                        }
                    }
                } else {
                    if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                    } else {
                        if (!onRequest) {
                            settingVM.attemptGetLatestAppVersion();
                        }
                    }
                }
            }
        };
        SettingActivity.this.registerReceiver(
                checkVersionReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private class DownloadObserver extends ContentObserver {
        private Handler mHandler;
        private Context mContext;
        private int progress;
        private DownloadManager mDownloadManager;
        private DownloadManager.Query query;
        private Cursor cursor;

        DownloadObserver(Handler handler, Context context, long downId) {
            super(handler);
            Logger.d(downId + "");
            this.mHandler = handler;
            this.mContext = context;
            mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            query = new DownloadManager.Query().setFilterById(downId);
        }

        @Override
        public void onChange(boolean selfChange) {
            Logger.d(downloadId + ".....");
            // 每当/data/data/com.android.providers.download/database/database.db变化后，触发onCHANGE，开始具体查询
            super.onChange(selfChange);
            cursor = mDownloadManager.query(query);
            if (!cursor.moveToFirst()) return;
            //Logger.d(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) + "");
            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING) {
                if (null != progressDialog && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
                int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                progress = ((bytesDownloaded * 100) / bytesTotal);
                mHandler.sendEmptyMessageDelayed(progress, 100);
            }
            cursor.close();
        }
    }
}
