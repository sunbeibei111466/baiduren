package com.yl.baiduren.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Buness_Hall;
import com.yl.baiduren.entity.result.Debt_Type_Result;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/26.
 */

public class Buness_DrawlayoutAdapter extends BaseAdapter {

    private Debt_Buness_Hall debtBunessHall;
    private List<Debt_Type_Result.CategoryDO>  list;
    private int index;


    public Buness_DrawlayoutAdapter(Debt_Buness_Hall context ) {
        this.debtBunessHall = context;

    }

    public void setList(List<Debt_Type_Result.CategoryDO>  list) {
        this.list = list;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(debtBunessHall).inflate(R.layout.drawlayout_buness, null);
        TextView textView = view.findViewById(R.id.tv_type);
        textView.setText(list.get(position).getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debtBunessHall.onClickItem(String.valueOf(list.get(position).getId()),list.get(position).getName(), index);
            }
        });
        return textView;
    }
}
