package com.yl.baiduren.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.activity.mypager.My_Delisting;
import com.yl.baiduren.activity.pay_for.Confirm_Delisting;
import com.yl.baiduren.entity.result.My_Delisting_Result;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.Util;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2017/12/20.
 */

public class My_Delisting_Adapter extends RecyclerView.Adapter<My_Delist_ViewHolder> {

    private My_Delisting context;
    private List<My_Delisting_Result.DataListBean> dataList;

    public My_Delisting_Adapter(My_Delisting context) {
        this.context = context;
    }

    public void setDataList(List<My_Delisting_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }
    public List<My_Delisting_Result.DataListBean>getList(){
        return dataList;
    }


    @Override
    public My_Delist_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_delisting_item, parent, false);
        My_Delist_ViewHolder viewHolder = new My_Delist_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final My_Delist_ViewHolder holder, int position) {
        final My_Delisting_Result.DataListBean dataListBean = dataList.get(position);
        holder.tv_delisting_timer.setText("债事登记时间:" + Util.LongParseString(dataListBean.getCreateTime()));
        Glide.with(context).load(dataListBean.getUserImage())//加载时图片
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.iv_delisting_header);
        Integer flag = dataListBean.getFlag();
        if (flag==2){//2子账号的摘牌
            holder.child_name.setVisibility(View.VISIBLE);
            holder.child_name.setText("子账号："+dataListBean.getSettleName());
        }

        if (dataListBean.getStatus()==1||dataListBean.getStatus()==2||dataListBean.getStatus() == 3) {//已摘牌
            if (dataListBean.getEndTime()!=null){
                holder.end_time.setText("到期时间:"+Util.LongParseString(dataListBean.getEndTime()));
            }
            holder.tV_type.setText("摘牌时间");
            holder.delisting_break_timer.setText(Util.LongParseString(dataListBean.getDelistingTime()));
            holder.delist_num.setBackgroundResource(R.drawable.item_top);
            holder.lv_type.setVisibility(View.VISIBLE);
        } else if (dataListBean.getStatus() == 4) {//待确认
            holder.lv_type.setVisibility(View.GONE);
            holder.tV_type.setText("摘牌时间");
            holder.delisting_break_timer.setText(Util.LongParseString(dataListBean.getDelistingTime()));
            holder.tv_debt_type.setText("等待备案人确认债事已完成");
            holder.delist_num.setBackgroundResource(R.drawable.item_top);
        } else if (dataListBean.getStatus() == 5) {//已解债
            holder.tV_type.setText("解债时间");
            if (dataListBean.getSolveTime()!=null){
                holder.delisting_break_timer.setText(Util.LongParseString(dataListBean.getSolveTime()));
            }
            holder.lv_type.setVisibility(View.GONE);
            holder.tv_debt_type.setText("债事已解决");
            holder.delist_num.setBackgroundResource(R.drawable.item_top_gray);
        }
        holder.tv_delisting_adress.setText(dataListBean.getAreaName());
        holder.tv_delisting_amout.setText(dataListBean.getAmountStr());
        holder.delisting_break_aoumt.setText(dataListBean.getCommission() * 100 + "%");
        holder.delist_num.setText("债事编号:" + dataListBean.getCode());
        holder.comfire_complate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onDelete(dataListBean.getId(), 0);
                holder.lv_type.setVisibility(View.GONE);
                holder.tv_debt_type.setText("等待备案人确认债事已完成");
            }
        });
        holder.delisting_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//摘牌续费
                context.startActivity(new Intent(context, Confirm_Delisting.class).putExtra("debtid",dataListBean.getId()));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//债事详情
                context.startActivity(new Intent(context, Debt_Details.class)
                        .putExtra("id", dataListBean.getId())
                        .putExtra("comePager", Constant.PAGER_MY_ZHAIPAI)
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

class My_Delist_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_delisting_header;
    public TextView tv_delisting_adress, tv_delisting_timer,
            tv_delisting_amout, delisting_break_timer, delisting_break_aoumt, delist_num, tV_type, tv_debt_type,end_time,child_name;
    public Button comfire_complate, delisting_renewal;
    public LinearLayout lv_type;

    public My_Delist_ViewHolder(View itemView) {
        super(itemView);
        iv_delisting_header = itemView.findViewById(R.id.iv_delisting_header);//头像
        tv_delisting_adress = itemView.findViewById(R.id.tv_delisting_adress); //地址
        tv_delisting_timer = itemView.findViewById(R.id.tv_delisting_timer);//债事登记时间
        tv_delisting_amout = itemView.findViewById(R.id.tv_delisting_amout);//z债事金额
        delisting_break_timer = itemView.findViewById(R.id.delisting_break_timer);//解债时间
        delisting_break_aoumt = itemView.findViewById(R.id.delisting_break_aoumt);//解债佣金
        comfire_complate = itemView.findViewById(R.id.comfire_complate);//确认完后
        delisting_renewal = itemView.findViewById(R.id.delisting_renewal);//续费
        delist_num = itemView.findViewById(R.id.delist_num);//债事编号
        tV_type = itemView.findViewById(R.id.tv_type);//摘牌时间or解债时间
        tv_debt_type = itemView.findViewById(R.id.tv_debt_type);//债事装态
        lv_type = itemView.findViewById(R.id.lv_type);//是否显示按钮
        end_time=itemView.findViewById(R.id.end_time);//距摘牌失效
        child_name =itemView.findViewById(R.id.child_name);//子账号姓名



    }
}
