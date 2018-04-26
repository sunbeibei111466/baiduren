package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.asster_dispose.Assets_Dispose;

public class AssterTransitionPager extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_ex_sq_back;
    private LinearLayout asster_pager_1,asster_pager_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asster_transition_pager);
        iv_ex_sq_back=findViewById(R.id.iv_ex_sq_back);
        iv_ex_sq_back.setOnClickListener(this);
        asster_pager_1=findViewById(R.id.asster_pager_1);
        asster_pager_1.setOnClickListener(this);
        asster_pager_2=findViewById(R.id.asster_pager_2);
        asster_pager_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==iv_ex_sq_back){
            finish();
        }else if(v==asster_pager_1){
            startActivity(new Intent(this, Assets_Dispose.class));//资产大厅
        }else if (v==asster_pager_2){
            startActivity(new Intent(this, AddAssterTransition.class));//新增资产
        }
    }
}
