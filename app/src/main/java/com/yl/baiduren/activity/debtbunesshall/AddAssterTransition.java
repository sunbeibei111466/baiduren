package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.asster_dispose.AddAssetsDispose;
import com.yl.baiduren.utils.UserInfoUtils;

public class AddAssterTransition extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_title_left;
    private LinearLayout ll_transition_1,ll_transition_2,ll_transition_3,ll_transition_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asster_transition);
        iv_title_left=findViewById(R.id.iv_title_left);
        iv_title_left.setOnClickListener(this);
        ll_transition_1=findViewById(R.id.ll_transition_1);//个人录入
        ll_transition_1.setOnClickListener(this);
        ll_transition_2=findViewById(R.id.ll_transition_2);//机构对接
        ll_transition_2.setOnClickListener(this);
        ll_transition_3=findViewById(R.id.ll_transition_3);//银行对接
        ll_transition_3.setOnClickListener(this);
        ll_transition_4=findViewById(R.id.ll_transition_4);//法院对接
        ll_transition_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==iv_title_left){
            finish();
        }else if (v==ll_transition_1){//新增资产
            if (UserInfoUtils.IsLogin(AddAssterTransition.this)) {
                startActivity(new Intent(AddAssterTransition.this, AddAssetsDispose.class));
            } else {
                startActivity(new Intent(AddAssterTransition.this, Login_Activity_Password.class));
            }
        }else if (v == ll_transition_2) {
            startActivity(new Intent(this, AgencyDockingActivity.class));
        } else if (v == ll_transition_3) {
            startActivity(new Intent(this, BankDockingActivity.class));
        } else if (v == ll_transition_4) {
            startActivity(new Intent(this, CourtDockingActivity.class));
        }
    }
}
