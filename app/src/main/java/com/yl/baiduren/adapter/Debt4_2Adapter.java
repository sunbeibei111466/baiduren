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
import com.yl.baiduren.activity.debtrecord.PhysicalBorrowing;
import com.yl.baiduren.entity.greenentity.GoodLoanDO;
import com.yl.baiduren.utils.GreenDaoUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4_2Adapter extends RecyclerView.Adapter<MyViewH> {

    private Context context;
    private List<GoodLoanDO> goodLoanDOList;

    public Debt4_2Adapter(Context context) {
        this.context = context;
    }

    public void setGoodLoanDOList(List<GoodLoanDO> goodLoanDOList) {
        this.goodLoanDOList = goodLoanDOList;
    }


    @Override
    public MyViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_adapter_proof_2, parent, false);
        MyViewH myViewH = new MyViewH(view);
        return myViewH;
    }

    @Override
    public void onBindViewHolder(MyViewH holder, final int position) {

        holder.tv_adapter_item2_name.setText(goodLoanDOList.get(position).getName());
        holder.tv_adapter2_jiazhi.setText(goodLoanDOList.get(position).getValueStr());
        holder.tv_adapter2_item2_whje.setText(goodLoanDOList.get(position).getBalanceStr() + "");
        holder.iv_debt_details_item2_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除
                GreenDaoUtils.getInstance(context).getGoodLoanDODao().delete(goodLoanDOList.get(position));
                goodLoanDOList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.ll_parent_2.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PhysicalBorrowing.class).
                        putExtra("updataId",goodLoanDOList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodLoanDOList.size();
    }
}

class MyViewH extends RecyclerView.ViewHolder {

    public ImageView iv_item2_image, iv_debt_details_item2_delete;
    public TextView tv_adapter_item2_name, tv_adapter2_jiazhi, tv_adapter2_item2_whje;
    public LinearLayout ll_parent_2;

    public MyViewH(View itemView) {
        super(itemView);
        iv_item2_image = itemView.findViewById(R.id.iv_item2_image);
        tv_adapter_item2_name = itemView.findViewById(R.id.tv_adapter_item2_name);
        tv_adapter2_jiazhi = itemView.findViewById(R.id.tv_adapter2_jiazhi);
        tv_adapter2_item2_whje = itemView.findViewById(R.id.tv_adapter2_item2_whje);
        iv_debt_details_item2_delete = itemView.findViewById(R.id.iv_debt_details_item2_delete);
        ll_parent_2=itemView.findViewById(R.id.ll_parent_2);
    }
}