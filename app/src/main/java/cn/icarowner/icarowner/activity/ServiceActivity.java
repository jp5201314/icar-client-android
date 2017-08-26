package cn.icarowner.icarowner.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ServiceV;
import cn.icarowner.icarowner.activity.viewmodel.ServiceVM;
import cn.icarowner.icarowner.databinding.ActivityServiceBinding;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.entity.AppVersionEntity;
import cn.icarowner.icarowner.entity.BannerEntity;
import cn.icarowner.icarowner.event.LoginRefreshUserInfoEvent;
import cn.icarowner.icarowner.event.RefreshBalanceEvent;
import cn.icarowner.icarowner.event.RefreshHeaderEvent;
import cn.icarowner.icarowner.event.RefreshPersonalInfoEvent;
import cn.icarowner.icarowner.event.RefreshServiceActivityEvent;
import cn.icarowner.icarowner.event.SignOutRefreshEvent;
import cn.icarowner.icarowner.service.UpdateVersionService;

/**
 * ServiceActivity 主页
 * create by 崔婧
 * create at 2017/5/18 下午1:04
 */
public class ServiceActivity extends BaseActivity implements DrawerLayout.DrawerListener, ServiceV {

    private View content;

    // Banner
    private CBViewHolderCreator<LocalImageHolderView> holderCreator;

    // 抽屉的背景图，要切换的照片，放在drawable文件夹下
    final int[] images = {R.drawable.background_mine_center_one, R.drawable.background_mine_center_two,
            R.drawable.background_mine_center_three, R.drawable.background_mine_center_four};
    // 背景图索引
    int num = 0;

    private BroadcastReceiver receiver;

    private ActivityServiceBinding binding;
    private ServiceVM serviceVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "我是车主");
        EventBus.getDefault().register(this);

        serviceVM = new ServiceVM(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service);
        binding.setService(serviceVM);
        this.setViewModel(serviceVM);
        this.setObservers();

        // 验证是否跳转引导页
        validNewVersion();

        initDrawer();
        initBanner();

        registerReceiver();

        //从通知栏跳转到最终页面
        gotoDestActivity();

        //请求特殊权限：Manifest.permission.SYSTEM_ALERT_WINDOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(ServiceActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        //请求普通权限
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET,
                        Manifest.permission.INSTALL_PACKAGES,
                        Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceVM.checkLogin();
        serviceVM.attemptGetBannersIfNoneBanners();
        serviceVM.attemptGetMainButtonStatusIfNotLoding();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "我是车主");
        EventBus.getDefault().unregister(this);
        if (receiver == null) {
            return;
        } else {
            this.unregisterReceiver(receiver);
        }
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
//        serviceVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//                if (serviceVM.isLoading.get()) {
//                    showWaitingDialog(true);
//                } else {
//                    showWaitingDialog(false);
//                }
//            }
//        });

        serviceVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(serviceVM.toastMsg.get());
                serviceVM.toastMsg.set(null);
            }
        });
    }

    /**
     * 从通知栏跳转到最终页面
     */
    private void gotoDestActivity() {
        Intent intent = getIntent();
        if (intent.hasExtra("dest_class")) {
            intent.setClass(ServiceActivity.this, (Class) getIntent().getSerializableExtra("dest_class"));
            startActivity(intent);
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

    /**
     * 初始化banner
     */
    private void initBanner() {
        //获取系统屏幕信息
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        int width1 = width;
        int height1 = 346 * width / 750;
        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(width1, height1);
        findViewById(R.id.cb_banner).setLayoutParams(layoutParams);

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(width, 104 * width / 120);
        findViewById(R.id.fl_button).setLayoutParams(buttonLayoutParams);

        //自定义Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        holderCreator = new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        };

        ((ConvenientBanner) findViewById(R.id.cb_banner))
                // .setPages(holderCreator, banners)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.indicator_circle_unchecked, R.drawable.indicator_circle_choice})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(3000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);//集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        //convenientBanner.setManualPageable(false);//设置不能手动影响
        // ((ConvenientBanner) findViewById(R.id.cb_banner)).setOnItemClickListener(new OnItemClickListener() {
        //    @Override
        //    public void onItemClick(int position) {
        //    }
        // });
    }

    /**
     * 控制抽屉和抽屉背景图片显示
     */
    private void initDrawer() {
        content = binding.dlMainLayout.getChildAt(0);
        binding.dlMainLayout.addDrawerListener(this);
        //  去除阴影
        //  dlMainLayout.setScrimColor(Color.parseColor("#101416"));
        binding.dlMainLayout.setScrimColor(Color.TRANSPARENT);
        binding.dlMainLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.llDrawer.setBackground(getResources().getDrawable(images[num++]));
                        if (num >= images.length) {
                            num = 0;
                        }
                    }
                }, 0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    /**
     * 开关抽屉
     */
    public void openOrCloseDrawer() {
        if (binding.dlMainLayout.isDrawerOpen(GravityCompat.START)) {
            binding.dlMainLayout.closeDrawer(GravityCompat.START);
        } else {
            binding.dlMainLayout.openDrawer(GravityCompat.START);
        }
    }

    public void renderBanners(List<BannerEntity.BannersBean> banners) {
        if (null == banners) {
            banners = new ArrayList<>();
        }

        ((ConvenientBanner) findViewById(R.id.cb_banner)).setPages(holderCreator, banners);
    }

    @Override
    public void jumpToUpdateVersionService(AppVersionEntity appVersionEntity) {
        Intent intent = new Intent(ServiceActivity.this, UpdateVersionService.class);
        intent.putExtra("app_version", appVersionEntity);
        intent.setAction("updateVersion");
        startService(intent);
    }

    @Override
    public void jumpToWebPage(String title, String url) {
        Intent intent = new Intent(ServiceActivity.this, WebActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void jumpToDealerDetailPage(String dealerId, String dealerName) {
        Intent intent = new Intent(ServiceActivity.this, DealerDetailActivity.class);
        intent.putExtra("dealerId", dealerId);
        intent.putExtra("dealerName", dealerName);
        startActivity(intent);
    }

    @Override
    public void jumpToLoginPage() {
        if (ServiceActivity.this.getRunningActivityName().equals(".activity.LoginActivity")) {
            return;
        }

        startActivity(new Intent(ServiceActivity.this, LoginActivity.class));
    }

    @Override
    public void jumpToReceiveCouponPageInDealer(String dealerId) {
        Intent intent = new Intent(ServiceActivity.this, ReceiveCouponActivity.class);
        intent.putExtra("dealerId", dealerId);
        startActivity(intent);
    }

    @Override
    public void jumpToReceiveCouponPageInGroup(String groupId) {
        Intent intent = new Intent(ServiceActivity.this, ReceiveCouponActivity.class);
        intent.putExtra("groupId", groupId);
        startActivity(intent);
    }

    @Override
    public void jumpToGroupDetailPage(String groupId, String groupName) {
        Intent intent = new Intent(ServiceActivity.this, GroupActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("groupName", groupName);
        startActivity(intent);
    }

    @Override
    public void jumpToCouponIntroductionPage() {
        startActivity(new Intent(ServiceActivity.this, CouponIntroductionActivity.class));
    }

    @Override
    public void jumpToSchedulePage() {
        startActivity(new Intent(this, ScheduleActivity.class));
    }

    @Override
    public void jumpToBillPage() {
        startActivity(new Intent(this, BillActivity.class));
    }

    @Override
    public void jumpToEvaluationListPage() {
        startActivity(new Intent(this, EvaluateListActivity.class));
    }

    @Override
    public void jumpToServiceOrderListPage() {
        startActivity(new Intent(this, ServiceOrderListActivity.class));
    }

    @Override
    public void jumpToPersonalPage() {
        startActivity(new Intent(this, PersonalInformationActivity.class));
    }

    @Override
    public void jumpToDealerUsersListPage() {
        startActivity(new Intent(this, AdvisorListActivity.class));
    }

    @Override
    public void jumpToCouponListPage() {
        startActivity(new Intent(new Intent(this, CouponListActivity.class)));
    }

    @Override
    public void jumpToBalancePage() {
        startActivity(new Intent(this, BalanceChangeRecordListActivity.class));
    }

    @Override
    public void jumpToSettingPage() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void showOneKeyHelpDialog(String dealerName, String rescuePhone) {
        TCAgent.onEvent(this, "一键救援");
        final String[] strings = {rescuePhone};
        DialogCreater.createOneKeyHelpDialog(this, dealerName, strings, new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                String data = "tel:" + strings[position];
                Uri uri = Uri.parse(data);
                intent.setData(uri);
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public void startButtonAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(false);
        AlphaAnimation alpha = new AlphaAnimation(0.6f, 1.0f);
        ScaleAnimation scale = new ScaleAnimation(0.95f, 1.f, 0.95f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(alpha);
        animationSet.addAnimation(scale);
        animationSet.setDuration(1000);
        animationSet.setStartOffset(0);
        animationSet.setFillAfter(false);
        view.startAnimation(animationSet);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
          /*slideOffset为0~1之间变化的数*/
        float contentTraRadio;
        drawerView.setPivotY(drawerView.getMeasuredHeight() / 2);
        if (drawerView.getId() == R.id.ll_drawer) {
            contentTraRadio = drawerView.getMeasuredWidth() * slideOffset;
            content.setPivotX(0);
            content.setPivotY(content.getMeasuredHeight() / 2);
            drawerView.setPivotX(drawerView.getMeasuredWidth());
        } else {
            contentTraRadio = -drawerView.getMeasuredWidth() * slideOffset;
            content.setPivotX(content.getMeasuredWidth());
            content.setPivotY(content.getMeasuredHeight() / 2);
            drawerView.setPivotX(0);
        }
        content.setTranslationX(contentTraRadio);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        /*解锁左边抽屉*/
        binding.dlMainLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    /**
     * banner的viewHolder
     */
    private class LocalImageHolderView implements Holder<BannerEntity.BannersBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, BannerEntity.BannersBean data) {
            Picasso.with(context)
                    .load(data.getCoverUrl())
                    .placeholder(R.drawable.banner_holder)
                    .error(R.drawable.banner_holder)
                    .into(imageView);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.dlMainLayout.isDrawerOpen(GravityCompat.START)) {
            binding.dlMainLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 刷新头像
     *
     * @param refreshHeaderEvent 头像刷新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshHeaderEvent refreshHeaderEvent) {
        serviceVM.headImgUrl.set(refreshHeaderEvent.getImage());
    }

    /**
     * 修改个人资料后刷新个人资料
     *
     * @param refreshPersonalInfoEvent 个人资料刷新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefrshInfoEvent(RefreshPersonalInfoEvent refreshPersonalInfoEvent) {
        serviceVM.attemptGetUserInfoIfNotLoding();
    }

    /**
     * 登陆后刷新页面
     *
     * @param loginRefreshUserInfoEvent 登录成功后舒心页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginRefreshEvent(LoginRefreshUserInfoEvent loginRefreshUserInfoEvent) {
        serviceVM.attemptGetBannersIfNoneBanners();
        serviceVM.attemptGetMainButtonStatusIfNotLoding();
    }

    /**
     * 退出登陆后刷新页面
     *
     * @param signOutRefreshEvent 退出登录后刷新页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignOutRefreshEvent(SignOutRefreshEvent signOutRefreshEvent) {
        serviceVM.appBarServiceVM.contentServiceVM.setDefault();
        serviceVM.setDefault();
        renderBanners(null);
        serviceVM.attemptGetBannersIfNoneBanners();
    }

    /**
     * 收到推送消息后刷新主页
     *
     * @param refreshServiceActivityEvent 收到推送消息刷新主页事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pushToRefreshActivity(RefreshServiceActivityEvent refreshServiceActivityEvent) {
        serviceVM.attemptGetMainButtonStatusIfNotLoding();
    }

    /**
     * 收到消息后刷新余额
     *
     * @param refreshBalanceEvent 刷新余额事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBalance(RefreshBalanceEvent refreshBalanceEvent) {
        serviceVM.attemptGetUserInfoIfNotLoding();
    }

    private void registerReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent service) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (null == mobNetInfo) {
                    if (wifiNetInfo.isConnected()) {
                        serviceVM.attemptGetBannersIfNoneBanners();
                    }
                } else {
                    if (wifiNetInfo.isConnected() || mobNetInfo.isConnected()) {
                        serviceVM.attemptGetBannersIfNoneBanners();
                    }
                }
            }
        };
        //注册广播
        ServiceActivity.this.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}