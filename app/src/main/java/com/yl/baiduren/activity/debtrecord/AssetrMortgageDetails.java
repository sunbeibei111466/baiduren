package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.ImageShowAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.Asset_Details_Body;
import com.yl.baiduren.entity.result.Assert_Mortgae_Details_Relut;
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

public class AssetrMortgageDetails extends BaseActivity {
    /*货币抵押详情*/

    private Long mort_detail;
    private ImageView iv_assetr_details_back;
    private ImageView iv_assetr_details_updata;
    private TextView mort_assert_name;
    private TextView mort_assert_num;
    private TextView mort_assert_value;
    private TextView mort_assert_area;
    private TextView mort_assert_adress;
    private MyRecyclerView recyclerView;
    private Assert_Mortgae_Details_Relut assertMortgaeDetailsRelut = null;
    private ImageShowAdapter adapter;
    private Long userId;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_assetr_mortgage_details;
    }

    @Override
    public void initViews() {
        mort_detail = getIntent().getLongExtra("mort_detail", 0);//资产id
        userId= getIntent().getLongExtra("userId", 0);
        iv_assetr_details_back = findViewById(R.id.iv_assetr_details_back);
        iv_assetr_details_back.setOnClickListener(listener);
        iv_assetr_details_updata = findViewById(R.id.iv_assetr_details_updata);//编辑货币抵押详情
        iv_assetr_details_updata.setOnClickListener(listener);
        mort_assert_name = findViewById(R.id.mort_assert_name);//资产名称
        mort_assert_num = findViewById(R.id.mort_assert_num);//资产数量
        mort_assert_value = findViewById(R.id.mort_assert_value);//评估价值
        mort_assert_adress = findViewById(R.id.mort_assert_adress);//评估机构
        recyclerView = findViewById(R.id.rv_sponsor_details_imagelist);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        isShowUpdata();
    }

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId == baseRequestList.get(0).getUid()) {
                iv_assetr_details_updata.setVisibility(View.VISIBLE);
            } else {
                iv_assetr_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestWord();
    }

    private void requestWord() {
        //设置基础参数
        Asset_Details_Body entity = new Asset_Details_Body();
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        entity.setAccessToken(baseRequests.get(0).getAccessToken());
        entity.setPlatform(2);
        entity.setUuid(baseRequests.get(0).getUuid());
        entity.setUid(baseRequests.get(0).getUid());
        entity.setLoginUsername(baseRequests.get(0).getLoginUsername());
        entity.setId(mort_detail);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getAssetMortgageDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Assert_Mortgae_Details_Relut>>bindToLifecycle()))
                .subscribe(new BaseObserver<Assert_Mortgae_Details_Relut>(this) {
                    @Override
                    protected void onSuccees(String code, Assert_Mortgae_Details_Relut data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(AssetrMortgageDetails.this, baseResponse);
                            mort_assert_name.setText(data.getName());
                            mort_assert_num.setText(data.getNum() + "");
                            mort_assert_value.setText(data.getAmountStr());
                            mort_assert_adress.setText(data.getEvaluation());

                            adapter = new ImageShowAdapter(AssetrMortgageDetails.this);
                            if (!TextUtils.isEmpty(data.getImage())) {
                                String imgUrl = data.getImage();
                                String[] split = imgUrl.split(",");
                                adapter.setImageArry(split);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.getImageList().clear();
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        assertMortgaeDetailsRelut = data;
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_assetr_details_back) {
                finish();
            } else if (v == iv_assetr_details_updata) {
                if (assertMortgaeDetailsRelut != null) {
                    Intent intent = new Intent(AssetrMortgageDetails.this, AssetrMortgage.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("assertMortgaeDetailsRelut", assertMortgaeDetailsRelut);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

        }
    };
}
