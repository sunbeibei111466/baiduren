package com.yl.baiduren.adapter.crdeit_query_result_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;

/**
 * Created by sunbeibei on 2018/2/5.
 */

public class Creit_Reporting_Query_People_Adapter extends RecyclerView.Adapter<My_Creit_People_ViewHolder> {


    private Context context;

    public Creit_Reporting_Query_People_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public My_Creit_People_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_lndividual_adapter_item,parent,false);
        My_Creit_People_ViewHolder viewHolder =new My_Creit_People_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Creit_People_ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
class My_Creit_People_ViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_lnquiry_name,tv_number,company_name;


    public My_Creit_People_ViewHolder(View itemView) {
        super(itemView);
        tv_lnquiry_name=itemView.findViewById(R.id.tv_lnquiry_name);//姓名
        tv_number=itemView.findViewById(R.id.tv_number);//公司任职数量
        company_name=itemView.findViewById(R.id.company_name);//公司任职名字




    }
}
