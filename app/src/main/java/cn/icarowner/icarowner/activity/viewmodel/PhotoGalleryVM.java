package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;

import cn.icarowner.icarowner.activity.view.PhotoGalleryV;
import cn.icarowner.icarowner.adapter.PhotoGalleryAdapter;
import cn.icarowner.icarowner.customizeview.MultiTouchViewPager;

/**
 * PhotoGalleryVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class PhotoGalleryVM extends BaseVM {

    public final ObservableField<String> imageUrl = new ObservableField<>();

    public ToolBarTitleVM toolBarTitleVM;

    public int position;
    public PhotoGalleryAdapter adapter;

    private ArrayList<String> list;
    private PhotoGalleryV photoGalleryV;

    public PhotoGalleryVM(PhotoGalleryV photoGalleryV, ArrayList<String> list, int position) {
        this.photoGalleryV = photoGalleryV;
        this.list = list;
        this.position = position;
        this.adapter = new PhotoGalleryAdapter(photoGalleryV, list);
        this.initToolBar();
    }

    @BindingAdapter("adapter")
    public static void bindAdapter(MultiTouchViewPager multiTouchViewPager, PagerAdapter pagerAdapter) {
        multiTouchViewPager.setAdapter(pagerAdapter);
    }

    @BindingAdapter("currentItem")
    public static void bindCurrentItem(MultiTouchViewPager multiTouchViewPager, int position) {
        multiTouchViewPager.setCurrentItem(position);
    }

    public void initToolBar() {
        this.toolBarTitleVM = new ToolBarTitleVM("服务单附件") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                photoGalleryV.closePage();
            }
        };
    }
}
