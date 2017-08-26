package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import cn.icarowner.icarowner.activity.view.PaymentPageV;
import cn.icarowner.icarowner.httptask.CreateAliPayOrderTask;
import cn.icarowner.icarowner.httptask.CreateWxPayOrderTask;
import cn.icarowner.icarowner.httptask.CreateXinYePayOrderTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.CreateWxPayOrderModel;
import cn.icarowner.icarowner.utils.ArithmeticUtil;
import cn.icarowner.icarowner.utils.OperationUtils;

/**
 * PaymentPageVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class PaymentPageVM extends BaseVM {


    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();
    public final ObservableField<Boolean> isXYPay = new ObservableField<>();
    public final ObservableField<Boolean> isAliPay = new ObservableField<>();

    public ToolBarTitleVM toolBarTitleVM;

    public String actualAmountOfPay;

    public int amountOfPay;
    public int amountOfBalance;
    public String serviceOrderId;
    public String advisor;
    public String dealer;
    public String orderId;

    private PaymentPageV paymentPageV;

    public PaymentPageVM(PaymentPageV paymentPageV, int amountOfPay, int amountOfBalance, String serviceOrderId, String advisor, String dealer, String orderId) {
        this.paymentPageV = paymentPageV;
        this.amountOfPay = amountOfPay;
        this.amountOfBalance = amountOfBalance;
        this.serviceOrderId = serviceOrderId;
        this.advisor = advisor;
        this.dealer = dealer;
        this.orderId = orderId;
        this.actualAmountOfPay = String.format("¥%s", OperationUtils.formatNum(ArithmeticUtil.sub(OperationUtils.division(amountOfPay), OperationUtils.division(amountOfBalance))));
        this.isXYPay.set(true);
        this.isAliPay.set(false);
        this.initToolBar();
    }

    public void onAliPayClick(View view) {
        isAliPay.set(true);
        isXYPay.set(false);
        //attemptCreateAliPayOrder();
    }

    public void onWxPayClick(View view) {
        attemptCreateWXPayOrder();
    }

    public void onXyPayClick(View view) {
        isAliPay.set(false);
        isXYPay.set(true);
        //attemptCreateXYPayOrder();
    }

    public void onOtherClick(View view) {
        paymentPageV.displayAliPay();
    }

    public void onConfirmClick(View view) {
        if (isAliPay.get() && !isXYPay.get()) {
            attemptCreateAliPayOrder();
            return;
        }

        if (isXYPay.get() && !isAliPay.get()) {
            attemptCreateXYPayOrder();
        }
    }

    /**
     * 生成支付宝订单
     */
    private void attemptCreateAliPayOrder() {
        CreateAliPayOrderTask createAliPayOrderTask = new CreateAliPayOrderTask(this);
        double amount = ArithmeticUtil.sub(OperationUtils.division(amountOfPay), OperationUtils.division(amountOfBalance));
        createAliPayOrderTask.createOrder(orderId, amountOfBalance, (int) ArithmeticUtil.mul(amount, 100), new Callback<String>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(String payString) {
                super.onDataSuccess(payString);
                paymentPageV.toAliPayTask(payString);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                //toastMsg.set(msg);
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
     * 生成微信支付订单
     */
    private void attemptCreateWXPayOrder() {
        CreateWxPayOrderTask createWxPayOrderTask = new CreateWxPayOrderTask(this);
        double amount = ArithmeticUtil.sub(OperationUtils.division(amountOfPay), OperationUtils.division(amountOfBalance));
        createWxPayOrderTask.createOrder(orderId, amountOfBalance, (int) ArithmeticUtil.mul(amount, 100), new Callback<CreateWxPayOrderModel>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(CreateWxPayOrderModel createWxPayOrderModel) {
                super.onDataSuccess(createWxPayOrderModel);
                paymentPageV.toWXPay(createWxPayOrderModel.getAppId(),
                        createWxPayOrderModel.getPrepayId(),
                        createWxPayOrderModel.getPartnerId(),
                        createWxPayOrderModel.getPackageValue(),
                        createWxPayOrderModel.getNonceStr(),
                        createWxPayOrderModel.getTimestamp(),
                        createWxPayOrderModel.getSign()
                );
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
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
     * 生成兴业银行支付订单
     */
    private void attemptCreateXYPayOrder() {
        CreateXinYePayOrderTask createXinYePayOrderTask = new CreateXinYePayOrderTask(this);
        double amount = ArithmeticUtil.sub(OperationUtils.division(amountOfPay), OperationUtils.division(amountOfBalance));
        createXinYePayOrderTask.createOrder(orderId, amountOfBalance, (int) ArithmeticUtil.mul(amount, 100), new Callback<String>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(String tokenId) {
                super.onDataSuccess(tokenId);
                paymentPageV.toXinyePay(tokenId);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }


    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM("支付") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                paymentPageV.closePage();
            }
        };
    }
}
