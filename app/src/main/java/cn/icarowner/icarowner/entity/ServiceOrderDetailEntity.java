package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * ServiceOrderDetailEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class ServiceOrderDetailEntity implements Serializable {

    /**
     * 已创建
     */
    public final static int STATUS_CREATED = 0;

    /**
     * 已进厂
     */
    public final static int STATUS_INTO_FACTORY = 10;

    /**
     * 上钟
     */
    public final static int STATUS_WORKING = 20;

    /**
     * 暂停
     */
    public final static int STATUS_PAUSED = 30;

    /**
     * 等待复钟
     */
    public final static int STATUS_PENDING_REPEAT = 40;

    /**
     * 下钟
     */
    public final static int STATUS_WORK_COMPLETE = 50;

    /**
     * 终检
     */
    public final static int STATUS_FINAL_INSPECTION = 60;

    /**
     * 终检完成
     */
    public final static int STATUS_FINAL_INSPECTION_COMPLETE = 70;

    /**
     * 洗车
     */
    public final static int STATUS_WASHING_CAR = 74;

    /**
     * 洗车完成
     */
    public final static int STATUS_WASHING_CAR_COMPLETE = 76;

    /**
     * 已出厂
     */
    public final static int STATUS_OUT_FACTORY = 80;

    /**
     * 订单取消
     */
    public final static int STATUS_PENDING_PAY_CANCELED = 88;

    /**
     * 等待支付
     */
    public final static int STATUS_PENDING_PAY = 90;

    /**
     * 支付完成
     */
    public final static int STATUS_PAY_COMPLETE = 100;

    /**
     * 支付已确认
     */
    public final static int STATUS_PAY_ALREADY_CONFIRMED = 110;

    /**
     * 已完成
     */
    public final static int STATUS_FINISH = 120;

    /**
     * total : 2
     * id : d165faf9-2f04-4f2d-bd4b-26da80b9513b
     * dealer_id : 8986937c-f297-41c1-8ff0-3ae071d7b835
     * dealer : {"id":"8986937c-f297-41c1-8ff0-3ae071d7b835","name":"四川华星锦业","full_name":"四川华星锦业羊西店","logo_url":"http://logo.jpg","cover_url":"http://cover.jpg","address":"四川省成都市羊犀立交101号","contact_phone":"028-87120900","rescue_phone":"028-99889098","service_types":[{"id":"82025cfe-a9ae-484f-ba31-c8b0ba8dfe64","name":"A保","check_items":["01.测试制动系统","02.复位保养间隔指示器","03.清洁导雨槽"],"material_items":[{"name":"机油","image_url":"http://domain.com/image.jpg"},{"name":"机油滤清器","image_url":"http://domain.com/image.jpg"},{"name":"空气滤清器","image_url":"http://domain.com/image.jpg"}]}]}
     * dealer_user_id : 08ed47f3-c795-4ecb-9518-2a7506e9ade8
     * dealer_user : {"id":"08ed47f3-c795-4ecb-9518-2a7506e9ade8","mobile":"18900007777","role":"service_advisor","name":"张顾问","avatar_url":"http://avatar.jpg","introduction":"顾问的自我简介","star":"5"}
     * user_id : 71003f4d-aa0c-4923-955d-324b004ee364
     * types : [{"id":"368a9d26-cde7-448c-bc9b-cef53b99294c","name":"A保","full_name":"A保套餐","bill_items":["套餐","其他"]}]
     * mobile : 13800001111
     * plate_number : 川A9L123
     * customer_name : 张三
     * kilometers : 12000
     * next_kilometers : 22000
     * wip : AB12JKOKLL99999
     * estimated_waiting_time : 10
     * estimated_time_of_taking_car : 2016-12-01 09:20:15
     * image_urls : ["http://domain/logo_url.jpg"]
     * status : 0
     * created_at : 2016-10-03 21:21:13
     * bill : {"id":"dd1588b9-5b39-447f-a9bd-faf4f5b525db","created_at":"2016-10-08 11:07:02","types":[{"id":"4ce94086-8410-4f4d-8e35-60d2803778a5","name":"A保","full_name":"A保套餐","bill_items":["套餐","其他"],"bill_detail_items":[{"key":"套餐","amount":40000},{"key":"其他","amount":10000}]}],"order":{"id":"6fe4985e-17c1-4361-87d4-44ea270f3ceb","order_no":"2016100812280856100481","user_id":"641dc61c-2915-3e28-8a1b-150366084eae","amount":50000,"discount":0,"pay_amount":50000,"status":1,"pay_channel":null,"payed_at":null,"created_at":"2016-10-08 12:26:08","goods":[{"id":"5e01fbce-8399-47ee-a562-5538a45c1596","name":"A保套餐","describe":"A保套餐","price":0,"pay_price":50000,"number":1}],"bound_coupon":{"id":"98b016d8-1a3b-4671-987b-ecfe41dde534","name":"A保","discount":5000,"effective_at":"2016-10-17 17:12:06","expired_at":"2016-10-19 17:12:06"}}}
     * overtime_compensate : {"id":"98b016d8-1a3b-4671-987b-ecfe41dde534","types":["98b016d8-1a3b-4671-987b-ecfe41dde534"],"basic_items_spend":60,"add_items":[{"name":"增项1","spend":1}],"total_spend":66,"actual_into_factory_at":null,"actual_out_factory_at":null,"actual_spend":null,"overtime":null,"compensate_fee":null}
     * into_factory_at : 2016-10-03 21:21:18
     * out_factory_at : 2016-10-03 21:21:19
     * worked_at : 2016-10-03 21:21:19
     * final_inspection_at : 2016-10-03 21:21:19
     * final_inspection_completed_at : 2016-10-03 21:21:19
     * wash_completed_at : 2016-10-03 21:21:15
     */

    private int total;
    private String id;
    @JSONField(name = "dealer_id")
    private String dealerId;
    private DealerEntity dealer;
    @JSONField(name = "dealer_user_id")
    private String dealerUserId;
    @JSONField(name = "dealer_user")
    private AdvisorEntity advisor;
    @JSONField(name = "user_id")
    private String userId;
    private String mobile;
    @JSONField(name = "plate_number")
    private String plateNumber;
    @JSONField(name = "customer_name")
    private String customerName;
    private int kilometers;
    @JSONField(name = "next_kilometers")
    private int nextKilometers;
    private String wip;
    @JSONField(name = "estimated_waiting_time")
    private int estimatedWaitingTime;
    @JSONField(name = "estimated_time_of_taking_car")
    private String estimatedTimeOfTakingCar;
    private int status;
    @JSONField(name = "created_at")
    private String createdAt;
    private BillEntity bill;
    @JSONField(name = "overtime_compensate")
    private OvertimeCompensateEntity overtimeCompensate;
    @JSONField(name = "into_factory_at")
    private String intoFactoryAt;
    @JSONField(name = "out_factory_at")
    private String outFactoryAt;
    @JSONField(name = "worked_at")
    private String workedAt;
    @JSONField(name = "final_inspection_at")
    private String finalInspectionAt;
    @JSONField(name = "final_inspection_completed_at")
    private String finalInspectionCompletedAt;
    private List<TypesEntity> types;
    @JSONField(name = "image_urls")
    private List<String> imageUrls;
    @JSONField(name = "wash_completed_at")
    private String washCompletedAt;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public DealerEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerEntity dealer) {
        this.dealer = dealer;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public AdvisorEntity getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorEntity advisor) {
        this.advisor = advisor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getNextKilometers() {
        return nextKilometers;
    }

    public void setNextKilometers(int nextKilometers) {
        this.nextKilometers = nextKilometers;
    }

    public String getWip() {
        return wip;
    }

    public void setWip(String wip) {
        this.wip = wip;
    }

    public int getEstimatedWaitingTime() {
        return estimatedWaitingTime;
    }

    public void setEstimatedWaitingTime(int estimatedWaitingTime) {
        this.estimatedWaitingTime = estimatedWaitingTime;
    }

    public String getEstimatedTimeOfTakingCar() {
        return estimatedTimeOfTakingCar;
    }

    public void setEstimatedTimeOfTakingCar(String estimatedTimeOfTakingCar) {
        this.estimatedTimeOfTakingCar = estimatedTimeOfTakingCar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }

    public OvertimeCompensateEntity getOvertimeCompensate() {
        return overtimeCompensate;
    }

    public void setOvertimeCompensate(OvertimeCompensateEntity overtimeCompensate) {
        this.overtimeCompensate = overtimeCompensate;
    }

    public String getIntoFactoryAt() {
        return intoFactoryAt;
    }

    public void setIntoFactoryAt(String intoFactoryAt) {
        this.intoFactoryAt = intoFactoryAt;
    }

    public String getOutFactoryAt() {
        return outFactoryAt;
    }

    public void setOutFactoryAt(String outFactoryAt) {
        this.outFactoryAt = outFactoryAt;
    }

    public String getWorkedAt() {
        return workedAt;
    }

    public void setWorkedAt(String workedAt) {
        this.workedAt = workedAt;
    }

    public String getFinalInspectionAt() {
        return finalInspectionAt;
    }

    public void setFinalInspectionAt(String finalInspectionAt) {
        this.finalInspectionAt = finalInspectionAt;
    }

    public String getFinalInspectionCompletedAt() {
        return finalInspectionCompletedAt;
    }

    public void setFinalInspectionCompletedAt(String finalInspectionCompletedAt) {
        this.finalInspectionCompletedAt = finalInspectionCompletedAt;
    }

    public List<TypesEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypesEntity> types) {
        this.types = types;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getWashCompletedAt() {
        return washCompletedAt;
    }

    public void setWashCompletedAt(String washCompletedAt) {
        this.washCompletedAt = washCompletedAt;
    }
}
