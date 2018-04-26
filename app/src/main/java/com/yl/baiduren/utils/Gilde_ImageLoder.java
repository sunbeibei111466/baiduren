package com.yl.baiduren.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;

import java.io.File;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by sunbeibei on 2017/12/5.
 */

public class Gilde_ImageLoder implements ImageLoader {


    public Gilde_ImageLoder() {

        this(Bitmap.Config.RGB_565);

    }



    public Gilde_ImageLoder(Bitmap.Config config) {

        Bitmap.Config mConfig = config;

    }
    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {

        Glide.with(activity)
                .load(new File(path))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
