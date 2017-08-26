package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.DealerDetailV;
import cn.icarowner.icarowner.entity.DealerEntity;
import cn.icarowner.icarowner.httptask.GetDealerDetailTask;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * DealerDetailVM
 * create by 崔婧
 * create at 2017/5/18 下午12:00
 */
public class DealerDetailVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> dealerImageUrl = new ObservableField();
    public final ObservableField<String> dealerFullName = new ObservableField<>();
    public final ObservableField<String> detailUrl = new ObservableField<>();

    private DealerDetailV dealerDetailV;
    public ToolBarTitleVM toolBarTitleVM;

    private String dealerName;
    private String dealerId;
    private DealerEntity dealerEntity;

    public DealerDetailVM(DealerDetailV dealerDetailV, String dealerName, String dealerId) {
        this.dealerDetailV = dealerDetailV;
        this.dealerName = dealerName;
        this.dealerId = dealerId;
        initToolBar();

        attemptGetDealerInfo();
    }

    public void morphDealerDetail(DealerEntity entity) {
        dealerImageUrl.set(entity.getCoverUrl());
        dealerFullName.set(entity.getFullName());
        detailUrl.set(entity.getDetailUrl());
    }

    @BindingAdapter("dealerImageUrl")
    public static void setDealerImageUrl(ImageView dealerImageView, String dealerImageUrl) {
        Picasso.with(dealerImageView.getContext())
                .load(dealerImageUrl)
                .placeholder(R.drawable.img_dealer)
                .error(R.drawable.img_dealer)
                .into(dealerImageView);
    }

    @BindingAdapter("webUrl")
    public static void setWebUrl(WebView webView, String webUrl) {
        webView.loadUrl(webUrl);
    }

    public void attemptGetDealerInfo() {
        GetDealerDetailTask getDealerDetailTask = new GetDealerDetailTask(this);
        getDealerDetailTask.getDealerDetail(dealerId, new Callback<DealerEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(DealerEntity entity) {
                super.onDataSuccess(entity);
                dealerEntity = entity;
                morphDealerDetail(entity);
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
     * 打电话给经销店
     *
     * @param view
     */
    public void callToDealer(View view) {
        if (null == dealerEntity || TextUtils.isEmpty(dealerEntity.getContactPhone())) {
            return;
        }

        dealerDetailV.callToDealer(dealerEntity.getContactPhone());
    }

    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM(dealerName) {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                dealerDetailV.closePage();
            }
        };
    }
}
