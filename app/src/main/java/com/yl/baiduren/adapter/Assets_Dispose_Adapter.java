package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.asster_dispose.Asstes_DisposeHall_Detials;
import com.yl.baiduren.entity.result.My_Supply_Result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/23.
 */

public class Assets_Dispose_Adapter extends RecyclerView.Adapter<My_Ass_ViewHolder> {

    private Context context;
    private List<My_Supply_Result.DataListBean> dataList;

    public void setDataList(List<My_Supply_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }

    public Assets_Dispose_Adapter(Context context) {
        this.context = context;
    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataList;
    }


    @Override
    public My_Ass_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assets_dispose_item, parent, false);
        return new My_Ass_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(My_Ass_ViewHolder holder, final int position) {
        holder.assets_query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Asstes_DisposeHall_Detials.class).putExtra("id",dataList.get(position).getId()));
            }
        });
        Glide.with(context).load(dataList.get(position).getCategoryImg()).into(holder.assets_image_type);
        holder.assets_biaohao.setText("编号:" + dataList.get(position).getCode());
        holder.assets_type.setText("资产类型:"+dataList.get(position).getCategoryName());
        holder.assets_remark.setText("归属地:" + dataList.get(position).getAreaName());
        holder.assets_adress.setText(dataList.get(position).getName());
        Glide.with(context).load(dataList.get(position).getAngleimg()).into(holder.iv_sign);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class My_Ass_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView assets_image_type,iv_sign;
    public TextView assets_biaohao, assets_adress, assets_type, assets_remark;
    public Button assets_query_detials;

    public My_Ass_ViewHolder(View itemView) {
        super(itemView);
        assets_image_type = itemView.findViewById(R.id.assets_image_type);//图片
        assets_biaohao = itemView.findViewById(R.id.assets_biaohao);//资产标号
        assets_query_detials = itemView.findViewById(R.id.assets_dispose_query_detials);//查看详情
        assets_adress = itemView.findViewById(R.id.assets_adress);//资产地址
        assets_type = itemView.findViewById(R.id.assets_type);//资产类型
        assets_remark = itemView.findViewById(R.id.assets_remark);//简介
        iv_sign=itemView.findViewById(R.id.iv_sign);//标识 eg:个人，法院图

    }
}
