package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * MainActivityStatusEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class MainActivityStatusEntity implements Serializable {

    /**
     * title : 杂七杂八
     * button_status : {"un_finished":0,"pending_pay":0,"pending_evaluated":0,"finished":0}
     * dealer : {"id":"37737208-28ba-4a16-bc3d-738ebbd9ff09","name":"羊西店","full_name":"四川华星锦业羊西店","contact_phone":"028-89786524","rescue_phone":"028-78562536"}
     */


    private String title;


    @JSONField(name = "button_status")
    private ButtonStatusEntity buttonStatus;

    private DealerEntity dealer;
    /**
     * my_coupons_count : 0
     */

    @JSONField(name = "my_coupons_count")
    private int myCouponsCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ButtonStatusEntity getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(ButtonStatusEntity buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }

    public int getMyCouponsCount() {
        return myCouponsCount;
    }

    public void setMyCouponsCount(int myCouponsCount) {
        this.myCouponsCount = myCouponsCount;
    }
}
