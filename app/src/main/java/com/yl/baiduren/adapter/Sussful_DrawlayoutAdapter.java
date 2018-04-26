package com.yl.baiduren.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Sussful_Exmple;
import com.yl.baiduren.entity.result.Debt_Type_Result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/4.
 */

public class Sussful_DrawlayoutAdapter extends BaseAdapter {
    private Sussful_Exmple debtBunessHall;
    private List<Debt_Type_Result.CategoryDO> list;
    private int index;


    public Sussful_DrawlayoutAdapter(Sussful_Exmple context ) {
        this.debtBunessHall = context;

    }

    public void setList(List<Debt_Type_Result.CategoryDO> list) {
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
