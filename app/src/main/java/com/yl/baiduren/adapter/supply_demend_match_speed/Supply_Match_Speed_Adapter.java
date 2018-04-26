package com.yl.baiduren.adapter.supply_demend_match_speed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.supply_demand.Supply_Demend_Details;
import com.yl.baiduren.entity.result.My_Supply_Result;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sunbeibei on 2018/1/19.
 */

public class Supply_Match_Speed_Adapter extends RecyclerView.Adapter<Supply_Match_ViewHolder> {
    private Context context;
    private List<My_Supply_Result.DataListBean> dataList;
    private HashMap<String, Boolean> states;

    public Supply_Match_Speed_Adapter(Context context) {
        this.context = context;
        states = new HashMap<>();

    }

    public void setDataList(List<My_Supply_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }
    public List<My_Supply_Result.DataListBean>getList(){
        return dataList;
    }

    @Override
    public Supply_Match_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.supply_mathc_speed_item, parent, false);
        Supply_Match_ViewHolder viewHolder = new Supply_Match_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Supply_Match_ViewHolder holder, final int position) {
        final RadioButton radio = holder.radio_btn.findViewById(R.id.radio_btn);
        final My_Supply_Result.DataListBean dataListBean = dataList.get(position);
        holder.radio_btn = radio;
        holder.radio_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单数据传递到openmember中
                EventBus.getDefault().post(dataListBean.getId());

                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), radio.isChecked());
                notifyDataSetChanged();

            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null || !states.get(String.valueOf(position))) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
        }
        holder.radio_btn.setChecked(res);

        Glide.with(context).load(dataListBean.getCategoryImg()).into(holder.image_type);
        Glide.with(context).load(dataListBean.getUserImg())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.suppll_header);
        holder.supply_biaohao.setText("编号:" + dataListBean.getCode());
        holder.supply_num.setText("数量：" + dataListBean.getNum() + "  归属地: " + dataListBean.getAreaName());
        holder.supply_remork.setText(dataListBean.getName());
        holder.supply_price.setText("¥" + dataListBean.getValueStr());
        holder.supply_name.setText(dataListBean.getUserName());
        holder.query_detials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,
                        Supply_Demend_Details.class).
                        putExtra("id", dataListBean.getId())
                        .putExtra("mYSupplyCollection", true));
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class Supply_Match_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView image_type, suppll_header;
    public TextView supply_biaohao, supply_remork, supply_num, supply_price, supply_name;
    public Button query_detials;
    public RadioButton radio_btn;

    public Supply_Match_ViewHolder(View itemView) {
        super(itemView);
        image_type = itemView.findViewById(R.id.match_image_type);//供应端类型图片
        supply_biaohao = itemView.findViewById(R.id.match_supply_biaohao);//编号
        query_detials = itemView.findViewById(R.id.match_query_detials);//查看详情
        supply_remork = itemView.findViewById(R.id.match_supply_remork);//备注
        supply_num = itemView.findViewById(R.id.match_supply_num);//数量，归属地
        supply_price = itemView.findViewById(R.id.match_supply_price);//价值
        suppll_header = itemView.findViewById(R.id.match_suppll_header);//头像
        supply_name = itemView.findViewById(R.id.match_supply_name);//姓名
        radio_btn = itemView.findViewById(R.id.radio_btn);//匹配选择


    }
}
