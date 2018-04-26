package com.yl.baiduren.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.asster_dispose.AddAssetsDispose;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/22.
 */

public class AddAsstes_Type_Adapter extends RecyclerView.Adapter<MyViewHolder> {
    private AddAssetsDispose context;
    private List<CategoryDo> list;

    public AddAsstes_Type_Adapter(AddAssetsDispose debt_three_debtorAssets, List<CategoryDo> list) {
        context=debt_three_debtorAssets;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.debt2_demand_item,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(list.get(position).getImage()).override(90, 90).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LUtils.e("++++点击++++++++");
               context.getType(list.get(position).getName(),list.get(position).getCg_Id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
