package com.yl.baiduren.fragment.open_member;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Open_Preson_Member_Adapter;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.view.MyRecyclerView;

import static com.yl.baiduren.activity.pay_for.Open_Member.personMonthsAndPriceList1;
import static com.yl.baiduren.activity.pay_for.Open_Member.personexpand;
import static com.yl.baiduren.activity.pay_for.Open_Member.toVipTimeAfterPay;

/**
 * Created by sunbeibei on 2017/12/19.
 */

public class Open_Preson_Member extends BaseFragment implements View.OnClickListener {

    private TextView preson_shuoming;
    private TextView preson_instructions;
    private int indext = 0;
    private TextView member_help;
    private TextView service_agreement;
    private TextView type_pay;
    private int type = 1;
    private LinearLayout preson_pay;


    @Override
    public int loadWindowLayout() {
        return R.layout.open_preson_member;
    }


    @Override
    public void initViews(View rootView) {
        TextView pay_time = rootView.findViewById(R.id.pay_time);
        type_pay = rootView.findViewById(R.id.type_pay);//安卓支付协议
        type_pay.setOnClickListener(this);
        service_agreement = rootView.findViewById(R.id.service_agreement);//会员服务协议
        service_agreement.setOnClickListener(this);
        member_help = rootView.findViewById(R.id.member_help);//会员帮助
        member_help.setOnClickListener(this);
        preson_pay = rootView.findViewById(R.id.person_pay);
        preson_instructions = rootView.findViewById(R.id.preson_instructions);//说明内容
        preson_shuoming = rootView.findViewById(R.id.preson_shuoming);//特权说明
        preson_shuoming.setOnClickListener(this);
        preson_instructions.setText(personexpand);
        MyRecyclerView preson_recyclerview = rootView.findViewById(R.id.preson_recyclerview);
        preson_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (personMonthsAndPriceList1 != null) {
            Open_Preson_Member_Adapter adapter = new Open_Preson_Member_Adapter(getActivity(), personMonthsAndPriceList1);
            preson_recyclerview.setAdapter(adapter);
        }
        if (toVipTimeAfterPay != null) {
            pay_time.setText("1.支付成功后,会员将在" + toVipTimeAfterPay + "分钟后开通");
        }


    }

    @Override
    public void onClick(View v) {
        if (v == preson_shuoming) {
            if (indext == 0) {//展开说明
                Drawable drawable = getResources().getDrawable(R.mipmap.bl_up);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                preson_shuoming.setCompoundDrawables(null, null, drawable, null);
                preson_instructions.setVisibility(View.VISIBLE);
                indext = 1;
            } else if (indext == 1) {//收起说明
                Drawable drawable = getResources().getDrawable(R.mipmap.bl_down);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                preson_shuoming.setCompoundDrawables(null, null, drawable, null);
                preson_instructions.setVisibility(View.GONE);
                indext = 0;
            }
        } else if (v == member_help) {
            if (type == 1) {
                preson_pay.setVisibility(View.VISIBLE);
                type = 2;
            } else if (type == 2) {
                preson_pay.setVisibility(View.INVISIBLE);
                type = 1;
            }
        } else if (v == service_agreement) {//会员服务协议


        } else if (v == type_pay) {//安卓支付协议

        }
    }
}
