package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;

public class AuthorizationManagment extends BaseActivity implements View.OnClickListener {
    private ImageView iv_recod_finish;
    private LinearLayout authourzation_confrim;
    private LinearLayout authorization_record;
    /*授权管理*/

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_authorization_managment;
    }

    @Override
    public void initViews() {
        iv_recod_finish = findViewById(R.id.iv_recod_finish);
        iv_recod_finish.setOnClickListener(this);
        authourzation_confrim = findViewById(R.id.authourzation_confrim);//授权确认
        authourzation_confrim.setOnClickListener(this);
        authorization_record = findViewById(R.id.authorization_record);//授权记录
        authorization_record.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == iv_recod_finish) {
            finish();
        } else if (v == authorization_record) {//授权申请
            startActivity(new Intent(this,Authorization_Record.class));
        }else if (v==authourzation_confrim){//授权接收
            startActivity(new Intent(this,Authorizaton_Confrim_List.class));
        }
    }
}