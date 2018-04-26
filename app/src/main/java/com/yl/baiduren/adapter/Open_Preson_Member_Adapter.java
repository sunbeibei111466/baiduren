package com.yl.baiduren.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.Reture_Price;
import com.yl.baiduren.entity.result.Open_Member_Result;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class Open_Preson_Member_Adapter extends RecyclerView.Adapter<My_Open_Member> {
    private Activity context;
    private HashMap<String, Boolean> states;
    private List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList;

    public Open_Preson_Member_Adapter(Activity context, List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList) {
        this.context = context;
        this.personMonthsAndPriceList = personMonthsAndPriceList;
        states = new HashMap<>();
        // 用于记录每个RadioButton的状态，并保证只可选一个

    }

    @Override
    public My_Open_Member onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.open_preson_member_item, parent, false);
        My_Open_Member viewholder = new My_Open_Member(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final My_Open_Member holder, final int position) {
        final Open_Member_Result.PersonVipListBean personMonthsAndPriceListBean = personMonthsAndPriceList.get(position);
        holder.tv_deta.setText(personMonthsAndPriceListBean.getMonth()+"个月");
        if(personMonthsAndPriceListBean.getPriceStr().equals(personMonthsAndPriceListBean.getOriginalPriceStr())){
            holder.tv_xianjia.setText("现价:￥"+personMonthsAndPriceListBean.getPriceStr());
        }else {
            holder.tv_yuanjia.setVisibility(View.VISIBLE);
            holder.tv_yuanjia.setText("原价:￥"+personMonthsAndPriceListBean.getOriginalPriceStr());
            holder.tv_yuanjia.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            holder.tv_xianjia.setText("现价:￥"+personMonthsAndPriceListBean.getPriceStr());
        }
        holder.rl_open_vip_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单数据传递到openmember中
                EventBus.getDefault().post(new Reture_Price(personMonthsAndPriceListBean.getId(), 2, personMonthsAndPriceListBean.getMonth(), personMonthsAndPriceListBean.getPriceStr() + ""));//type=2时个人vip
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
            res = false;
            states.put(String.valueOf(position), false);
            holder.tv_deta.setTextColor(context.getResources().getColor(R.color.light_hei));
            holder.tv_xianjia.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            res = true;
            holder.tv_deta.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_xianjia.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.rl_open_vip_parent.setSelected(res);

    }

    @Override
    public int getItemCount() {
        return personMonthsAndPriceList.size();
    }
}

class My_Open_Member extends RecyclerView.ViewHolder {

    public RelativeLayout rl_open_vip_parent;
    public TextView tv_deta;
    public TextView tv_xianjia;
    public TextView tv_yuanjia;

    public My_Open_Member(View itemView) {
        super(itemView);
        rl_open_vip_parent = itemView.findViewById(R.id.rl_open_vip_parent);
        tv_deta=itemView.findViewById(R.id.tv_deta);
        tv_xianjia=itemView.findViewById(R.id.tv_xianjia);
        tv_yuanjia=itemView.findViewById(R.id.tv_yuanjia);
    }
}
