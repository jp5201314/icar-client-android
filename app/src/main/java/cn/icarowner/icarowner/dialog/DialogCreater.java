package cn.icarowner.icarowner.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * DialogCreater
 * create by 崔婧
 * create at 2017/5/18 下午1:18
 */
public class DialogCreater {
    /**
     * 创建带图片的文字listdialog
     *
     * @param context
     * @param title
     * @param items
     * @return
     */
    public static NormalListDialog createImageListDialog(Context context, String title, ArrayList<DialogMenuItem> items, OnOperItemClickL l) {
        final NormalListDialog dialog = new NormalListDialog(context, items);
        dialog.isTitleShow(null != title);
        dialog.title(title)//
                .titleTextSize_SP(16)//
                .titleBgColor(Color.parseColor("#ffffff"))//
                .itemPressColor(Color.parseColor("#dedede"))//
                .itemTextColor(Color.parseColor("#000000"))//
                .titleTextColor(Color.parseColor("#ffffff"))
                .titleBgColor(Color.parseColor("#000000"))
                .titleBgColor(Color.parseColor("darkgray"))
                .itemTextSize(16)//
                .cornerRadius(0)//
                .widthScale(0.8f);
        dialog.setOnOperItemClickL(l);
        return dialog;
    }

    /**
     * 创建文字listdialog
     *
     * @param context
     * @param title
     * @param items
     * @return
     */
    public static NormalListDialog createTextListDialog(Context context, String title, String[] items, OnOperItemClickL l) {
        final NormalListDialog dialog = new NormalListDialog(context, items);
        dialog.isTitleShow(null != title);
        dialog.title(title)//
                .titleTextSize_SP(16)//
                .titleBgColor(Color.parseColor("#3CB4BC"))//
                .itemPressColor(Color.parseColor("#3CB4BC"))//
                .itemTextColor(Color.parseColor("#000000"))//
                .titleTextColor(Color.parseColor("#ffffff"))
                .itemTextSize(16)//
                .cornerRadius(5f)//
                .widthScale(0.8f);
        dialog.setOnOperItemClickL(l);
        return dialog;
    }

    /**
     * 创建电话列表的dialog
     *
     * @param context
     * @param title
     * @param items
     * @return
     */
    public static OneKeyHelpDialog createOneKeyHelpDialog(Context context, String title, String[] items, OnOperItemClickL l) {
        final OneKeyHelpDialog dialog = new OneKeyHelpDialog(context, items);
        dialog.isTitleShow(null != title);
        dialog.title(title)//
                .titleTextSize_SP(16)//
                .titleBgColor(Color.parseColor("#3CB4BC"))//
                .itemPressColor(Color.parseColor("#C7C7C7"))//
                .itemTextColor(Color.parseColor("#0E1214"))//
                .titleTextColor(Color.parseColor("#ffffff"))
                .itemTextSize(24)//
                .cornerRadius(5f)//
                .widthScale(0.8f);
        dialog.setOnOperItemClickL(l);
        return dialog;
    }

    /**
     * 创建一般的带确定和取消按钮的dialog
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static NormalDialog createNormalDialog(Context context, String title, String content, OnBtnClickL l) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content(content)//
                .title(title)
                .style(NormalDialog.STYLE_TWO)//
                .titleTextSize(18)
                .contentTextSize(14);
        OnBtnClickL cancelBtnClickL = new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        };
        dialog.setOnBtnClickL(cancelBtnClickL, l);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 创建带图标带确定和取消按钮的dialog
     *
     * @param context
     * @param titleIcon
     * @param content
     * @return
     */
    public static SureTipDialog.Builder createUpdateVersionDialog(Context context, int titleIcon,
                                                                  String content,
                                                                  String positiveText, String negativeText,
                                                                  int positiveTextColor, int negativeTextColor,
                                                                  DialogInterface.OnClickListener positiveClickListener,
                                                                  DialogInterface.OnClickListener negativeClickListener) {

        final SureTipDialog.Builder builder = new SureTipDialog.Builder(context);
        builder.setMessage(content);
        builder.setIcon(titleIcon);
        builder.setPositiveButton(positiveText, positiveTextColor, positiveClickListener);
        builder.setNegativeButton(negativeText, negativeTextColor, negativeClickListener
        );
        return builder;
    }

    /**
     * 创建确定dialog
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static NormalDialog createTipsDialog(Context context, String title, String content, String btnText, boolean cancelable, OnBtnClickL l) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.content(content)//
                .title(title)
                .btnText(btnText)
                .titleTextSize(18)
                .contentTextSize(14)
                .style(NormalDialog.STYLE_ONE);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setOnBtnClickL(l);
        return dialog;
    }

    public static ACProgressFlower createProgressDialog(Context context, String text) {
        return new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.GRAY)
                .bgColor(Color.WHITE)
                .text(text)
                .textColor(Color.BLACK)
                .fadeColor(Color.DKGRAY)
                .build();
    }


//
//    /**
//     * 创建分享dialog
//     * @param context
//     * @return
//     */
//    public static NormalListDialog createShareDialog(Context context, OnOperItemClickL l){
//        ArrayList<DialogMenuItem> list = new ArrayList<>();
//        list.add(new DialogMenuItem("QQ", R.drawable.icon_share_qq));
//        list.add(new DialogMenuItem("微信", R.drawable.icon_share_wechat));
//        list.add(new DialogMenuItem("朋友圈", R.drawable.icon_share_square));
//        list.add(new DialogMenuItem("微博", R.drawable.icon_share_sina));
//        final NormalListDialog dialog = createImageListDialog(context, "分享", list, l);
//        return dialog;
//    }

    /**
     * 创建sheet dialog
     *
     * @param context
     * @param title
     * @param items
     * @return
     */
    public static ActionSheetDialog createActionSheetDialog(Context context, String title, String[] items, OnOperItemClickL l) {
        ActionSheetDialog dialog = new ActionSheetDialog(context, items, null);
        dialog.isTitleShow(null != title);
        dialog.title(title);
        dialog.setOnOperItemClickL(l);
        return dialog;
    }

    /**
     * 创建带确定和取消按钮的dialog
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static UpdateTipDialog.Builder createUpdateTipDialog(Context context, String title,
                                                                String content,
                                                                String positiveText, String negativeText,
                                                                int positiveTextColor, int negativeTextColor,
                                                                DialogInterface.OnClickListener positiveClickListener,
                                                                DialogInterface.OnClickListener negativeClickListener) {

        final UpdateTipDialog.Builder builder = new UpdateTipDialog.Builder(context);
        builder.setMessage(content);
        builder.setTitle(title);
        builder.setPositiveButton(positiveText, positiveTextColor, positiveClickListener);
        builder.setNegativeButton(negativeText, negativeTextColor, negativeClickListener
        );
        return builder;
    }
}
