package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * ServiceOrderItemEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class ServiceOrderItemEntity {


    /**
     * id : 70007950-e634-4bc3-8ade-8fa5cf48abb0
     * plate_number : 川A20286
     * created_at : 2016-10-12 10:54:11
     * dealer_id : 1a6d0048-6986-4639-8401-3471d395dfaf
     * dealer_user_id : 7a79735c-1db1-4147-8e98-48d9faba0966
     * dealer : {"id":"1a6d0048-6986-4639-8401-3471d395dfaf","name":"4S店","full_name":"四川华星锦业"}
     * dealer_user : {"id":"7a79735c-1db1-4147-8e98-48d9faba0966","name":"崔同学"}
     * types : [{"id":"759025cb-962e-4608-93af-c70cccec20ba","name":"A保","full_name":"A保套餐"}]
     */

    private String id;
    @JSONField(name = "plate_number")
    private String plateNumber;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "dealer_id")
    private String dealerId;
    @JSONField(name = "dealer_user_id")
    private String dealerUserId;

    private DealerEntity dealer;

    @JSONField(name = "dealer_user")
    private AdvisorEntity advisor;

    private List<TypesEntity> types;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }

    public AdvisorEntity getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorEntity advisor) {
        this.advisor = advisor;
    }

    public List<TypesEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypesEntity> types) {
        this.types = types;
    }
}
