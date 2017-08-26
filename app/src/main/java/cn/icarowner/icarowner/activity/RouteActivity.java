package cn.icarowner.icarowner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.icarowner.icarowner.manager.AppManage;

/**
 * RouteActivity 推送跳转路由---中转页面
 * create by 崔婧
 * create at 2017/5/18 下午1:03
 */
public class RouteActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra("dest_class")) {
            if (AppManage.isAppAlive()) {
                intent.setClass(RouteActivity.this, (Class) getIntent().getSerializableExtra("dest_class"));
                startActivity(intent);
            } else {
                intent.setClass(RouteActivity.this, ServiceActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }
}
