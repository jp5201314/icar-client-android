package cn.icarowner.icarowner.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shizhefei.mvc.IDataAdapter;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.mvc.MVCUltraHelper;
import com.shizhefei.mvc.OnRefreshStateChangeListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import cn.icarowner.icarowner.API;
import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.ChoiceCouponListActivity;
import cn.icarowner.icarowner.activity.EvaluateDetailActivity;
import cn.icarowner.icarowner.activity.PaymentPageActivity;
import cn.icarowner.icarowner.adapter.BillDetailAdapter;
import cn.icarowner.icarowner.customizeview.NormalLoadViewFactory;
import cn.icarowner.icarowner.datasource.ServiceOrderDetailDataSource;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.dialog.SureTipDialog;
import cn.icarowner.icarowner.entity.ServiceOrderDetailEntity;
import cn.icarowner.icarowner.entity.TypesEntity;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.event.FinishPayToRefreshBillListEvent;
import cn.icarowner.icarowner.event.RefreshBalanceEvent;
import cn.icarowner.icarowner.net.okhttpfinal.ICarHttpRequestCallBack;
import cn.icarowner.icarowner.utils.ArithmeticUtil;
import cn.icarowner.icarowner.utils.OperationUtils;
import cn.xiaomeng.httpdog.HttpRequest;
import cn.xiaomeng.httpdog.RequestParams;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * BillDetailFragment
 * create by 崔婧
 * create at 2017/5/18 下午1:26
 */
public class BillDetailFragment extends BaseFragment {

    private FrameLayout flBillDetail;
    private TextView tvTitle;
    private ImageButton ibBack;
    private PtrClassicFrameLayout pflPtrFrame;
    private ScrollView svScroll;
    private LinearLayout llLicensePlate;
    private ImageView ivCar;
    private TextView tvLicensePlate;
    private ImageView ivVip;
    private LinearLayout llServiceType;
    private TextView tvBillFee;
    private LinearLayout llDiscountFee;
    private TextView tvDiscountRmbSymbol;
    private TextView tvPreferentialFee;
    private ImageButton ibToChoiceCoupon;
    private LinearLayout llBalanceFee;
    private TextView tvTotalBalanceFee;
    private TextView tvBalanceRmbSymbol;
    private EditText etChoiceBalanceFee;
    private ImageButton ibToEditBalanceFee;
    private TextView tvPayFee;
    private Button btnSurePay;

    private MVCHelper mvc;
    private List<TypesEntity> types;
    private ServiceOrderDetailEntity serviceOrderDetailEntity;
    private ServiceOrderDetailDataSource paymentDetailDataSource;
    private String orderId;
    private int balance;
    private double choiceBalance;
    private boolean isFromList = false;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_bill_detail);
        initView();
        orderId = getArguments().getString("orderId");
        isFromList = getArguments().getBoolean("isFromList");
        setTitle();
        initMVC();

        this.initListeners();
    }

    private void initListeners() {
        llDiscountFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseCouponClick(v);
            }
        });
        ibToChoiceCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseCouponClick(v);
            }
        });
        llBalanceFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditBalanceFeeClick(v);
            }
        });
        btnSurePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPayClick(v);
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIbBack(view);
            }
        });
    }

    public void setIbBack(View view) {
        getActivity().finish();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String couponId = data.getStringExtra("couponId");
            int amount = data.getIntExtra("amount", 0);
            int discount = data.getIntExtra("discount", 0);
            int payAmount = data.getIntExtra("payAmount", 0);
            tvBillFee.setText(OperationUtils.formatNum(OperationUtils.division(amount)));
            if (discount == 0) {
                if (TextUtils.isEmpty(couponId)) {
                    tvDiscountRmbSymbol.setVisibility(View.GONE);
                    tvPreferentialFee.setHint("选择");
                    tvPreferentialFee.setText(null);
                } else {
                    tvDiscountRmbSymbol.setVisibility(View.VISIBLE);
                    tvPreferentialFee.setText(OperationUtils.formatNum(OperationUtils.division(discount)));
                }
            } else {
                tvDiscountRmbSymbol.setVisibility(View.VISIBLE);
                tvPreferentialFee.setText(OperationUtils.formatNum(OperationUtils.division(discount)));
            }
            tvPayFee.setText(OperationUtils.formatNum(ArithmeticUtil.sub(OperationUtils.division(payAmount), choiceBalance)));
            mvc.refresh();
        }

        if (requestCode == 200 && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            btnSurePay.setText(result);
            btnSurePay.setClickable(false);
            getActivity().finish();
        }

        if (requestCode == 200 && resultCode == RESULT_CANCELED) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            btnSurePay.setText(result);
            btnSurePay.setClickable(true);
        }
    }

    public void onBackClick(View view) {
        if (isFromList) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new BillListFragment()).commitAllowingStateLoss();
        } else {
            getActivity().finish();
        }
    }

    public void onEditBalanceFeeClick(View view) {
        etChoiceBalanceFee.requestFocus();
        etChoiceBalanceFee.setSelection(etChoiceBalanceFee.getText().length());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }


    public void onChooseCouponClick(View view) {
        Intent intent = new Intent(getActivity(), ChoiceCouponListActivity.class);
        intent.putExtra("orderId", serviceOrderDetailEntity.getBill().getOrder().getId());
        if (serviceOrderDetailEntity.getBill().getOrder().getBoundCoupon() != null) {
            intent.putExtra("couponId", serviceOrderDetailEntity.getBill().getOrder().getBoundCoupon().getId());
        }
        startActivityForResult(intent, 100);
    }

    public void onPayClick(View view) {
        if (!TextUtils.isEmpty(tvPayFee.getText())) {
            Number number = null;
            try {
                number = new DecimalFormat().parse(tvPayFee.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (0 == (number != null ? number.doubleValue() : 0)) {
                showSureDialog();
            } else {
                Intent intent = new Intent(getActivity(), PaymentPageActivity.class);
                intent.putExtra("orderId", serviceOrderDetailEntity.getId());
                intent.putExtra("advisor", serviceOrderDetailEntity.getAdvisor().getName());
                intent.putExtra("dealer", serviceOrderDetailEntity.getDealer().getFullName());
                intent.putExtra("order", serviceOrderDetailEntity.getBill().getOrder().getId());
                intent.putExtra("orderNo", serviceOrderDetailEntity.getBill().getOrder().getOrderNo());
                intent.putExtra("payFee", serviceOrderDetailEntity.getBill().getOrder().getPayAmount());
                if (TextUtils.isEmpty(etChoiceBalanceFee.getText())) {
                    intent.putExtra("balance", 0);
                } else {
                    double doubleValue = 0;
                    try {
                        doubleValue = new DecimalFormat().parse(etChoiceBalanceFee.getText().toString()).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int balanceFee = (int) (doubleValue * 100);
                    intent.putExtra("balance", balanceFee);
                }
                startActivityForResult(intent, 200);
            }
        }
    }

    private void setTitle() {
        tvTitle.setText(R.string.bill_detail);
    }

    private void initMVC() {
        MVCUltraHelper.setLoadViewFractory(new NormalLoadViewFactory());
        setMaterialHeader(pflPtrFrame);
        paymentDetailDataSource = new ServiceOrderDetailDataSource(this);
        paymentDetailDataSource.setId(orderId);
        mvc = new MVCUltraHelper(pflPtrFrame);
        mvc.setDataSource(paymentDetailDataSource);
        mvc.setOnStateChangeListener(new OnRefreshStateChangeListener<ServiceOrderDetailEntity>() {
            @Override
            public void onStartRefresh(IDataAdapter adapter) {

            }

            @Override
            public void onEndRefresh(IDataAdapter adapter, ServiceOrderDetailEntity result) {
                requestBalance(result);
            }
        });
        mvc.setAdapter(new BillDetailAdapter());
        mvc.refresh();
    }

    /**
     * 请求用户余额总数
     */
    private void requestBalance(final ServiceOrderDetailEntity result) {

        HttpRequest.get(
                Constant.getHost() + API.PERSONAL_INFORMATION,
                new RequestParams(this),
                new ICarHttpRequestCallBack() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showWaitingDialog(true);

                    }

                    @Override
                    protected void onDataSuccess(JSONObject data) {
                        super.onDataSuccess(data);
                        String jsonString = JSON.toJSONString(data);
                        UserEntity userEntity = JSON.parseObject(jsonString, UserEntity.class);
                        if (null != result) {
                            serviceOrderDetailEntity = result;
                            renderDetail(result, userEntity);
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        toast(msg);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        showWaitingDialog(false);
                    }
                });
    }

    /**
     * 渲染详情
     *
     * @param serviceOrderDetailEntity 服务单详情实体类
     */
    private void renderDetail(ServiceOrderDetailEntity serviceOrderDetailEntity, UserEntity userEntity) {

        tvLicensePlate.setText(serviceOrderDetailEntity.getPlateNumber());
        if (serviceOrderDetailEntity.getBill() != null) {
            types = serviceOrderDetailEntity.getBill().getTypes();
            showServiceType();
            tvBillFee.setText(OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getAmount())));

            balance = userEntity.getBalance();
            if (0 >= balance) {
                llBalanceFee.setClickable(false);
                etChoiceBalanceFee.clearFocus();
                etChoiceBalanceFee.setFocusable(false);
                tvTotalBalanceFee.setText("(无余额)");
                tvBalanceRmbSymbol.setHint("¥");
                etChoiceBalanceFee.setHint("0.00");
                tvPayFee.setText(OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount())));
            } else {
                llBalanceFee.setClickable(true);
                etChoiceBalanceFee.requestFocus();
                etChoiceBalanceFee.setFocusable(true);
                tvTotalBalanceFee.setText(String.format("(余额¥%s)", OperationUtils.formatNum(OperationUtils.division(balance))));
                tvBalanceRmbSymbol.setText("¥");
                if (serviceOrderDetailEntity.getBill().getOrder().getAmount() > balance) {
                    etChoiceBalanceFee.setText(OperationUtils.formatNum(OperationUtils.division(balance)));
                    tvPayFee.setText(OperationUtils.formatNum(ArithmeticUtil.sub(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount()), balance / 100)));
                } else {
                    etChoiceBalanceFee.setText(OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount())));
                    tvPayFee.setText(OperationUtils.formatNum(ArithmeticUtil.sub(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount()), OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount()))));
                }
            }
            if (!TextUtils.isEmpty(etChoiceBalanceFee.getText())) {
                etChoiceBalanceFee.setSelection(etChoiceBalanceFee.getText().length());
            }
            listenerEditBalanceFee();

            if (serviceOrderDetailEntity.getBill().getOrder().getBoundCoupon() != null) {
                tvDiscountRmbSymbol.setVisibility(View.VISIBLE);
                if (serviceOrderDetailEntity.getBill().getOrder().getBoundCoupon().getDiscount() > serviceOrderDetailEntity.getBill().getOrder().getAmount()) {
                    tvPreferentialFee.setText(OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getAmount())));
                } else {
                    tvPreferentialFee.setText(OperationUtils.formatNum(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getBoundCoupon().getDiscount())));
                }
            }
        } else {
            getActivity().finish();
            toast("该服务单不存在账单");
        }
    }

    /**
     * 显示账单的服务类型
     */
    private void showServiceType() {
        LinearLayout itemOfBill;
        llServiceType.removeAllViews();
        for (int i = 0; i < types.size(); i++) {
            itemOfBill = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_bill_detail, llServiceType, false);
            LinearLayout llServiceTypeDetail = (LinearLayout) itemOfBill.findViewById(R.id.ll_service_type_detail);
            TextView tvServiceName = (TextView) itemOfBill.findViewById(R.id.tv_service_type_name);
            TextView tvServiceFee = (TextView) itemOfBill.findViewById(R.id.tv_service_type_fee);
            tvServiceName.setText(types.get(i).getName());
            int total = 0;
            LinearLayout itemOfTypeDetail;
            for (int j = 0; j < types.get(i).getBillDetailItems().size(); j++) {
                itemOfTypeDetail = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_bill_detail_detail, llServiceTypeDetail, false);
                TextView tvServiceDetailName = (TextView) itemOfTypeDetail.findViewById(R.id.tv_service_type_detail_name);
                TextView tvServiceDetailFee = (TextView) itemOfTypeDetail.findViewById(R.id.tv_service_type_detail_fee);
                View divider = itemOfTypeDetail.findViewById(R.id.divider);
                if (j == types.get(i).getBillDetailItems().size() - 1) {
                    itemOfTypeDetail.removeView(divider);
                }
                int amount = types.get(i).getBillDetailItems().get(j).getAmount();
                tvServiceDetailName.setText(types.get(i).getBillDetailItems().get(j).getKey());
                tvServiceDetailFee.setText(OperationUtils.formatNum(OperationUtils.division(amount)));
                total += amount;
                llServiceTypeDetail.addView(itemOfTypeDetail);
            }
            tvServiceFee.setText(OperationUtils.formatNum(OperationUtils.division(total)));
            llServiceType.addView(itemOfBill);
        }
    }

    /**
     * 监听余额编辑框
     */
    private void listenerEditBalanceFee() {
        etChoiceBalanceFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTwoPoint(etChoiceBalanceFee, s, start, before, count);
                checkNum(etChoiceBalanceFee, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etChoiceBalanceFee.getText())) {
                    choiceBalance = 0;
                } else {
                    try {
                        choiceBalance = new DecimalFormat().parse(etChoiceBalanceFee.getText().toString()).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (choiceBalance > OperationUtils.division(balance)) {
                    toast("请选择小于总余额的金额");
                    choiceBalance = 0;
                    etChoiceBalanceFee.setText("");
                }
                if (choiceBalance > OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount())) {
                    toast("请按实际金额支付");
                    choiceBalance = 0;
                    etChoiceBalanceFee.setText("");
                }
                String text = OperationUtils.formatNum(ArithmeticUtil.sub(OperationUtils.division(serviceOrderDetailEntity.getBill().getOrder().getPayAmount()), choiceBalance));
                tvPayFee.setText(text);
            }
        });
    }


    /**
     * 确认0元支付
     */
    private void showSureDialog() {
        SureTipDialog sureTipDialog = DialogCreater.createUpdateVersionDialog(getActivity(), 0,
                "确认支付？", "支付", "取消",
                R.color.color_gray_a5a5a6, R.color.color_gray_a5a5a6,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                        requestAppPay();
                    }
                },
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        sureTipDialog.findViewById(R.id.iv_icon).setVisibility(View.GONE);
        sureTipDialog.show();
    }

    private void requestAppPay() {
        RequestParams params = new RequestParams(this);
        params.addFormDataPart("order", serviceOrderDetailEntity.getBill().getOrder().getId());
        if (TextUtils.isEmpty(etChoiceBalanceFee.getText())) {
            params.addFormDataPart("amount", 0);
        } else {
            double doubleValue = 0;
            try {
                doubleValue = new DecimalFormat().parse(etChoiceBalanceFee.getText().toString()).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int balanceFee = (int) (doubleValue * 100);
            params.addFormDataPart("amount", balanceFee);
        }
        HttpRequest.post(Constant.getHost() + API.APP_PAY, params, new ICarHttpRequestCallBack() {
            @Override
            public void onStart() {
                super.onStart();
                showWaitingDialog(true);
            }

            @Override
            protected void onDataSuccess(JSONObject data) {
                super.onDataSuccess(data);
                toast("支付成功");
                btnSurePay.setClickable(false);
                EventBus.getDefault().post(new FinishPayToRefreshBillListEvent());
                EventBus.getDefault().post(new RefreshBalanceEvent());
                Intent toEvaluation = new Intent(getActivity(), EvaluateDetailActivity.class);
                toEvaluation.putExtra("orderId", serviceOrderDetailEntity.getId());
                toEvaluation.putExtra("advisorName", serviceOrderDetailEntity.getAdvisor().getName());
                toEvaluation.putExtra("dealerFullName", serviceOrderDetailEntity.getDealer().getFullName());
                startActivity(toEvaluation);
                getActivity().finish();
            }

            @Override
            protected void onDataError(int status, JSONObject statusInfo) {
                super.onDataError(status, statusInfo);
                btnSurePay.setText("支付失败，请重新支付");
                btnSurePay.setClickable(true);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                toast(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                showWaitingDialog(false);
            }
        });
    }

    /**
     * 检测小数点后的位数
     *
     * @param numEt  编辑框
     * @param s      输入字符
     * @param start  开始位置
     * @param before 最初位置
     * @param count  计数
     */
    public void checkTwoPoint(EditText numEt, CharSequence s, int start, int before, int count) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                numEt.setText(s);
                numEt.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            numEt.setText(s);
            numEt.setSelection(2);
        }

        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                numEt.setText(s.subSequence(0, 1));
                numEt.setSelection(1);
            }
        }

    }

    /**
     * 检测数字大小
     *
     * @param numEt  编辑框
     * @param s      输入字符
     * @param start  开始位置
     * @param before 最初位置
     * @param count  计数
     */
    public void checkNum(EditText numEt, CharSequence s, int start, int before, int count) {
        double MIN_MARK = 0;
        double MAX_MARK = 9999999.99;

        if (start > 1) {
            if (MAX_MARK != -1 && MIN_MARK != -1) {

                double aDouble = 0;
                try {
                    aDouble = new DecimalFormat().parse(s.toString()).doubleValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (aDouble > MAX_MARK) {
                    toast("输入金额过大，系统无法处理！");
                    numEt.setText(s = String.valueOf(MIN_MARK));
                } else if (aDouble < MIN_MARK) {
                    toast("输入了负数，系统无法处理！");
                    s = String.valueOf(MIN_MARK);
                }
            }
        }
    }

    private void initView() {
        flBillDetail = (FrameLayout) findViewById(R.id.fl_bill_detail);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        pflPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.pfl_ptr_frame);
        svScroll = (ScrollView) findViewById(R.id.sv_scroll);
        llLicensePlate = (LinearLayout) findViewById(R.id.ll_license_plate);
        ivCar = (ImageView) findViewById(R.id.iv_car);
        tvLicensePlate = (TextView) findViewById(R.id.tv_license_plate);
        ivVip = (ImageView) findViewById(R.id.iv_vip);
        llServiceType = (LinearLayout) findViewById(R.id.ll_service_type);
        tvBillFee = (TextView) findViewById(R.id.tv_bill_fee);
        llDiscountFee = (LinearLayout) findViewById(R.id.ll_discount_fee);
        tvDiscountRmbSymbol = (TextView) findViewById(R.id.tv_discount_rmb_symbol);
        tvPreferentialFee = (TextView) findViewById(R.id.tv_preferential_fee);
        ibToChoiceCoupon = (ImageButton) findViewById(R.id.ib_to_choice_coupon);
        llBalanceFee = (LinearLayout) findViewById(R.id.ll_balance_fee);
        tvTotalBalanceFee = (TextView) findViewById(R.id.tv_total_balance_fee);
        tvBalanceRmbSymbol = (TextView) findViewById(R.id.tv_balance_rmb_symbol);
        etChoiceBalanceFee = (EditText) findViewById(R.id.et_choice_balance_fee);
        ibToEditBalanceFee = (ImageButton) findViewById(R.id.ib_to_edit_balance_fee);
        tvPayFee = (TextView) findViewById(R.id.tv_pay_fee);
        btnSurePay = (Button) findViewById(R.id.btn_sure_pay);
    }
}
