package com.yl.baiduren.adapter.tradinghall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.Investment_Mode;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/7.
 */

public class Investment_situation_adapter2_Adapter extends RecyclerView.Adapter<MyViewHolder2> {

    private Context context;
    private List<Investment_Mode.Chlie> chlieList;

    public Investment_situation_adapter2_Adapter(Context activity) {
        context = activity;

    }


    public void setChlieList(List<Investment_Mode.Chlie> chlieList) {
        this.chlieList = chlieList;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout,parent ,false);
        MyViewHolder2 myViewHolder = new MyViewHolder2(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder2 holder, final int position) {

        LUtils.e("-----chlieList.size()-----"+chlieList.size());

        LUtils.e("---name---"+chlieList.get(position).getGudName());
        LUtils.e("---jine---"+chlieList.get(position).getJine());
        LUtils.e("---zhanbi---"+chlieList.get(position).getZhanbi());
        holder.et_recycler_adapter_name.setText(chlieList.get(position).getGudName());
        holder.et_recycler_adapter_jine.setText(chlieList.get(position).getJine());
        holder.et_recycler_adapter_scale.setText(chlieList.get(position).getZhanbi());
        holder.iv_recycler_adapter_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chlieList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return chlieList.size();
    }
}

class MyViewHolder2 extends RecyclerView.ViewHolder{

    TextView et_recycler_adapter_name,et_recycler_adapter_jine,et_recycler_adapter_scale;
    ImageView iv_recycler_adapter_delete;

    public MyViewHolder2(View itemView) {
        super(itemView);
        iv_recycler_adapter_delete=itemView.findViewById(R.id.iv_recycler_adapter_delete);
        et_recycler_adapter_name=itemView.findViewById(R.id.et_recycler_adapter_name);
        et_recycler_adapter_jine=itemView.findViewById(R.id.et_recycler_adapter_jine);
        et_recycler_adapter_scale=itemView.findViewById(R.id.et_recycler_adapter_scale);
    }
}