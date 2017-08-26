package cn.icarowner.icarowner.activity.viewmodel;

import android.view.View;

/**
 * AppBarServiceVM
 * create by 崔婧
 * create at 2017/5/18 上午11:46
 */
public class AppBarServiceVM extends BaseVM {
    public ContentServiceVM contentServiceVM;

    public AppBarServiceVM() {
        this.initContentServiceVM();
    }

    private void initContentServiceVM() {
        this.contentServiceVM = new ContentServiceVM() {
            @Override
            public void onBannerClick(View view) {
                super.onBannerClick(view);
                AppBarServiceVM.this.onBannerClick(view);
            }

            @Override
            public void onScheduleClick(View view) {
                super.onScheduleClick(view);
                AppBarServiceVM.this.onScheduleClick(view);
            }

            @Override
            public void onPayClick(View view) {
                super.onPayClick(view);
                AppBarServiceVM.this.onPayClick(view);
            }

            @Override
            public void onEvaluateClick(View view) {
                super.onEvaluateClick(view);
                AppBarServiceVM.this.onEvaluateClick(view);
            }

            @Override
            public void onHistoryClick(View view) {
                super.onHistoryClick(view);
                AppBarServiceVM.this.onHistoryClick(view);
            }

            @Override
            public void onOneKeyHelpClick(View view) {
                super.onOneKeyHelpClick(view);
                AppBarServiceVM.this.onOneKeyHelpClick(view);
            }

            @Override
            public void onBannerItemClick(int position) {
                super.onBannerItemClick(position);
                AppBarServiceVM.this.onBannerItemClick(position);
            }
        };
    }

    /**
     * OnOpenDrawer click listener
     *
     * @param view
     */
    public void onOpenOrCloseDrawerClick(View view) {
    }

    /**
     * OnBanner click listener on ContentServiceVM
     */
    public void onBannerClick(View view) {
    }

    /**
     * OnScheduleButton click listener on ContentServiceVM
     */
    public void onScheduleClick(View view) {
    }

    /**
     * OnPayButton click listener on ContentServiceVM
     */
    public void onPayClick(View view) {
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onEvaluateClick(View view) {
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onHistoryClick(View view) {
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onOneKeyHelpClick(View view) {
    }

    public void onBannerItemClick(int position) {

    }
}