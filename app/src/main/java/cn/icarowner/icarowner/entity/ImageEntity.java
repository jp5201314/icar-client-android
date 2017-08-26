package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ImageEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:21
 */
public class ImageEntity implements Serializable {

    /**
     * image_url : http://domain/tmp/image_url.jpg
     */

    @JSONField(name = "image_url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
