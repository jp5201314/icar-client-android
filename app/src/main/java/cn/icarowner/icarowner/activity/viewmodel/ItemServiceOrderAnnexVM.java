package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ServiceOrderDetailV;

/**
 * ItemServiceOrderAnnexVM
 * create by 崔婧
 * create at 2017/5/18 上午11:57
 */
public class ItemServiceOrderAnnexVM extends BaseVM {
    public final ObservableField<String> annexUrl = new ObservableField<>();

    private ArrayList<String> imageUrlList;
    private String imageUrl;
    private int position;
    private ServiceOrderDetailV serviceOrderDetailV;

    public ItemServiceOrderAnnexVM(ArrayList imageUrlList, int position, String imageUrl, ServiceOrderDetailV serviceOrderDetailV) {
        this.imageUrlList = imageUrlList;
        this.position = position;
        this.imageUrl = imageUrl;
        this.serviceOrderDetailV = serviceOrderDetailV;
        annexUrl.set(imageUrl);
    }

    @BindingAdapter("annexUrl")
    public static void setAnnexUrl(ImageView imageView, String annexUrl) {
        Picasso.with(imageView.getContext())
                .load(annexUrl)
                .centerCrop()
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_interruption)
                .resize(320, 320)
                .into(imageView);
    }

    public void onItemClick(View view) {
        serviceOrderDetailV.jumpToBigImageScan(imageUrlList, position);
    }
}
