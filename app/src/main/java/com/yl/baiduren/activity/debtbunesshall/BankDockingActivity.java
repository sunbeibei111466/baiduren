package com.yl.baiduren.activity.debtbunesshall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tencent.smtt.sdk.WebView;
import com.yl.baiduren.R;
import com.yl.baiduren.service.ServiceUrl;

/**
 * 银行对接
 */
public class BankDockingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_shuoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_docking);
        iv_shuoming = findViewById(R.id.iv_shuoming);//关闭页面
        iv_shuoming.setOnClickListener(this);


        WebView webView = findViewById(R.id.debt_bank_name);
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setDomStorageEnabled(true);//开启js dom storage api功能
        webView.loadUrl(ServiceUrl.H5_YH_PATH);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_shuoming) {
            finish();
        }
    }
}