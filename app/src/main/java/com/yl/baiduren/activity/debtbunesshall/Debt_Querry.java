package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.debtmanagpreson.Debt_Person_Manger;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.utils.UserInfoUtils;

/**
 * Created by sunbeibei on 2017/12/15.
 * 查询
 */

public class Debt_Querry extends BaseActivity {

    private LinearLayout debt_all_look;
    private LinearLayout my_beian;
    private LinearLayout my_debt_preson;
    private ImageView iv_querry_finish;
    private LinearLayout my_sufful_exmple;


    @Override
    public int loadWindowLayout() {
        return R.layout.debt_querry;
    }

    @Override
    public void initViews() {

        iv_querry_finish = findViewById(R.id.iv_querry_finish);
        iv_querry_finish.setOnClickListener(listener);
        debt_all_look = findViewById(R.id.debt_all_look); //债事总览
        debt_all_look.setOnClickListener(listener);
        my_beian = findViewById(R.id.my_beian);//我的备案
        my_beian.setOnClickListener(listener);
        my_debt_preson = findViewById(R.id.my_debt_preson);//我的债事人
        my_debt_preson.setOnClickListener(listener);
        my_sufful_exmple = findViewById(R.id.my_sufful_exmple);//解债案例
        my_sufful_exmple.setOnClickListener(listener);


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_querry_finish) {
                finish();
            } else if (v == debt_all_look) {//债事总览
                if (UserInfoUtils.IsLogin(Debt_Querry.this)) {
                    startActivity(new Intent(Debt_Querry.this, Debt_Buness_Hall.class));

                } else {
                    startActivity(new Intent(Debt_Querry.this, Login_Activity_Password.class));
                }
            } else if (v == my_beian) {//我的备案
                if (UserInfoUtils.IsLogin(Debt_Querry.this)) {
                    startActivity(new Intent(Debt_Querry.this, My_Record_Activity.class));
                } else {
                    startActivity(new Intent(Debt_Querry.this, Login_Activity_Password.class));
                }

            } else if (v == my_sufful_exmple) {//解债案例
                if (UserInfoUtils.IsLogin(Debt_Querry.this)) {
                    startActivity(new Intent(Debt_Querry.this, Sussful_Exmple.class));
                } else {
                    startActivity(new Intent(Debt_Querry.this, Login_Activity_Password.class));
                }

            } else if (v == my_debt_preson) {//我的债事人
                if (UserInfoUtils.IsLogin(Debt_Querry.this)) {
                    startActivity(new Intent(Debt_Querry.this, Debt_Person_Manger.class));
                } else {
                    startActivity(new Intent(Debt_Querry.this, Login_Activity_Password.class));
                }
            }
        }
    };
}
