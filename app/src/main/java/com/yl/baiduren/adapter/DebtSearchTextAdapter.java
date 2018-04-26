package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.greenentity.DebtPerson;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/29.
 */

public class DebtSearchTextAdapter extends RecyclerView.Adapter<MyViewHolderSearch> {

    private Context context;
    private List<DebtPerson> debtPersonList;
    public IClickItem clickItem;
    public interface IClickItem{
        void onClickItem(String text);
    }

    public void setClickItem(IClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public DebtSearchTextAdapter(Context context) {
        this.context = context;
    }

    public void setDebtPersonList(List<DebtPerson> debtPersonList) {
        this.debtPersonList = debtPersonList;
    }

    public List<DebtPerson> getDebtPersonList() {
        return debtPersonList;
    }

    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.search_text,null);
        MyViewHolderSearch myViewHolderSearch=new MyViewHolderSearch(view);
        return myViewHolderSearch;
    }

    @Override
    public void onBindViewHolder(MyViewHolderSearch holder, final int position) {
        holder.tv_context.setText(debtPersonList.get(position).getText());
        holder.tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.onClickItem(debtPersonList.get(position).getText());
            }
        });

    }

    @Override
    public int getItemCount() {
        return debtPersonList.size();
    }
}
class MyViewHolderSearch extends RecyclerView.ViewHolder{

    public TextView tv_context;

    public MyViewHolderSearch(View itemView) {
        super(itemView);
        tv_context=itemView.findViewById(R.id.tv_context);
    }
}