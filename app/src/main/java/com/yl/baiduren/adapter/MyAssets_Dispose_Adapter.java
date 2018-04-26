package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.asster_dispose.MyAsstesDisposeDetials;
import com.yl.baiduren.entity.result.My_Supply_Result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/23.
 */

public class MyAssets_Dispose_Adapter extends RecyclerView.Adapter<My_Ass_ViewHolder> {
    private Context context;
    private List<My_Supply_Result.DataListBean> dataListBeans;

    public MyAssets_Dispose_Adapter(Context context) {
        this.context = context;
    }

    public void setDataListBeans(List<My_Supply_Result.DataListBean> dataListBeans) {
        this.dataListBeans = dataListBeans;
    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataListBeans;
    }

    @Override
    public My_Ass_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assets_dispose_item, parent, false);
        My_Ass_ViewHolder viewHolder = new My_Ass_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Ass_ViewHolder holder, final int position) {
        holder.assets_query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //查看详情
                context.startActivity(new Intent(context, MyAsstesDisposeDetials.class).
                        putExtra("id",dataListBeans.get(position).getId()));
            }
        });
        Glide.with(context).load(dataListBeans.get(position).getCategoryImg()).into(holder.assets_image_type);
        holder.assets_biaohao.setText("编号:" + dataListBeans.get(position).getCode());
        holder.assets_type.setText("资产类型:"+dataListBeans.get(position).getCategoryName());
        holder.assets_remark.setText("归属地:" + dataListBeans.get(position).getAreaName());
        holder.assets_adress.setText(dataListBeans.get(position).getName());
        Glide.with(context).load(dataListBeans.get(position).getAngleimg()).into(holder.iv_sign);
    }

    @Override
    public int getItemCount() {
        return dataListBeans.size();
    }
}
