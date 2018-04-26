package com.yl.baiduren.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liji.imagezoom.util.ImageZoom;
import com.yl.baiduren.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sunbeibei on 2017/12/26.
 */

public class ImageShowAdapter extends RecyclerView.Adapter<My_Image_Adapter> {
    private Context context;
    private List<String> imageList;

    public ImageShowAdapter(Context context ) {
        this.context = context;
        imageList=new ArrayList<>();
    }

    public void setImageArry(String[] imagearry) {
        this.imageList.addAll(new ArrayList<>(Arrays.asList(imagearry)));
    }

    public List<String> getImageList() {
        return imageList;
    }

    @Override
    public My_Image_Adapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageshow_item,parent,false);
        My_Image_Adapter adapter =new My_Image_Adapter(view);
        return adapter;
    }

    @Override
    public void onBindViewHolder(final My_Image_Adapter holder, final int position) {
        Glide.with(context).load(imageList.get(position)).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageZoom.show(context, imageList.get(position),imageList );
            }
        });

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
class My_Image_Adapter extends RecyclerView.ViewHolder{
public    ImageView image;
    public My_Image_Adapter(View itemView) {
        super(itemView);
     image=itemView.findViewById(R.id.imageshow);
    }
}