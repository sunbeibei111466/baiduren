package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.greenentity.Hall_Querry;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/29.
 */

public class HallQuerryTextAdapter extends RecyclerView.Adapter<MyViewHolderSearch> {

    private Context context;
    private List<Hall_Querry> hallQuerryList;
    public IClickItem clickItem;
    public interface IClickItem{
        void onClickItem(String text);
    }

    public void setClickItem(IClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public HallQuerryTextAdapter(Context context) {
        this.context = context;
    }

    public void setHallQuerryList(List<Hall_Querry> hallQuerryList) {
        this.hallQuerryList = hallQuerryList;
    }

    public List<Hall_Querry> getHallQuerryList() {
        return hallQuerryList;
    }

    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.search_text,parent,false);
        MyViewHolderSearch myViewHolderSearch=new MyViewHolderSearch(view);
        return myViewHolderSearch;
    }

    @Override
    public void onBindViewHolder(MyViewHolderSearch holder, final int position) {
        holder.tv_context.setText(hallQuerryList.get(position).getQuerryText());
        holder.tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.onClickItem(hallQuerryList.get(position).getQuerryText());
            }
        });

    }

    @Override
    public int getItemCount() {
        return hallQuerryList.size();
    }
}
