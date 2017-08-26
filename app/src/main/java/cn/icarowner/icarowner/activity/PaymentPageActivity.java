package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.PaymentPageV;
import cn.icarowner.icarowner.activity.viewmodel.PaymentPageVM;
import cn.icarowner.icarowner.databinding.ActivityPaymentPageBinding;
import cn.icarowner.icarowner.event.FinishPayToRefreshBillListEvent;
import cn.icarowner.icarowner.event.RefreshBalanceEvent;
import cn.icarowner.icarowner.event.WeChatPayEvent;
import cn.icarowner.icarowner.pay.PayResult;

/**
 * PaymentPageActivity 支付
 * create by 崔婧
 * create at 2017/5/18 下午1:01
 */
public class PaymentPageActivity extends BaseActivity implements PaymentPageV {

    private static final int SDK_PAY_FLAG = 1;
    private int payFee;

    private int balance;

    private String serviceOrderId;
    private String advisor;
    private String dealer;
    private String orderId;
    private ActivityPaymentPageBinding binding;
    private PaymentPageVM paymentPageVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "支付");
        Intent intent = getIntent();
        payFee = intent.getIntExtra("payFee", 0);
        serviceOrderId = intent.getStringExtra("orderId");
        orderId = intent.getStringExtra("order");
        advisor = intent.getStringExtra("advisor");
        dealer = intent.getStringExtra("dealer");
        balance = intent.getIntExtra("balance", 0);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_page);
        paymentPageVM = new PaymentPageVM(this, payFee, balance, serviceOrderId, advisor, dealer, orderId);
        binding.setPaymentPage(paymentPageVM);
        this.setObservers();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(this, "支付");
        super.onDestroy();
    }

    /**
     * Set observers for fields in view model
     */
    public void setObservers() {
        binding.getPaymentPage().isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (binding.getPaymentPage().isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        binding.getPaymentPage().toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                toast(binding.getPaymentPage().toastMsg.get());
            }
        });
    }

    /**
     * 调用支付宝支付
     */
    @Override
    public void toAliPayTask(final String payment_data) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPay = new PayTask(PaymentPageActivity.this);
                String result = aliPay.pay(payment_data, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝消息回调
     */
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看
                     * https://doc.open.alipay.com/doc2/detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&docType=1
                     * ) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PaymentPageActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("result", "支付完成");
                        EventBus.getDefault().post(new FinishPayToRefreshBillListEvent());
                        EventBus.getDefault().post(new RefreshBalanceEvent());
                        setResult(RESULT_OK, intent);
                        Intent toEvaluation = new Intent(PaymentPageActivity.this, EvaluateDetailActivity.class);
                        toEvaluation.putExtra("orderId", serviceOrderId);
                        toEvaluation.putExtra("advisorName", advisor);
                        toEvaluation.putExtra("dealerFullName", dealer);
                        startActivity(toEvaluation);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PaymentPageActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PaymentPageActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("result", "支付失败,请重新支付");
                            setResult(RESULT_CANCELED, intent);
                        }
                    }
                    break;
                }
            }
        }

    };

    /**
     * 发起微信支付
     *
     * @param appId        APP终端号
     * @param prepayId     预支付ID
     * @param partnerId    商户ID
     * @param packageValue 包名
     * @param nonceStr     混淆字符串
     * @param timeStamp    时间戳
     * @param sign         签名
     */
    @Override
    public void toWXPay(String appId, String prepayId,
                        String partnerId, String packageValue,
                        String nonceStr, String timeStamp, String sign) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, appId);
        msgApi.registerApp(appId);
        PayReq wxPayRequest = new PayReq();
        if (msgApi.isWXAppInstalled() && msgApi.isWXAppSupportAPI()) {
            wxPayRequest.appId = appId;
            wxPayRequest.prepayId = prepayId;
            wxPayRequest.partnerId = partnerId;
            wxPayRequest.packageValue = packageValue;
            wxPayRequest.nonceStr = nonceStr;
            wxPayRequest.timeStamp = timeStamp;
            wxPayRequest.sign = sign;
            msgApi.sendReq(wxPayRequest);
        } else {
            toast("请先安装微信客户端");
        }
    }

    /**
     * 发起兴业支付
     *
     * @param tokenId
     */
    @Override
    public void toXinyePay(String tokenId) {
        RequestMsg msg = new RequestMsg();
        msg.setTokenId(tokenId);
        msg.setTradeType(MainApplication.WX_APP_TYPE);
        msg.setAppId(Constant.WE_CHAT_APPID);
        PayPlugin.unifiedAppPay(this, msg);
    }

    /**
     * 显示支付宝支付选项
     */
    @Override
    public void displayAliPay() {
        binding.llOther.setVisibility(View.GONE);
        binding.llAliPay.setVisibility(View.VISIBLE);
    }

    /**
     * 微信支付回调
     *
     * @param event 微信回调事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WeChatPayEvent event) {
        if (event.success == 1) {
            Intent intent = new Intent();
            intent.putExtra("result", "支付完成");
            EventBus.getDefault().post(new FinishPayToRefreshBillListEvent());
            EventBus.getDefault().post(new RefreshBalanceEvent());
            setResult(RESULT_OK, intent);
            Intent toEvaluation = new Intent(PaymentPageActivity.this, EvaluateDetailActivity.class);
            toEvaluation.putExtra("orderId", serviceOrderId);
            toEvaluation.putExtra("advisorName", advisor);
            toEvaluation.putExtra("dealerFullName", dealer);
            startActivity(toEvaluation);
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", "支付失败，请重新支付");
            setResult(RESULT_CANCELED, intent);
        }
    }

    @Override
    public Context getContext() {
        return PaymentPageActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
