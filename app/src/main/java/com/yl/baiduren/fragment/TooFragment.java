package com.yl.baiduren.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.Util;

public class TooFragment extends BaseFragment implements View.OnClickListener{


    private LinearLayout new_1;//债事人员
    private LinearLayout new_2;//债事机构
    private LinearLayout new_3;//解债天使

    @Override
    public int loadWindowLayout() {
        return R.layout.fragment_too;
    }

    @Override
    public void initViews(View rootView) {
        new_1=rootView.findViewById(R.id.new_1);
        new_2=rootView.findViewById(R.id.new_2);
        new_3=rootView.findViewById(R.id.new_3);
        new_1.setOnClickListener(this);
        new_2.setOnClickListener(this);
        new_3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), Break_Debt_Activity.class);
        if(v==new_1){//债事人员
            intent.putExtra("url", ServiceUrl.H5_DEBT_PERSON);
            intent.putExtra("type", 5);
        }else if(v==new_2){//债事机构
            intent.putExtra("url", ServiceUrl.H5_DEBT_AGENCY);
            intent.putExtra("type", 6);
        }else if(v==new_3){//解债天使
            intent.putExtra("url", ServiceUrl.H5_DEBT_ANGEL);
            intent.putExtra("type", 7);
        }
        startActivity(intent);
    }


}
