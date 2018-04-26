package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.request_body.AssignmentEntity;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/15.
 */

public class Operation_Condition_Adapter extends RecyclerView.Adapter<My_Opteration> {
    private Context context;
    private List<AssignmentEntity.ManageDO> list;
    private boolean isVisibility;//true 时隐藏删除按钮

    public Operation_Condition_Adapter(Context context) {
        this.context = context;
    }

    public void setData(List<AssignmentEntity.ManageDO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public List<AssignmentEntity.ManageDO> getList() {
        return list;
    }

    @Override
    public My_Opteration onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.operation_item, parent, false);
        My_Opteration opteration = new My_Opteration(view);
        return opteration;
    }

    @Override
    public void onBindViewHolder(My_Opteration holder, final int position) {
        holder.operation_adapter_name.setText(list.get(position).getIndustry());
        holder.operation_adapter_amout.setText(String.valueOf(list.get(position).getAmount()));
        String b=list.get(position).getIsProfit();
        if(b.equals("1")){
            holder.operaiotn_adapter_zk.setText("是");
        }else{
            holder.operaiotn_adapter_zk.setText("否");
        }
        if(isVisibility){
            holder.delete.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class My_Opteration extends RecyclerView.ViewHolder {
    public TextView operation_adapter_name, operaiotn_adapter_zk, operation_adapter_amout;
    public ImageView delete;

    public My_Opteration(View itemView) {
        super(itemView);
        operation_adapter_name = itemView.findViewById(R.id.operation_adapter_name);//产业名称
        operaiotn_adapter_zk = itemView.findViewById(R.id.operaiotn_adapter_zk);//盈亏状况
        operation_adapter_amout = itemView.findViewById(R.id.operation_adapter_amout);//具体价值
        delete = itemView.findViewById(R.id.operation_adapter_delete);


    }
}