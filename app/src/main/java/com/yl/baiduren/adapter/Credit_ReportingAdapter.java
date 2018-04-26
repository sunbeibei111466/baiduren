package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;

import java.util.HashMap;

/**
 * Created by Android_apple on 2018/3/22.
 */

public class Credit_ReportingAdapter extends RecyclerView.Adapter<MyCredit> {

    private Context context;
    private HashMap<String, Boolean> states;
//    private List<CreditReport> creditReports;

    public Credit_ReportingAdapter(Context context) {
        this.context=context;
        states = new HashMap<>();
    }

//    public void setCreditReports(List<CreditReport> creditReports) {
//        this.creditReports = creditReports;
//    }

    @Override
    public MyCredit onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_reporting_item, parent, false);
        MyCredit myCredit=new MyCredit(view);
        return myCredit;
    }

    @Override
    public void onBindViewHolder(MyCredit holder, final int position) {
//        holder.type.setText(creditReports.get(position).getInformation());
//        if(!creditReports.get(position).getPriceStr().equals("0")){//基础报告
//            holder.xianjia.setText("￥"+creditReports.get(position).getPriceStr()+"/份");
//        }else {
//            holder.xianjia.setText("联系客服");
//        }
//
//        holder.rl_credit_parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EventBus.getDefault().post(new Reture_Price(creditReports.get(position).getId(),creditReports.get(position).getInformation()));//type=2时个人vip
//                // 重置，确保最多只有一项被选中
//                for (String key : states.keySet()) {
//                    states.put(key, false);
//                }
//                states.put(String.valueOf(position), true);
//                notifyDataSetChanged();
//            }
//        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null || !states.get(String.valueOf(position))) {//未选中
            res = false;
            states.put(String.valueOf(position), false);
            holder.type.setTextColor(context.getResources().getColor(R.color.light_hei));
            holder.xianjia.setTextColor(context.getResources().getColor(R.color.blue));
        } else {  //选中时
            res = true;
            holder.type.setTextColor(context.getResources().getColor(R.color.white));
            holder.xianjia.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.rl_credit_parent.setSelected(res);
    }

    @Override
    public int getItemCount() {
//        return creditReports.size();
        return 0;
    }
}
class MyCredit extends RecyclerView.ViewHolder{

    public TextView type,xianjia;
    public RelativeLayout rl_credit_parent;

    public MyCredit(View itemView) {
        super(itemView);

        type=itemView.findViewById(R.id.tv_credit_type_item);//报告类别
        xianjia=itemView.findViewById(R.id.tv_credit_xianjia_item);//现价
        rl_credit_parent=itemView.findViewById(R.id.rl_credit_parent);//
    }
}