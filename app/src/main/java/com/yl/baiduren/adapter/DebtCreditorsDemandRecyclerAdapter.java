package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsDemand;
import com.yl.baiduren.activity.debtrecord.Debt_Too_CreditorsDemand;
import com.yl.baiduren.entity.greenentity.CategoryDo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */

public class DebtCreditorsDemandRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<CategoryDo> mList;

    public DebtCreditorsDemandRecyclerAdapter(Debt_Too_CreditorsDemand debt_too_debtorDemand, List<CategoryDo> list) {
        context = debt_too_debtorDemand;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt2_demand_item, null, false);
        MyViewHolder myHv = new MyViewHolder(view);
        return myHv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(mList.get(position).getImage()).override(90, 90).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Debt_CreditorsDemand.class);
                intent.putExtra("demandName", mList.get(position).getName());
                intent.putExtra("demandId",mList.get(position).getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
    }
}