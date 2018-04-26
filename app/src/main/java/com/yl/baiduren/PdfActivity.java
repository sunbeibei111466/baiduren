package com.yl.baiduren;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.LUtils;

import org.feezu.liuli.timeselector.Utils.TextUtil;

/**
 * pdf 预览
 */
public class PdfActivity extends BaseActivity implements View.OnClickListener{

    private WebView mWebView;
    private ProgressBar pro;
   // private String fileUrl = "https://pic.bincrea.com/bc_bg_6D40C91A170D41C182511ABBB8A634A4.pdf";
   private String fileUrl;
    public static final int LOAD_JAVASCRIPT = 0X01;
    private ImageView iv_pdf_finish;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_pdf;
    }

    @Override
    public void initViews() {
        Long userId = DataWarehouse.getBaseRequest(this).uid;
        int reportId = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        int status = getIntent().getIntExtra("status", 0);
        mWebView = (WebView) findViewById(R.id.wb_pdf);
        TextView tv_pdf = findViewById(R.id.tv_pdf);
        if(!TextUtil.isEmpty(name)){
            tv_pdf.setText(name);
        }else {
            tv_pdf.setText("pdf预览");
        }

        TextView tv_wait = findViewById(R.id.tv_wait);
        iv_pdf_finish=findViewById(R.id.iv_pdf_finish);
        iv_pdf_finish.setOnClickListener(this);
        pro = (ProgressBar) findViewById(R.id.pro);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //自适应屏幕
        if (reportId != 0) {
            fileUrl = ServiceUrl.H5_MAIN_PATH_TEXT + "credit/showStream.pdf?reportId=" + reportId +"&userId="+ userId;
            LUtils.e("-------fileUrl-------",fileUrl);
        }
     /*   if(status==2){//状态为2时加模糊 加提示
//            tv_wait.setVisibility(View.VISIBLE);
        }else {

        }*/
        mWebView.addJavascriptInterface(new JsObject(PdfActivity.this, fileUrl), "client");
        String pdfHtml = "file:///android_asset/pdf.html";
        mWebView.loadUrl(pdfHtml);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                handler.sendEmptyMessage(LOAD_JAVASCRIPT);
                Toast.makeText(PdfActivity.this, "开始请求pdf文件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==iv_pdf_finish){
            finish();
        }
    }

    class JsObject {
        Activity mActivity;
        String url;

        public JsObject(Activity activity, String url) {
            mActivity = activity;
            this.url = url;
        }

        //    测试方法
        @JavascriptInterface
        public String dismissProgress() {
            pro.setVisibility(View.GONE);
            return this.url;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String javaScript = "javascript: getpdf2('" + fileUrl + "')";
            mWebView.loadUrl(javaScript);
        }
    };
}
