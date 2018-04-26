package com.yl.baiduren.fragment.open_member;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Open_Enter_Member_Adpater;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.view.MyRecyclerView;

import static com.yl.baiduren.activity.pay_for.Open_Member.mechanexpand;
import static com.yl.baiduren.activity.pay_for.Open_Member.openMemberResults;
import static com.yl.baiduren.activity.pay_for.Open_Member.toVipTimeAfterPay;

/**
 * Created by sunbeibei on 2017/12/19.
 */

public class Open_Enterprise_Member extends BaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private TextView instructions;
    private TextView enter_preson_shuoming;
    private int indext=0;
    private RadioGroup rg_parent;
    private Open_Enter_Member_Adpater adpater;
    private TextView member_help;
    private TextView service_agreement;
    private TextView type_pay;
    private int type = 1;
    private LinearLayout preson_pay;


    @Override
    public int loadWindowLayout() {
        return R.layout.open_enterprise_member;
    }

    @Override
    public void initViews(View rootView) {
        TextView pay_time = rootView.findViewById(R.id.pay_time);
        pay_time = rootView.findViewById(R.id.pay_time);//时间
        type_pay = rootView.findViewById(R.id.type_pay);//安卓支付协议
        type_pay.setOnClickListener(this);
        service_agreement = rootView.findViewById(R.id.service_agreement);//会员服务协议
        service_agreement.setOnClickListener(this);
        member_help = rootView.findViewById(R.id.member_help);//会员帮助
        member_help.setOnClickListener(this);
        preson_pay = rootView.findViewById(R.id.person_pay);

        rg_parent=rootView.findViewById(R.id.rg_parent);
        rg_parent.setOnCheckedChangeListener(this);
        enter_preson_shuoming = rootView.findViewById(R.id.enter_preson_shuoming);//特权说明
        enter_preson_shuoming.setOnClickListener(this);
        instructions = rootView.findViewById(R.id.instructions);//说明内容
        instructions.setText(mechanexpand);
        MyRecyclerView recycelrView = rootView.findViewById(R.id.enter_recycler);
        recycelrView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if(openMemberResults!=null){
            adpater =new Open_Enter_Member_Adpater(getActivity(),openMemberResults);
            adpater.setType(1);
            recycelrView.setAdapter(adpater);
        }
        if(toVipTimeAfterPay!=null){
            pay_time.setText("1.支付成功后,会员将在" + toVipTimeAfterPay + "分钟后开通");
        }
    }

    @Override
    public void onClick(View v) {
        if (v==enter_preson_shuoming){
            if (indext==0){//展开说明
                    Drawable drawable = getResources().getDrawable(R.mipmap.or_up);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    enter_preson_shuoming.setCompoundDrawables(null, null, drawable, null);
                    instructions.setVisibility(View.VISIBLE);
                    indext=1;
                }else if (indext==1){
                    Drawable drawable = getResources().getDrawable(R.mipmap.or_down);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    enter_preson_shuoming.setCompoundDrawables(null, null, drawable, null);
                    instructions.setVisibility(View.GONE);
                    indext=0;
                }

        }else if(v==member_help){//会员帮助
            if (type == 1) {
                preson_pay.setVisibility(View.VISIBLE);
                type = 2;
            } else if (type == 2) {
                preson_pay.setVisibility(View.INVISIBLE);
                type = 1;
            }
        }else if(v==service_agreement){//会员服务协议

        }else if(v==type_pay){//安卓支付协议

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group==rg_parent){
            if(checkedId==R.id.rb_qiye_5){
                type=1;
            }else if(checkedId==R.id.rb_qiye_10){
                type=2;
            }
            adpater.setType(type);
            adpater.notifyDataSetChanged();
        }
    }
}
