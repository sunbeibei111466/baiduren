package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.baiduren.MainActivity;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.credit_reporting_queries.Authorization_Record;
import com.yl.baiduren.activity.credit_reporting_queries.Authorizaton_Confrim_List;
import com.yl.baiduren.activity.debtbunesshall.My_Record_Activity;
import com.yl.baiduren.activity.mypager.My_Delisting;
import com.yl.baiduren.activity.pay_for.Open_Member;
import com.yl.baiduren.entity.result.MyMessageResult;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/18.
 */

public class My_Message_Adapter extends RecyclerView.Adapter<My_Message_ViewHolder> {

    private Context context;
    private List<MyMessageResult> myMessageResults;

    public  My_Message_Adapter(Context context) {
        this.context = context;
    }

    public void setMyMessageResults(List<MyMessageResult> myMessageResults) {
        this.myMessageResults = myMessageResults;
    }
    public List<MyMessageResult>getList(){
        return myMessageResults;
    }

    public List<MyMessageResult> getMyMessageResults() {
        return myMessageResults;
    }

    @Override
    public My_Message_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false);
        My_Message_ViewHolder viewHolder = new My_Message_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Message_ViewHolder holder, int position) {
        holder.mess_debt_state.setText(myMessageResults.get(position).getText());
        holder.message_timer.setText(Util.LongParseString(myMessageResults.get(position).getCreateTime()));
        int type=myMessageResults.get(position).getType();
        if(type==1){//系统消息

        }else{
            final int messageType=Integer.valueOf(myMessageResults.get(position).getMessageType());
            if(messageType==8){ //新用户注册成功
                holder.message_tv.setVisibility(View.GONE);
            }
            holder.message_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messageType==1||messageType==5||messageType==8||messageType==11) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("currMenu", 3);
                        context.startActivity(intent);
                    } else if (messageType==2) {//vip到期 调到   vip充值页

                        if (UserInfoUtils.IsLogin(context)) {
                            context.startActivity(new Intent(context, Open_Member.class));
                        } else {
                            context.startActivity(new Intent(context, Login_Activity_Password.class));
                        }
                    } else if (messageType==3 ||messageType==6||messageType==10) { //备案被摘牌 提醒备案人 跳转到我的备案已摘牌页
                        if (UserInfoUtils.IsLogin(context)) {
                            context.startActivity(new Intent(context, My_Record_Activity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra(Constant.MESSAGE_INDEX, true));
                        } else {
                            context.startActivity(new Intent(context, Login_Activity_Password.class));
                        }
                    }else if(messageType==4 ||messageType==7||messageType==9){
                        //4 摘牌到期提醒摘牌人 6摘牌人完成提醒备案人 7备案人完成提醒摘牌人  我的摘牌
                        if (UserInfoUtils.IsLogin(context)) {
                            context.startActivity(new Intent(context, My_Delisting.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            context.startActivity(new Intent(context, Login_Activity_Password.class));
                        }
                    }else if(messageType==12){//购买信用报告
//                        if (UserInfoUtils.IsLogin(context)) {
//                            context.startActivity(new Intent(context, Authorization_Record.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                        } else {
//                            context.startActivity(new Intent(context, Login_Activity_Password.class));
//                        }
                    }
                    else if(messageType==13){//給被授权申请人发 ---> 授权接收 回复
                        //
                        if (UserInfoUtils.IsLogin(context)) {
                            context.startActivity(new Intent(context, Authorization_Record.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            context.startActivity(new Intent(context, Login_Activity_Password.class));
                        }
                    }else if(messageType==14){//給授权申请人发 ---> 授权申请
                        //
                        if (UserInfoUtils.IsLogin(context)) {
                            context.startActivity(new Intent(context, Authorizaton_Confrim_List.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            context.startActivity(new Intent(context, Login_Activity_Password.class));
                        }
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return myMessageResults.size();
    }
}

class My_Message_ViewHolder extends RecyclerView.ViewHolder {

    public TextView mess_debt_state, message_timer, message_tv;

    public My_Message_ViewHolder(View itemView) {
        super(itemView);
        mess_debt_state = itemView.findViewById(R.id.mess_debt_state);//债事状态
        message_timer = itemView.findViewById(R.id.message_timer);//状态时间
        message_tv = itemView.findViewById(R.id.message_tv);//点击状态


    }
}
