package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.AdvisorDetailV;
import cn.icarowner.icarowner.entity.AdvisorEntity;
import cn.icarowner.icarowner.httptask.GetAdvisorDetailTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * AdvisorDetailVM
 * create by 崔婧
 * create at 2017/5/18 上午11:45
 */
public class AdvisorDetailVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> headImgUrl = new ObservableField<>();
    public final ObservableField<String> advisorName = new ObservableField<>();
    public final ObservableField<Float> rating = new ObservableField<>();
    public final ObservableField<String> advisorType = new ObservableField<>();
    public final ObservableField<String> advisorIntroduction = new ObservableField<>();
    public final ObservableField<String> belongCompany = new ObservableField<>();

    private AdvisorDetailV advisorDetailV;
    public ToolBarTitleVM toolBarTitleVM;

    private String advisorId;
    private AdvisorEntity advisorEntity;

    public AdvisorDetailVM(AdvisorDetailV advisorDetailV, String advisorId) {
        this.advisorDetailV = advisorDetailV;
        this.advisorId = advisorId;
        initToolBar();

        attemptGetAdvisorInfo();
    }

    @BindingAdapter("headImgUrl")
    public static void setHeadImgUrl(CircleImageView circleImageView, String headImgUrl) {
        Picasso.with(circleImageView.getContext()).load(headImgUrl)
                .placeholder(R.drawable.default_head_deep_gray)
                .error(R.drawable.default_head_deep_gray)
                .into(circleImageView);
    }

    private void morphDealerUserInfo(AdvisorEntity entity) {
        advisorName.set(entity.getName());
        rating.set(entity.getStar());
        if (entity.getRole().equals("service_advisor")) {
            advisorType.set("服务顾问");
        }
        belongCompany.set(entity.getDealer().getFullName());
        advisorIntroduction.set(entity.getIntroduction());
        headImgUrl.set(entity.getAvatarUrl());
    }

    private void attemptGetAdvisorInfo() {
        final GetAdvisorDetailTask advisorDetailTask = new GetAdvisorDetailTask(this);
        advisorDetailTask.getAdvisorDetailInfo(advisorId, new Callback<AdvisorEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(AdvisorEntity entity) {
                super.onDataSuccess(entity);
                advisorEntity = entity;
                morphDealerUserInfo(entity);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }

    public void callToAdvisor(View view) {
        if (null == advisorEntity || TextUtils.isEmpty(advisorEntity.getMobile())) {
            return;
        }
        advisorDetailV.callToAdvisor(advisorEntity.getMobile());
    }

    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM("顾问") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                advisorDetailV.closePage();
            }
        };
    }
}
