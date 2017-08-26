package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * ServiceTypesEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class ServiceTypesEntity implements Serializable {
    /**
     * id : 82025cfe-a9ae-484f-ba31-c8b0ba8dfe64
     * name : A保
     * check_items : ["01.测试制动系统","02.复位保养间隔指示器","03.清洁导雨槽"]
     * material_items : [{"name":"机油","image_url":"http://domain.com/image.jpg"},{"name":"机油滤清器","image_url":"http://domain.com/image.jpg"},{"name":"空气滤清器","image_url":"http://domain.com/image.jpg"}]
     */

    private String id;
    private String name;
    @JSONField(name = "check_items")
    private List<String> checkItems;

    @JSONField(name = "material_items")
    private List<MaterialsEntity> materialItems;

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

    public List<String> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<String> checkItems) {
        this.checkItems = checkItems;
    }

    public List<MaterialsEntity> getMaterialItems() {
        return materialItems;
    }

    public void setMaterialItems(List<MaterialsEntity> materialItems) {
        this.materialItems = materialItems;
    }
}
