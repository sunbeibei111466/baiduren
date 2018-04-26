package com.yl.baiduren.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yl.baiduren.R;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/26.
 */

public class CommissionAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;


    public CommissionAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.drawlayout_buness,null);
        TextView textView=view.findViewById(R.id.tv_type);
        textView.setText(list.get(position).toString());
        return textView;
    }
}
