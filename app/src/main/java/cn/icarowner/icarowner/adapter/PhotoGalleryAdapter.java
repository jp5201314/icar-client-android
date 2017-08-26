package cn.icarowner.icarowner.adapter;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;

import cn.icarowner.icarowner.activity.view.PhotoGalleryV;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * PhotoGalleryAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:13
 */
public class PhotoGalleryAdapter extends PagerAdapter {

    private PhotoGalleryV photoGalleryV;
    private ArrayList<String> list;

    public PhotoGalleryAdapter(PhotoGalleryV photoGalleryV, ArrayList<String> list) {
        this.photoGalleryV = photoGalleryV;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        if (list.get(position).startsWith("http")) {
            controller.setUri(Uri.parse(list.get(position)));
        } else {
            controller.setUri(Uri.parse("file://" + list.get(position)));
        }
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoDraweeView.setController(controller.build());
        photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                photoGalleryV.closePage();
            }
        });
        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                photoGalleryV.closePage();
            }
        });

        try {
            viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoDraweeView;
    }
}
