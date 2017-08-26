package cn.icarowner.icarowner.activity.view;

/**
 * advisorListV
 * create by 崔婧
 * create at 2017/5/18 上午11:21
 */
public interface AdvisorListV extends BaseV {
    //跳转到店的详情页
    void jumpToDealerDetailActivity(String id, String name);

    //跳转到顾问的详情页
    void jumpToAdvisorDetailActivity(String id);

    //联系到当前顾问
    void callToAdvisor(String phoneNum);
}
