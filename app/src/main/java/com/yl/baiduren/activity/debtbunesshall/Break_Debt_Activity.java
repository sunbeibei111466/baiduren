package com.yl.baiduren.activity.debtbunesshall;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.utils.LUtils;

/**
 * 解债 各个支付协议
 */

public class Break_Debt_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_buness_hall_back;
    private com.tencent.smtt.sdk.WebView webView;
    private ProgressBar progressBar2;

    @Override
    public int loadWindowLayout() {

        return R.layout.break_debt_acvivty;
    }

    @Override
    public void initViews() {
        String url = getIntent().getStringExtra("url");
        LUtils.e(url);
        int type = getIntent().getIntExtra("type", 0);
        TextView debt_details_name = findViewById(R.id.debt_details_name);
        if (type == 1) {//从解债跳转
            debt_details_name.setText("解债");
        } else if (type == 2) {//从备案第一步跳转
            debt_details_name.setText("备案用户需知协议");
        } else if (type == 3) {//用户注册协议
            debt_details_name.setText("用户服务协议");
        } else if (type == 4) {//用户支付协议
            debt_details_name.setText("支付服务协议");
        } else if (type == 5) {
            debt_details_name.setText("债事人员");
        } else if (type == 6) {
            debt_details_name.setText("债事机构");
        } else if (type == 7) {
            debt_details_name.setText("解债天使");
        }else if (type == 8) {
            debt_details_name.setText("国民征信企业信用报告使用协议");
        }else if (type == 9) {
            debt_details_name.setText("授权委托书");
        }else if (type == 10) {
            debt_details_name.setText("委托方承诺书");
        }
        iv_buness_hall_back = findViewById(R.id.iv_buness_hall_back);
        iv_buness_hall_back.setOnClickListener(this);
        progressBar2=findViewById(R.id.progressBar2);
         webView = findViewById(R.id.webview);
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient());
        webView.setWebChromeClient(webChromeClient);
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setDomStorageEnabled(true);//开启js dom storage api功能
        //这似乎是因为硬件加速canvas渲染不支持Chromium WebView，这一行代码可以关闭硬件加速的canvas
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(url);

    }
    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView webView, int i) {
            if(i==100){
                progressBar2.setVisibility(View.GONE);//加载完网页进度条消失
            }
            else{
                progressBar2.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar2.setProgress(i);//设置进度值
            }
            super.onProgressChanged(webView, i);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://处理返回键事件
                if (webView.canGoBack()) {
                    webView.goBack();//让WebView回退到上一个网页
                    return true;
                } else {//如果WebView不能回退
                    finish();
                    webView.destroy();
                }
                break;
            case KeyEvent.KEYCODE_SEARCH://当
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == iv_buness_hall_back) {
            if (webView.canGoBack()) {
                webView.goBack();//让WebView回退到上一个网页
            } else {
                finish();
                webView.destroy();
            }
        }
    }
}
