package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by sunbeibei on 2017/12/15.
 */

public class My_Record_Fragmnet_Yet_Adapter extends RecyclerView.Adapter<My_Recrod_Yet_ViewHolder>{

    private Context context;
    private List<My_Record_Result.DataListBean> dataList;

    public My_Record_Fragmnet_Yet_Adapter(Context context) {
        this.context = context;
    }


    public  void setDataList(List<My_Record_Result.DataListBean> dataList){
        this.dataList=dataList;
    }
    public  List<My_Record_Result.DataListBean>getList(){
        return dataList;
    }
    @Override
    public My_Recrod_Yet_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_record_yet_item,parent,false);
        My_Recrod_Yet_ViewHolder viewHolder=new My_Recrod_Yet_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Recrod_Yet_ViewHolder holder, int position) {
         int num=0;
        final My_Record_Result.DataListBean dataListBean = dataList.get(position);
        holder.debt_record_amout.setText(dataListBean.getAmountStr());
        holder.debt_record_adress.setText(dataListBean.getAreaName());
        holder.break_record_aoumt.setText(dataListBean.getCommission().multiply(new BigDecimal("100")).setScale(1)+"%");
        holder.tv_record_timer.setText("债事登记时间："+ Util.LongParseString(dataListBean.getCreateTime()));
        holder.debt_record_timer.setText(Util.LongParseString(dataListBean.getRecordTime()));
        holder.myyet_debt_num.setText("债事编号:"+dataListBean.getCode());
        String progress = dataListBean.getProgress();
        if (!TextUtils.isEmpty(progress)){
            char [] chars=progress.toCharArray();
            for (char aChar : chars) {
                if (aChar == '1') {
                    num++;
                }
            }
            if (num==1){
                holder.information_show.setText("资料不足，请及时补充资料");
            }else if (num==2||num==3){
                holder.information_show.setText("资料未全，影响匹配、摘牌，请及时补充资料");
            }else if (num==4){
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
                        putExtra("id",dataListBean.getId())
                        .putExtra("comePager", Constant.PAGER_MYDEBT_WZP)
                );
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class  My_Recrod_Yet_ViewHolder extends RecyclerView.ViewHolder{
    public ImageView my_record_header;
    public TextView debt_record_adress,tv_record_timer,debt_record_amout,

            debt_record_timer,break_record_aoumt,information_show,myyet_debt_num;
    public LinearLayout line_layout;
    public My_Recrod_Yet_ViewHolder(View itemView) {
        super(itemView);
        my_record_header=itemView.findViewById(R.id.my_record_header);//列表头像
        debt_record_adress=itemView.findViewById(R.id.debt_record_adress);//列表发生地址
        tv_record_timer=itemView.findViewById(R.id.tv_record_timer);//债事登记时间
        debt_record_amout=itemView.findViewById(R.id.debt_record_amout);//债事金额
        debt_record_timer=itemView.findViewById(R.id.debt_record_timer);//债事发生时间
        break_record_aoumt=itemView.findViewById(R.id.break_record_aoumt);//解债佣金
        information_show=itemView.findViewById(R.id.information_show);//信息提示
        line_layout=itemView.findViewById(R.id.line_layout);
        myyet_debt_num=itemView.findViewById(R.id.myyet_debt_num);//债事编号








    }
}
