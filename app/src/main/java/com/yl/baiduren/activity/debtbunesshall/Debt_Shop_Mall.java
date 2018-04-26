package com.yl.baiduren.activity.debtbunesshall;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.entity.greenentity.LoginSuccess;
import com.yl.baiduren.utils.CommomDialog;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.view.FloatDragView;

import java.io.File;
import java.util.List;


/**
 * Created by sunbeibei on 2018/1/16.
 * 债事商城
 */

public class Debt_Shop_Mall extends BaseActivity implements View.OnClickListener {

    //    private ImageView iv_shop_mall_back;
    private String APP_CACAHE_DIRNAME = "mall_app";
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private WebView webview_mall;
    private Uri imageUri;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;//拍照
    private static final int REQ_CHOOSE = REQ_CAMERA + 1; //调用相册
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = REQ_CHOOSE + 1;
    private static final int ABLUM_PERMISSIONS_REQUEST_CODE = CAMERA_PERMISSIONS_REQUEST_CODE + 1;
    private static final int SCAN_CODE = ABLUM_PERMISSIONS_REQUEST_CODE + 1;
    private TextView iv_shop_mall_text;
    String http = "http://shop.jxzcbd.com/mobile/Order/order_detail/id/";

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_shop_mall;
    }

    @Override
    public void initViews() {
        //清除Cookie
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
        Long id = getIntent().getLongExtra("orderId", 0);
        iv_shop_mall_text = findViewById(R.id.iv_shop_mall_text);//登陆按钮
        iv_shop_mall_text.setOnClickListener(this);
        RelativeLayout mMainLayout = findViewById(R.id.ll_shop_layout);
        ImageView iv_shop_mall_back = findViewById(R.id.iv_shop_mall_back);
        iv_shop_mall_back.setOnClickListener(this);
        webview_mall = findViewById(R.id.webview_mall);

        String url;
        if (UserInfoUtils.IsLogin(this)) { //App用户已登录
            iv_shop_mall_text.setVisibility(View.GONE);
            List<LoginSuccess> loginSuccessList = GreenDaoUtils.getInstance(this).getLoginSuccessDao().loadAll();
            String mobile = loginSuccessList.get(0).getMobile();
            String signature = SecurityUtils.md5Signature(mobile, "yl_api_security_md5_signature_AF8F89C4DD0C1ABE0493");//加密
            if (id != 0) {
                url = "http://shop.jxzcbd.com/mobile/user/do_login_api?mobile=" + mobile + "&redirect=" + http + id + "&signature=" + signature;

            } else {
                url = "http://shop.jxzcbd.com/mobile/user/do_login_api?mobile=" + mobile + "&signature=" + signature;
            }

        } else { //未登录
            iv_shop_mall_text.setVisibility(View.VISIBLE);
            url = "http://shop.jxzcbd.com/mobile";
        }

        com.tencent.smtt.sdk.WebSettings webSettings = webview_mall.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //允许js弹窗
//        webSettings.
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setPluginsEnabled(true);//支持插件
        webSettings.setDomStorageEnabled(true);//开启js dom storage api功能
        webview_mall.setWebViewClient(webViewClient);
        webview_mall.setWebChromeClient(webChromeClient);
        webview_mall.requestFocusFromTouch();//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点
        webview_mall.loadUrl(url);

        FloatDragView.addFloatDragView(this, mMainLayout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击事件
                finish();
            }
        });
    }


    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            LUtils.e("-----------shouldOverrideUrlLoading----------" + s);
            if (s.contains("/login")) {
                if (!UserInfoUtils.IsLogin(Debt_Shop_Mall.this)) {
                    iv_shop_mall_text.setVisibility(View.VISIBLE);
                    startActivity(new Intent(Debt_Shop_Mall.this, Login_Activity_Password.class).putExtra("backPage", 4));
                    finish();
                } else {
                    LUtils.e("-----App---已登录----------");
                    iv_shop_mall_text.setVisibility(View.GONE);
                }
//                /User/login
            }
            return super.shouldOverrideUrlLoading(webView, s);
        }

        @Override
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
            LUtils.e("-----------onLoadResource----------" + s);
        }
    };

    /**
     * android webview 兼容相机相册选择
     */
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            if (s1 != null) {
                //弹出对话框
                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            takePhoto();
            return true;
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            takePhoto();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            takePhoto();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            takePhoto();
        }
    };

    public void takePhoto() {
        //弹出提示框
        new CommomDialog(this, new CommomDialog.CameraOpenListener() {

            @Override
            public void onClick(CommomDialog c) {
                checkCameraPermission();
                c.dismiss();
            }
        }, new CommomDialog.AblumOpenListener() {

            @Override
            public void onClick(CommomDialog c) {
                showAblum();
                c.dismiss();
            }
        })
                .show();
    }

    /**
     * 调用相机权限控制
     */
    public void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(Debt_Shop_Mall.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Debt_Shop_Mall.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(Debt_Shop_Mall.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            showCamera();
        }
    }

    /**
     * 调用相机
     */
    public void showCamera() {
        File tempFile = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        imageUri = Uri.fromFile(tempFile);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, REQ_CAMERA);
    }

    /**
     * 相册权限控制
     */
    public void checkAblumPermission() {
        if (ContextCompat.checkSelfPermission(Debt_Shop_Mall.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(Debt_Shop_Mall.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, ABLUM_PERMISSIONS_REQUEST_CODE);
        } else {
            showAblum();
        }
    }

    /**
     * 调用相册
     */
    public void showAblum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.addCategory(Intent.CATEGORY_OPENABLE);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, REQ_CHOOSE);
    }

    /**
     * 权限处理回掉
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                showCamera();
            } else {
                Toast.makeText(Debt_Shop_Mall.this, "你拒绝了权限，该功能不可用\n可在应用设置里授权拍照哦", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == ABLUM_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功
                showAblum();
            } else {
                Toast.makeText(Debt_Shop_Mall.this, "你拒绝了权限，该功能不可用\n可在应用设置里授权查看相册哦", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAMERA || requestCode == REQ_CHOOSE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else if (requestCode == SCAN_CODE) {
            String arg = data.getStringExtra("result");
            webview_mall.loadUrl("javascript:receptionResult('" + arg + "')");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }
        return;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://处理返回键事件
                if (webview_mall.canGoBack()) {
                    webview_mall.goBack();//让WebView回退到上一个网页
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


    @Override
    public void onClick(View v) {
        if (webview_mall.canGoBack()) {
            webview_mall.goBack();//让WebView回退到上一个网页
        } else if (v == iv_shop_mall_text) {//登陆
            startActivity(new Intent(Debt_Shop_Mall.this, Login_Activity_Password.class).putExtra("backPage", 4));
            finish();
        } else {//如果WebView不能回退
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview_mall.clearHistory();
        webview_mall.stopLoading();
        webview_mall.clearView();
        webview_mall.clearCache(true);
        webview_mall.destroyDrawingCache();
        webview_mall.setWebChromeClient(null);
        webview_mall.setWebViewClient(null);
        webview_mall.getSettings().setJavaScriptEnabled(false);
        webview_mall.removeAllViews();
        webview_mall.destroy();
    }

}
