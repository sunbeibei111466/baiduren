package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtmanagpreson.Debt_EnterPrise_Details;
import com.yl.baiduren.entity.result.DebtPersonList;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/7.
 * 债事企业列表适配器
 */

public class Debt_EnterPrise_Adapter extends RecyclerView.Adapter<MyEnterPrise_Holder> {

    private Context context;
    private List<DebtPersonList.Mode> list;
    private Long childUserId;

    public Debt_EnterPrise_Adapter(Long childUserId, Context context) {
        this.context = context;
        this.childUserId=childUserId;
    }
    public void setDataList(List<DebtPersonList.Mode> list){
        this.list=list;
    }

    public List<DebtPersonList.Mode> getList() {
        return list;
    }

    @Override
    public MyEnterPrise_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_enterprise_item, parent, false);
        MyEnterPrise_Holder holder = new MyEnterPrise_Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyEnterPrise_Holder holder, final int position) {
        holder.enterprise_num.setText(position+1 + "");
        holder.enterprise_name.setText(list.get(position).getName());
        holder.enterprise_area.setText(list.get(position).getAreaName());
        holder.enterprise_type.setText(list.get(position).getCompanyCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, Debt_EnterPrise_Details.class).
                        putExtra("debtEnterPriseId", list.get(position).getId()).
                        putExtra("childUserId",childUserId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyEnterPrise_Holder extends RecyclerView.ViewHolder {

    public TextView enterprise_num;
    public TextView enterprise_name;//企业名称
    public TextView enterprise_area;//企业位置
    public TextView enterprise_type;//企业类型

    public MyEnterPrise_Holder(View itemView) {
        super(itemView);
        enterprise_num = itemView.findViewById(R.id.enterprise_num);
        enterprise_name = itemView.findViewById(R.id.debt_enterprise_name);
        enterprise_area = itemView.findViewById(R.id.debt_enterprise_area);
        enterprise_type = itemView.findViewById(R.id.debt_enterprsie_type);
    }
}