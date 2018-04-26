package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.credit_reporting_queries.AuthorizationConfirmPager;
import com.yl.baiduren.entity.result.Authouization_Confrim_List_Result;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/27.
 */

public class Authorzation_Confrim_Adapter extends RecyclerView.Adapter<Authour_Comfrim_ViewHolder> {
    private Context context;
    private List<Authouization_Confrim_List_Result.AuthorizeResponse> dataList;

    public Authorzation_Confrim_Adapter(Context context) {
        this.context = context;
    }
    public void setList(List<Authouization_Confrim_List_Result.AuthorizeResponse> dataList){
        this.dataList=dataList;
    }
    public List<Authouization_Confrim_List_Result.AuthorizeResponse>getList(){
        return dataList;
    }

    @Override
    public Authour_Comfrim_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.authorization_confirm_item,parent,false);
        Authour_Comfrim_ViewHolder viewHolder =new Authour_Comfrim_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Authour_Comfrim_ViewHolder holder, int position) {
        final Authouization_Confrim_List_Result.AuthorizeResponse authorizeResponse = dataList.get(position);
        holder.tv_applyName.setText(authorizeResponse.getSendName());
        holder.apply_time.setText(Util.LongParseString(authorizeResponse.getCreateTime()));
        holder.reson.setText(authorizeResponse.getSendReason());
        Integer status = authorizeResponse.getStatus();
        if (status==1){//1是未回复，2,3是已回复
            holder.return_status.setText("未回复");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, AuthorizationConfirmPager.class)
                            .putExtra("id",authorizeResponse.getId()).putExtra("main_name",authorizeResponse.getSendName());
                    context.startActivity(intent);
                }
            });
        }else{
            holder.return_status.setText("已回复");
        }



    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class  Authour_Comfrim_ViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_applyName,apply_time,return_status,reson;

    public Authour_Comfrim_ViewHolder(View itemView) {
        super(itemView);
        tv_applyName=itemView.findViewById(R.id.tv_applyName);//申请主体
        apply_time=itemView.findViewById(R.id.apply_time);//申请时间
        return_status=itemView.findViewById(R.id.return_status);//回复状态
        reson=itemView.findViewById(R.id.reson);//回复原因




    }
}
