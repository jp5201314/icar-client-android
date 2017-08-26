package cn.icarowner.icarowner.activity.viewmodel;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.view.View;

import cn.icarowner.icarowner.activity.view.AboutUsV;

/**
 * AboutUsVM
 * create by 崔婧
 * create at 2017/5/18 上午11:45
 */
public class AboutUsVM extends BaseVM {
    public final ObservableField<Integer> versionCode = new ObservableField<>();
    public final ObservableField<String> versionName = new ObservableField<>();

    public ToolBarTitleVM toolBarTitleVM;

    private AboutUsV aboutUsV;

    public AboutUsVM(AboutUsV aboutUsV) {
        this.aboutUsV = aboutUsV;
        this.initToolBarVM();
        this.checkVersion();
    }

    private void initToolBarVM() {
        this.toolBarTitleVM = new ToolBarTitleVM("关于我们") {
            /**
             * On back click listener
             */
            public void onBackClick(View view) {
                AboutUsVM.this.aboutUsV.closePage();
            }
        };
    }

    private void checkVersion() {
        try {
            PackageManager manager = aboutUsV.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(aboutUsV.getContext().getPackageName(), 0);
            versionCode.set(info.versionCode);
            versionName.set(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}