package com.yl.baiduren.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;


import com.yl.baiduren.R;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;


/**
 * Created by sunbeibei on 2017/11/30.
 */

public class Photo_Select {

    public static int REQUEST_CODE_CAMERA = 1000;
    public static int REQUEST_CODE_GALLERY = 1001;
    private static int phoneShu = 8;
    private static int cum = 0;

    public static void open_Photo(Context context, int shu, GalleryFinal.OnHanlderResultCallback callback) {
        initOnClick(context, callback);
        cum = shu;
    }

    private static void initOnClick(final Context mContext, final GalleryFinal.OnHanlderResultCallback callback) {
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.photo_select_pouplewindow, null);
        Button camer = popupView.findViewById(R.id.camera);
        Button garray = popupView.findViewById(R.id.gallery);
        Button cancle = popupView.findViewById(R.id.cancel);

        Util.backgroundAlpha((Activity) mContext, 0.6f);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
        int[] location = new int[2];
        LayoutInflater.from(mContext).inflate(R.layout.add_new_natural_person, null).getLocationOnScreen(location);
//        LayoutInflater.from(mContext).inflate(R.layout.add_new_natural_person,
        popupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha((Activity) mContext, 1f);
            }
        });



        camer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(Build.VERSION.SDK_INT>=26){
                    //。判断是否开启写权限 相机权限
                    if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            &&
                            ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED){
                        //申请写权限  相机权限
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
                    }else {
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, callback);
                        popupWindow.dismiss();
                        Util.backgroundAlpha((Activity) mContext, 1f);

                    }
//                }else {
//                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        //申请权限
//                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                    else {
//                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, callback);
//                        popupWindow.dismiss();
//                        Util.backgroundAlpha((Activity) mContext, 1f);
//
//                    }
//                }









            }
        });
        garray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(phoneShu - cum).build();
                    GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, callback);
                    popupWindow.dismiss();
                    Util.backgroundAlpha((Activity) mContext, 1f);
                }


            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Util.backgroundAlpha((Activity) mContext, 1f);

            }
        });
    }
}
