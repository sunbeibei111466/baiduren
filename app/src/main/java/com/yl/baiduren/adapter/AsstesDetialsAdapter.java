package com.yl.baiduren.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yl.baiduren.R;

/**
 * Created by Android_apple on 2018/1/23.
 */

public class AsstesDetialsAdapter extends BaseAdapter {


    private Context context;

    public AsstesDetialsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolde viewHolde;
        if(convertView==null){
            convertView =LayoutInflater.from(context).inflate(R.layout.asstes_detials_item,null);
            viewHolde=new ViewHolde();
            viewHolde.imageView=convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolde);
        }else{
            viewHolde= (ViewHolde) convertView.getTag();
        }
        viewHolde.imageView.setImageResource(R.mipmap.logo);
        return convertView;
    }
}
class ViewHolde{
    public ImageView imageView;
}