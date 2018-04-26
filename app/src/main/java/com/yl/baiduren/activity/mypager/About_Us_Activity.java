package com.yl.baiduren.activity.mypager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.utils.AppInfoUtil;

/**
 * Created by sunbeibei on 2018/3/14.
 */

public class About_Us_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView about_us_back;

    @Override
    public int loadWindowLayout() {
        return R.layout.about_us_acitvity;
    }

    @Override
    public void initViews() {
        about_us_back = findViewById(R.id.about_us_back);
        about_us_back.setOnClickListener(this);
        TextView version_code = findViewById(R.id.verson_code);
        version_code.setText("版本号：V "+AppInfoUtil.getVersionName());

    }

    @Override
    public void onClick(View v) {
        if (v==about_us_back){
            finish();
        }

    }
}
