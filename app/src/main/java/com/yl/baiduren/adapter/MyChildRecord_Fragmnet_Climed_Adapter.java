package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.base.IDeleteCall;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.fragment.my_record.MyChildRecord_Fragment_Climed;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.Util;

import java.math.BigDecimal;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2017/12/27.
 */

public class MyChildRecord_Fragmnet_Climed_Adapter extends RecyclerView.Adapter<My_Recrod_Climed_ViewHolder> {

    public MyChildRecord_Fragment_Climed fragment_climed;
    private Context context;
    private List<My_Record_Result.DataListBean> dataList;
    private Long childUserId;

    public MyChildRecord_Fragmnet_Climed_Adapter(Context context, Long childUserId) {
        this.context = context;
        this.childUserId=childUserId;
    }

    public void setFragment_climed(MyChildRecord_Fragment_Climed fragment_climed) {
        this.fragment_climed = fragment_climed;
    }

    public void setDataList(List<My_Record_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }
    public List<My_Record_Result.DataListBean>getList(){
        return dataList;
    }

    @Override
    public My_Recrod_Climed_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_rocord_cilemd_item, parent, false);
        My_Recrod_Climed_ViewHolder viewHolder = new My_Recrod_Climed_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Recrod_Climed_ViewHolder holder, int position) {
        final IDeleteCall call = null;
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);
        holder.debt_record_amout.setText(dataListBean.getAmountStr());
        holder.debt_record_adress.setText(dataListBean.getAreaName());
        holder.break_record_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1) + "%");
        holder.tv_record_timer.setText("债事登记时间：" + Util.LongParseString(dataListBean.getCreateTime()));
        holder.record_ren_ling_name.setText("认领人: " + dataListBean.getSettleName() + "  联系电话: " + dataListBean.getSettleMobile());

        holder.myyet_debt_num.setText("债事编号:" + dataListBean.getCode());
        if (dataListBean.getStatus() == 5) {//解债时间
            holder.rcoud_timer_type.setText("解债时间");
            if (dataListBean.getSolveTime() != null) {
                holder.record_day_date.setText(Util.LongParseString(dataListBean.getSolveTime()));
            }

            holder.debt_record_timer.setVisibility(View.GONE);
            holder.myyet_debt_num.setBackgroundResource(R.drawable.item_top_gray);
            holder.record_ren_ling_name.setTextColor(context.getResources().getColor(R.color.light_black));
            holder.tv_complte.setVisibility(View.VISIBLE);
            holder.tv_complte.setBackground(null);
            holder.tv_complte.setText("已完成");
            holder.tv_complte.setTextColor(context.getResources().getColor(R.color.blue));
            holder.tv_complte.setClickable(false);

        } else if (dataListBean.getStatus() == 4) {//待确认
            holder.tv_complte.setVisibility(View.VISIBLE);
            holder.rcoud_timer_type.setText("距摘牌失效");
            holder.debt_record_timer.setVisibility(View.VISIBLE);
            holder.debt_record_timer.setText(dataListBean.getEndDays() + "");
            holder.record_day_date.setText(" 天");

        } else {//新建,以展出，已摘牌
            holder.rcoud_timer_type.setText("距摘牌失效");
            holder.debt_record_timer.setVisibility(View.VISIBLE);
            holder.debt_record_timer.setText(dataListBean.getEndDays() + "");
            holder.record_day_date.setText(" 天");
            holder.tv_complte.setVisibility(View.GONE);
        }
        holder.tv_complte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_climed.onDelete(dataListBean.getId(), 0);//确认完成
            }
        });
        Glide.with(context).load(dataListBean.getUserImage()).
                bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000).into(holder.my_record_header);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Debt_Details.class).
                        putExtra("id", dataListBean.getId())
                        .putExtra("childUserId",childUserId)
                        .putExtra("comePager", Constant.PAGER_MY_CHILDDEBT_YZP)

                );
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

