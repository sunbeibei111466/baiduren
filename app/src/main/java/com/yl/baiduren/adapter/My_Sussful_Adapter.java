package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.Util;

import java.math.BigDecimal;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2017/12/18.
 */

public class My_Sussful_Adapter extends RecyclerView.Adapter<My_Sufful_ViewHolder> {
    private List<My_Record_Result.DataListBean> dataList;
    private Context context;
    public My_Sussful_Adapter(Context context) {
        this.context = context;


    }
    public  void  setDataList(List<My_Record_Result.DataListBean> dataList){
        this.dataList=dataList;
        notifyDataSetChanged();
    }

    public List<My_Record_Result.DataListBean> getDataList() {
        return dataList;
    }

    public List<My_Record_Result.DataListBean>getList(){
        return dataList;
    }



    @Override
    public My_Sufful_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sufful_item,parent,false);
        My_Sufful_ViewHolder viewHolder = new My_Sufful_ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Sufful_ViewHolder holder, int position) {
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);
        holder.sussful_num.setText("债事编号:"+dataListBean.getCode());
        Glide.with(context).load(dataListBean.getUserImage())//加载时图片
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.sufful_header);
        holder.sufful_adress.setText(dataListBean.getAreaName());
        holder.sufful_tv_timer.setText("债事登记时间:"+ Util.LongParseString(dataListBean.getCreateTime()));
        holder.sufful_debt_amout.setText(dataListBean.getAmountStr());
        holder.sufful_break_timer.setText(Util.LongParseString(dataListBean.getSolveTime()));
        holder.sufful_break_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1)+"%");
        holder.sufful_ren_ling_name.setText(dataListBean.getSettleName());
        holder.sufful_ren_ling_phone.setText(dataListBean.getSettleMobile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Debt_Details.class)
                        .putExtra("id",dataListBean.getId())
                        .putExtra("comePager", Constant.PAGER_SUSSFUL)
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class My_Sufful_ViewHolder extends RecyclerView.ViewHolder{
    public TextView sussful_num,sufful_adress,sufful_tv_timer,sufful_debt_amout,sufful_break_timer,
            sufful_break_aoumt,sufful_ren_ling_name,sufful_ren_ling_phone;
    public ImageView sufful_header;

    public My_Sufful_ViewHolder(View itemView) {
        super(itemView);
        sussful_num=itemView.findViewById(R.id.sussful_num);//债事编号
        sufful_header=itemView.findViewById(R.id.sufful_header);//头像
        sufful_adress=itemView.findViewById(R.id.sufful_adress);//归属地
        sufful_tv_timer=itemView.findViewById(R.id.sufful_tv_timer);//债事登记时间
        sufful_debt_amout=itemView.findViewById(R.id.sufful_debt_amout);//债事金额
        sufful_break_timer=itemView.findViewById(R.id.sufful_break_timer);//借债时间
        sufful_break_aoumt=itemView.findViewById(R.id.sufful_break_aoumt);//解债佣金
        sufful_ren_ling_name=itemView.findViewById(R.id.sufful_ren_ling_name);//认领人
        sufful_ren_ling_phone=itemView.findViewById(R.id.sufful_ren_ling_phone);//联系电话



    }
}
