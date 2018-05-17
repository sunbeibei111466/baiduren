package com.yl.baiduren;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yl.baiduren.utils.Util;


/**
 * 欢迎页
 */
public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.getInstance().setCJ(this);
        /*
          页面跳转
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomePage.this, MainActivity.class);
                WelcomePage.this.startActivity(mainIntent);
                WelcomePage.this.finish();
            }
        }, 2000);
    }
}
