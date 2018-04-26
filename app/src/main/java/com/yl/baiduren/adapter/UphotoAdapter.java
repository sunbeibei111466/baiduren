package com.yl.baiduren.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/4.
 */

public class UphotoAdapter extends BaseAdapter {
    private List<String> path ;
    private Context context;
    private int maxImages = 9;
    public int count;
    private boolean isVisibility;//true 时隐藏删除按钮

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public UphotoAdapter(Context context) {
        this.context = context;
    }

    public void  setPath(List<String> path){
        this.path=path;
    }

    public List<String> getPath() {
        return path;
    }

    @Override
    public int getCount() {
        count = path==null ? 1 : path.size() + 1;
        if (count >= maxImages) {
            LUtils.e("---path.size()----1--",count+"");
            return path.size();
        } else {
            LUtils.e("----path.size()==0---getCount--",count+"");
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return path.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.photo_item,null,false);
            vh=new ViewHolder();
            vh.imageView = convertView.findViewById(R.id.image);
            vh.deleteImage = convertView.findViewById(R.id.deleteimage);
            convertView.setTag(vh);

        }else {
            vh= (ViewHolder) convertView.getTag();
        }

        if(path!=null&&position<path.size()){
            vh.imageView.setVisibility(View.VISIBLE);
            vh.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if(isVisibility){
                vh.deleteImage.setVisibility(View.GONE);
            }
            vh.deleteImage.setImageResource(R.mipmap.deletephoto);
            Glide.with(context).load(path.get(position)).into(vh.imageView);
            vh.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    path.remove(position);
                    notifyDataSetChanged();
                }
            });
        }else {
            Glide.with(context).load(R.mipmap.addphoto).into(vh.imageView);
            vh.deleteImage.setVisibility(View.GONE);
        }

        return convertView;
    }
    public class ViewHolder {
        ImageView imageView, deleteImage;

    }












}




