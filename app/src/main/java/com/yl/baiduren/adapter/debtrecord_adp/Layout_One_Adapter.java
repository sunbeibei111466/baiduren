package com.yl.baiduren.adapter.debtrecord_adp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/6.
 */

public class Layout_One_Adapter extends RecyclerView.Adapter<MyvH> {

    private Context mContext;
    private List<String> stringList;

    public Layout_One_Adapter(Context context) {
        mContext = context;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public MyvH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.debt4_layout1_adapter1_item, null);
        MyvH myvH = new MyvH(view);
        return myvH;
    }

    @Override
    public void onBindViewHolder(MyvH holder, int position) {
        holder.textView.setText(stringList.get(position));
        LUtils.e("---顶顶顶---"+stringList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}

class MyvH extends RecyclerView.ViewHolder {

    TextView textView;

    public MyvH(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv_layout1_adapter1_jine);
    }
}