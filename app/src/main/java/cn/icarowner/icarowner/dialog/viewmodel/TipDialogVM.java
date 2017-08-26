package cn.icarowner.icarowner.dialog.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

/**
 * TipDialogVM
 * create by 崔婧
 * create at 2017/5/18 下午1:18
 */
public class TipDialogVM extends BaseObservable {
    public final ObservableField<Boolean> showOrDismiss = new ObservableField<>();

    public final ObservableField<Boolean> withTitle = new ObservableField<>();
    public final ObservableField<Integer> iconSrc = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<Boolean> withLeftBtn = new ObservableField<>();
    public final ObservableField<String> leftBtnStr = new ObservableField<>();
    public final ObservableField<Boolean> withRightBtn = new ObservableField<>();
    public final ObservableField<String> rightBtnStr = new ObservableField<>();
    public final ObservableField<Boolean> withDivider = new ObservableField<>();

    public TipDialogVM(String title, Integer iconSrc, String content, String leftBtnStr, String rightBtnStr) {
        this.showOrDismiss.set(false);
        this.withTitle.set(null != title);
        this.title.set(title);
        this.iconSrc.set(iconSrc);
        this.content.set(content);

        this.withLeftBtn.set(null != leftBtnStr);
        if (!TextUtils.isEmpty(leftBtnStr)) this.leftBtnStr.set(leftBtnStr);
        this.withRightBtn.set(null != rightBtnStr);
        if (!TextUtils.isEmpty(rightBtnStr)) this.rightBtnStr.set(rightBtnStr);
        this.withDivider.set(null != leftBtnStr && null != rightBtnStr);
    }

    @BindingAdapter("srcRes")
    public static void bindSrcRes(ImageView imageView, int res) {
        imageView.setImageResource(res);
    }

    /**
     * OnLeftBtnClick Listener
     *
     * @param view
     */
    public void onLeftBtnClick(View view) {
        showOrDismiss.set(false);
    }

    /**
     * OnRightBtnClick
     *
     * @param view
     */
    public void onRightBtnClick(View view) {
        showOrDismiss.set(false);
    }
}