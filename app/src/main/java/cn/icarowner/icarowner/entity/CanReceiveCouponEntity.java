package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * CanReceiveCouponEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class CanReceiveCouponEntity implements Serializable {


    /**
     * group : {"id":"37737208-28ba-4a16-bc3d-738ebbd9ff09","name":"四川华星汽车集团有限公司","logo_url":"http://domain/logo_url","status":0}
     * dealer : {"id":"37737208-28ba-4a16-bc3d-738ebbd9ff09","name":"羊西店","full_name":"四川华星锦业羊西店","status":0}
     * donation_coupons : [{"key":"service_type_common","name":"通用","discount":10000,"can_received":1}]
     */

    private GroupEntity group;
    private DealerEntity dealer;
    @JSONField(name = "donation_coupons")
    private List<DonationCouponsEntity> donationCoupons;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }

    public List<DonationCouponsEntity> getDonationCoupons() {
        return donationCoupons;
    }

    public void setDonationCoupons(List<DonationCouponsEntity> donationCoupons) {
        this.donationCoupons = donationCoupons;
    }
}
