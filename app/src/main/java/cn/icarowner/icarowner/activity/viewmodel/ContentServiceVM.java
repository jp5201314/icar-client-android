package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import cn.icarowner.icarowner.entity.BannerEntity;
import cn.icarowner.icarowner.entity.MainActivityStatusEntity;

/**
 * ContentServiceVM
 * create by 崔婧
 * create at 2017/5/18 上午11:52
 */
public class ContentServiceVM extends BaseVM {
    public final ObservableList<BannerEntity.BannersBean> banners = new ObservableArrayList<>();
    public final ObservableField<String> tip = new ObservableField<>();
    // 0, 1, 2, default 0
    public final ObservableField<Integer> scheduleStatus = new ObservableField<>(0);
    public final ObservableField<Integer> payStatus = new ObservableField<>(0);
    public final ObservableField<Integer> evaluateStatus = new ObservableField<>(0);
    public final ObservableField<Integer> historyStatus = new ObservableField<>(0);
    public final ObservableField<Boolean> hasOneKeyHelp = new ObservableField<>(false);

    private MainActivityStatusEntity mainActivityStatusEntity;

    public ContentServiceVM() {
        this.setDefault();
    }

    @BindingAdapter("onItemClickListener")
    public static void bindOnItemClickListener(ConvenientBanner convenientBanner, OnItemClickListener onItemClickListener) {
        convenientBanner.setOnItemClickListener(onItemClickListener);
    }

    public void setDefault() {
        this.mainActivityStatusEntity = null;
        this.tip.set("立即登录—尊享会员优先通道");
        this.banners.clear();
        this.scheduleStatus.set(0);
        this.payStatus.set(0);
        this.evaluateStatus.set(0);
        this.historyStatus.set(0);
        this.hasOneKeyHelp.set(false);
    }

    public void setMainActivityStatusEntity(MainActivityStatusEntity entity) {
        mainActivityStatusEntity = entity;
        morphMainButtonStatus(mainActivityStatusEntity);
    }

    private void morphMainButtonStatus(MainActivityStatusEntity entity) {
        tip.set(entity.getTitle());
        scheduleStatus.set(entity.getButtonStatus().getUnFinished());
        payStatus.set(entity.getButtonStatus().getPendingPay());
        evaluateStatus.set(entity.getButtonStatus().getPendingEvaluated());
        historyStatus.set(entity.getButtonStatus().getFinished());
        hasOneKeyHelp.set(null != entity.getDealer());
    }

    /**
     * OnBanner click listener
     *
     * @param view
     */
    public void onBannerClick(View view) {
    }

    /**
     * OnScheduleButton click listener
     *
     * @param view
     */
    public void onScheduleClick(View view) {
    }

    /**
     * OnPayButton click listener
     *
     * @param view
     */
    public void onPayClick(View view) {
    }

    public void onEvaluateClick(View view) {
    }

    public void onHistoryClick(View view) {
    }

    public void onOneKeyHelpClick(View view) {
    }

    public void onBannerItemClick(int position) {

    }

    public OnItemClickListener onBannerItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            onBannerItemClick(position);
        }
    };
}