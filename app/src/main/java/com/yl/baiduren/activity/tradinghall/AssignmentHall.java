package com.yl.baiduren.activity.tradinghall;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.utils.UserInfoUtils;

public class AssignmentHall extends BaseActivity implements View.OnClickListener {

    private LinearLayout hall_1, hall_2;
    private ImageView iv_ex_sq_back;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_assignment_hall;
    }

    @Override
    public void initViews() {
        iv_ex_sq_back = findViewById(R.id.iv_ex_sq_back);
        iv_ex_sq_back.setOnClickListener(this);
        hall_1 = findViewById(R.id.hall_1);
        hall_1.setOnClickListener(this);
        hall_2 = findViewById(R.id.hall_2);
        hall_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == hall_1) {//债权大厅
            startActivity(new Intent(this, Debt_Transfer_Platform_Hall_Activity.class));
        } else if (v == hall_2) {//我的债权
            if (UserInfoUtils.IsLogin(this)){
                startActivity(new Intent(this, Debt_Transfer_Platform_Activity.class));
            }else{
                startActivity(new Intent(this, Login_Activity_Password.class));
            }

        }else if (v==iv_ex_sq_back){
            finish();
        }
    }
}
