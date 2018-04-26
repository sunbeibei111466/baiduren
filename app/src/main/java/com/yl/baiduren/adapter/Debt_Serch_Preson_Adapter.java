package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.baiduren.R;

import java.util.ArrayList;

/**
 * Created by sunbeibei on 2017/12/7.
 */

public class Debt_Serch_Preson_Adapter extends RecyclerView.Adapter<Debt_Serch_ViewHolder> {
    private Context context;

    public Debt_Serch_Preson_Adapter(ArrayList<String> list, Context context) {
        ArrayList<String> list1 = list;
        this.context = context;
    }

    @Override
    public Debt_Serch_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(Debt_Serch_ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class Debt_Serch_ViewHolder extends RecyclerView.ViewHolder{
    public Debt_Serch_ViewHolder(View itemView) {
        super(itemView);
    }
}
