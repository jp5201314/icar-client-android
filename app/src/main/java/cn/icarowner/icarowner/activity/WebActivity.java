package cn.icarowner.icarowner.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.CookieStore;
import java.net.HttpCookie;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.net.PersistentCookieStore;
import cn.icarowner.icarowner.utils.ClickUtil;

/**
 * WebActivity 网页
 * create by 崔婧
 * create at 2017/5/18 下午1:06
 */
public class WebActivity extends BaseActivity {

    private String url;
    private ValueCallback mUploadMessage;
    private TextView tvTitle;
    private ImageButton ibBack;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        this.setStatusBarColor(R.color.color_black_0e1214);
        initView();
        renderWeb();
    }

    private void renderWeb() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        setBarTitle(title);
        if (intent.hasExtra("URL")) {
            url = intent.getStringExtra("URL");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    setTitle(title);
                }

                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                    if (mUploadMessage != null) return false;
                    mUploadMessage = filePathCallback;
                    //selectImage(false);
                    return true;
                }

                // For Android 3.0+
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                    if (mUploadMessage != null) return;
                    mUploadMessage = uploadMsg;
                    //selectImage(true);
                }

                // For Android < 3.0
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    openFileChooser(uploadMsg, "");
                }

                // For Android  > 4.1.1
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    openFileChooser(uploadMsg, acceptType);
                }

            });
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //pbWebView.setProgress(2);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });

            //synCookies(url);
            webView.loadUrl(url);
            // 注册供js调用的本地java方法
            webView.addJavascriptInterface(new JsInterface(), "icarowner");

        } else {
            toast("网页信息错误");
            finish();
        }
    }

    private void setBarTitle(String title) {
        tvTitle.setText(title);
    }

    public void onBackClick(View view) {
        finish();
    }

    //在java中调用js代码
    public void sendInfoToJs(String info) {
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + info + "')");
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        //pbWebView = (ProgressBar) findViewById(R.id.pbWebView);
        webView = (WebView) findViewById(R.id.webView);
    }

    private class JsInterface {
        //在js中调用window.AndroidWebView.showInfoFromJs(info)，便会触发此方法。
        @JavascriptInterface
        public void startFunction(final String info, final String id, final String name) {
            if (info.equals("dealer")) {
                Intent intent = new Intent(WebActivity.this, DealerDetailActivity.class);
                intent.putExtra("dealerName", name);
                intent.putExtra("dealerId", id);
                startActivity(intent);
            }
        }
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(String url) {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        //cookieManager.removeSessionCookie();//移除
        CookieStore store = new PersistentCookieStore(this);

        for (HttpCookie cookie : store.getCookies()) {
            String cookieString = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();
            CookieManager.getInstance().setCookie(url, cookieString);
        }
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!ClickUtil.isFastClick()) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
