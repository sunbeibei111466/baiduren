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
import com.yl.baiduren.activity.debtrecord.AssetrMortgage;
import com.yl.baiduren.entity.greenentity.MortgageDO;
import com.yl.baiduren.utils.GreenDaoUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4_5Adapter extends RecyclerView.Adapter<MyViewH5> {

    private Context context;
    List<MortgageDO> mortgageDOList;

    public Debt4_5Adapter(Context context) {
        this.context = context;
    }

    public void setMortgageDOList(List<MortgageDO> mortgageDOList) {
        this.mortgageDOList = mortgageDOList;
    }

    @Override
    public MyViewH5 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_adapter_proof_5, parent, false);
        MyViewH5 myViewH = new MyViewH5(view);
        return myViewH;
    }

    @Override
    public void onBindViewHolder(MyViewH5 holder, final int position) {

         holder.tv_adapter_item5_name.setText(mortgageDOList.get(position).getName());
         holder.tv_adapter_item5_jiazhi.setText(mortgageDOList.get(position).getAmountStr());
         holder.iv_debt_details_item5_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreenDaoUtils.getInstance(context).getMortgageDODao().delete(mortgageDOList.get(position));
                mortgageDOList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.ll_parent_5.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AssetrMortgage.class).
                        putExtra("updataId",mortgageDOList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mortgageDOList.size();
    }
}

class MyViewH5 extends RecyclerView.ViewHolder {

    public TextView tv_adapter_item5_name,tv_adapter_item5_jiazhi;
    public ImageView iv_item5_image,iv_debt_details_item5_delete;
    public LinearLayout ll_parent_5;

    public MyViewH5(View itemView) {
        super(itemView);
        iv_item5_image=itemView.findViewById(R.id.iv_item5_image);
        tv_adapter_item5_name=itemView.findViewById(R.id.tv_adapter_item5_name);//资产名称
        tv_adapter_item5_jiazhi=itemView.findViewById(R.id.tv_adapter_item5_jiazhi);//评估价值
        iv_debt_details_item5_delete=itemView.findViewById(R.id.iv_debt_details_item5_delete);
        ll_parent_5=itemView.findViewById(R.id.ll_parent_5);
    }
}