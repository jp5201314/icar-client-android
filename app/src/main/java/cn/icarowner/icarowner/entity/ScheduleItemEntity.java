package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * ScheduleItemEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class ScheduleItemEntity implements Serializable {

    /**
     * id : 70007950-e634-4bc3-8ade-8fa5cf48abb0
     * plate_number : 川A20286
     * created_at : 2016-10-12 10:54:11
     * into_factory_at : 2016-10-12 10:54:11
     * dealer_id : 1a6d0048-6986-4639-8401-3471d395dfaf
     * dealer_user_id : 7a79735c-1db1-4147-8e98-48d9faba0966
     * estimated_time_of_taking_car : 2016-10-12 10:54:11
     * joined_overtime_compensate : 0
     * types : [{"id":"759025cb-962e-4608-93af-c70cccec20ba","name":"A保","full_name":"A保套餐"}]
     */

    private String id;
    @JSONField(name = "plate_number")
    private String plateNumber;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "into_factory_at")
    private String intoFactoryAt;
    @JSONField(name = "dealer_id")
    private String dealerId;
    @JSONField(name = "dealer_user_id")
    private String dealerUserId;
    @JSONField(name = "estimated_time_of_taking_car")
    private String estimatedTimeOfTakingCar;
    @JSONField(name = "joined_overtime_compensate")
    private int overtimeCompensate;
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

    public String getIntoFactoryAt() {
        return intoFactoryAt;
    }

    public void setIntoFactoryAt(String intoFactoryAt) {
        this.intoFactoryAt = intoFactoryAt;
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

    public String getEstimatedTimeOfTakingCar() {
        return estimatedTimeOfTakingCar;
    }

    public void setEstimatedTimeOfTakingCar(String estimatedTimeOfTakingCar) {
        this.estimatedTimeOfTakingCar = estimatedTimeOfTakingCar;
    }

    public int getOvertimeCompensate() {
        return overtimeCompensate;
    }

    public void setOvertimeCompensate(int overtimeCompensate) {
        this.overtimeCompensate = overtimeCompensate;
    }

    public List<TypesEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypesEntity> types) {
        this.types = types;
    }
}
