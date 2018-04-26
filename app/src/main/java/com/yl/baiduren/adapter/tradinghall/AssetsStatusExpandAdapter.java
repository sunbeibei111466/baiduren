package com.yl.baiduren.adapter.tradinghall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.baiduren.R;
import com.yl.baiduren.base.ICallBack;
import com.yl.baiduren.entity.AsstesStatusProject;

import java.util.List;

/**
 * Created by Android_apple on 2018/1/4.
 */

public class AssetsStatusExpandAdapter extends BaseExpandableListAdapter {

    private List<AsstesStatusProject> asstesStatusProjectsList;
    private Context context;
    private boolean isVisibility;//true 时隐藏删除按钮

    public AssetsStatusExpandAdapter(Context context) {
        this.context = context;
    }

    public void setAsstesStatusProjectsList(List<AsstesStatusProject> asstesStatusProjectsList) {
        this.asstesStatusProjectsList = asstesStatusProjectsList;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public List<AsstesStatusProject> getAsstesStatusProjectsList() {
        return asstesStatusProjectsList;
    }

    private ICallBack iCallBack;

    public void setICallBack(ICallBack iCallBack){
        this.iCallBack = iCallBack;
    }

    @Override
    public int getGroupCount() {
        return asstesStatusProjectsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return asstesStatusProjectsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AsstesParentViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.asstes_status_item_parent_view, null);
            viewHolder = new AsstesParentViewHolder();
            viewHolder.asstes_name_str = (TextView) convertView.findViewById(R.id.asstes_name_str);
            viewHolder.iv_asster_delete_project = (ImageView) convertView.findViewById(R.id.iv_asster_delete_project);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AsstesParentViewHolder) convertView.getTag();
        }
        viewHolder.asstes_name_str.setText(asstesStatusProjectsList.get(groupPosition).getLndustryName());
        if(isVisibility){
            viewHolder.iv_asster_delete_project.setVisibility(View.GONE);
        }
        viewHolder.iv_asster_delete_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除项目:" + asstesStatusProjectsList.get(groupPosition).getLndustryName(), Toast.LENGTH_SHORT).show();
                asstesStatusProjectsList.remove(groupPosition);
                iCallBack.updateData();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        AsstesChildrenViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.asstes_status_item_children_view, null);
            viewHolder = new AsstesChildrenViewHolder();
            viewHolder.tv_asster_name = (TextView) convertView.findViewById(R.id.tv_asster_name);
            viewHolder.tv_asstes_number_str = (TextView) convertView.findViewById(R.id.tv_asstes_number_str);
            viewHolder.tv_asstes_vules_str = (TextView) convertView.findViewById(R.id.tv_asstes_vules_str);
            viewHolder.iv_delete_asstes = (ImageView) convertView.findViewById(R.id.iv_delete_asstes);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AsstesChildrenViewHolder) convertView.getTag();
        }
        viewHolder.tv_asster_name.setText(asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().get(childPosition).getAsstesType());
        viewHolder.tv_asstes_number_str.setText(asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().get(childPosition).getAsstesName());
        viewHolder.tv_asstes_vules_str.setText(asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().get(childPosition).getAsstesValue());
        if(isVisibility){
            viewHolder.iv_delete_asstes.setVisibility(View.GONE);
        }
        viewHolder.iv_delete_asstes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "删除产业:" + asstesStatusProjectsList.get(groupPosition).getLndustryName() + "中的：" +
                                asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().get(childPosition).getAsstesName() + "资产",
                        Toast.LENGTH_SHORT).show();
                asstesStatusProjectsList.get(groupPosition).getAsstesEntityList().remove(childPosition);
                iCallBack.updateData();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

class AsstesParentViewHolder {
    public TextView asstes_name_str;
    public ImageView iv_asster_delete_project;
}

class AsstesChildrenViewHolder {
    public TextView tv_asster_name;
    public TextView tv_asstes_number_str;
    public TextView tv_asstes_vules_str;
    public ImageView iv_delete_asstes;
}