package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * GroupEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class GroupEntity implements Serializable {

    /**
     * id : 37737208-28ba-4a16-bc3d-738ebbd9ff09
     * name : 四川华星汽车集团有限公司
     * logo_url : http://domain/logo_url
     * status : 0
     */

    private String id;
    private String name;
    @JSONField(name = "logo_url")
    private String logoUrl;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
