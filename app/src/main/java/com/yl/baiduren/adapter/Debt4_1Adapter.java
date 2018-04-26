package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greendao.PayRecordDao;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.CurrencyLending;
import com.yl.baiduren.entity.request_body.MoneyLoan;
import com.yl.baiduren.utils.GreenDaoUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4_1Adapter extends RecyclerView.Adapter<MyViewH1> {

    private Context context;
    private List<MoneyLoan> moneyLoanList;

    public Debt4_1Adapter(Context context) {
        this.context = context;
    }

    public void setMoneyLoanList(List<MoneyLoan> moneyLoanList) {
        this.moneyLoanList = moneyLoanList;
    }

    @Override
    public MyViewH1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.debt_details_adapter_proof_one, parent, false);
        MyViewH1 myViewH = new MyViewH1(view);
        return myViewH;
    }

    @Override
    public void onBindViewHolder(MyViewH1 holder, final int position) {

        holder.tv_adapter_item1_money.setText(moneyLoanList.get(position).getType());
        holder.tv_adapter_item1_number.setText(moneyLoanList.get(position).getPrincipalStr());
        holder.tv_adapter_item1_whje.setText(moneyLoanList.get(position).getBalanceStr());
        holder.iv_debt_details_item1_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GreenDaoUtils.getInstance(context).getMoneyLoanDODao().deleteByKey(moneyLoanList.get(position).getId());//删除主表
                //删除子表
                PayRecordDao payRecordDao=GreenDaoUtils.getInstance(context).getPayRecordDao();
                Query query =payRecordDao.queryBuilder().where(PayRecordDao.Properties.PId.eq(moneyLoanList.get(position).getId())).build();
                query.list().clear();
                moneyLoanList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.ll_parent_one.setOnClickListener(new View.OnClickListener() {//编辑
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CurrencyLending.class).
                        putExtra("updataId", moneyLoanList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return moneyLoanList.size();
    }
}

class MyViewH1 extends RecyclerView.ViewHolder {

    public TextView tv_adapter_item1_money,tv_adapter_item1_number,tv_adapter_item1_whje;
    public ImageView iv_item1_image,iv_debt_details_item1_delete;
    public LinearLayout ll_parent_one;

    public MyViewH1(View itemView) {
        super(itemView);
        tv_adapter_item1_money=itemView.findViewById(R.id.tv_adapter_item1_money);//币种
        tv_adapter_item1_number=itemView.findViewById(R.id.tv_adapter_item1_number);//总额
        tv_adapter_item1_whje=itemView.findViewById(R.id.tv_adapter_item1_whje);//未还金额
        iv_item1_image=itemView.findViewById(R.id.iv_item1_image);//图片
        iv_debt_details_item1_delete=itemView.findViewById(R.id.iv_debt_details_item1_delete);
        ll_parent_one=itemView.findViewById(R.id.ll_parent_one);
    }
}