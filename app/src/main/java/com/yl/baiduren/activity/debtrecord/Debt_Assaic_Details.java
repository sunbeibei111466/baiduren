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
import com.yl.baiduren.entity.result.Asset_Details_Result;
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
 * Created by sunbeibei on 2017/12/26.
 */

public class Debt_Assaic_Details extends BaseActivity {

    private ImageView assic_details_back;
    private ImageView assic_details_updata;
    private TextView assic_type;
    private TextView assic_name;
    private TextView assic_guzhi;
    private TextView assic_num;
    private TextView assic_amout;
    private TextView assic_pinggu;
    private TextView assic_area;
    private TextView assic_miaoshu;
    private MyRecyclerView recyclerView;
    private Long assetId;
    private Asset_Details_Result assetDetailsResult = null;
    private Long userId;//备案人id

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_assic_details;
    }

    @Override
    public void initViews() {
        assetId = getIntent().getLongExtra("assetId", 0);
        userId = getIntent().getLongExtra("userId", 0);
        assic_details_back = findViewById(R.id.assic_details_back);
        assic_details_back.setOnClickListener(listener);
        assic_details_updata = findViewById(R.id.assic_details_updata);//编辑资产
        assic_details_updata.setOnClickListener(listener);
        assic_type = findViewById(R.id.assic_type);//类别
        assic_name = findViewById(R.id.assic_name);//名称
        assic_guzhi = findViewById(R.id.assic_guzhi);//估值
        assic_num = findViewById(R.id.assic_num);//数量
        assic_amout = findViewById(R.id.assic_amout);//估值总额
        assic_pinggu = findViewById(R.id.assic_pinggu);//评估机构
        assic_area = findViewById(R.id.assic_area);//地区
        assic_miaoshu = findViewById(R.id.assic_miaoshu);//资产描述
        recyclerView = findViewById(R.id.assic_details_image);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        isShowUpdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (assetId != 0) {
            requestWork();
        }
    }

    private void requestWork() {
        //设置基础参数
        Asset_Details_Body entity = new Asset_Details_Body();

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        entity.setAccessToken(baseRequests.get(0).getAccessToken());
        entity.setPlatform(2);
        entity.setUuid(baseRequests.get(0).getUuid());
        entity.setUid(baseRequests.get(0).getUid());
        entity.setLoginUsername(baseRequests.get(0).getLoginUsername());
        entity.setId(assetId);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getAssetDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Asset_Details_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Asset_Details_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Asset_Details_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Debt_Assaic_Details.this, baseResponse);
                            assic_type.setText(data.getCategoryName());
                            assic_name.setText(data.getName());
                            assic_guzhi.setText(data.getPriceStr() + "");
                            assic_num.setText(data.getNum() + "");
                            assic_amout.setText(data.getTotalStr() + "");
                            assic_pinggu.setText(data.getEvaluation() + "");
                            assic_area.setText(data.getAreaName() + "");
                            assic_miaoshu.setText(data.getRemark() + "");

                            ImageShowAdapter adapter = new ImageShowAdapter(Debt_Assaic_Details.this);
                            if (!TextUtils.isEmpty(data.getImgUrl())) {
                                String[] split = data.getImgUrl().split(",");
                                adapter.setImageArry(split);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.getImageList().clear();
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        assetDetailsResult = data;

                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == assic_details_back) {
                finish();
            } else if (v == assic_details_updata) {//资产编辑

                if (assetDetailsResult != null) {
                    Intent intent = new Intent(Debt_Assaic_Details.this, Debt_CreditorsAssets.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("assetDetailsResult", assetDetailsResult);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }
    };

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId == baseRequestList.get(0).getUid()) {
                assic_details_updata.setVisibility(View.VISIBLE);
            } else {
                assic_details_updata.setVisibility(View.GONE);
            }
        }
    }
}
