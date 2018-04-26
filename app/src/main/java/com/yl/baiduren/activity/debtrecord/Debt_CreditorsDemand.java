package com.yl.baiduren.activity.debtrecord;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.DemandDO;
import com.yl.baiduren.entity.request_body.DemandUpdata;
import com.yl.baiduren.entity.result.Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 债权人需求
 */
public class Debt_CreditorsDemand extends BaseActivity implements View.OnClickListener {

    private String demandName;//需求名称
    private String demandId;//需求Id
    private TextView et_debt2_demand_type, et_debt2_demand_diqu_too;
    private EditText et_debt2_demand_name, et_debt2_demand_jine, et_debt2_demand_beizhu_too;
    private String quCode;//区Code
    private Button bt_debt2_demand;
    private Long updataDemandId;
    private Demend_Details_Result detailsResult = null;
    private ImageView iv_debt2_demand_back;


    /**/
    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__debtor_demand;
    }

    @Override
    public void initViews() {

        detailsResult = (Demend_Details_Result) getIntent().getSerializableExtra("demendUpdataMode");//带过来进行编辑的
        updataDemandId = getIntent().getLongExtra("updataDemandId", 0);
        demandName = getIntent().getStringExtra("demandName");
        demandId = getIntent().getStringExtra("demandId");
        et_debt2_demand_type = findViewById(R.id.et_debt2_demand_type_too);//需求类型
        et_debt2_demand_type.setText(demandName);
        iv_debt2_demand_back = findViewById(R.id.iv_debt2_demand_back);//关闭页面
        iv_debt2_demand_back.setOnClickListener(this);
        et_debt2_demand_name = findViewById(R.id.et_debt2_demand_name_too);//需求名称
        et_debt2_demand_jine = findViewById(R.id.et_debt2_demand_jine_too);//估值
        et_debt2_demand_diqu_too = findViewById(R.id.et_debt2_demand_diqu_too);//地区
        et_debt2_demand_diqu_too.setOnClickListener(this);
        et_debt2_demand_beizhu_too = findViewById(R.id.et_debt2_demand_beizhu_too);//备注
        bt_debt2_demand = findViewById(R.id.bt_debt2_demand);
        bt_debt2_demand.setOnClickListener(this);


        updata();//本地数据编辑
        updataService();//服务器数据编辑
    }

    private void updataService() {
        if (detailsResult != null) {
//            bt_debt2_demand.setText("保存");
            et_debt2_demand_type.setText(detailsResult.getCategoryName());//类型名称
            et_debt2_demand_name.setText(detailsResult.getName());//需求名称
            et_debt2_demand_jine.setText(detailsResult.getValueStr() + "");//金额
            et_debt2_demand_diqu_too.setText(detailsResult.getAreaName());//地址
            et_debt2_demand_beizhu_too.setText(detailsResult.getRemark());//描述
        }
    }

    private void updata() {

        if (updataDemandId != 0) {
            DemandDO demandDO = GreenDaoUtils.getInstance(this).getDemandDODao().load(updataDemandId);
            et_debt2_demand_type.setText(demandDO.getCategoryName());//类型名称
            et_debt2_demand_name.setText(demandDO.getName());//需求名称
            et_debt2_demand_jine.setText(demandDO.getValueStr() + "");//金额
            et_debt2_demand_diqu_too.setText(demandDO.getDizhi());//地址
            et_debt2_demand_beizhu_too.setText(demandDO.getRemark());//描述
        }

    }

    @Override
    public void onClick(View v) {
        if (v == et_debt2_demand_diqu_too) {//地址选择
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_debt2_demand_diqu_too.setText(string);
                    quCode = quId;
                }
            });
        }else if (v==iv_debt2_demand_back){//关闭页面
            finish();
        }else if (v == bt_debt2_demand) {//保存按钮

            String demand_type = et_debt2_demand_type.getText().toString().trim();
            String name = et_debt2_demand_name.getText().toString().trim();
            String jine = et_debt2_demand_jine.getText().toString().trim();
            String dizhi = et_debt2_demand_diqu_too.getText().toString().trim();
            String beizhu = et_debt2_demand_beizhu_too.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                ToastUtil.showShort(this,"请输入需求名称");
                return;
            }
            if (TextUtils.isEmpty(jine)){
                ToastUtil.showShort(this,"请输入需求估值");
                return;
            }
            if (TextUtils.isEmpty(dizhi)){
                ToastUtil.showShort(this,"请选择需求所在地区");
                return;
            }
            if (TextUtils.isEmpty(beizhu)){
                ToastUtil.showShort(this,"请填写需求描述");
                return;
            }

            if (updataDemandId != 0) {//本地编辑
                DemandDO demandDO = GreenDaoUtils.getInstance(this).getDemandDODao().load(updataDemandId);
                demandDO.setName(name);
                demandDO.setCategoryName(demandDO.getCategoryName());
                int demanId = demandDO.getCategoryId();
                demandDO.setCategoryId(demanId);
                demandDO.setValueStr(jine);
                if(!TextUtils.isEmpty(quCode)){//不为空时，表示修改过
                    demandDO.setArea(quCode);
                }else{ //为空时，表示没修改过
                    demandDO.setArea(demandDO.getArea());
                }

                demandDO.setDizhi(dizhi);
                demandDO.setRemark(beizhu);
                GreenDaoUtils.getInstance(this).getDemandDODao().update(demandDO);
            } else if (detailsResult != null) { //编辑服务器

                DemandUpdata demandUpdata = new DemandUpdata();
                demandUpdata.setId(detailsResult.getId());
                demandUpdata.setDebtRelationId(detailsResult.getDebtRelationId());
                demandUpdata.setCategoryId(detailsResult.getCategoryId());
                if(!TextUtils.isEmpty(quCode)){
                    demandUpdata.setArea(quCode);
                }else{
                    demandUpdata.setArea(detailsResult.getArea());
                }
                demandUpdata.setName(name);
                demandUpdata.setValueStr(jine);
                demandUpdata.setRemark(beizhu);
                getHttpUpdata(demandUpdata);
            } else {//本地新增
                DemandDO demandDO = new DemandDO();
                demandDO.setCategoryName(demandName);
                int demanId = Integer.valueOf(demandId);
                demandDO.setCategoryId(demanId);
                demandDO.setName(name);
                demandDO.setValueStr(jine);
                demandDO.setArea(quCode);
                demandDO.setDizhi(dizhi);
                demandDO.setRemark(beizhu);
                GreenDaoUtils.getInstance(this).getDemandDODao().insert(demandDO);
            }
            finish();
        }
    }

    private void getHttpUpdata(DemandUpdata demandUpdata) {

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            demandUpdata.setAccessToken(baseRequests.get(0).getAccessToken());
            demandUpdata.setPlatform(2);
            demandUpdata.setUuid(baseRequests.get(0).getUuid());
            demandUpdata.setUid(baseRequests.get(0).getUid());
            demandUpdata.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(demandUpdata);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getDemandUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(Debt_CreditorsDemand.this,baseResponse);
                                finish();
                            }
                        }
                    });

        }
    }
}
