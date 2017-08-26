package cn.icarowner.icarowner.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * BannerEntity
 * create by 崔婧
 * create at 2017/5/18 下午1:20
 */
public class BannerEntity implements Serializable {

    /**
     * total : 2
     * banners : [{"id":"acd5d225-fc1e-4183-a544-10fadf54957a","title":"新活动","cover_url":"http://domain.image_url.jpg","redirect_url":"http://domain/api/banners/acd5d225-fc1e-4183-a544-10fadf54957a/redirect"},{"id":"df38c5dd-3315-42de-b0f8-58225c31a95d","title":"集团推广","cover_url":"http://domain.introduction_url.jpg","redirect_url":"http://domain/api/banners/df38c5dd-3315-42de-b0f8-58225c31a95d/redirect"}]
     */

    private int total;
    /**
     * id : acd5d225-fc1e-4183-a544-10fadf54957a
     * title : 新活动
     * cover_url : http://domain.image_url.jpg
     * redirect_url : http://domain/api/banners/acd5d225-fc1e-4183-a544-10fadf54957a/redirect
     */

    private List<BannersBean> banners;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class BannersBean {
        private String id;
        private String title;
        @JSONField(name = "cover_url")
        private String coverUrl;
        @JSONField(name = "redirect_url")
        private String redirectUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }
    }
}
