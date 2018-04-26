package com.yl.baiduren.activity.tradinghall;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Operation_Condition_Adapter;
import com.yl.baiduren.adapter.tradinghall.Share_structure_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.view.MyExpandListView;
import com.yl.baiduren.view.MyRecyclerView;

/**
 * Created by sunbeibei on 2017/12/20.
 */

public class Debt_Transfer_Details extends BaseActivity {

    private ImageView tranfer_back;
    private ImageView tranfer_editor;
    public TextView tranfer_name,tranfer_code,tranfre_tv_area,tranfer_tv_name,tranfer_icode,tranfer_phone;
    public TextView tranfer_adress,tranfer_profile,tranfer_industry_type,up_industry_type,down_industry_type;
    public MyRecyclerView share_structure_recylcer,assets_status_recylcer;
    public MyExpandListView investment_situation_recylcer;
    public MyRecyclerView liabilities_recylcer,operating_condition_recylcer;
    private Button btn_claim;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_transfer_details;
    }

    @Override
    public void initViews() {
        tranfer_back = findViewById(R.id.tranfer_back);
        tranfer_back.setOnClickListener(listener);
        btn_claim = findViewById(R.id.btn_claim); //我要认领
        btn_claim.setOnClickListener(listener);
        tranfer_editor = findViewById(R.id.tranfer_editor);//编辑
        tranfer_editor.setOnClickListener(listener);
        tranfer_name = findViewById(R.id.tranfer_name);//企业全称
        tranfer_code = findViewById(R.id.tranfer_code);//企业证号
        tranfre_tv_area=findViewById(R.id.tranfre_tv_area);//所属地
        tranfer_tv_name=findViewById(R.id.tranfer_tv_name);//法人姓名
        tranfer_icode=findViewById(R.id.tranfer_icode);//身份证号
        tranfer_phone=findViewById(R.id.tranfer_phone);//联系电话
        tranfer_adress=findViewById(R.id.tranfer_adress);//详细地址
        tranfer_profile=findViewById(R.id.tranfer_profile);//简介
        tranfer_industry_type=findViewById(R.id.tranfer_industry_type);//所属行业类型
        up_industry_type=findViewById(R.id.up_industry_type);//上游行业类型
        down_industry_type=findViewById(R.id.down_industry_type);//下游行业类型

        LinearLayoutManager manager1 =new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        share_structure_recylcer = findViewById(R.id.share_structure_recylcer);//股份结构
        share_structure_recylcer.setLayoutManager(manager1);
        Share_structure_Adapter adapter1 =new Share_structure_Adapter(this);
        share_structure_recylcer.setAdapter(adapter1);

        investment_situation_recylcer=findViewById(R.id.investment_situation_recylcer);//投资情况

        LinearLayoutManager manager3 =new LinearLayoutManager(this);
        manager3.setOrientation(LinearLayoutManager.VERTICAL);
        assets_status_recylcer=findViewById(R.id.assets_status_recylcer);//资产状况
        assets_status_recylcer.setLayoutManager(manager3);

        LinearLayoutManager manager4 =new LinearLayoutManager(this);
        manager4.setOrientation(LinearLayoutManager.VERTICAL);
        liabilities_recylcer=findViewById(R.id.liabilities_recylcer);//负债情况
        liabilities_recylcer.setLayoutManager(manager4);

        LinearLayoutManager manager5 =new LinearLayoutManager(this);
        manager5.setOrientation(LinearLayoutManager.VERTICAL);
        operating_condition_recylcer=findViewById(R.id.operating_condition_recylcer);//经营状况
        operating_condition_recylcer.setLayoutManager(manager5);
        Operation_Condition_Adapter adapter5=new Operation_Condition_Adapter(this);
        operating_condition_recylcer.setAdapter(adapter5);
    }
    private View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v==tranfer_back){//关闭页面
                finish();
            }else if(v==tranfer_editor){//编辑

            }else if (v==btn_claim){//我要认领

            }
        }
    };
}
