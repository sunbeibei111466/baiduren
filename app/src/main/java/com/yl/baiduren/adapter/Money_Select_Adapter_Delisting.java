package com.yl.baiduren.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.mypager.My_Delisting;

import java.util.ArrayList;

/**
 * Created by sunbeibei on 2017/12/11.
 */

public class Money_Select_Adapter_Delisting extends RecyclerView.Adapter<My_Money_Delisting> {
    private My_Delisting context;
    private ArrayList<String>list;


    public Money_Select_Adapter_Delisting(My_Delisting context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public My_Money_Delisting onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.money_select_item,parent,false);
        My_Money_Delisting my_money_select = new My_Money_Delisting(view);
        return my_money_select;
    }

    @Override
    public void onBindViewHolder(My_Money_Delisting holder, final int position) {
        holder.money.setText(list.get(position));
        holder.money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.onClickItem(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class My_Money_Delisting extends RecyclerView.ViewHolder{

    public TextView money;

    public My_Money_Delisting(View itemView) {
        super(itemView);
        money = itemView.findViewById(R.id.money);
    }
}