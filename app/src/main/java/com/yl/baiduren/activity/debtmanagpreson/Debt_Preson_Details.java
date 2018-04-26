package com.yl.baiduren.activity.debtmanagpreson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_Details_Entity;
import com.yl.baiduren.entity.result.DebtPresonEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 债事人详情
 */

public class Debt_Preson_Details extends BaseActivity implements View.OnClickListener {

    private ImageView iv_title_left;
    private ImageView editor_icon;
    private TextView real_name;
    private TextView ic_code;
    private TextView tv_cotect_phone;
    private TextView tv_adress;
    private TextView tv_detail_area;
    private RecyclerView detail_photo;
    private TextView name;
    private Long debtPersonId;
    private DebtPresonEntity debtPresonEntity = null;
    private ImageShowAdapter imageShowAdapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_preson_details;
    }

    @Override
    public void initViews() {
        int describe = getIntent().getIntExtra("describe", 0);
        debtPersonId = getIntent().getLongExtra("debtPersonId", 0);//债事人Id
        iv_title_left = findViewById(R.id.iv_title_left);//关闭页面
        iv_title_left.setOnClickListener(this);
        //债事人姓名
        name = findViewById(R.id.debt_details_name_d);
        editor_icon = findViewById(R.id.editor_icon);//编辑
        editor_icon.setOnClickListener(this);
        real_name = findViewById(R.id.tv_real_name);//真实姓名
        ic_code = findViewById(R.id.tv_ic_code); //身份证件号
        tv_cotect_phone = findViewById(R.id.tv_cotect_phone);//联系电话
        tv_adress = findViewById(R.id.tv_adress);//所属地
        tv_detail_area = findViewById(R.id.tv_detail_area);//详细地址
        detail_photo = findViewById(R.id.detail_photo);//图片展示
        detail_photo.setLayoutManager(new GridLayoutManager(this, 4));
        if (describe == 2) {
            editor_icon.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (debtPersonId != 0) {
            requestWork();
        } else {
            LUtils.e("-----debtPersonId----空-----" + debtPersonId);
        }
    }

    private void requestWork() {
        Debt_Details_Entity entity = new Debt_Details_Entity(DataWarehouse.getBaseRequest(this));
        entity.setId(debtPersonId);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getDebtPerson_Details(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<DebtPresonEntity>>bindToLifecycle()))
                .subscribe(new BaseObserver<DebtPresonEntity>(this) {
                    @Override
                    protected void onSuccees(String code, DebtPresonEntity data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            if (data.getType() == 2) {//债事人
                                LUtils.e("-----------设置 债事人信息-------");
                                if (!TextUtils.isEmpty(data.getName())) {
                                    real_name.setText(data.getName());//真实姓名
                                    name.setText(data.getName());

                                    LUtils.e("------1111-----data.getName()-------");
                                }

                                LUtils.e("----data.getIdCode()-----", data.getIdCode());
                                if (!TextUtils.isEmpty(data.getIdCode())) {//身份证件号
                                    ic_code.setText(data.getIdCode());
                                }
                                if (!TextUtils.isEmpty(data.getPhoneNumber())) {//联系电话
                                    tv_cotect_phone.setText(data.getPhoneNumber());
                                }
                                if (!TextUtils.isEmpty(data.getAreaStr())) {//所属地
                                    tv_adress.setText(data.getAreaStr());
                                }
                                if (!TextUtils.isEmpty(data.getAddress())) {//详细地址
                                    tv_detail_area.setText(data.getAddress());
                                }
                                imageShowAdapter = new ImageShowAdapter(Debt_Preson_Details.this);
                                if (!TextUtils.isEmpty(data.getImg())) {//设置图片
                                    String[] split = data.getImg().split(",");
                                    imageShowAdapter.setImageArry(split);
                                    detail_photo.setAdapter(imageShowAdapter);
                                } else {
                                    imageShowAdapter.getImageList().clear();
                                    detail_photo.setAdapter(imageShowAdapter);
                                    imageShowAdapter.notifyDataSetChanged();
                                }
                                debtPresonEntity = data;
                            }
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v == iv_title_left) {
            finish();
        } else if (v == editor_icon) { //编辑
            Intent intent = new Intent(this, Debt_Person_Updata.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("debtPresonEntity", debtPresonEntity);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
