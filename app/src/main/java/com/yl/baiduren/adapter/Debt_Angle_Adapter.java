package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.entity.result.Debt_Angle_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2018/3/12.
 */

public class Debt_Angle_Adapter extends RecyclerView.Adapter<Debt_Angle_ViewHolder> {
    private Context context;
    private List<Debt_Angle_Result.Angle> dataList;
    public Debt_Angle_Adapter(Context context) {
        this.context = context;
    }
    public void setList(List<Debt_Angle_Result.Angle> dataList){
        this.dataList=dataList;

    }
    public List<Debt_Angle_Result.Angle>getList(){
        return dataList;
    }



    @Override
    public Debt_Angle_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_angle_item,parent,false);
        Debt_Angle_ViewHolder viewHolder =new Debt_Angle_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Debt_Angle_ViewHolder holder, int position) {
        Debt_Angle_Result.Angle angle = dataList.get(position);
        Glide.with(context).load(angle.getImage())//加载时图片
                .placeholder(R.mipmap.login_head)
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.break_head);
        holder.break_name.setText(angle.getUserName());
        holder.break_adress.setText(angle.getAreaStr());
        holder.break_num.setText(angle.getCompleteCount());
        holder.debt_amout.setText(angle.getAmount());
        holder.delist_number.setText(angle.getTotalCount());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class Debt_Angle_ViewHolder extends RecyclerView.ViewHolder{
    public ImageView break_head;
    public TextView break_name,break_adress,debt_amout,delist_number,break_num;

    public Debt_Angle_ViewHolder(View itemView) {
        super(itemView);
        break_head=itemView.findViewById(R.id.break_head);//解债天使头像
        break_name=itemView.findViewById(R.id.break_name);//解债人姓名
        break_adress=itemView.findViewById(R.id.break_adress);//解债人地址
        debt_amout=itemView.findViewById(R.id.debt_amout);//解债金额
        delist_number=itemView.findViewById(R.id.delist_number);//摘牌数量
        break_num=itemView.findViewById(R.id.break_num);//解债数量










    }
}
