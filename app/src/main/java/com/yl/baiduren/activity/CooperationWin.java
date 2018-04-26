package com.yl.baiduren.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;

import java.util.List;


/**
 * 合作共赢
 */
public class CooperationWin extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_win_left;//返回
    private com.tencent.smtt.sdk.WebView webview_win;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_cooperation_win;
    }

    @Override
    public void initViews() {
        iv_title_win_left = findViewById(R.id.iv_title_win_left);
        iv_title_win_left.setOnClickListener(this);
        webview_win = findViewById(R.id.webview_win);
        com.tencent.smtt.sdk.WebSettings webSettings = webview_win.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setDomStorageEnabled(true);//开启js dom storage api功能
        List<BaseRequest> baseRequestList=GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        String url = null;
        if (UserInfoUtils.IsLogin(this)){

           url = ServiceUrl.H5_MAIN_PATH+"debt/getTotal?uid="+baseRequestList.get(0).getUid();
       }else {
           url = ServiceUrl.H5_MAIN_PATH+"debt/getTotal?";
       }
        LUtils.e(url);
        webview_win.addJavascriptInterface(new Success(this),"Success");
        webview_win.loadUrl(url);
        webview_win.setWebChromeClient(webChromeClient);
        webview_win.setWebViewClient(webViewClient);
//
    }
    private com.tencent.smtt.sdk.WebViewClient webViewClient=new com.tencent.smtt.sdk.WebViewClient(){
        @Override
        public void onLoadResource(com.tencent.smtt.sdk.WebView webView, String s) {
            super.onLoadResource(webView, s);
            LUtils.e("-onLoadResource---地址-------",s);
        }

        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
            LUtils.e("--shouldOverrideUrlLoading--地址-------",s);
            return super.shouldOverrideUrlLoading(webView, s);
        }
    };


    private WebChromeClient webChromeClient=new WebChromeClient() {

        @Override
        public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, JsResult jsResult) {
            if (s1 != null) {
                //弹出对话框
                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                LUtils.e("------onJsAlert------"+s1);
            }
            return true;
        }


        @Override
        public boolean onJsConfirm(com.tencent.smtt.sdk.WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsConfirm(webView, s, s1, jsResult);
        }

        @Override
        public boolean onJsPrompt(com.tencent.smtt.sdk.WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }

    };

    @Override
    public void onClick(View v) {
        if (v == iv_title_win_left) {
            if (webview_win.canGoBack()) {
                webview_win.goBack();//让WebView回退到上一个网页
            } else {//如果WebView不能回退
                finish();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://处理返回键事件
                if (webview_win.canGoBack()) {
                    webview_win.goBack();//让WebView回退到上一个网页
                    return true;
                } else {//如果WebView不能回退
                    finish();
                }
                break;
            case KeyEvent.KEYCODE_SEARCH://当
                break;
            default:
                break;
        }
        return false;
    }
}


class Success{
    private Activity context;

    public Success(Activity context) {
        this.context = context;
    }

    @JavascriptInterface
    public void getSuccess(String type){
        LUtils.e("--------"+type);
        ToastUtil.showShort(context,type);
        if(type.equals("申请成功,请等待后台人员审核")){
            context.finish();
        }
    }
}