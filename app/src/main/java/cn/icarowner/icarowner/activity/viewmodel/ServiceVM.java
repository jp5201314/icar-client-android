package cn.icarowner.icarowner.activity.viewmodel;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;

import com.squareup.picasso.Picasso;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.ServiceV;
import cn.icarowner.icarowner.entity.AppVersionEntity;
import cn.icarowner.icarowner.entity.BannerEntity;
import cn.icarowner.icarowner.entity.MainActivityStatusEntity;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.httptask.GetBannersTask;
import cn.icarowner.icarowner.httptask.GetHomePageStatusTask;
import cn.icarowner.icarowner.httptask.GetLatestAppVersionTask;
import cn.icarowner.icarowner.httptask.GetUserInfoTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.manager.ACache;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ServiceVM
 * create by 崔婧
 * create at 2017/5/18 上午11:59
 */
public class ServiceVM extends BaseVM {
    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<Boolean> hasLogin = new ObservableField<>();
    public final ObservableField<String> headImgUrl = new ObservableField<>();
    public final ObservableField<String> nickName = new ObservableField<>();
    public final ObservableField<String> mobile = new ObservableField<>();
    public final ObservableField<Integer> myCouponCount = new ObservableField<>();
    public final ObservableField<Integer> balance = new ObservableField<>();

    public final ObservableField<Integer> versionCode = new ObservableField<>();
    public final ObservableField<String> versionName = new ObservableField<>();
    public final ObservableField<Integer> latestVersionCode = new ObservableField<>();

    public AppBarServiceVM appBarServiceVM;

    private ServiceV serviceV;
    private AppVersionEntity appVersionEntity;
    private MainActivityStatusEntity mainActivityStatusEntity;
    private UserEntity userEntity;

    public ServiceVM(ServiceV serviceV) {
        this.serviceV = serviceV;
        this.initAppBarServiceVM();

        this.setDefault();

        this.checkLogin();
        this.checkVersion();

        if (hasLogin.get()) appBarServiceVM.contentServiceVM.tip.set("");
        ACache.get(serviceV.getContext()).remove("un_toast_network_error");
        this.attemptGetLatestAppVersion();
    }

    public void setDefault() {
        this.appVersionEntity = null;
        this.mainActivityStatusEntity = null;
        this.userEntity = null;
        this.isLoading.set(false);
        this.hasLogin.set(false);
        this.headImgUrl.set(null);
        this.nickName.set(null);
        this.mobile.set(null);
        this.myCouponCount.set(0);
        this.balance.set(0);
    }

    private void initAppBarServiceVM() {
        this.appBarServiceVM = new AppBarServiceVM() {
            @Override
            public void onOpenOrCloseDrawerClick(View view) {
                super.onOpenOrCloseDrawerClick(view);
                ServiceVM.this.onOpenOrCloseDrawerClick(view);
            }

            @Override
            public void onBannerClick(View view) {
                super.onBannerClick(view);
                ServiceVM.this.onBannerClick(view);
            }

            @Override
            public void onScheduleClick(View view) {
                super.onScheduleClick(view);
                ServiceVM.this.onScheduleClick(view);
            }

            @Override
            public void onPayClick(View view) {
                super.onPayClick(view);
                ServiceVM.this.onPayClick(view);
            }

            @Override
            public void onEvaluateClick(View view) {
                super.onEvaluateClick(view);
                ServiceVM.this.onEvaluateClick(view);
            }

            @Override
            public void onHistoryClick(View view) {
                super.onHistoryClick(view);
                ServiceVM.this.onHistoryClick(view);
            }

            @Override
            public void onOneKeyHelpClick(View view) {
                super.onOneKeyHelpClick(view);
                ServiceVM.this.onOneKeyHelpClick(view);
            }

            @Override
            public void onBannerItemClick(int position) {
                super.onBannerItemClick(position);
                ServiceVM.this.onBannerItemClick(position);
            }
        };
    }

    @BindingAdapter("headImgSrc")
    public static void setHeadImgSrc(CircleImageView circleImageView, String headImgUrl) {
        Picasso.with(circleImageView.getContext())
                .load(headImgUrl)
                .placeholder(R.drawable.default_head_deep_gray)
                .error(R.drawable.default_head_deep_gray)
                .into(circleImageView);
    }

    private void morphMainButtonStatus(MainActivityStatusEntity entity) {
        myCouponCount.set(entity.getMyCouponsCount());
    }

    private void morphUserInfo(UserEntity entity) {
        headImgUrl.set(entity.getAvatarUrl());
        nickName.set(entity.getName());
        mobile.set(entity.getMobile());
        balance.set(entity.getBalance());
    }

    /**
     * 检测当前系统是否登录
     */
    public void checkLogin() {
        hasLogin.set(UserSharedPreference.getInstance().hasLogined());
    }

    /**
     * 检测当前系统已安装版本
     */
    private void checkVersion() {
        try {
            PackageManager manager = serviceV.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(serviceV.getContext().getPackageName(), 0);
            versionCode.set(info.versionCode);
            versionName.set(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onHeaderClick(View view) {
        if (ifJumpToLogin()) return;

        serviceV.jumpToPersonalPage();
    }

    public void onDealerUserListClick(View view) {
        if (ifJumpToLogin()) return;

        serviceV.jumpToDealerUsersListPage();
    }

    public void onCouponListClick(View view) {
        if (ifJumpToLogin()) return;

        serviceV.jumpToCouponListPage();
    }

    public void onBalanceClick(View view) {
        if (ifJumpToLogin()) return;

        serviceV.jumpToBalancePage();
    }

    public void onSettingClick(View view) {
        if (ifJumpToLogin()) return;

        serviceV.jumpToSettingPage();
    }

    /**
     * OnOpenDrawer click listener on AppBarServiceVM
     */
    public void onOpenOrCloseDrawerClick(View view) {
        serviceV.openOrCloseDrawer();
    }

    /**
     * OnBanner click listener on ContentServiceVM
     */
    public void onBannerClick(View view) {
        if (appBarServiceVM.contentServiceVM.banners.isEmpty()) {
            toastMsg.set("加载中, 请稍候");
        }
    }

    public void onBannerItemClick(int position) {
        if (position >= appBarServiceVM.contentServiceVM.banners.size()) {
            return;
        }

        BannerEntity.BannersBean banner = appBarServiceVM.contentServiceVM.banners.get(position);

        if (banner.getRedirectUrl().startsWith("http://") || banner.getRedirectUrl().startsWith("https://")) {
            serviceV.jumpToWebPage(banner.getTitle(), banner.getRedirectUrl());
        } else if (banner.getRedirectUrl().startsWith("icarowner://dealers")) {
            if (!banner.getRedirectUrl().endsWith("/coupons/extract/page")) {
                serviceV.jumpToDealerDetailPage(banner.getRedirectUrl().substring(20, banner.getRedirectUrl().indexOf("/page")), banner.getTitle());
            } else {
                if (!hasLogin.get()) {
                    serviceV.jumpToLoginPage();
                } else {
                    serviceV.jumpToReceiveCouponPageInDealer(banner.getRedirectUrl().substring(20, banner.getRedirectUrl().indexOf("/coupons/extract/page")));
                }
            }
        } else if (banner.getRedirectUrl().startsWith("icarowner://groups")) {
            if (!banner.getRedirectUrl().endsWith("/coupons/extract/page")) {
                serviceV.jumpToGroupDetailPage(banner.getRedirectUrl().substring(19, banner.getRedirectUrl().indexOf("/page")), banner.getTitle());
            } else {
                if (!hasLogin.get()) {
                    serviceV.jumpToLoginPage();
                } else {
                    serviceV.jumpToReceiveCouponPageInGroup(banner.getRedirectUrl().substring(19, banner.getRedirectUrl().indexOf("/coupons/extract/page")));
                }
            }
        } else if (banner.getRedirectUrl().equals("icarowner://evaluation/coupons/introduction")) {
            serviceV.jumpToCouponIntroductionPage();
        } else {
            toastMsg.set("暂时无法查看此内容，请更新版本");
        }
    }

    /**
     * OnScheduleButton click listener on ContentServiceVM
     */
    public void onScheduleClick(View view) {
        if (ifJumpToLogin()) return;
        serviceV.startButtonAnimation(view);
        serviceV.jumpToSchedulePage();
    }

    /**
     * OnPayButton click listener on ContentServiceVM
     */
    public void onPayClick(View view) {
        if (ifJumpToLogin()) return;
        serviceV.startButtonAnimation(view);
        serviceV.jumpToBillPage();
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onEvaluateClick(View view) {
        if (ifJumpToLogin()) return;
        serviceV.startButtonAnimation(view);
        serviceV.jumpToEvaluationListPage();
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onHistoryClick(View view) {
        if (ifJumpToLogin()) return;
        serviceV.startButtonAnimation(view);
        serviceV.jumpToServiceOrderListPage();
    }

    /**
     * Listener on ContentServiceVM
     */
    public void onOneKeyHelpClick(View view) {
        serviceV.showOneKeyHelpDialog(mainActivityStatusEntity.getDealer().getName(), mainActivityStatusEntity.getDealer().getRescuePhone());
    }

    private boolean ifJumpToLogin() {
        if (!hasLogin.get()) {
            serviceV.jumpToLoginPage();
            return true;
        }

        return false;
    }

    /**
     * 获取最新版
     */
    public void attemptGetLatestAppVersion() {
        GetLatestAppVersionTask getLatestAppVersionTask = new GetLatestAppVersionTask(this);
        getLatestAppVersionTask.getLatestVersion(new Callback<AppVersionEntity>() {
            @Override
            public void onDataSuccess(AppVersionEntity entity) {
                super.onDataSuccess(entity);
                appVersionEntity = entity;
                latestVersionCode.set(entity.getCode());
                if (latestVersionCode.get() > versionCode.get()) {
                    serviceV.jumpToUpdateVersionService(appVersionEntity);
                }
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                if (null == ACache.get(serviceV.getContext()).getAsString("un_toast_network_error")) {
                    ACache.get(serviceV.getContext()).put("un_toast_network_error", "", 60);
                    toastMsg.set(msg);
                }
            }
        });
    }

    /**
     * 第一次获取banners
     */
    public void attemptGetBannersIfNoneBanners() {
        if (appBarServiceVM.contentServiceVM.banners.isEmpty() && !isLoading.get()) {
            attemptGetBanners();
        }
    }

    /**
     * 获取banners
     */
    public void attemptGetBanners() {
        final GetBannersTask getBannersTask = new GetBannersTask(this);
        getBannersTask.getBanners(new Callback<BannerEntity>() {
            private boolean success;

            @Override
            public void onStart() {
                super.onStart();
                success = false;
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(BannerEntity entity) {
                super.onDataSuccess(entity);
                success = true;
                appBarServiceVM.contentServiceVM.banners.clear();
                appBarServiceVM.contentServiceVM.banners.addAll(entity.getBanners());
                serviceV.renderBanners(appBarServiceVM.contentServiceVM.banners);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                if (null == ACache.get(serviceV.getContext()).getAsString("un_toast_network_error")) {
                    ACache.get(serviceV.getContext()).put("un_toast_network_error", "", 60);
                    toastMsg.set(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (hasLogin.get() && success) {
                    attemptGetMainButtonStatus();
                } else {
                    isLoading.set(false);
                }
            }
        });
    }

    public void attemptGetMainButtonStatusIfNotLoding() {
        if (!isLoading.get() && hasLogin.get()) {
            attemptGetMainButtonStatus();
        }
    }

    /**
     * 获取主要按钮状态
     */
    public void attemptGetMainButtonStatus() {
        GetHomePageStatusTask getHomePageStatusTask = new GetHomePageStatusTask(this);
        getHomePageStatusTask.getStatus(new Callback<MainActivityStatusEntity>() {
            private boolean success;

            @Override
            public void onStart() {
                super.onStart();
                success = false;
                if (!isLoading.get()) isLoading.set(true);
            }

            @Override
            public void onDataSuccess(MainActivityStatusEntity entity) {
                super.onDataSuccess(entity);
                success = true;
                mainActivityStatusEntity = entity;
                appBarServiceVM.contentServiceVM.setMainActivityStatusEntity(entity);
                morphMainButtonStatus(mainActivityStatusEntity);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                if (null == ACache.get(serviceV.getContext()).getAsString("un_toast_network_error")) {
                    ACache.get(serviceV.getContext()).put("un_toast_network_error", "", 60);
                    toastMsg.set(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (hasLogin.get() && success) {
                    attemptGetUserInfo();
                } else {
                    isLoading.set(false);
                }
            }
        });
    }

    public void attemptGetUserInfoIfNotLoding() {
        if (!isLoading.get()) {
            attemptGetUserInfo();
        }
    }

    /**
     * 获取个人资料
     */
    public void attemptGetUserInfo() {
        GetUserInfoTask getUserInfoTask = new GetUserInfoTask(this);
        getUserInfoTask.getInfo(new Callback<UserEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                if (!isLoading.get()) isLoading.set(true);
            }

            @Override
            public void onDataSuccess(UserEntity entity) {
                super.onDataSuccess(entity);
                userEntity = entity;
                morphUserInfo(userEntity);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                if (null == ACache.get(serviceV.getContext()).getAsString("un_toast_network_error")) {
                    ACache.get(serviceV.getContext()).put("un_toast_network_error", "", 60);
                    toastMsg.set(msg);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }
}