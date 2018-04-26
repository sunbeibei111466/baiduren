package com.yl.baiduren.adapter.my_collection_adapter;

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
import com.yl.baiduren.activity.supply_demand.Supply_Demend_Details;
import com.yl.baiduren.entity.result.My_Supply_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2018/1/19.
 */

public class My_Supply_Collection_Adapter extends RecyclerView.Adapter<My_Collection_ViewHolder> {
    private Context context;
    private List<My_Supply_Result.DataListBean> dataList;
    private CancleColection cancleColection;

    public My_Supply_Collection_Adapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<My_Supply_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }

    public interface CancleColection {
        void setCancle(Long cancle_id);
    }

    public void Item_Cancle(CancleColection cancleColection) {
        this.cancleColection = cancleColection;

    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataList;
    }

    @Override
    public My_Collection_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_collection_item, parent, false);
        My_Collection_ViewHolder viewHolder = new My_Collection_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Collection_ViewHolder holder, final int position) {
        final My_Supply_Result.DataListBean dataListBean = dataList.get(position);
        Glide.with(context).load(dataListBean.getCategoryImg()).into(holder.image_type);
        Glide.with(context).load(dataListBean.getUserImg())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.suppll_header);
        holder.supply_biaohao.setText("编号:" + dataListBean.getCode());
        holder.supply_remork.setText(dataListBean.getName());
        holder.supply_num.setText("数量  " + dataListBean.getNum() + "归属地  " + dataListBean.getAreaName());
        holder.supply_price.setText("¥" + dataListBean.getValueStr());
        holder.supply_name.setText(dataListBean.getUserName());
        holder.supply_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleColection.setCancle(dataListBean.getId());
                dataList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Supply_Demend_Details.class)
                        .putExtra("MySupplyCollection", true)
                        .putExtra("id", dataListBean.getId())
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class My_Collection_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView image_type, suppll_header;
    public TextView supply_biaohao, supply_remork, supply_num, supply_price, supply_name;
    public Button query_detials, supply_collection;

    public My_Collection_ViewHolder(View itemView) {
        super(itemView);
        image_type = itemView.findViewById(R.id.collection_image_type);//供应端类型图片
        supply_biaohao = itemView.findViewById(R.id.collection_biaohao);//编号
        query_detials = itemView.findViewById(R.id.collection_query_detials);//查看详情
        supply_remork = itemView.findViewById(R.id.collection_remork);//备注
        supply_num = itemView.findViewById(R.id.collection_num);//数量，归属地
        supply_price = itemView.findViewById(R.id.collection_price);//价值
        suppll_header = itemView.findViewById(R.id.collection_header);//头像
        supply_name = itemView.findViewById(R.id.collection_name);//姓名
        supply_collection = itemView.findViewById(R.id.cancle_collection);//取消收藏
    }
}
