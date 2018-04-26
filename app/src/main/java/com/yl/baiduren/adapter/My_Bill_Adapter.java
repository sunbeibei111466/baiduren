package com.yl.baiduren.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Shop_Mall;
import com.yl.baiduren.activity.pay_for.Confirm_Delisting;
import com.yl.baiduren.activity.pay_for.Open_Member;
import com.yl.baiduren.activity.pay_for.Pay;
import com.yl.baiduren.activity.pay_for.Pay_Complate;
import com.yl.baiduren.activity.pay_for.Recharge;
import com.yl.baiduren.entity.result.My_Bill_Result;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.yl.baiduren.utils.Constant.PAY_TYPE_1;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_2;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_3;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_4;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_5;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_6;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_7;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_8;
import static com.yl.baiduren.utils.Constant.PAY_TYPE_9;
import static com.yl.baiduren.utils.Constant.STATUS_0;
import static com.yl.baiduren.utils.Constant.STATUS_1;
import static com.yl.baiduren.utils.Constant.STATUS_2;
import static com.yl.baiduren.utils.Constant.STATUS_3;
import static com.yl.baiduren.utils.Constant.STATUS_4;
import static com.yl.baiduren.utils.Constant.STATUS_5;


/**
 * Created by sunbeibei on 2017/12/20.
 */

public class My_Bill_Adapter extends RecyclerView.Adapter<My_Bill_ViewHolder> {
    private Context context;
    private List<My_Bill_Result.DataListBean> dataList;


    private IDeleteItem iDeleteItem;

    public interface IDeleteItem {
        void onClickDeleteItem(Long id, int position);
    }

    public void setiDeleteItem(IDeleteItem iDeleteItem) {
        this.iDeleteItem = iDeleteItem;
    }

    public My_Bill_Adapter(Context context) {
        this.context = context;

    }

    public void setDataList(List<My_Bill_Result.DataListBean> dataList) {
        this.dataList = dataList;
    }

    public List<My_Bill_Result.DataListBean> getList() {
        return dataList;
    }


    @Override
    public My_Bill_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_bill_item, parent, false);
        My_Bill_ViewHolder viewHolder = new My_Bill_ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(My_Bill_ViewHolder holder, int position) {

        final My_Bill_Result.DataListBean dataListBean = dataList.get(position);
        Glide.with(context).load(dataListBean.getUserImage())
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(holder.bill_header);
        holder.my_bill_company_name.setText(dataListBean.getUserName());
        holder.bill_jine.setText(dataListBean.getPriceAndAmount());
        holder.bill_total.setText("共计：" + dataListBean.getPriceStr());
        int type = dataListBean.getType();
        final int status = dataListBean.getStatus();
        Integer isShangCheng = dataListBean.getIsShangCheng();
         /*isshangCheng 当0的时候是债事订单，当1的时候是商城订单*/
        if (isShangCheng == 0) {
            //点击查看账单详情
            holder.bill_type_details.setText("APP订单");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == STATUS_3 || status == STATUS_4) {//交易成功
                        Log.e("点击事件", "交易成功" + status);
                        Intent intent = new Intent();
                        intent.setClass(context, Pay_Complate.class);
                        intent.putExtra("orderId", dataListBean.getId());
                        context.startActivity(intent);
                    } else if (status == STATUS_1 || status == STATUS_2) {
                        Intent intent = new Intent();
                        intent.setClass(context, Pay.class);
                        intent.putExtra("orderId", dataListBean.getId());
                        context.startActivity(intent);
                    }
                }
            });
            billType(holder, type, dataListBean);
        } else {
            holder.bill_type_details.setText("商城订单");
            holder.bill_jine.setText("数量:" + dataListBean.getGoodsNum());
        }
        payStatus(holder, position, status, type, dataListBean);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 判断账单类型
     *
     * @param holder
     */
    public void billType(My_Bill_ViewHolder holder, int type, My_Bill_Result.DataListBean dataListBean) {

        if (type == PAY_TYPE_1) {//企业vip
            holder.my_bill_type.setText("充值企业vip");

        } else if (type == PAY_TYPE_2) {//个人vip
            holder.my_bill_type.setText("充值个人vip");

        } else if (type == PAY_TYPE_3) {//解债人摘牌
            holder.my_bill_type.setText("解债人摘牌");
            holder.id_code_num.setText("备案编号:" + dataListBean.getDebtRelationCode());

        } else if (type == PAY_TYPE_4) {//备案充值次数
            holder.my_bill_type.setText("充值备案次数");

        } else if (type == PAY_TYPE_5) {
            holder.my_bill_type.setText("债权转让");

        } else if (type == PAY_TYPE_6) {
            holder.my_bill_type.setText(dataListBean.getSearchName() + "大众版征信报告");

        } else if (type == PAY_TYPE_7) {
            holder.my_bill_type.setText(dataListBean.getSearchName() + "基础版征信报告");
        } else if (type == PAY_TYPE_8) {
            holder.my_bill_type.setText(dataListBean.getSearchName() + "深度版征信报告");
        }else if (type==PAY_TYPE_9){
            holder.my_bill_type.setText(dataListBean.getSearchName()+"征信报告");
        }
    }

    /**
     * 支付状态判断
     *
     * @param holder
     */
    public void payStatus(final My_Bill_ViewHolder holder, final int position, int status, final int type, final My_Bill_Result.DataListBean dataListBean) {
        Integer isShangCheng = dataListBean.getIsShangCheng();
        /*isshangCheng 当0的时候是债事订单，当1的时候是商城订单*/

        if (isShangCheng == 0) {//债事系统订单
            if (status == STATUS_1 || status == STATUS_2) {
                holder.bill_state.setText("未付款");
                holder.bill_pay_state.setText("支付");
                holder.bill_pay_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//订单未付款进入支付页面
                        Intent intent = new Intent();
                        intent.setClass(context, Pay.class);
                        intent.putExtra("orderId", dataListBean.getId());
                        context.startActivity(intent);
                    }
                });
            } else if (status == STATUS_3 || status == STATUS_4) {//当status是3.4的时候交易成功
                holder.bill_state.setText("交易成功");
                if (type == PAY_TYPE_6 || type == PAY_TYPE_7 || type == PAY_TYPE_8||type==PAY_TYPE_9) {
                    holder.bill_pay_state.setText("删除");
                } else {
                    holder.bill_pay_state.setText("续费");
                }


                holder.bill_pay_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//交易成功进入到续费页面
                        if (type == PAY_TYPE_1 || type == PAY_TYPE_2) {//会员续费
                            context.startActivity(new Intent(context, Open_Member.class));
                        } else if (type == PAY_TYPE_3) {//摘牌续费
                            context.startActivity(new Intent(context, Confirm_Delisting.class).putExtra("debtid", dataListBean.getDebtrelationId()));
                        } else if (type == PAY_TYPE_4) {//备案次数续费
                            context.startActivity(new Intent(context, Recharge.class));
                        } else if (type == PAY_TYPE_5) {

                        } else if (type == PAY_TYPE_6 || type == PAY_TYPE_7 || type == PAY_TYPE_8||type==PAY_TYPE_9) {//当订单是报告时交易完成删除

                            iDeleteItem.onClickDeleteItem(dataList.get(position).getId(), position);


                        }

                    }
                });
            } else if (status == STATUS_5) {//订单未支付 ，订单超时 关闭
                holder.bill_state.setText("已取消");
                holder.bill_pay_state.setText("删除");
                holder.bill_pay_state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击删除
                        iDeleteItem.onClickDeleteItem(dataList.get(position).getId(), position);

                    }
                });
            }
        } else if (isShangCheng == 1) {//商城订单
            holder.my_bill_type.setText(dataListBean.getGoodsName());
            if (status == STATUS_0) {
                holder.bill_state.setText("待支付");
            } else if (status == STATUS_1) {
                holder.bill_state.setText("已支付");
            } else if (status == STATUS_2) {
                holder.bill_state.setText("支付失败");
            } else if (status == STATUS_3) {
                holder.bill_state.setText("已退款");
            } else if (status == STATUS_4) {
                holder.bill_state.setText("拒绝退款");
            }
            holder.bill_pay_state.setText("详情");
            holder.bill_pay_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Debt_Shop_Mall.class).putExtra("orderId", dataListBean.getOrderId()));
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, Debt_Shop_Mall.class).putExtra("orderId", dataListBean.getOrderId()));
                }
            });
        }

    }

}

class My_Bill_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView bill_header;
    public TextView my_bill_company_name, bill_state, my_bill_type, bill_jine, bill_total, id_code_num, bill_type_details;
    public Button bill_pay_state;

    public My_Bill_ViewHolder(View itemView) {
        super(itemView);
        bill_header = itemView.findViewById(R.id.bill_header);//账单头像
        my_bill_company_name = itemView.findViewById(R.id.my_bill_company_name);//订单人名
        bill_state = itemView.findViewById(R.id.bill_state);//订单状态
        my_bill_type = itemView.findViewById(R.id.my_bill_type);//订单类型
        bill_jine = itemView.findViewById(R.id.bill_jine);//抽象金额
        bill_total = itemView.findViewById(R.id.bill_total);//具体金额
        bill_pay_state = itemView.findViewById(R.id.bill_pay_state);//支付、续费
        id_code_num = itemView.findViewById(R.id.id_code_num);//备案编号
        bill_type_details = itemView.findViewById(R.id.bill_type_details);//账单类型详情


    }
}
