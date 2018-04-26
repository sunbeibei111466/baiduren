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
import com.yl.baiduren.entity.result.Sponsor_Details_Relult;
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

public class SponsorDetails extends BaseActivity {
    /*担保人详情*/

    private ImageView iv_sponsor_details_back;
    private ImageView iv_sponsor_details_updata;
    private TextView spone_name;
    private TextView spone_phone;
    private TextView spone_icard;
    private TextView spone_area;
    private TextView spone_adress;
    private MyRecyclerView recyclerView;
    private Long sponsor_detail;
    private Sponsor_Details_Relult sponsorDetailsRelult = null;
    private Long userId;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_sponsor_details;

    }

    @Override
    public void initViews() {
        sponsor_detail = getIntent().getLongExtra("sponsor_detail", 0);
        userId = getIntent().getLongExtra("userId", 0);
        iv_sponsor_details_back = findViewById(R.id.iv_sponsor_details_back);
        iv_sponsor_details_back.setOnClickListener(listener);
        iv_sponsor_details_updata = findViewById(R.id.iv_sponsor_details_updata);//担保人编辑
        iv_sponsor_details_updata.setOnClickListener(listener);
        spone_name = findViewById(R.id.spone_name);//担保人姓名
        spone_phone = findViewById(R.id.spone_phone);//手机号
        spone_icard = findViewById(R.id.spone_icard);//身份证号
        spone_area = findViewById(R.id.spone_area);//所属地
        spone_adress = findViewById(R.id.spone_adress);//详细地址
        recyclerView = findViewById(R.id.rv_sponsor_details_imagelist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        isShowUpdata();

    }

    /**
     * 判断是否显示编辑按钮
     */
    public void isShowUpdata() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            if (userId == baseRequestList.get(0).getUid()) {
                iv_sponsor_details_updata.setVisibility(View.VISIBLE);
            } else {
                iv_sponsor_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sponsor_detail != 0) {
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
        entity.setId(sponsor_detail);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getSponsorDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Sponsor_Details_Relult>>bindToLifecycle()))
                .subscribe(new BaseObserver<Sponsor_Details_Relult>(this) {
                    @Override
                    protected void onSuccees(String code, Sponsor_Details_Relult data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(SponsorDetails.this, baseResponse);
                            spone_name.setText(data.getName());
                            spone_phone.setText(data.getPhoneNumber());
                            spone_area.setText(data.getAreaName());
                            spone_adress.setText(data.getAddress());
                            spone_icard.setText(data.getIdCode());

                            ImageShowAdapter adapter = new ImageShowAdapter(SponsorDetails.this);
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

                            sponsorDetailsRelult = data;

                        }
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_sponsor_details_back) {
                finish();
            } else if (v == iv_sponsor_details_updata) {

                Intent intent = new Intent(SponsorDetails.this, Sponsor.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sponsorDetailsRelult", sponsorDetailsRelult);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };
}
