package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.entity.result.HallListMode;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2017/12/20.
 */

public class Debt_Transfer_Platform_Adapter extends RecyclerView.Adapter<Debt_transfre_ViewHolder> {

    private Context context;
    private int type;//1 从债权大厅进入详情，2从我的债权进入详情
    private List<HallListMode> hallListModeList;
    private onDeleteItem onDeleteItem;
    public interface onDeleteItem{
        void onClickDeleteItem(Long id, int position);
    }

    public void setOnDeleteItem(Debt_Transfer_Platform_Adapter.onDeleteItem onDeleteItem) {
        this.onDeleteItem = onDeleteItem;
    }

    public Debt_Transfer_Platform_Adapter(Context context) {
        this.context = context;
    }

    public List<HallListMode> getHallListModeList() {
        return hallListModeList;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setHallListModeList(List<HallListMode> hallListModeList) {
        this.hallListModeList = hallListModeList;
    }

    @Override
    public Debt_transfre_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_transfer_item, parent, false);
        Debt_transfre_ViewHolder viewHolder = new Debt_transfre_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Debt_transfre_ViewHolder holder, final int position) {
        if(!TextUtil.isEmpty(hallListModeList.get(position).getImgUrl())){
            Glide.with(context).load(hallListModeList.get(position).getImgUrl())//加载时图片
                    .bitmapTransform(new CropCircleTransformation(context))
                    .crossFade(1000)
                    .error(R.drawable.qiy_icon)
                    .into(holder.company_logo);
        }else{
            holder.company_logo.setImageResource(R.drawable.qiy_icon);
        }

        holder.transfer_company_name.setText(hallListModeList.get(position).getEnterpriseName());
        holder.tranfer_area.setText(hallListModeList.get(position).getAreaStr());
        holder.profile.setText(hallListModeList.get(position).getProfile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//查询详情 type
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AssignmentActivity.class);
                intent.putExtra("type",type);//判断是从哪里进入详情，且是否可以编辑
                intent.putExtra("claimsId",hallListModeList.get(position).getClaimsId());
                context.startActivity(intent);
            }
        });
        if(type==1){
            holder.iv_delete_assignment.setVisibility(View.GONE);
        }
        holder.iv_delete_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteItem.onClickDeleteItem(hallListModeList.get(position).getClaimsId(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hallListModeList.size();
    }
}

class Debt_transfre_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView company_logo,iv_delete_assignment;
    public TextView transfer_company_name, tranfer_area, profile;

    public Debt_transfre_ViewHolder(View itemView) {
        super(itemView);
        company_logo = itemView.findViewById(R.id.company_logo);//公司logo
        transfer_company_name = itemView.findViewById(R.id.transfer_company_name);//公司名称
        tranfer_area = itemView.findViewById(R.id.tranfer_area);//地区
        profile = itemView.findViewById(R.id.profile);//简介
        iv_delete_assignment=itemView.findViewById(R.id.iv_delete_assignment);//删除

    }
}
