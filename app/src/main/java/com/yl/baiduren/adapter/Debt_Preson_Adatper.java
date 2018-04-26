package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtmanagpreson.Debt_EnterPrise_Details;
import com.yl.baiduren.activity.debtmanagpreson.Debt_Preson_Details;
import com.yl.baiduren.entity.result.DebtPersonList;
import com.yl.baiduren.utils.LUtils;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/6.
 * 债事自然人列表适配器
 */

public class Debt_Preson_Adatper extends RecyclerView.Adapter<Debt_Preson_Holder> {
    private Context context;
    //    private ArrayList<Debt_Preson>list;
    private List<DebtPersonList.Mode> list;


    public Debt_Preson_Adatper(Long childUserId,Context context) {
        this.context = context;
        Long childUserId1 = childUserId;
    }
    public void setDataList(List<DebtPersonList.Mode> list){
        this.list=list;
    }

    public List<DebtPersonList.Mode>getList(){
        return list;
    }

    public void setThreePage(String threePage) {
        String threePage1 = threePage;
    }


    @Override
    public Debt_Preson_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_preson_item, parent, false);
        Debt_Preson_Holder holder = new Debt_Preson_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Debt_Preson_Holder holder, final int position) {
        holder.num.setText(position+1 + "");
        holder.name.setText(list.get(position).getName());
        holder.hangye.setText(list.get(position).getPhoneNumber());
        holder.weizhi.setText(list.get(position).getAreaName());

        //点击去详情
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LUtils.e("---list.get(position).getId()---",list.get(position).getId()+"");
                if(list.get(position).getType()==1){//企业
                    context.startActivity(new Intent(context, Debt_EnterPrise_Details.class).
                            putExtra("debtEnterPriseId", list.get(position).getId())
                            .putExtra("describe",list.get(position).getDescribe())
                    );
                }else{ //个人
                    context.startActivity(new Intent(context, Debt_Preson_Details.class).
                            putExtra("debtPersonId", list.get(position).getId()).
                            putExtra("describe",list.get(position).getDescribe())
                    );
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Debt_Preson_Holder extends RecyclerView.ViewHolder {

    public TextView num;//序号
    public TextView name;//债事人姓名
    public TextView weizhi;//债事人位置
    public TextView hangye;//行业

    public Debt_Preson_Holder(View itemView) {
        super(itemView);
        num = itemView.findViewById(R.id.xuhao);
        name = itemView.findViewById(R.id.debt_name);
        weizhi = itemView.findViewById(R.id.debt_area);
        hangye = itemView.findViewById(R.id.debt_type);
    }
}
