package com.yl.baiduren.adapter.my_supply_demend_apapger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.supply_demand.My_Demend_Details;
import com.yl.baiduren.entity.result.My_Supply_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2018/1/19.
 */

public class My_Demend_Adapter extends RecyclerView.Adapter<Mine_Supply_ViewHolder>  {
    private List<My_Supply_Result.DataListBean> dataList;
    private Context context;
    public My_Demend_Adapter(Context context) {
        this.context = context;
    }
    public void setDataList(List<My_Supply_Result.DataListBean> dataList){
        this.dataList=dataList;
    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataList;
    }



    @Override
    public Mine_Supply_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.supply_hall_item,parent,false);
        Mine_Supply_ViewHolder viewHolder =new Mine_Supply_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Mine_Supply_ViewHolder holder, int position) {
        holder.supply_collection.setVisibility(View.GONE);
        final My_Supply_Result.DataListBean dataListBean = dataList.get(position);
        Glide.with(context).load(dataListBean.getCategoryImg()).into(holder.image_type);
        Glide.with(context).load(dataListBean.getUserImg())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.suppll_header);
        holder.supply_biaohao.setText("编号:"+dataListBean.getCode());
        holder.supply_num.setText("数量："+dataListBean.getNum()+"  归属地: "+dataListBean.getAreaName());
        holder.supply_remork.setText(dataListBean.getName());
        holder.supply_price.setText("¥"+dataListBean.getValueStr());
        holder.supply_name.setText(dataListBean.getUserName());
        holder.query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, My_Demend_Details.class).putExtra("demend_id",dataListBean.getId()));
            }
        });






    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
