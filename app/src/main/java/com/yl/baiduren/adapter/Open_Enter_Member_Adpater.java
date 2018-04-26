package com.yl.baiduren.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.Reture_Price;
import com.yl.baiduren.entity.result.Open_Member_Result;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class Open_Enter_Member_Adpater extends RecyclerView.Adapter<My_Open_Prese_Member> {

    private Context context;
    private HashMap<String, Boolean> states;
    private Open_Member_Result openMemberResult;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Open_Enter_Member_Adpater(Activity context, Open_Member_Result open_member_result) {
        this.context = context;
        this.openMemberResult = open_member_result;
        states = new HashMap<>();
        // 用于记录每个RadioButton的状态，并保证只可选一个

    }

    @Override
    public My_Open_Prese_Member onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.open_enterprse_member_item, parent, false);
        My_Open_Prese_Member viewholder = new My_Open_Prese_Member(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final My_Open_Prese_Member holder, final int position) {
        final Open_Member_Result.MechanisSixtListBean mechanisMonthsAndPriceListBean = openMemberResult.getMechanisSixtList().get(position);
        final Open_Member_Result.MechanisElevenListBean mechanisElevenListBean = openMemberResult.getMechanisElevenList().get(position);
        if (type == 1) {//企业一拖五
            holder.tv_deta.setText(mechanisMonthsAndPriceListBean.getMonth()+"个月");
            if (mechanisMonthsAndPriceListBean.getOriginalPriceStr().equals(mechanisMonthsAndPriceListBean.getPriceStr())) {
                holder.tv_xianjia.setText("原价:￥" + mechanisMonthsAndPriceListBean.getPriceStr());
            } else {
                holder.tv_xianjia.setText("现价:￥" + mechanisMonthsAndPriceListBean.getPriceStr());
                holder.tv_yuanjia.setVisibility(View.VISIBLE);
                holder.tv_yuanjia.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                holder.tv_yuanjia.setText("原价:￥" + mechanisMonthsAndPriceListBean.getOriginalPriceStr());
            }
        } else if (type == 2) {//企业一拖十
            holder.tv_deta.setText(mechanisElevenListBean.getMonth()+"个月");
            if (mechanisElevenListBean.getOriginalPriceStr().equals(mechanisElevenListBean.getPriceStr())) {
                holder.tv_xianjia.setText("原价:￥" + mechanisElevenListBean.getPriceStr());
            } else {
                holder.tv_xianjia.setText("现价:￥" + mechanisElevenListBean.getPriceStr());
                holder.tv_yuanjia.setVisibility(View.VISIBLE);
                holder.tv_yuanjia.setText("原价:￥" + mechanisElevenListBean.getOriginalPriceStr());
            }
        }

        holder.rl_open_vip_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    EventBus.getDefault().postSticky(new Reture_Price(mechanisMonthsAndPriceListBean.getId(),
                            1, mechanisMonthsAndPriceListBean.getMonth(),
                            mechanisMonthsAndPriceListBean.getPriceStr() + ""));//type=1时时企业vip
                } else {
                    EventBus.getDefault().postSticky(new Reture_Price(mechanisElevenListBean.getId(),
                            1, mechanisElevenListBean.getMonth(),
                            mechanisElevenListBean.getPriceStr() + ""));//type=1时时企业vip
                }

                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), true);
                notifyDataSetChanged();
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null || !states.get(String.valueOf(position))) {
            states.put(String.valueOf(position), false);
            res = false;
            holder.tv_deta.setTextColor(context.getResources().getColor(R.color.light_hei));
            holder.tv_xianjia.setTextColor(context.getResources().getColor(R.color.orgin));
        } else {
            res = true;
            holder.tv_deta.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_xianjia.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.rl_open_vip_parent.setSelected(res);
    }

    @Override
    public int getItemCount() {
        return openMemberResult.getMechanisElevenList().size();
    }
}

class My_Open_Prese_Member extends RecyclerView.ViewHolder {
    public RadioButton rad;
    public TextView textView;

    public RelativeLayout rl_open_vip_parent;
    public TextView tv_deta;
    public TextView tv_xianjia;
    public TextView tv_yuanjia;

    public My_Open_Prese_Member(View itemView) {
        super(itemView);
//        rad = itemView.findViewById(R.id.enter_rab);
        textView = itemView.findViewById(R.id.text);

        rl_open_vip_parent = itemView.findViewById(R.id.rl_open_vip_parent);
        tv_deta = itemView.findViewById(R.id.tv_deta);
        tv_xianjia = itemView.findViewById(R.id.tv_xianjia);
        tv_yuanjia = itemView.findViewById(R.id.tv_yuanjia);

    }
}
