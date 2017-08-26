package cn.icarowner.icarowner.httptask.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * GetAvatarModel
 * create by 崔婧
 * create at 2017/5/18 下午1:28
 */
public class GetAvatarModel {
    @JSONField(name = "image_url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
