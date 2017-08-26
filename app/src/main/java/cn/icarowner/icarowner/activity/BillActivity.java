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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.event.ReplaceToBillDetailFragmentEvent;
import cn.icarowner.icarowner.fragment.BillDetailFragment;
import cn.icarowner.icarowner.fragment.BillListFragment;
import cn.icarowner.icarowner.net.okhttpfinal.ICarHttpRequestCallBack;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;

/**
 * BillActivity 支付
 * create by 崔婧
 * create at 2017/5/18 下午12:03
 */
public class BillActivity extends BaseActivity {

    private ServiceOrderDetailEntity serviceOrderDetailEntity;
    private String orderId;
    private BroadcastReceiver receiver;
    private boolean isFromList = false;
    private boolean onRequest = false;
    private int total;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setStatusBarColor(R.color.color_black_0e1214);
        initView();
        EventBus.getDefault().register(this);
        isFromList = false;
        onRequest = false;
        requestLatestOrders();
        registerReceiver();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BillListFragment()).commitAllowingStateLoss();
                isFromList = false;
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * 请求最近的服务单
     */
    public void requestLatestOrders() {

        RequestParams params = new RequestParams(this);
        params.addFormDataPart("type", "pending_pay");
        HttpRequest.get(Constant.getHost() + API.LATEST_ORDER_DETAIL, params, new ICarHttpRequestCallBack() {
            @Override
            public void onStart() {
                super.onStart();
                showWaitingDialog(true);
                onRequest = true;
            }

            @Override
            protected void onDataSuccess(JSONObject data) {
                super.onDataSuccess(data);
                String jsonString = JSON.toJSONString(data);
                serviceOrderDetailEntity = JSON.parseObject(jsonString, ServiceOrderDetailEntity.class);
                orderId = serviceOrderDetailEntity.getId();
                total = serviceOrderDetailEntity.getTotal();
                if (total == 0) {
                    View view = LayoutInflater.from(BillActivity.this).inflate(R.layout.activity_bill_empty, null);
                    container.addView(view);
                } else if (total == 1) {
                    BillDetailFragment billDetailFragment = new BillDetailFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, billDetailFragment).commitAllowingStateLoss();
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    billDetailFragment.setArguments(bundle);
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new BillListFragment()).commitAllowingStateLoss();
                }
            }

            @Override
            protected void onDataError(int status, JSONObject statusInfo) {
                super.onDataError(status, statusInfo);
                inflateNetFailed();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                toast(msg);
                inflateNetFailed();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                showWaitingDialog(false);
                onRequest = false;
            }
        });
    }

    /**
     * 加载网络加载失败布局
     */
    private void inflateNetFailed() {
        View view = LayoutInflater.from(BillActivity.this).inflate(R.layout.layout_net_fail, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLatestOrders();
            }
        });
        container.addView(view);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Replace(ReplaceToBillDetailFragmentEvent replaceToBillDetailFragmentEvent) {
        BillDetailFragment billDetailFragment = new BillDetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, billDetailFragment).commitAllowingStateLoss();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", replaceToBillDetailFragmentEvent.getOrderId());
        isFromList = true;
        bundle.putBoolean("isFromList", isFromList);
        billDetailFragment.setArguments(bundle);
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
                        if (!onRequest) {
                            requestLatestOrders();
                        }
                    }
                } else {
                    if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
                        if (!onRequest) {
                            requestLatestOrders();
                        }
                    }
                }
            }
        };
        //注册广播
        BillActivity.this.registerReceiver(
                receiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void initView() {
        container = (FrameLayout) findViewById(R.id.container);
    }
}
