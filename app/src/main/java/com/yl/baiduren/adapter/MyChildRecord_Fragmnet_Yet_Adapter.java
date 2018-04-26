package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by sunbeibei on 2017/12/15.
 */

public class MyChildRecord_Fragmnet_Yet_Adapter extends RecyclerView.Adapter<My_Recrod_Yet_ViewHolder> {

    private Context context;
    private List<My_Record_Result.DataListBean> dataList;
    private Long childUserId;//子账号id

    public MyChildRecord_Fragmnet_Yet_Adapter(Context context, Long childUserId) {
        this.context = context;
        this.childUserId = childUserId;
    }


    public void setDataList(List<My_Record_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }
    public List<My_Record_Result.DataListBean>getList(){
        return dataList;
    }

    @Override
    public My_Recrod_Yet_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_record_yet_item, parent, false);
        My_Recrod_Yet_ViewHolder viewHolder = new My_Recrod_Yet_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Recrod_Yet_ViewHolder holder, int position) {
        int num = 0;
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);
        holder.debt_record_amout.setText(dataListBean.getAmountStr());
        holder.debt_record_adress.setText(dataListBean.getAreaName());
        holder.break_record_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1) + "%");
        holder.tv_record_timer.setText("债事登记时间：" + Util.LongParseString(dataListBean.getCreateTime()));
        holder.debt_record_timer.setText(Util.LongParseString(dataListBean.getRecordTime()));
        holder.myyet_debt_num.setText("债事编号:" + dataListBean.getCode());
        String progress = dataListBean.getProgress();
        if (!TextUtils.isEmpty(progress)) {
            char[] chars = progress.toCharArray();
            for (char aChar : chars) {
                if (aChar == '1') {
                    num++;
                }
            }
            if (num == 1) {
                holder.information_show.setText("资料不足展出标准，请及时补充资料");
            } else if (num == 2 || num == 3) {
                holder.information_show.setText("资料未全，影响匹配、摘牌，请及时补充资料");
            } else if (num == 4) {
                holder.line_layout.setVisibility(View.GONE);
            }
        }


        Glide.with(context).load(dataListBean.getUserImage()).
                bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000).into(holder.my_record_header);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Debt_Details.class).
                        putExtra("id", dataListBean.getId()).//债事id
                        putExtra("childUserId", childUserId)//
                        .putExtra("comePager", Constant.PAGER_MY_CHILDDEBT_WZP)
                );
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

