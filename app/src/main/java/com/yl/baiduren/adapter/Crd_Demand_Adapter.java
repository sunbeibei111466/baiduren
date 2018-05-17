package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsAssets;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsDemand;
import com.yl.baiduren.entity.greenentity.AssetDO;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.greenentity.DemandDO;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/14.
 */

public class Crd_Demand_Adapter extends RecyclerView.Adapter<My_Demand_ViewHolder> {
    private Context context;
    private List<DemandDO> lsit;
    private List<AssetDO> assetDOList;
    private int index;

    public Crd_Demand_Adapter(Context context, List<DemandDO> lsit, List<AssetDO> assetDOList, int i) {
        this.context = context;
        this.lsit = lsit;
        index = i;
        this.assetDOList = assetDOList;
    }


    @Override
    public My_Demand_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crd_assic_item, parent, false);
        My_Demand_ViewHolder viewHolder = new My_Demand_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Demand_ViewHolder holder, final int position) {

        String imageUrl="";
        if (index == 1) {

            CategoryDo categoryDo=GreenDaoUtils.getInstance(context).getCategoryDoDao().load((long) lsit.get(position).getCategoryId());
            imageUrl=categoryDo.getImage();
            Glide.with(context).load(imageUrl).into(holder.iv_crd_assic);
            holder.tv_crd_amout.setText(lsit.get(position).getValueStr());
            holder.tv_crd_area.setText(lsit.get(position).getDizhi());
            holder.tv_crd_beizhu.setText(lsit.get(position).getRemark());
            holder.tv_need_name.setText("需求名称: " + lsit.get(position).getName());

        } else if (index == 2) {
            CategoryDo categoryDo=GreenDaoUtils.getInstance(context).getCategoryDoDao().load((long) assetDOList.get(position).getCategoryId());
            imageUrl=categoryDo.getImage();
            Glide.with(context).load(imageUrl).into(holder.iv_crd_assic);
            holder.tv_crd_amout.setText(assetDOList.get(position).getPriceStr());
            holder.tv_crd_area.setText(assetDOList.get(position).getDizhi());
            holder.tv_crd_beizhu.setText(assetDOList.get(position).getRemark());
            holder.tv_need_name.setText("资产名称: " + assetDOList.get(position).getName());
        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //删除

                if (index == 1) { //需求
                    List<DemandDO> demandDOList = GreenDaoUtils.getInstance(context).getDemandDODao().loadAll();
                    if (demandDOList != null && demandDOList.size() != 0) {
                        LUtils.e("----size---", demandDOList.size() + "");
                        LUtils.e("-----lsit-----", lsit.size() + "");

                        //删除数据库
                        GreenDaoUtils.getInstance(context).getDemandDODao().delete(lsit.get(position));
                        lsit.remove(position);
                    }
                } else if (index == 2) {//资产
                    List<AssetDO> assetDOS = GreenDaoUtils.getInstance(context).getAssetDODao().loadAll();
                    if (assetDOS != null && assetDOS.size() != 0) {
                        LUtils.e("----size---", assetDOS.size() + "");
                        //删除数据库
                        GreenDaoUtils.getInstance(context).getAssetDODao().delete(assetDOList.get(position));
                        assetDOList.remove(position);
                    }
                }
                notifyDataSetChanged();

            }
        });
        holder.ll_demand_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//编辑
                if (index == 1) {//需求
                    Intent intent = new Intent(context, Debt_CreditorsDemand.class);
                    intent.putExtra("updataDemandId", lsit.get(position).getId());
                    context.startActivity(intent);
                } else if (index == 2) {//资产
                    Intent intent = new Intent(context, Debt_CreditorsAssets.class);
                    intent.putExtra("updataAssetrId", assetDOList.get(position).getId());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if(index==1){
            return lsit.size();
        }else {
            return assetDOList.size();
        }

    }
}

class My_Demand_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_crd_assic, iv_delete;
    public TextView tv_need_name, tv_crd_amout, tv_crd_area, tv_crd_beizhu;
    public CardView ll_demand_parent;

    public My_Demand_ViewHolder(View itemView) {
        super(itemView);
        iv_crd_assic = itemView.findViewById(R.id.iv_crd_assic);
        tv_need_name = itemView.findViewById(R.id.tv_need_name);
        iv_delete = itemView.findViewById(R.id.iv_delete);
        tv_crd_amout = itemView.findViewById(R.id.tv_crd_amout);
        tv_crd_area = itemView.findViewById(R.id.tv_crd_area);
        tv_crd_beizhu = itemView.findViewById(R.id.tv_crd_beizhu);
        ll_demand_parent = itemView.findViewById(R.id.ll_demand_parent);

    }
}
