package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.result.CurrencyLendingDetails_Result;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class Curry_Details_Adapter extends RecyclerView.Adapter<My_Curry_ViewHolder>{
    public Curry_Details_Adapter(Context context, List<CurrencyLendingDetails_Result.PayRecordListBean> payRecordList) {
        this.context = context;
        this.payRecordList = payRecordList;
    }

    private Context context;
    private List<CurrencyLendingDetails_Result.PayRecordListBean> payRecordList;

    @Override
    public My_Curry_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_curry_detaisl_item,parent,false);
        My_Curry_ViewHolder viewHolder =new My_Curry_ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Curry_ViewHolder holder, int position) {
        CurrencyLendingDetails_Result.PayRecordListBean payRecordListBean = payRecordList.get(position);
        holder.tv_currency_details_riqi.setText(Util.LongParseString(payRecordListBean.getPayTime()));
        holder.tv_currency_details_jine.setText(payRecordListBean.getPayMoneyStr()+"万");

    }

    @Override
    public int getItemCount() {
        return payRecordList.size();
    }
}
class My_Curry_ViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_currency_details_riqi,tv_currency_details_jine;

    public My_Curry_ViewHolder(View itemView) {
        super(itemView);
        tv_currency_details_riqi = itemView.findViewById(R.id.tv_currency_details_riqi);//结算日期
        tv_currency_details_jine=itemView.findViewById(R.id.tv_currency_details_jine);//结算金额

    }
}
