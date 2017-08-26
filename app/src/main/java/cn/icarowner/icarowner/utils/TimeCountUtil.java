package cn.icarowner.icarowner.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import cn.icarowner.icarowner.R;

/**
 * TimeCountUtil
 * create by 崔婧
 * create at 2017/5/18 下午1:40
 */
public class TimeCountUtil extends CountDownTimer {

    private Context mActivity;
    private Button btn;//按钮
    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了

    public TimeCountUtil(Context context, long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity = context;
        this.btn = btn;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText(millisUntilFinished / 1000 + "s");//设置倒计时时间
        //设置按钮为灰色，这时是不能点击的
        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_get_valid_code_press));
        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        btn.setText(span);
    }


    @Override
    public void onFinish() {
        btn.setText("重发");
        btn.setClickable(true);//重新获得点击
        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_get_valid_code_normal));//还原背景色
    }


}
