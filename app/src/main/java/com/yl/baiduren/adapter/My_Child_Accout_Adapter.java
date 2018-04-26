package com.yl.baiduren.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.mypager.My_Child_Account;
import com.yl.baiduren.activity.mypager.My_Child_Accouont_Details;
import com.yl.baiduren.entity.result.My_Child_Accort_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 我的子账号适配器
 */

public class My_Child_Accout_Adapter extends RecyclerView.Adapter<My_Child_ViewHolder> {

    private List<My_Child_Accort_Result.DataListBean> dataResult;
    private My_Child_Account context;

    public My_Child_Accout_Adapter(My_Child_Account context) {
        this.context = context;
    }

    public void setDataResult(List<My_Child_Accort_Result.DataListBean> dataResult) {
        this.dataResult = dataResult;
    }
    public List<My_Child_Accort_Result.DataListBean>getList(){
        return dataResult;
    }

    @Override
    public My_Child_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_child_item, parent, false);
        My_Child_ViewHolder viewHolder = new My_Child_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Child_ViewHolder holder, final int position) {
        final My_Child_Accort_Result.DataListBean dataListBean = dataResult.get(position);
        Glide.with(context).
                load(dataListBean.getImage()).bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.child_header);
        holder.nice_phone.setText(dataListBean.getMobile());
        holder.nickname.setText(dataListBean.getNickName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//
                Intent intent = new Intent(context, My_Child_Accouont_Details.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myChild", dataResult.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.delete_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onDelete(dataListBean.getUserId(), 0);
                dataResult.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataResult.size();
    }
}

class My_Child_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView child_header, delete_child;
    public TextView nickname, nice_phone;

    public My_Child_ViewHolder(View itemView) {
        super(itemView);
        child_header = itemView.findViewById(R.id.child_header); //账号头像
        nickname = itemView.findViewById(R.id.nickname);//昵称
        nice_phone = itemView.findViewById(R.id.nice_phone);//手机号
        delete_child = itemView.findViewById(R.id.delete_child);//删除


    }
}
