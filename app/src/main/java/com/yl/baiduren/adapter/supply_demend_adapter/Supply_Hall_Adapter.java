package com.yl.baiduren.adapter.supply_demend_adapter;

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
import com.yl.baiduren.utils.ToastUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2018/1/19.
 */

public class Supply_Hall_Adapter extends RecyclerView.Adapter<My_Supply_ViewHolder> {
    private Context context;
    private List<My_Supply_Result.DataListBean> dataList;
    public  Item_getId item_getId;

    public Supply_Hall_Adapter(Context context) {
        this.context = context;
    }
    public void setDataList(List<My_Supply_Result.DataListBean> dataList){
        this.dataList=dataList;
    }
    public interface Item_getId{
        void setItemId(Long supplyId);
    }
    public void getItemString(Item_getId item_getId){
       this.item_getId=item_getId;
    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataList;
    }
    @Override

    public My_Supply_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.supply_hall_item,parent,false);
        My_Supply_ViewHolder viewHolder =new My_Supply_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Supply_ViewHolder holder, int position) {
        final My_Supply_Result.DataListBean dataListBean = dataList.get(position);
        Glide.with(context).load(dataListBean.getCategoryImg()).into(holder.image_type);
        Glide.with(context).load(dataListBean.getUserImg())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.suppll_header);
        holder.supply_biaohao.setText("编号:"+dataListBean.getCode());
        holder.supply_remork.setText(dataListBean.getName());
        holder.supply_name.setText(dataListBean.getUserName());
        holder.supply_num.setText("数量："+dataListBean.getNum()+"  归属地: "+dataListBean.getAreaName());
        holder.supply_price.setText("¥"+dataListBean.getValueStr());

        holder.supply_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataListBean.getIsCollect()){
                    ToastUtil.showShort(context,"您已收藏请勿重复收藏");
                }else{
                    item_getId.setItemId(dataListBean.getId());
                }

            }
        });


        holder.query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Supply_Demend_Details.class).putExtra("id",dataListBean.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class  My_Supply_ViewHolder extends RecyclerView.ViewHolder{
    public ImageView image_type,suppll_header;
    public TextView supply_biaohao,supply_remork,supply_num,supply_price,supply_name;
    public Button query_detials,supply_collection;

    public My_Supply_ViewHolder(View itemView) {
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