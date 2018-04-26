package com.yl.baiduren.adapter.my_supply_demend_apapger;

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
import com.yl.baiduren.activity.supply_demand.My_Supply_Demend_Details;
import com.yl.baiduren.entity.result.My_Supply_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by sunbeibei on 2018/1/19.
 */

public class My_Supply_Adpater extends RecyclerView.Adapter<Mine_Supply_ViewHolder> {
    private Context context;
    private List<My_Supply_Result.DataListBean> dataList;

    public My_Supply_Adpater(Context context) {
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

        View  view = LayoutInflater.from(context).inflate(R.layout.supply_hall_item,parent,false);
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
                context.startActivity(new Intent(context, My_Supply_Demend_Details.class).putExtra("id",dataListBean.getId()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class Mine_Supply_ViewHolder extends RecyclerView.ViewHolder{
    public ImageView image_type,suppll_header;
    public TextView supply_biaohao,supply_remork,supply_num,supply_price,supply_name;
    public Button query_detials,supply_collection;

    public Mine_Supply_ViewHolder(View itemView) {
        super(itemView);
        image_type =itemView.findViewById(R.id.image_type);//供应端类型图片
        supply_biaohao=itemView.findViewById(R.id.supply_biaohao);//编号
        query_detials=itemView.findViewById(R.id.query_detials);//查看详情
        supply_remork=itemView.findViewById(R.id.supply_remork);//备注
        supply_num=itemView.findViewById(R.id.supply_num);//数量，归属地
        supply_price=itemView.findViewById(R.id.supply_price);//价值
        suppll_header=itemView.findViewById(R.id.suppll_header);//头像
        supply_name=itemView.findViewById(R.id.supply_name);//姓名
        supply_collection=itemView.findViewById(R.id.supply_collection);//收藏

    }
}
