package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * MaterialsEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:22
 */
public class MaterialsEntity implements Serializable {
    /**
     * name : 机油
     * image_url : http://domain.com/image.jpg
     */
    private String name;
    @JSONField(name = "image_url")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
