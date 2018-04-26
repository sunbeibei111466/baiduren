package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.debtbunesshall.AssterTransitionPager;
import com.yl.baiduren.activity.debtbunesshall.Debt_Shop_Mall;
import com.yl.baiduren.activity.tradinghall.AssignmentHall;
import com.yl.baiduren.utils.UserInfoUtils;

/**
 * 交易大厅
 */

public class Exchange_Square_Activiyt extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout shopmall;
    private LinearLayout debt_trantion;
    private LinearLayout creditor_asster;
    private ImageView iv_ex_sq_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_square_activity);
        initViews();
    }


    public void initViews() {
        iv_ex_sq_back = findViewById(R.id.iv_ex_sq_back);
        iv_ex_sq_back.setOnClickListener(this);
        //商城
        shopmall = findViewById(R.id.shopp_mall);
        shopmall.setOnClickListener(this);
        //资产处理
        creditor_asster = findViewById(R.id.creditor_asster);
        creditor_asster.setOnClickListener(this);
        //权益交易
        debt_trantion = findViewById(R.id.creditor_transfer);
        debt_trantion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == shopmall) {
            startActivity(new Intent(this, Debt_Shop_Mall.class));//债事商城
        } else if (v == creditor_asster) {
            if (UserInfoUtils.IsLogin(this)){
                startActivity(new Intent(this, AssterTransitionPager.class));
            }else {
                startActivity(new Intent(this, Login_Activity_Password.class));
            }
        } else if (v == debt_trantion) {
            startActivity(new Intent(this, AssignmentHall.class));//权益交易
        } else if (v == iv_ex_sq_back) {
            finish();
        }

    }
}
