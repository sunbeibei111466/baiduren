package com.yl.baiduren;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.BASE64Encoder;
import com.yl.baiduren.utils.LUtils;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by sunbeibei on 2018/4/13.
 */

public class Downlod_Pdf_Activity extends BaseActivity implements View.OnClickListener {
    private ProgressBar pro;
    // private String fileUrl = "https://pic.bincrea.com/bc_bg_6D40C91A170D41C182511ABBB8A634A4.pdf";
    private String fileUrl;
    private ImageView iv_pdf_finish;


    @Override
    public int loadWindowLayout() {
        return R.layout.downlod_pdf_activity;
    }

    @Override
    public void initViews() {

        Long userId = DataWarehouse.getBaseRequest(this).uid;
        String reportUrl=getIntent().getStringExtra("reportUrl");
        int reportId = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        int status = getIntent().getIntExtra("status", 0);

        TextView tv_pdf = findViewById(R.id.tv_pdf);
        if (!TextUtil.isEmpty(name)) {
            tv_pdf.setText(name);
        } else {
            tv_pdf.setText("pdf预览");
        }

        if(!TextUtils.isEmpty(reportUrl)){
            fileUrl=reportUrl;
            LUtils.e("-------fileUrl-------", fileUrl);
        }
        if (reportId != 0) {
//            "static.jxzcbd.com/pdf/"+userId+"/"+reportId+".pdf";
//            String url="http://static.jxzcbd.com/pdf/"+userId+"/"+reportId+".pdf";
//            fileUrl = ServiceUrl.H5_MAIN_PATH + "credit/showStream.pdf?reportId=" + reportId + "&userId=" + userId;
//            LUtils.e("-------fileUrl-------", fileUrl);
//            fileUrl=url;
        }
        android.webkit.WebView pdf_show_webview = findViewById(R.id.pdf_show_webview);
        TextView tv_wait = findViewById(R.id.tv_wait);
        iv_pdf_finish = findViewById(R.id.iv_pdf_finish);
        iv_pdf_finish.setOnClickListener(this);
        pro = findViewById(R.id.pro);
        pdf_show_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                pro.setVisibility(View.GONE);
            }
        });

        WebSettings settings = pdf_show_webview.getSettings();
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        pdf_show_webview.setWebChromeClient(new WebChromeClient());


        if (!"".equals(fileUrl)) {
            byte[] bytes = null;
            try {// 获取以字符编码为utf-8的字符
                bytes = fileUrl.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            if (bytes != null) {
                fileUrl = new BASE64Encoder().encode(bytes);// BASE64转码
            }
        }
        pdf_show_webview.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file="
                + fileUrl);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_pdf_finish) {
            finish();
        }
    }
}
