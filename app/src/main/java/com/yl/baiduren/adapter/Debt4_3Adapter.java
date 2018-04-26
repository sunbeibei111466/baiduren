package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.PropertyRightsProof;
import com.yl.baiduren.entity.greenentity.PropertyLoanDO;
import com.yl.baiduren.utils.GreenDaoUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4_3Adapter extends RecyclerView.Adapter<MyViewH3> {

    private Context context;
    private List<PropertyLoanDO> propertyLoanDOList;

    public Debt4_3Adapter(Context context) {
        this.context = context;
    }

    public void setPropertyLoanDOList(List<PropertyLoanDO> propertyLoanDOList) {
        this.propertyLoanDOList = propertyLoanDOList;
    }

    @Override
    public MyViewH3 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_adapter_proof_3, parent, false);
        MyViewH3 myViewH = new MyViewH3(view);
        return myViewH;
    }

    @Override
    public void onBindViewHolder(MyViewH3 holder, final int position) {

        holder.tv_adapter_item3_name.setText(propertyLoanDOList.get(position).getName());
        holder.tv_adapter_item3_heji.setText(propertyLoanDOList.get(position).getReturnMoneyStr()+"");
        holder.tv_adapter_item3_whje.setText(propertyLoanDOList.get(position).getBalanceStr());
        holder.iv_debt_details_item3_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreenDaoUtils.getInstance(context).getPropertyLoanDODao().delete(propertyLoanDOList.get(position));
                propertyLoanDOList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.ll_parent_3.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PropertyRightsProof.class).
                        putExtra("updataId",propertyLoanDOList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return propertyLoanDOList.size();
    }
}

class MyViewH3 extends RecyclerView.ViewHolder {

    public TextView tv_adapter_item3_name, tv_adapter_item3_heji, tv_adapter_item3_whje;
    public ImageView iv_item3_image, iv_debt_details_item3_delete;
    public LinearLayout ll_parent_3;

    public MyViewH3(View itemView) {
        super(itemView);
        iv_item3_image = itemView.findViewById(R.id.iv_item3_image);
        tv_adapter_item3_name = itemView.findViewById(R.id.tv_adapter_item3_name);//标的名称
        tv_adapter_item3_heji = itemView.findViewById(R.id.tv_adapter_item3_heji);//合计
        tv_adapter_item3_whje = itemView.findViewById(R.id.tv_adapter_item3_whje);//未还金额
        iv_debt_details_item3_delete = itemView.findViewById(R.id.iv_debt_details_item3_delete);
        ll_parent_3=itemView.findViewById(R.id.ll_parent_3);
    }
}