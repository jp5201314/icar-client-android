package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.entity.MaterialsEntity;

/**
 * ItemScheduleDetailMaterialsVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemScheduleDetailMaterialsVM extends BaseObservable {
    public final ObservableField<String> materialName = new ObservableField<>();
    public final ObservableField<String> materialImgUrl = new ObservableField<>();

    public ItemScheduleDetailMaterialsVM(MaterialsEntity materialsEntity) {
        materialName.set(materialsEntity.getName());
        materialImgUrl.set(materialsEntity.getImageUrl());
    }

    @BindingAdapter("materialImage")
    public static void setMaterialImage(ImageView imageView, String imageUrl) {
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.img_default_error)
                .error(R.drawable.img_default_error)
                .into(imageView);
    }
}
