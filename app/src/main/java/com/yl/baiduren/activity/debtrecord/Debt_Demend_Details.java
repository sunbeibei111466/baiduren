package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_Details_Entity;
import com.yl.baiduren.entity.result.Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/25.
 */

public class Debt_Demend_Details extends BaseActivity {

    private ImageView demend_details_back;
    private ImageView demend_details_updata;
    private TextView demned_type;
    private TextView demend_name;
    private TextView demend_guzhi;
    private TextView demend_area;
    private TextView demend_misu;
    private Long id;
    private Demend_Details_Result detailsResult = null;
    private Long userId;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_demend_details;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);//债事ID
        userId = getIntent().getLongExtra("userId", 0);//是否隐藏编辑按钮
        demend_details_back = findViewById(R.id.demend_details_back);
        demend_details_back.setOnClickListener(listener);
        demend_details_updata = findViewById(R.id.demend_details_updata);//编辑
        demend_details_updata.setOnClickListener(listener);
        demned_type = findViewById(R.id.demned_type);//类别
        demend_name = findViewById(R.id.demend_name);//名称
        demend_guzhi = findViewById(R.id.demend_guzhi);//估值
        demend_area = findViewById(R.id.demend_area);//地区
        demend_misu = findViewById(R.id.demend_misu);//资产描述
        MyRecyclerView recyclerView = findViewById(R.id.demnd_details_imagelist);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        isShowUpdata();
    }

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId == baseRequestList.get(0).getUid()) {
                demend_details_updata.setVisibility(View.VISIBLE);
            } else {
                demend_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestWord();
    }

    private void requestWord() {
        LUtils.e("----启动----");
        //设置基础参数
        Debt_Details_Entity entity = new Debt_Details_Entity(DataWarehouse.getBaseRequest(this));
        entity.setId(id);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getDemendDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Demend_Details_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Demend_Details_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Demend_Details_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Debt_Demend_Details.this, baseResponse);
                            demned_type.setText(data.getCategoryName());
                            demend_name.setText(data.getName());
                            demend_guzhi.setText(data.getValueStr() + "");
                            demend_area.setText(data.getAreaName());
                            demend_misu.setText(data.getRemark());
                            detailsResult = data;

                        }
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == demend_details_back) {
                finish();
            } else if (v == demend_details_updata) {//编辑
                if (detailsResult != null) {
                    Intent intent = new Intent(Debt_Demend_Details.this, Debt_CreditorsDemand.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("demendUpdataMode", detailsResult);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
        }
    };
}

