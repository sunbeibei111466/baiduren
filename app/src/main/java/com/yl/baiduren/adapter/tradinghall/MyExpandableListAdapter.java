package com.yl.baiduren.adapter.tradinghall;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.Investment_Mode;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/8.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    private List<Investment_Mode> investmentModes;

    public MyExpandableListAdapter(Context context) {
        this.context = context;
    }

    public void setInvestmentModes(List<Investment_Mode> investmentModes) {
        this.investmentModes = investmentModes;
    }

    //获取组视图总数
    @Override
    public int getGroupCount() {
        return investmentModes.size();
    }
    //获得子视图的总数
    @Override
    public int getChildrenCount(int groupPosition) {
        return investmentModes.get(groupPosition).getChlieList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return investmentModes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return investmentModes.get(groupPosition).getChlieList().get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.investment_situation_adapte_item,null);
        TextView et_investment_adapter_name=view.findViewById(R.id.et_investment_adapter_name);
        ImageView iv_investment_delete=view.findViewById(R.id.iv_investment_delete);
        et_investment_adapter_name.setText(investmentModes.get(groupPosition).getProjectName());
        LUtils.e("--getGroupView--"+investmentModes.get(groupPosition).getProjectName());
        iv_investment_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                investmentModes.remove(groupPosition);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view2 = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout,null);
        ImageView iv_recycler_adapter_delete=view2.findViewById(R.id.iv_recycler_adapter_delete);
        TextView et_recycler_adapter_name=view2.findViewById(R.id.et_recycler_adapter_name);
        TextView et_recycler_adapter_jine=view2.findViewById(R.id.et_recycler_adapter_jine);
        TextView et_recycler_adapter_scale=view2.findViewById(R.id.et_recycler_adapter_scale);
        et_recycler_adapter_name.setText(investmentModes.get(groupPosition).getChlieList().get(childPosition).getGudName());
        et_recycler_adapter_jine.setText(investmentModes.get(groupPosition).getChlieList().get(childPosition).getJine());
        et_recycler_adapter_scale.setText(investmentModes.get(groupPosition).getChlieList().get(childPosition).getZhanbi());

        LUtils.e("--getChildView--"+investmentModes.get(groupPosition).getChlieList().get(childPosition).getGudName());
        LUtils.e("--getChildView--"+investmentModes.get(groupPosition).getChlieList().get(childPosition).getJine());
        LUtils.e("--getChildView--"+investmentModes.get(groupPosition).getChlieList().get(childPosition).getZhanbi());
        iv_recycler_adapter_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                investmentModes.get(groupPosition).getChlieList().remove(childPosition);
            }
        });
        return view2;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
