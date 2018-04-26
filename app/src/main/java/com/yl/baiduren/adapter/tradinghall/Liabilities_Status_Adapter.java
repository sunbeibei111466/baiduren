package com.yl.baiduren.adapter.tradinghall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/7.
 */

public class Liabilities_Status_Adapter extends RecyclerView.Adapter<MyViewHolderLiabilities> {

    private Context context;
    private List<AssignmentEntity.LiabilitiesDO> liabilitiesDOList;
    private boolean isVisibility;//true 时隐藏删除按钮

    public Liabilities_Status_Adapter(Context activity) {
        context = activity;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public void setStringList(List<AssignmentEntity.LiabilitiesDO> stringList) {
        this.liabilitiesDOList = stringList;
    }

    public List<AssignmentEntity.LiabilitiesDO> getLiabilitiesDOList() {
        return liabilitiesDOList;
    }

    @Override
    public MyViewHolderLiabilities onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.liabilities_status_adapte_item,parent ,false);
        MyViewHolderLiabilities myViewHolder = new MyViewHolderLiabilities(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderLiabilities holder, final int position) {
        holder.et_liabilities_adapter_name.setText(liabilitiesDOList.get(position).getName());
        holder.et_liabilities_zjh.setText(liabilitiesDOList.get(position).getIdNumber());
        holder.et_liabilities_adapter_phone.setText(liabilitiesDOList.get(position).getMobile());
        holder.et_liabilities_area.setText(liabilitiesDOList.get(position).getAreaStr());
        holder.et_liabilities_adapter_address.setText(liabilitiesDOList.get(position).getAddress());
        holder.et_liabilities_amount.setText(String.valueOf(liabilitiesDOList.get(position).getAmount()));
        holder.et_liabilities_adapter_time.setText(Util.LongParseString(liabilitiesDOList.get(position).getDebtTime()));
        if(isVisibility){
            holder.iv_liabilities_adaptr_delete.setVisibility(View.GONE);
        }
        holder.iv_liabilities_adaptr_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liabilitiesDOList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return liabilitiesDOList.size() ;
    }
}

class MyViewHolderLiabilities extends RecyclerView.ViewHolder{

    public TextView et_liabilities_adapter_name,et_liabilities_zjh,et_liabilities_adapter_phone
            ,et_liabilities_area,et_liabilities_adapter_address,et_liabilities_amount
            ,et_liabilities_adapter_time;
    public ImageView iv_liabilities_adaptr_delete;

    public MyViewHolderLiabilities(View itemView) {
        super(itemView);
        et_liabilities_adapter_name=itemView.findViewById(R.id.et_liabilities_adapter_name);
        et_liabilities_zjh=itemView.findViewById(R.id.et_liabilities_zjh);
        et_liabilities_adapter_phone=itemView.findViewById(R.id.et_liabilities_adapter_phone);
        et_liabilities_area=itemView.findViewById(R.id.et_liabilities_area);
        et_liabilities_adapter_address=itemView.findViewById(R.id.et_liabilities_adapter_address);
        et_liabilities_amount=itemView.findViewById(R.id.et_liabilities_amount);
        et_liabilities_adapter_time=itemView.findViewById(R.id.et_liabilities_adapter_time);
        iv_liabilities_adaptr_delete=itemView.findViewById(R.id.iv_liabilities_adaptr_delete);
    }
}