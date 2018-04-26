package com.yl.baiduren.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.entity.result.Authouization_Apply_List;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/27.
 */

public class Authorization_Recoud_Adapter extends RecyclerView.Adapter<Authorizaton_ViewHolder> {
    private Context context;
    private List<Authouization_Apply_List.AuthorizeResponse> dataList;

    public Authorization_Recoud_Adapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<Authouization_Apply_List.AuthorizeResponse> dataList) {
        this.dataList = dataList;
    }

    public List<Authouization_Apply_List.AuthorizeResponse> getList() {
        return dataList;
    }

    @Override
    public Authorizaton_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.authorization_records_item, parent, false);
        Authorizaton_ViewHolder viewHolder = new Authorizaton_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Authorizaton_ViewHolder holder, int position) {
        final Authouization_Apply_List.AuthorizeResponse authorizeResponse = dataList.get(position);
        holder.iv_record_name.setText(authorizeResponse.getSearchName());
        holder.return_reson.setText(authorizeResponse.getReplyReason());
        holder.time.setText(Util.LongParseString(authorizeResponse.getCreateTime()));
        holder.tv_authorization_code.setText(authorizeResponse.getAuthCode());
        int status = authorizeResponse.getStatus();
        if (status==1){
            holder.tv_status.setText("待确认");
            holder.tv_copy.setVisibility(View.GONE);
        }else if (status==2){
            holder.tv_status.setText("已确认");
        }else if (status==3) {
            holder.tv_status.setText("已拒绝");
            holder.tv_copy.setVisibility(View.GONE);
        }

        holder.tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtil.isEmpty(authorizeResponse.getAuthCode())) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(authorizeResponse.getAuthCode());
                    ToastUtil.showShort(context, "已经复制剪贴板");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class Authorizaton_ViewHolder extends RecyclerView.ViewHolder {
    public TextView iv_record_name, return_reson, time, tv_authorization_code, tv_status, tv_copy;

    public Authorizaton_ViewHolder(View itemView) {
        super(itemView);
        iv_record_name = itemView.findViewById(R.id.iv_record_name);//信息主体名称
        return_reson = itemView.findViewById(R.id.return_reson);//回复原因
        time = itemView.findViewById(R.id.recoud_time);//时间
        tv_authorization_code = itemView.findViewById(R.id.tv_authorization_code);//授权码
        tv_status = itemView.findViewById(R.id.tv_status);//回复状态
        tv_copy = itemView.findViewById(R.id.tv_copy);

    }
}
