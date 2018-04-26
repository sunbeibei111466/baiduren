package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Sponsor;
import com.yl.baiduren.entity.greenentity.SponsorDO;
import com.yl.baiduren.utils.GreenDaoUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4_4Adapter extends RecyclerView.Adapter<MyViewH4> {

    private Context context;
    private List<SponsorDO> sponsorDOList;

    public Debt4_4Adapter(Context context) {
        this.context = context;
    }

    public void setSponsorDOList(List<SponsorDO> sponsorDOList) {
        this.sponsorDOList = sponsorDOList;
    }

    @Override
    public MyViewH4 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_adapter_proof_4, parent, false);
        MyViewH4 myViewH = new MyViewH4(view);
        return myViewH;
    }

    @Override
    public void onBindViewHolder(MyViewH4 holder, final int position) {

        holder.tv_adapter_item4_name.setText(sponsorDOList.get(position).getName());
        holder.tv_adapter_item4_gsd.setText(sponsorDOList.get(position).getDizhi());
        holder.iv_debt_details_item4_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreenDaoUtils.getInstance(context).getSponsorDODao().delete(sponsorDOList.get(position));
                sponsorDOList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.ll_parent_4.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Sponsor.class).
                        putExtra("updataId",sponsorDOList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return sponsorDOList.size();
    }
}

class MyViewH4 extends RecyclerView.ViewHolder {

    public TextView tv_adapter_item4_name,tv_adapter_item4_gsd;
    public ImageView iv_item4_image,iv_debt_details_item4_delete;
    public LinearLayout ll_parent_4;

    public MyViewH4(View itemView) {
        super(itemView);
        iv_item4_image=itemView.findViewById(R.id.iv_item4_image);
        tv_adapter_item4_name=itemView.findViewById(R.id.tv_adapter_item4_name);//担保人姓名
        tv_adapter_item4_gsd=itemView.findViewById(R.id.tv_adapter_item4_gsd);//归属地
        iv_debt_details_item4_delete=itemView.findViewById(R.id.iv_debt_details_item4_delete);
        ll_parent_4=itemView.findViewById(R.id.ll_parent_4);
    }
}
