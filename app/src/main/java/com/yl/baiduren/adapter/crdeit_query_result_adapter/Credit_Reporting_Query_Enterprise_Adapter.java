package com.yl.baiduren.adapter.crdeit_query_result_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yl.baiduren.R;
import java.util.List;

/**
 * Created by sunbeibei on 2018/2/5.
 */

public class Credit_Reporting_Query_Enterprise_Adapter extends RecyclerView.Adapter<Credit_Query_ViewHolder> {
    private Context context;
    public IonClickItem ionClickItem;
   private List<String> entNameList;
    public interface IonClickItem {
        void onClickItem(String text);
    }

    public void setIonClickItem(IonClickItem ionClickItem) {
        this.ionClickItem = ionClickItem;
    }


    public Credit_Reporting_Query_Enterprise_Adapter(Context context) {
        this.context = context;
    }
    public void setEntNameList(List<String> entNameList){
        this.entNameList=entNameList;
    }

    @Override
    public Credit_Query_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.credit_enterpaise_adapter_item, parent, false);
        Credit_Query_ViewHolder viewHolder = new Credit_Query_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Credit_Query_ViewHolder holder, final int position) {
        holder.tv_enterpaise_name.setText(entNameList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ionClickItem.onClickItem(entNameList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return entNameList.size();
    }
}

class Credit_Query_ViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_enterpaise_name;

    public Credit_Query_ViewHolder(View itemView) {
        super(itemView);
        tv_enterpaise_name = itemView.findViewById(R.id.tv_enterpaise_name);//企业名称

    }
}
