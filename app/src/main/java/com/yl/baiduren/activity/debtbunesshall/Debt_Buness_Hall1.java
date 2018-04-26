package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.UserInfoUtils;

/**
 * Created by sunbeibei on 2017/12/12.
 * 债事大厅一级
 */

public class Debt_Buness_Hall1 extends BaseActivity implements View.OnClickListener {

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private ImageView title_left;


    @Override
    public int loadWindowLayout() {
        return R.layout.debt_buness_hall1;
    }

    @Override
    public void initViews() {
        title_left = findViewById(R.id.iv_title_left);
        title_left.setOnClickListener(this);
        linearLayout1 = findViewById(R.id.line1);//备案
        linearLayout1.setOnClickListener(this);
        linearLayout2 = findViewById(R.id.line2);//查询
        linearLayout2.setOnClickListener(this);
        linearLayout3 = findViewById(R.id.line3);//解债
        linearLayout3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == title_left) {
            finish();
        } else if (v == linearLayout1) {//备案
            startActivity(new Intent(this, DebtTransitionPager.class));
        } else if (v == linearLayout2) {//查询
            startActivity(new Intent(this, Debt_Querry.class));
        } else if (v == linearLayout3) {//解债
            if (UserInfoUtils.IsLogin(this)) {
                Intent intent = new Intent();
                intent.setClass(this, Break_Debt_Activity.class);
                intent.putExtra("url", ServiceUrl.H5_XIEZHAI);
                intent.putExtra("type", 1);
                startActivity(intent);
            } else {
                startActivity(new Intent(this, Login_Activity_Password.class));
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
