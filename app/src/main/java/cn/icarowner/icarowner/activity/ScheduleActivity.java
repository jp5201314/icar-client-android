package cn.icarowner.icarowner.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.event.ReplaceToScheduleDetailFragmentEvent;
import cn.icarowner.icarowner.fragment.ScheduleDetailFragment;
import cn.icarowner.icarowner.fragment.ScheduleListFragment;
import cn.icarowner.icarowner.httptask.GetLatestServiceOrderDetailTask;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * ScheduleActivity 进度
 * create by 崔婧
 * create at 2017/5/18 下午1:04
 */
public class ScheduleActivity extends BaseActivity {

    private String orderId;
    private int total;
    private boolean isFromList = false;
    private boolean isRequesting = false;
    private BroadcastReceiver receiver;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        setStatusBarColor(R.color.color_black_0e1214);
        initView();
        EventBus.getDefault().register(this);
        isFromList = false;
        isRequesting = false;
        if (!isFromNotification()) {
            requestLatestOrders();
            registerReceiver();
        } else {
            String orderId = getIntent().getStringExtra("orderId");
            showDetail(orderId);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (isFromNotification()) {
            String orderId = getIntent().getStringExtra("orderId");
            showDetail(orderId);
        }
    }

    /**
     * 判断页面是否由通知栏启动
     */
    private boolean isFromNotification() {
        return getIntent().hasExtra("isFromNotification");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != receiver) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onBackPressed() {
        if (total > 1) {
            if (isFromList) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScheduleListFragment()).commitAllowingStateLoss();
                isFromList = false;
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * 请求最近的服务单：
     * 1.若存在多个就直接跳到列页面
     * 2.若只存在一个就显示详情
     * 3.若存在0个就显示数据空白页
     */
    public void requestLatestOrders() {
        GetLatestServiceOrderDetailTask getLatestServiceOrderDetailTask = new GetLatestServiceOrderDetailTask(this);
        getLatestServiceOrderDetailTask.getLatestUnFinishedOrder(new Callback<ServiceOrderDetailEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isRequesting = true;
                showWaitingDialog(true);
            }

            @Override
            public void onDataSuccess(ServiceOrderDetailEntity serviceOrderDetailEntity) {
                super.onDataSuccess(serviceOrderDetailEntity);
                orderId = serviceOrderDetailEntity.getId();
                total = serviceOrderDetailEntity.getTotal();
                if (total == 0) {
                    View view = LayoutInflater.from(ScheduleActivity.this).inflate(R.layout.activity_schedule_empty, null);
                    container.addView(view);
                } else if (total == 1) {
                    showDetail(orderId);
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScheduleListFragment()).commitAllowingStateLoss();
                }
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                inflateNetFailed();
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isRequesting = false;
                showWaitingDialog(false);
            }
        });
    }

    /**
     * 详情fragment
     */
    private void showDetail(String orderId) {
        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, scheduleDetailFragment).commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        scheduleDetailFragment.setArguments(bundle);
    }

    /**
     * 加载网络加载失败布局
     */
    private void inflateNetFailed() {
        View view = LayoutInflater.from(ScheduleActivity.this).inflate(R.layout.layout_net_fail, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLatestOrders();
            }
        });
        container.addView(view);
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
                        if (!isRequesting) {
                            requestLatestOrders();
                        }
                    }
                } else {
                    if (wifiNetInfo.isConnected() || mobNetInfo.isConnected()) {
                        if (!isRequesting) {
                            requestLatestOrders();
                        }
                    }
                }
            }
        };
        //注册广播
        ScheduleActivity.this.registerReceiver(
                receiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Replace(ReplaceToScheduleDetailFragmentEvent replaceToScheduleDetailFragmentEvent) {
        ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, scheduleDetailFragment).commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", replaceToScheduleDetailFragmentEvent.getOrderId());
        isFromList = true;
        bundle.putBoolean("isFromList", isFromList);
        scheduleDetailFragment.setArguments(bundle);
    }

    private void initView() {
        container = (FrameLayout) findViewById(R.id.container);
    }
}
