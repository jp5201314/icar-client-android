package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * DealerEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class DealerEntity implements Serializable {

    /**
     * id : ddaceee2-5206-4370-8655-7e527b2029a7
     * name : 羊西店
     * full_name : 四川华星锦业羊西店
     * logo_url : http://logo.jpg
     * cover_url : http://cover.jpg
     * address : 四川省成都市羊犀立交101号
     * contact_phone : 028-87120900
     * rescue_phone : 028-99889098
     * detail_url : http://localhost/api/dealers/ddaceee2-5206-4370-8655-7e527b2029a7/detail_web
     * service_types : [{"id":"82025cfe-a9ae-484f-ba31-c8b0ba8dfe64","name":"A保","check_items":["01.测试制动系统","02.复位保养间隔指示器","03.清洁导雨槽"],"material_items":[{"name":"机油","image_url":"http://domain.com/image.jpg"},{"name":"机油滤清器","image_url":"http://domain.com/image.jpg"},{"name":"空气滤清器","image_url":"http://domain.com/image.jpg"}]}]
     * status : 0
     */


    private String id;
    private String name;
    @JSONField(name = "full_name")
    private String fullName;
    @JSONField(name = "logo_url")
    private String logoUrl;
    @JSONField(name = "cover_url")
    private String coverUrl;
    private String address;
    @JSONField(name = "contact_phone")
    private String contactPhone;
    @JSONField(name = "rescue_phone")
    private String rescuePhone;
    @JSONField(name = "detail_url")
    private String detailUrl;
    @JSONField(name = "service_types")
    private List<ServiceTypesEntity> serviceTypes;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getRescuePhone() {
        return rescuePhone;
    }

    public void setRescuePhone(String rescuePhone) {
        this.rescuePhone = rescuePhone;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ServiceTypesEntity> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<ServiceTypesEntity> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }
}
