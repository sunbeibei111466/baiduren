package com.yl.baiduren.adapter.tradinghall;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.Investment_Mode;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android_apple on 2017/12/7.
 */

public class Investment_situation_Adapter extends RecyclerView.Adapter<MyViewHolderIn> {

    public Context context;
    private List<Investment_Mode> investmentModes;
    private Investment_situation_adapter2_Adapter situationAdapter2Adapter;

    public Investment_situation_Adapter(Context activity) {
        context = activity;
        situationAdapter2Adapter=new Investment_situation_adapter2_Adapter(context);
        List<Investment_Mode.Chlie> lists = new ArrayList<>();
    }

    public void setInvestmentModes(List<Investment_Mode> investmentModes) {
        this.investmentModes = investmentModes;


    }

    @Override
    public MyViewHolderIn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.investment_situation_adapte_item,parent ,false);
        MyViewHolderIn myViewHolder = new MyViewHolderIn(view,context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderIn holder, final int position) {
        //设置项目名
        holder.et_investment_adapter_name.setText(investmentModes.get(position).getProjectName());
        //创建子试图Adapter
//
//        if(position==0){
            situationAdapter2Adapter.setChlieList(investmentModes.get(position).getChlieList());
            situationAdapter2Adapter.notifyDataSetChanged();
            holder.item_my_rv.setAdapter(situationAdapter2Adapter);
//        }


        holder.iv_investment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                investmentModes.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        if(investmentModes==null){
            return 0;
        }else {
            return investmentModes.size();
        }
    }
}

class MyViewHolderIn extends RecyclerView.ViewHolder{

    public ImageView iv_investment_delete;//删除项目
    public MyRecyclerView item_my_rv;
    public TextView  et_investment_adapter_name;

    public MyViewHolderIn(View itemView, Context context) {
        super(itemView);

        et_investment_adapter_name=itemView.findViewById(R.id.et_investment_adapter_name);
        iv_investment_delete=itemView.findViewById(R.id.iv_investment_delete);
        item_my_rv=itemView.findViewById(R.id.item_my_rv);
        item_my_rv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        item_my_rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }
}