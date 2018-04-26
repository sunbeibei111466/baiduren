package com.yl.baiduren.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.activity.debtrecord.AssetrMortgageDetails;
import com.yl.baiduren.activity.debtrecord.CurrencyLendingDetails;
import com.yl.baiduren.activity.debtrecord.PhysicalBorrowingDetails;
import com.yl.baiduren.activity.debtrecord.PropertyRightsProofDetails;
import com.yl.baiduren.activity.debtrecord.SponsorDetails;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.result.Debt_Details_Result;
import com.yl.baiduren.utils.Constant;

import java.util.List;


/**
 * Created by Android_apple on 2017/12/15.
 */

public class DebtDetailsProofAdapter extends RecyclerView.Adapter<MyViewHolder_Proof_1> {

    private Debt_Details context;
    private Long userId;
    private com.yl.baiduren.data.BaseRequest baseRequest;
    private String comePager;
    private List<Debt_Details_Result.DebtRelation4ResponsesBean> debtRelation4Responses;
    private String status;

    public DebtDetailsProofAdapter(Debt_Details debt_details, String comePager,String status) {
        this.status=status;
        context = debt_details;
        baseRequest = DataWarehouse.getBaseRequest(context);
        this.comePager = comePager;
    }

    public void setTest2List(List<Debt_Details_Result.DebtRelation4ResponsesBean> debtRelation4Responses) {
        this.debtRelation4Responses = debtRelation4Responses;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setChildUserId(Long childUserId) {
        Long childUserId1 = childUserId;
    }

    @Override
    public MyViewHolder_Proof_1 onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.debt_details_adapter_proof_1, parent, false);
        MyViewHolder_Proof_1 holder = new MyViewHolder_Proof_1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder_Proof_1 holder, final int position) {
        final Debt_Details_Result.DebtRelation4ResponsesBean debtRelation4ResponsesBean = debtRelation4Responses.get(position);
        final int category = debtRelation4ResponsesBean.getCategory();
        if (category == 1) {//货币借贷
            Glide.with(context).load(R.mipmap.huobijiedai).into(holder.iv_item1_image);
            holder.need_name.setText(debtRelation4ResponsesBean.getTitle());
            holder.gu_jia.setText("币种:");
            holder.total_money.setText("总额:");
            holder.tv_adapter_item1_whje.setText(debtRelation4ResponsesBean.getThird());
            holder.yet_amout.setVisibility(View.VISIBLE);
            holder.yet_amout.setText("未还金额:");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CurrencyLendingDetails.class).
                            putExtra("curry_detail", debtRelation4ResponsesBean.getId()).
                            putExtra("userId", userId));
                }
            });

        } else if (category == 2) {//实物借贷
            Glide.with(context).load(R.mipmap.shiwujiedai).into(holder.iv_item1_image);
            holder.need_name.setText(debtRelation4ResponsesBean.getTitle());
            holder.gu_jia.setText("物品名称:");
            holder.total_money.setText("评估价值:");
            holder.tv_adapter_item1_whje.setText(debtRelation4ResponsesBean.getThird());
            holder.yet_amout.setVisibility(View.VISIBLE);
            holder.yet_amout.setText("未还金额:");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, PhysicalBorrowingDetails.class).
                            putExtra("phy_detail", debtRelation4ResponsesBean.getId()).
                            putExtra("userId", userId));
                }
            });

        } else if (category == 3) {//产权借贷
            Glide.with(context).load(R.mipmap.cqjd).into(holder.iv_item1_image);
            holder.need_name.setText(debtRelation4ResponsesBean.getTitle());
            holder.gu_jia.setText("标的名称:");
            holder.total_money.setText("已还金额:");
            holder.yet_amout.setVisibility(View.VISIBLE);
            holder.tv_adapter_item1_whje.setText(debtRelation4ResponsesBean.getThird());
            holder.yet_amout.setText("未还金额:");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, PropertyRightsProofDetails.class).
                            putExtra("proper_detail", debtRelation4ResponsesBean.getId()).
                            putExtra("userId", userId));
                }
            });

        } else if (category == 4) {//担保人
            Glide.with(context).load(R.mipmap.danbaoren).into(holder.iv_item1_image);
            holder.need_name.setText(debtRelation4ResponsesBean.getTitle());
            holder.gu_jia.setText("姓名:");
            holder.total_money.setText("归属地:");
            holder.tv_adapter_item1_whje.setVisibility(View.GONE);
            holder.yet_amout.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, SponsorDetails.class).
                            putExtra("sponsor_detail", debtRelation4ResponsesBean.getId()).
                            putExtra("userId", userId));
                }
            });
        } else if (category == 5) {//资产抵押
            Glide.with(context).load(R.mipmap.zichandiya).into(holder.iv_item1_image);
            holder.need_name.setText(debtRelation4ResponsesBean.getTitle());
            holder.gu_jia.setText("名称:");
            holder.total_money.setText("评审估值:");
            holder.tv_adapter_item1_whje.setVisibility(View.GONE);
            holder.yet_amout.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AssetrMortgageDetails.class).
                            putExtra("mort_detail", debtRelation4ResponsesBean.getId()).
                            putExtra("userId", userId));
                }
            });
        }
        holder.tv_adapter_item1_money.setText(debtRelation4ResponsesBean.getFirst());
        holder.tv_adapter_item1_number.setText(debtRelation4ResponsesBean.getSecond());


        if (userId.equals(baseRequest.uid)) { //备案人 与 登陆人 是同一人
            if(comePager.equals(Constant.PAGER_MYDEBT_YZP)){
                if(status.equals("已解债")){
                    holder.iv_debt_details_item1_delete.setVisibility(View.GONE);
                }else {
                    holder.iv_debt_details_item1_delete.setVisibility(View.VISIBLE);
                }
            }else {
                holder.iv_debt_details_item1_delete.setVisibility(View.VISIBLE);
            }
        } else {
                holder.iv_debt_details_item1_delete.setVisibility(View.GONE);
        }

        holder.iv_debt_details_item1_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 1) {//货币借贷
                    context.onDelete(debtRelation4ResponsesBean.getId(), 3);
                } else if (category == 2) {//实物借贷
                    context.onDelete(debtRelation4ResponsesBean.getId(), 4);
                } else if (category == 3) {//产权借贷
                    context.onDelete(debtRelation4ResponsesBean.getId(), 5);
                } else if (category == 4) {//担保人
                    context.onDelete(debtRelation4ResponsesBean.getId(), 6);
                } else if (category == 5) {//资产抵押
                    context.onDelete(debtRelation4ResponsesBean.getId(), 7);
                }
                debtRelation4Responses.remove(position);
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return debtRelation4Responses.size();
    }
}

class MyViewHolder_Proof_1 extends RecyclerView.ViewHolder {

    public TextView tv_adapter_item1_money, tv_adapter_item1_number, tv_adapter_item1_whje, gu_jia, total_money, yet_amout, need_name;
    public ImageView iv_item1_image, iv_debt_details_item1_delete;

    public MyViewHolder_Proof_1(View itemView) {
        super(itemView);
        gu_jia = itemView.findViewById(R.id.gu_jia);//名称
        total_money = itemView.findViewById(R.id.total_money);//总额
        yet_amout = itemView.findViewById(R.id.yet_amout);//未还金额数
        need_name = itemView.findViewById(R.id.need_name);//总类型
        tv_adapter_item1_money = itemView.findViewById(R.id.tv_adapter_item1_money);//币种
        tv_adapter_item1_number = itemView.findViewById(R.id.tv_adapter_item1_number);//总额
        tv_adapter_item1_whje = itemView.findViewById(R.id.tv_adapter_item1_whje);//未还金额
        iv_item1_image = itemView.findViewById(R.id.iv_item1_image);//图片
        iv_debt_details_item1_delete = itemView.findViewById(R.id.iv_debt_details_item1_delete);
    }
}



