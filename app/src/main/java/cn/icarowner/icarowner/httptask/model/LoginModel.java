package cn.icarowner.icarowner.httptask.model;

import cn.icarowner.icarowner.entity.UserEntity;

/**
 * LoginModel
 * create by 崔婧
 * create at 2017/5/18 下午1:29
 */
public class LoginModel extends UserEntity {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
