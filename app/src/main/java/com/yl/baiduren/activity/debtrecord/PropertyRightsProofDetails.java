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
import com.yl.baiduren.entity.result.Property_Rights_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PropertyRightsProofDetails extends BaseActivity {
    /*产权借贷详情*/

    private Long proper_detail_id;
    private ImageView iv_property_details_back;
    private ImageView iv_property_details_updata;
    private TextView proper_name;
    private TextView proper_unit;
    private TextView proper_belong;
    private TextView proper_year;
    private TextView proper_pinggu;
    private TextView proper_jigou;
    private TextView proper_zhuli;
    private TextView proper_zh_year;
    private TextView proper_value;
    private TextView zhuanli_pinggu;
    private TextView zhuanli_return;
    private TextView yet_retun_amout;
    private MyRecyclerView recyclerView;
    private Property_Rights_Result propertyRightsResult = null;
    private Long userId;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_property_rights_proof_details;
    }

    @Override
    public void initViews() {
        proper_detail_id = getIntent().getLongExtra("proper_detail", 0);
        userId = getIntent().getLongExtra("userId", 0);
        iv_property_details_back = findViewById(R.id.iv_property_details_back);
        iv_property_details_back.setOnClickListener(listener);
        iv_property_details_updata = findViewById(R.id.iv_property_details_updata);//产权编辑
        iv_property_details_updata.setOnClickListener(listener);
        proper_name = findViewById(R.id.proper_name);//标的名称
        proper_unit = findViewById(R.id.proper_unit);//产权计量单位
        proper_belong = findViewById(R.id.proper_belong);//产权归属
        proper_year = findViewById(R.id.proper_year);//产权年限
        proper_pinggu = findViewById(R.id.proper_pinggu);//产权评估值
        proper_jigou = findViewById(R.id.proper_jigou);//产权评估机构
        proper_zhuli = findViewById(R.id.proper_zhuli);//专利号
        proper_zh_year = findViewById(R.id.proper_zh_year);//专利年限
        proper_value = findViewById(R.id.proper_value);//专利评估值
        zhuanli_pinggu = findViewById(R.id.zhuanli_pinggu);//专利评估机构
        zhuanli_return = findViewById(R.id.zhuanli_return); //专利已还合计
        yet_retun_amout = findViewById(R.id.yet_retun_amout);//未余额
        recyclerView = findViewById(R.id.rv_property_details_imagelist);
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
                iv_property_details_updata.setVisibility(View.VISIBLE);
            } else {
                iv_property_details_updata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proper_detail_id != 0) {
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
        entity.setId(proper_detail_id);
        String json = Util.toJson(entity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .getPropertyLoanDetails(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Property_Rights_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Property_Rights_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Property_Rights_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(PropertyRightsProofDetails.this, baseResponse);
                            if (!TextUtil.isEmpty(data.getName())) {
                                proper_name.setText(data.getName());
                            }
                            if (!TextUtil.isEmpty(data.getUnit())) {
                                proper_unit.setText(data.getUnit());
                            }
                            if (!TextUtil.isEmpty(data.getOwner())) {
                                proper_belong.setText(data.getOwner());
                            }
                            if (data.getRightYear() !=0) {
                                proper_year.setText(data.getRightYear() + "");
                            }
                            if (!TextUtil.isEmpty(data.getRightValueStr())) {
                                proper_pinggu.setText(data.getRightValueStr() + "元");
                            }
                            if (!TextUtil.isEmpty(data.getRightEvaluation())) {
                                proper_jigou.setText(data.getRightEvaluation());
                            }
                            if (!TextUtil.isEmpty(data.getPatentNo())) {
                                proper_zhuli.setText(data.getPatentNo());
                            }
                            if (data.getPatentYear() !=0) {
                                proper_zh_year.setText(data.getPatentYear() + "");
                            }
                            if (!TextUtil.isEmpty(data.getPatentValueStr())) {
                                proper_value.setText(data.getPatentValueStr() + "元");
                            }

                            if (!TextUtil.isEmpty(data.getPatentEvaluation())) {
                                zhuanli_pinggu.setText(data.getPatentEvaluation());
                            }
                            if (!TextUtil.isEmpty(data.getBalanceStr())) {
                                yet_retun_amout.setText(data.getBalanceStr() + "元");
                            }
                            if (!TextUtil.isEmpty(data.getReturnMoneyStr())) {
                                zhuanli_return.setText(data.getReturnMoneyStr() + "元");
                            }


                            ImageShowAdapter adapter = new ImageShowAdapter(PropertyRightsProofDetails.this);
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

                            propertyRightsResult = data;

                        }
                    }
                });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_property_details_back) {
                finish();
            } else if (v == iv_property_details_updata) {
                Intent intent = new Intent(PropertyRightsProofDetails.this, PropertyRightsProof.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("propertyRightsResult", propertyRightsResult);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }
    };
}
