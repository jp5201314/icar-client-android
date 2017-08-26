package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;

import com.tendcloud.tenddata.TCAgent;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.PhotoGalleryV;
import cn.icarowner.icarowner.activity.viewmodel.PhotoGalleryVM;
import cn.icarowner.icarowner.databinding.ActivityPhotoGalleryBinding;

/**
 * PhotoGalleryActivity 大图查看
 * create by 崔婧
 * create at 2017/5/18 下午1:02
 */
public class PhotoGalleryActivity extends BaseActivity implements PhotoGalleryV {


    private ActivityPhotoGalleryBinding binding;
    private PhotoGalleryVM photoGalleryVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "查看大图");
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_gallery);

        Intent intent = getIntent();
        if (intent.hasExtra("LIST") && intent.hasExtra("POSITION")) {
            photoGalleryVM = new PhotoGalleryVM(this, intent.getStringArrayListExtra("LIST"), intent.getExtras().getInt("POSITION"));
            binding.setPhotoGallery(photoGalleryVM);
            this.setViewModel(photoGalleryVM);
        } else {
            toast("查看大图信息错误");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "查看大图");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public Context getContext() {
        return PhotoGalleryActivity.this;
    }

    @Override
    public void closePage() {
        finish();
    }
}
