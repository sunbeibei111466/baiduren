package com.yl.baiduren.adapter.tradinghall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.request_body.AssignmentEntity;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/7.
 */

public class Share_structure_Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<AssignmentEntity.SharesDO> sharesDOList;
    private boolean isVisibility;//true 时隐藏删除按钮

    public Share_structure_Adapter(Context activity) {
        context = activity;
    }

    public boolean isVisibility() {
        return isVisibility;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public void setStringList(List<AssignmentEntity.SharesDO> stringList) {
        this.sharesDOList = stringList;
    }

    public List<AssignmentEntity.SharesDO> getSharesDOList() {
        return sharesDOList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.share_structure_adapte_item,parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(sharesDOList.get(position).getShareholders());
        holder.scale.setText(sharesDOList.get(position).getProportion());
        if(isVisibility){
            holder.iv_share_delete.setVisibility(View.GONE);
        }
        holder.iv_share_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharesDOList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sharesDOList.size() ;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name, scale;
    public ImageView iv_share_delete;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.et_share_adapter_name);
        scale = itemView.findViewById(R.id.et_share_adapter_scale);
        iv_share_delete = itemView.findViewById(R.id.iv_share_delete);
    }
}