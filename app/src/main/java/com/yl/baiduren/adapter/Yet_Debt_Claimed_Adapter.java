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
 * Created by sunbeibei on 2017/12/7.
 * 未认领债事适配器
 */

public class Yet_Debt_Claimed_Adapter extends RecyclerView.Adapter<Yet_Claimed_Holder> {
    private Context context;
   private List<My_Record_Result.DataListBean> dataList;

    public Yet_Debt_Claimed_Adapter(Context context) {
        this.context = context;

    }
    public void  setDataRecordList(List<My_Record_Result.DataListBean> dataList){
        this.dataList=dataList;
    }

    public List<My_Record_Result.DataListBean> getDataList() {
        return dataList;
    }

    public List<My_Record_Result.DataListBean>  getList(){
        return dataList;
    }

    @Override
    public Yet_Claimed_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.yet_debt_claimed_item,parent,false);
        Yet_Claimed_Holder yet_claimed_holder =new Yet_Claimed_Holder(view);
        return yet_claimed_holder;
    }

    @Override
    public void onBindViewHolder(Yet_Claimed_Holder holder, int position) {
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);

        holder.debt_adress.setText(dataListBean.getAreaName());
        holder.break_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1)+"%");
        holder.tv_timer.setText("债事登记时间:"+ Util.LongParseString(dataListBean.getCreateTime()));
        holder.debt_timer.setText(Util.LongParseString(dataListBean.getRecordTime()));
        holder.debt_amout.setText(dataListBean.getAmountStr());
        holder.yet_debt_num.setText("债事编号:"+dataListBean.getCode());
        Glide.with(context).load(dataListBean.getUserImage())//加载时图片
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.header);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Debt_Details.class).
                        putExtra("id",dataListBean.getId())
                        .putExtra("comePager", Constant.PAGER_DEBTHALL_WZP)
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class Yet_Claimed_Holder extends RecyclerView.ViewHolder{
   public  ImageView header;
   public TextView debt_adress,tv_timer,debt_amout,debt_timer,break_aoumt,yet_debt_num;
   public Yet_Claimed_Holder(View itemView) {
       super(itemView);
        header= itemView.findViewById(R.id.header);//头像
        debt_adress=itemView.findViewById(R.id.debt_adress);//债事地址
        tv_timer=itemView.findViewById(R.id.tv_timer);//债事登记时间
        debt_amout=itemView.findViewById(R.id.debt_amout);//债事金额
        debt_timer=itemView.findViewById(R.id.debt_timer);// 债事发生时间
        break_aoumt=itemView.findViewById(R.id.break_aoumt);//解债佣金百分比
       yet_debt_num=itemView.findViewById(R.id.yet_debt_num);//债事编号





   }
}