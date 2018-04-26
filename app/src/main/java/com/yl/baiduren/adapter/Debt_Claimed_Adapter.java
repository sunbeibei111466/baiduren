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
 * Created by sunbeibei on 2017/12/13.
 * 债事总览已摘牌
 */

public class Debt_Claimed_Adapter extends RecyclerView.Adapter<My_Climed_Holder> {
    private List<My_Record_Result.DataListBean> dataList;
    private Context context;

    public Debt_Claimed_Adapter(Context context) {
        this.context = context;
    }

    public void setList(List<My_Record_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }

    public List<My_Record_Result.DataListBean> getList(){
        return dataList;
    }
    @Override
    public My_Climed_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_climed_item, parent, false);
        My_Climed_Holder my_climed_holder = new My_Climed_Holder(view);
        return my_climed_holder;
    }

    @Override
    public void onBindViewHolder(My_Climed_Holder holder, int position) {
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);
        Glide.with(context).load(dataListBean.getUserImage()).
                bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000).into(holder.header);
        holder.debt_adress.setText(dataListBean.getAreaName());
        holder.tv_timer.setText("债事登记时间:" + Util.LongParseString(dataListBean.getCreateTime()));
        holder.debt_amout.setText(dataListBean.getAmountStr());
        holder.break_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1) + "%");
        holder.ren_ling_name.setText(dataListBean.getSettleName());
        holder.ren_ling_phone.setText(dataListBean.getSettleMobile());
        holder.debt_num.setText("债事编号:" + dataListBean.getCode());
        if (dataListBean.getStatus() == 5) {//解债时间
            holder.break_icon.setVisibility(View.VISIBLE);
            holder.debt_num.setBackgroundResource(R.drawable.item_top_gray);
            holder.timer_type.setText("解债时间");
            holder.day_date.setText(Util.LongParseString(dataListBean.getSolveTime()));
            holder.debt_timer.setVisibility(View.GONE);

        } else {
            holder.timer_type.setText("距认领失效");
            holder.debt_timer.setVisibility(View.VISIBLE);
            holder.debt_timer.setText(dataListBean.getEndDays() + "");
            holder.day_date.setText(" 天");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Debt_Details.class).
                        putExtra("id", dataListBean.getId())
                        .putExtra("comePager", Constant.PAGER_DEBTHALL_YZP)
                );
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class My_Climed_Holder extends RecyclerView.ViewHolder {
    public ImageView header,break_icon;
    public TextView debt_adress, tv_timer, debt_amout, debt_timer, break_aoumt, ren_ling_name,
            ren_ling_phone, debt_num, timer_type, day_date;

    public My_Climed_Holder(View itemView) {
        super(itemView);
        header = itemView.findViewById(R.id.header);//头像
        debt_adress = itemView.findViewById(R.id.debt_adress);//债事地址
        tv_timer = itemView.findViewById(R.id.tv_timer);//债事登记时间
        debt_amout = itemView.findViewById(R.id.debt_amout);//债事金额
        debt_timer = itemView.findViewById(R.id.debt_timer);//认领失效时间
        break_aoumt = itemView.findViewById(R.id.break_aoumt);//解债佣金
        ren_ling_name = itemView.findViewById(R.id.ren_ling_name);//认领人
        ren_ling_phone = itemView.findViewById(R.id.ren_ling_phone);//认领人的手机号
        debt_num = itemView.findViewById(R.id.debt_num);//债事编号
        timer_type = itemView.findViewById(R.id.timer_type);//时间类型
        day_date = itemView.findViewById(R.id.day_date);
        break_icon=itemView.findViewById(R.id.break_icon);


    }
}
