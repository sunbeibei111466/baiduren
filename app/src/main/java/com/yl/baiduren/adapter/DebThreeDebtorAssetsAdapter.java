package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsAssets;
import com.yl.baiduren.activity.debtrecord.Debt_Three_DebtorAssets;
import com.yl.baiduren.entity.greenentity.CategoryDo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */

public class DebThreeDebtorAssetsAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<CategoryDo> list;

    public DebThreeDebtorAssetsAdapter(Debt_Three_DebtorAssets debt_three_debtorAssets, List<CategoryDo> list) {
        context=debt_three_debtorAssets;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.debt2_demand_item,null,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImage()).override(90, 90).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Debt_CreditorsAssets.class);
                intent.putExtra("assetsName", list.get(position).getName());
                intent.putExtra("assetsId",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
