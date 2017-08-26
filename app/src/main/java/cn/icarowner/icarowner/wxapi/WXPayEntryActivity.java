package cn.icarowner.icarowner.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.event.WeChatPayEvent;

/**
 * WXPayEntryActivity
 * create by 崔婧
 * create at 2017/5/18 下午1:40
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, Constant.WE_CHAT_APPID);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                if (Constant.isDebug()) {
                    Toast.makeText(WXPayEntryActivity.this, "微信支付成功", Toast.LENGTH_SHORT).show();
                }
                EventBus.getDefault().post(new WeChatPayEvent(1));
                finish();
            } else {
                if (Constant.isDebug()) {
                    Toast.makeText(WXPayEntryActivity.this, "微信支付失败", Toast.LENGTH_SHORT).show();
                }
                EventBus.getDefault().post(new WeChatPayEvent(0));
                finish();
            }
        }
    }

}