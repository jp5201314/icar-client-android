package cn.icarowner.icarowner.activity.view;

import android.view.View;

import java.util.List;

import cn.icarowner.icarowner.entity.AppVersionEntity;
import cn.icarowner.icarowner.entity.BannerEntity;

/**
 * ServiceV
 * create by 崔婧
 * create at 2017/5/18 上午11:40
 */
public interface ServiceV extends BaseV {

    void openOrCloseDrawer();

    void renderBanners(List<BannerEntity.BannersBean> banners);

    void jumpToUpdateVersionService(AppVersionEntity appVersionEntity);

    void jumpToWebPage(String title, String url);

    void jumpToDealerDetailPage(String dealerId, String dealerName);

    void jumpToLoginPage();

    void jumpToReceiveCouponPageInDealer(String dealerId);

    void jumpToReceiveCouponPageInGroup(String groupId);

    void jumpToGroupDetailPage(String groupId, String groupName);

    void jumpToCouponIntroductionPage();

    void jumpToSchedulePage();

    void jumpToBillPage();

    void jumpToEvaluationListPage();

    void jumpToServiceOrderListPage();

    void jumpToPersonalPage();

    void jumpToDealerUsersListPage();

    void jumpToCouponListPage();

    void jumpToBalancePage();

    void jumpToSettingPage();

    void showOneKeyHelpDialog(String dealerName, String rescuePhone);

    void startButtonAnimation(View view);

}
