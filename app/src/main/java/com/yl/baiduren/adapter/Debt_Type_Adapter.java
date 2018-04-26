package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.result.Debt_Type_Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbeibei on 2018/3/6.
 */

public class Debt_Type_Adapter extends RecyclerView.Adapter<My_Serch_Viewholder>{
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    private Context context;
    private List<Debt_Type_Result.TypesDO> list;
    public Debt_Type_Adapter(Context context) {

        this.context = context;
    }
    public void setList(List<Debt_Type_Result.TypesDO> list) {
        this.list = list;
    }

    public List<Debt_Type_Result.TypesDO> getList() {
        return list;
    }

    @Override
    public My_Serch_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serch_item,parent,false);
        My_Serch_Viewholder viewholder =new My_Serch_Viewholder(view);
        return viewholder;
    }
    //设置给定位置条目的选择状态
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    //根据位置判断条目是否可选
    private boolean isSelectable() {
        return mIsSelectable;
    }
    //设置给定位置条目的可选与否的状态
    private void setSelectable(boolean selectable) {
        mIsSelectable = selectable;
    }
    //获得选中条目的结果
    public List<Long> getSelectedItem() {
        List<Long>selectList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(list.get(i).getId());
            }
        }
        return selectList;
    }

    @Override
    public void onBindViewHolder(final My_Serch_Viewholder holder, final int position) {
        Debt_Type_Result.TypesDO typesDO = list.get(position);
        holder.checkBox.setText(typesDO.getName());
        holder.checkBox.setChecked(isItemChecked(position));

        //checkBox的监听
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                    holder.checkBox.setChecked(false);
                } else {
                    setItemChecked(position, true);
                    holder.checkBox.setChecked(true);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}

