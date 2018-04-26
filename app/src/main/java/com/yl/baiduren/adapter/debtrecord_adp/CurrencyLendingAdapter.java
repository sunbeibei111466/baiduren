package com.yl.baiduren.adapter.debtrecord_adp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.greenentity.PayRecord;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/14.
 */

public class CurrencyLendingAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<PayRecord> recordList;

    public CurrencyLendingAdapter(Context context) {
        this.context = context;
    }

    public void setRecordList(List<PayRecord> recordList) {
        this.recordList = recordList;
    }

    public List<PayRecord> getRecordList() {
        return recordList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_lending_adapter_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.riqi.setText(Util.LongParseString(recordList.get(position).getPayTime()));
        holder.jine.setText(recordList.get(position).getPayMoneyStr());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除子列表数据
                recordList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView riqi, jine, delete;

    public MyViewHolder(View itemView) {
        super(itemView);
        riqi = itemView.findViewById(R.id.tv_currency_adapter_riqi);
        jine = itemView.findViewById(R.id.tv_currency_adapter_jine);
        delete = itemView.findViewById(R.id.tv_currency_delete);
    }
}
