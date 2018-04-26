package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.Downlod_Pdf_Activity;
import com.yl.baiduren.R;
import com.yl.baiduren.entity.result.ReportResult;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/26.
 */

public class My_Report_Adapter extends RecyclerView.Adapter<My_Report_ViewHolder> {

    private Context context;
    private List<ReportResult.Report> reportResults;

    public My_Report_Adapter(Context context) {
        this.context = context;
    }

    public void setReportResults(List<ReportResult.Report> reportResults) {
        this.reportResults = reportResults;
    }

    public List<ReportResult.Report> getReportResults() {
        return reportResults;
    }

    @Override
    public My_Report_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_report_adapter, parent, false);
        My_Report_ViewHolder viewHolder = new My_Report_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Report_ViewHolder holder, final int position) {
        holder.report_name.setText(reportResults.get(position).getSearchName());
        if(reportResults.get(position).isSupply()){
            holder.report_type.setText("资产");
            holder.report_type.setTextColor(context.getResources().getColor(R.color.pdf_red));
            holder.iv_pdf.setImageResource(R.mipmap.pdf);
        }else {
            holder.report_type.setText("征信");
            holder.report_type.setTextColor(context.getResources().getColor(R.color.blue));
            holder.iv_pdf.setImageResource(R.mipmap.lan);

        }
        holder.report_time.setText(Util.LongParseString(reportResults.get(position).getCreateTime()));
        holder.yu_lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Downlod_Pdf_Activity.class);
                if(reportResults.get(position).getStatus()==2){//加模糊
                    intent.putExtra("status",2);
                }else if(reportResults.get(position).getStatus()==3){//
                    intent.putExtra("status",3);
                }
                intent.putExtra("id",reportResults.get(position).getId());
                intent.putExtra("name",reportResults.get(position).getSearchName());
                intent.putExtra("reportUrl",reportResults.get(position).getReportUrl());
                context.startActivity(intent);
            }
        });
        holder.down_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(context,"请到官网下载");
            }
        });
    }


    @Override
    public int getItemCount() {
        return reportResults.size();
    }
}

class My_Report_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_pdf, down_load, yu_lan;
    public TextView report_name, report_type, report_time;

    public My_Report_ViewHolder(View itemView) {
        super(itemView);
        iv_pdf = itemView.findViewById(R.id.iv_pdf);
        report_name = itemView.findViewById(R.id.report_name);//报告名称
        report_type = itemView.findViewById(R.id.report_type);//报告类型
        report_time = itemView.findViewById(R.id.report_time);//报告时间
        down_load = itemView.findViewById(R.id.down_load);//下载
        yu_lan = itemView.findViewById(R.id.yu_lan);//预览


    }
}
