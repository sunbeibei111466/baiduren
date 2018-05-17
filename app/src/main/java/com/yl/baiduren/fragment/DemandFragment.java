package com.yl.baiduren.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.supply_demand.My_Supply_Demend_Manger;
import com.yl.baiduren.activity.supply_demand.Supply_Demend_Hall;
import com.yl.baiduren.activity.supply_demand.Supply_Demend_Speed_Dating;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;


public class DemandFragment extends BaseFragment {

    private LinearLayout supply_demend_hall, supply_demend_manger, supply_demend_match;

    @Override
    public int loadWindowLayout() {
        return R.layout.fragment_demand;
    }

    @Override
    public void initViews(View rootView) {
        supply_demend_hall = rootView.findViewById(R.id.supply_demend_hall);//供需展示大厅
        supply_demend_hall.setOnClickListener(listener);
        supply_demend_manger = rootView.findViewById(R.id.supply_demend_manger);//我的供需管理
        supply_demend_manger.setOnClickListener(listener);
        supply_demend_match = rootView.findViewById(R.id.supply_demend_match);//供需关系匹配
        supply_demend_match.setOnClickListener(listener);


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == supply_demend_hall) {//供需大厅
                if (UserInfoUtils.IsLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), Supply_Demend_Hall.class));
                } else {
                    startActivity(new Intent(getActivity(), Login_Activity_Password.class));
                }

            } else if (v == supply_demend_manger) {//我的供需管理
                if (UserInfoUtils.IsLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), My_Supply_Demend_Manger.class));
                } else {
                    startActivity(new Intent(getActivity(), Login_Activity_Password.class));
                }

            } else if (v == supply_demend_match) {//供需匹配
                if (UserInfoUtils.IsLogin(getActivity())) {
                    startActivity(new Intent(getActivity(), Supply_Demend_Speed_Dating.class));
                } else {
                    startActivity(new Intent(getActivity(), Login_Activity_Password.class));
                }
            }
        }
    };

}
