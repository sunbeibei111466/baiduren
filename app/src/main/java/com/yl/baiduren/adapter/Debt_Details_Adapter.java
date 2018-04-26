package com.yl.baiduren.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.activity.debtrecord.Debt_Assaic_Details;
import com.yl.baiduren.activity.debtrecord.Debt_Demend_Details;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.result.Debt_Details_Result;
import com.yl.baiduren.utils.Constant;

import java.util.List;


/**
 * Created by sunbeibei on 2017/12/11.
 */

public class Debt_Details_Adapter extends RecyclerView.Adapter<My_Debt_Detali_ViewHolder> {
    private Debt_Details context;
    private int type;
    private Long userId = 0l;//备案人Id
    private com.yl.baiduren.data.BaseRequest baseRequest;
    private List<Debt_Details_Result.DemandResponsesBean> demandResponses;
    private List<Debt_Details_Result.AssetResponsesBean> assetResponses;
    private Debt_Details_Result.DemandResponsesBean demandResponsesBean;
    private Debt_Details_Result.AssetResponsesBean assetResponsesBean;
    private String comePager=null;
    private String status = null;

    public Debt_Details_Adapter(int type, Debt_Details context, List<Debt_Details_Result.DemandResponsesBean> demandResponses, List<Debt_Details_Result.AssetResponsesBean> assetResponses, Long userId, Long childUserId, String comePager, Long debtRelationId, String status) {
        this.type = type;
        this.context = context;
        this.demandResponses = demandResponses;
        this.assetResponses = assetResponses;
        Long debtRelationId1 = debtRelationId;
        this.userId = userId;
        Long childUserId1 = childUserId;
        baseRequest = DataWarehouse.getBaseRequest(context);
        this.comePager = comePager;
        this.status = status;
    }

    @Override
    public My_Debt_Detali_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_item, parent, false);
        My_Debt_Detali_ViewHolder viewHolder = new My_Debt_Detali_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Debt_Detali_ViewHolder holder, final int position) {


        if (type == 1) {  //需求
            demandResponsesBean = demandResponses.get(position);
            holder.gu_jia.setText("金额:");
            holder.need_name.setText("需求名称:" + demandResponsesBean.getName());
            Glide.with(context).load(demandResponsesBean.getCategoryImg()).into(holder.iv_assic);
            holder.tv_area.setText(demandResponsesBean.getAreaName());
            holder.tv_money.setText(demandResponsesBean.getAmoutStr());
            holder.tv_beizhu.setText(demandResponsesBean.getRemark());

        } else if (type == 2) { //资产

            assetResponsesBean = assetResponses.get(position);
            holder.gu_jia.setText("估价:");
            holder.need_name.setText("资产名称:" + assetResponsesBean.getName());
            Glide.with(context).load(assetResponsesBean.getCategoryImg()).into(holder.iv_assic);
            holder.tv_area.setText(assetResponsesBean.getAreaName());
            holder.tv_money.setText(assetResponsesBean.getPriceStr());
            holder.tv_beizhu.setText(assetResponsesBean.getRemark());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//查看详情
                if (type == 1) {
                    context.startActivity(new Intent(context, Debt_Demend_Details.class)
                            .putExtra("id", demandResponsesBean.getId()).
                                    putExtra("userId", userId));
                } else {//查看资产详情
                    context.startActivity(new Intent(context, Debt_Assaic_Details.class).
                            putExtra("assetId", assetResponsesBean.getId()).
                            putExtra("userId", userId)
                    );
                }
            }
        });

        if (userId.equals(baseRequest.uid)) { //备案人 与 登陆人 是同一人
            if (comePager.equals(Constant.PAGER_MYDEBT_YZP)) {
                if (!TextUtils.isEmpty(status) && status.equals("已解债")) {
                    holder.iv_debt_details_delete.setVisibility(View.GONE);
                } else {
                    holder.iv_debt_details_delete.setVisibility(View.VISIBLE);
                }
            } else {
                holder.iv_debt_details_delete.setVisibility(View.VISIBLE);
            }
        } else {
            holder.iv_debt_details_delete.setVisibility(View.GONE);
        }


        holder.iv_debt_details_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//删除


                if (type == 1) {
                    demandResponses.remove(position);
                    notifyDataSetChanged();
                    context.onDelete(demandResponsesBean.getId(), 1);
                } else {
                    assetResponses.remove(position);
                    notifyDataSetChanged();
                    context.onDelete(assetResponsesBean.getId(), 2);
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        if (type == 1) {
            return demandResponses.size();
        } else {
            return assetResponses.size();
        }

    }
}

class My_Debt_Detali_ViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_need;
    public TextView tv_money;
    public TextView tv_area;
    public TextView tv_beizhu;
    public ImageView iv_assic, iv_debt_details_delete;
    public TextView need_name;
    public TextView gu_jia;


    public My_Debt_Detali_ViewHolder(View itemView) {
        super(itemView);
        iv_assic = itemView.findViewById(R.id.iv_assic);//需求图片
        need_name = itemView.findViewById(R.id.need_name);
        gu_jia = itemView.findViewById(R.id.gu_jia);
        tv_need = itemView.findViewById(R.id.tv_need);//需求名称
        tv_money = itemView.findViewById(R.id.tv_money);//需求金额
        tv_area = itemView.findViewById(R.id.tv_area);//地区
        tv_beizhu = itemView.findViewById(R.id.tv_beizhu);//备注
        iv_debt_details_delete = itemView.findViewById(R.id.iv_debt_details_delete);//删除
    }
}
