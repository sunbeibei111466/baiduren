package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.greenentity.Sussful_Exple_Query;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/5.
 */

public class Sufful_Exple_Histery_Serch_Adapter extends RecyclerView.Adapter<My_Histery_ViewHolder>{
    private List<Sussful_Exple_Query>list;
    private Context context;
    public Item_Click click;
    public Sufful_Exple_Histery_Serch_Adapter(Context context) {
        this.context = context;
    }
    public void setList(List<Sussful_Exple_Query>list){
        this.list=list;
    }
    public List<Sussful_Exple_Query>getList(){
        return list;
    }
    public interface Item_Click{
        void setItemString(String text);
    }
    public void getItemString(Item_Click click){
        this.click=click;
    }



    @Override
    public My_Histery_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_text,null);
        My_Histery_ViewHolder viewHolder =new My_Histery_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Histery_ViewHolder holder, int position) {
        final String querysufull = list.get(position).getQuerysufull();
        holder.tv_context.setText(querysufull);
        holder.tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               click.setItemString(querysufull);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class My_Histery_ViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_context;
    public My_Histery_ViewHolder(View itemView) {
        super(itemView);
        tv_context=itemView.findViewById(R.id.tv_context);
    }
}
