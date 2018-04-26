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
import com.yl.baiduren.entity.InvestmentProjectBean;

import java.util.List;

/**
 *  投资项目  适配器
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<InvestmentProjectBean> investmentProjectBeen;
    private ICallBack iCallBack;
    private boolean isVisibility;//true 时隐藏删除按钮

    public void setICallBack(ICallBack iCallBack){
        this.iCallBack = iCallBack;
    }


    public ExpandableAdapter(Context context){
        this.context = context.getApplicationContext();
    }

    public void setInvestmentProjectBeen(List<InvestmentProjectBean> investmentProjectBeen) {
        this.investmentProjectBeen = investmentProjectBeen;
    }

    public void setVisibility(boolean visibility) {
        isVisibility = visibility;
    }

    public List<InvestmentProjectBean> getInvestmentProjectBeen() {
        return investmentProjectBeen;
    }

    @Override
    public int getGroupCount() {
        return investmentProjectBeen.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return investmentProjectBeen.get(groupPosition).getShareholderBeen().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return investmentProjectBeen.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return investmentProjectBeen.get(groupPosition).getShareholderBeen().get(childPosition);
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



    /**
     *  获取  项目的view
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_parent_view,null);
            viewHolder = new ParentViewHolder();
            viewHolder.project_name_str = (TextView) convertView.findViewById(R.id.project_name_str);
            viewHolder.iv_delete_project = (ImageView) convertView.findViewById(R.id.iv_delete_project);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ParentViewHolder) convertView.getTag();
        }
        viewHolder.project_name_str.setText(investmentProjectBeen.get(groupPosition).getInvestmentProject());
        if(isVisibility){
            viewHolder.iv_delete_project.setVisibility(View.GONE);
        }
        viewHolder.iv_delete_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"删除项目:"+investmentProjectBeen.get(groupPosition).getInvestmentProject(),Toast.LENGTH_SHORT).show();
                investmentProjectBeen.remove(groupPosition);
                iCallBack.updateData();
            }
        });
        return convertView;
    }

    /**
     *   获取股东的   view
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildrenViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_children_view,null);
            viewHolder = new ChildrenViewHolder();
            viewHolder.investment_number_str = (TextView) convertView.findViewById(R.id.investment_number_str);
            viewHolder.iv_delete_shareholder = (ImageView) convertView.findViewById(R.id.iv_delete_shareholder);
            viewHolder.share_ratio_str = (TextView) convertView.findViewById(R.id.share_ratio_str);
            viewHolder.shareholder_name_str = (TextView) convertView.findViewById(R.id.shareholder_name_str);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ChildrenViewHolder) convertView.getTag();
        }
        viewHolder.shareholder_name_str.setText(investmentProjectBeen.get(groupPosition).getShareholderBeen().get(childPosition).getShareholderName());
        viewHolder.investment_number_str.setText(investmentProjectBeen.get(groupPosition).getShareholderBeen().get(childPosition).getInvestmentNumber());
        viewHolder.share_ratio_str.setText(investmentProjectBeen.get(groupPosition).getShareholderBeen().get(childPosition).getShareRatio());
        if(isVisibility){
            viewHolder.iv_delete_shareholder.setVisibility(View.GONE);
        }
        viewHolder.iv_delete_shareholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "删除项目:"+investmentProjectBeen.get(groupPosition).getInvestmentProject()+"中的：" +
                                investmentProjectBeen.get(groupPosition).getShareholderBeen().get(childPosition).getShareholderName()+"股东",
                        Toast.LENGTH_SHORT).show();
                investmentProjectBeen.get(groupPosition).getShareholderBeen().remove(childPosition);
               iCallBack.updateData();
            }
        });
        return convertView;
    }

    //股东  不可点击
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
