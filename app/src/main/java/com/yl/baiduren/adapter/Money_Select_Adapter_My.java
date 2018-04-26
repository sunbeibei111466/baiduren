package com.yl.baiduren.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Sussful_Exmple;

import java.util.ArrayList;

/**
 * 我的备案
 */

public class Money_Select_Adapter_My extends RecyclerView.Adapter<My_Money_SelectMy> {
    private  Sussful_Exmple context;
    private ArrayList<String>list;


    public Money_Select_Adapter_My(Sussful_Exmple context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public My_Money_SelectMy onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.money_select_item,parent,false);
        My_Money_SelectMy my_money_select = new My_Money_SelectMy(view);
        return my_money_select;
    }

    @Override
    public void onBindViewHolder(My_Money_SelectMy holder, final int position) {
        holder.money.setText(list.get(position));
        holder.money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class My_Money_SelectMy extends RecyclerView.ViewHolder{

    public TextView money;

    public My_Money_SelectMy(View itemView) {
        super(itemView);
        money = itemView.findViewById(R.id.money);
    }
}