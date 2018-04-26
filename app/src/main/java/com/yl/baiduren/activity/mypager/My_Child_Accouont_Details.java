package com.yl.baiduren.activity.mypager;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.MyChildRecord_Activity;
import com.yl.baiduren.activity.debtmanagpreson.Debt_Person_Manger;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.entity.result.My_Child_Accort_Result;

/**
 * Created by sunbeibei on 2018/1/9.
 * 子账号详情
 */

public class My_Child_Accouont_Details extends BaseActivity implements View.OnClickListener {

    private ImageView child_finsh;
    private TextView tv_child_record;
    private TextView tv_child_order;
    private TextView tv_child_about;
    private LinearLayout ll_updataPhone, ll_child_updataPass;
    private My_Child_Accort_Result.DataListBean dataListBean = null;

    @Override
    public int loadWindowLayout() {
        return R.layout.child_accouont_details;
    }

    @Override
    public void initViews() {
        //子账户信息
        dataListBean = (My_Child_Accort_Result.DataListBean) getIntent().getSerializableExtra("myChild");
        child_finsh = findViewById(R.id.iv_child_title_left);//关闭页
        child_finsh.setOnClickListener(this);
        TextView child_name = findViewById(R.id.child_name);
        ll_updataPhone = findViewById(R.id.ll_updataPhone);//修改手机号
        ll_updataPhone.setOnClickListener(this);
        ll_child_updataPass = findViewById(R.id.ll_child_updataPass);//修改密码
        ll_child_updataPass.setOnClickListener(this);
        tv_child_record = findViewById(R.id.tv_child_record);//子账号备案
        tv_child_record.setOnClickListener(this);
        tv_child_order = findViewById(R.id.tv_child_order);//子账号摘牌
        tv_child_order.setOnClickListener(this);
        tv_child_about = findViewById(R.id.tv_child_about);//子账号债事人
        tv_child_about.setOnClickListener(this);

        if (dataListBean != null) {
            child_name.setText(dataListBean.getNickName());
        }
    }


//

    @Override
    public void onClick(View v) {
        if (v == child_finsh) {//返回

        } else if (v == ll_updataPhone) {//修改手机号
            Intent intent = new Intent(My_Child_Accouont_Details.this, ChangePhone.class);
            intent.putExtra("index", 3).putExtra("childUserId", dataListBean.getUserId());
            intent.putExtra("phone", dataListBean.getMobile());
            startActivity(intent);
        } else if (v == ll_child_updataPass) {//修改密码
            if (dataListBean != null) {
                Intent intent = new Intent(My_Child_Accouont_Details.this, Change_Password.class);
                intent.putExtra("myChild", 1);
                intent.putExtra("childUserId", dataListBean.getUserId());
                startActivity(intent);
            }

        } else if (v == tv_child_record) {//子账号备案
            Intent intent = new Intent(My_Child_Accouont_Details.this, MyChildRecord_Activity.class);
            intent.putExtra("childUserId", dataListBean.getUserId());
            intent.putExtra("childUserName", dataListBean.getNickName());
            startActivity(intent);
        } else if (v == tv_child_order) {//子账号摘牌
            Intent intent = new Intent(My_Child_Accouont_Details.this, MyChild_Delisting.class);
            intent.putExtra("childUserId", dataListBean.getUserId());
            intent.putExtra("childUserName", dataListBean.getNickName());
            startActivity(intent);

        } else if (v == tv_child_about) {//子账号债事人
            Intent intent = new Intent(My_Child_Accouont_Details.this, Debt_Person_Manger.class);
            intent.putExtra("childUserId", dataListBean.getUserId());
            intent.putExtra("childUserName", dataListBean.getNickName());
            startActivity(intent);
        }
    }
}
