package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.result.CertifiedResult;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

//征信
public class CreditTransition extends BaseActivity implements View.OnClickListener {

    private LinearLayout credit_query, credit_management, credit_registration, credit_objection;
    private ImageView iv_querry_finish;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_credit_transition;
    }

    @Override
    public void initViews() {
        iv_querry_finish = findViewById(R.id.iv_querry_finish);
        iv_querry_finish.setOnClickListener(this);
        credit_query = findViewById(R.id.credit_query);//大众版
        credit_query.setOnClickListener(this);
        credit_management = findViewById(R.id.credit_management);//基础版
        credit_management.setOnClickListener(this);
        credit_registration = findViewById(R.id.credit_registration);//深度版
        credit_registration.setOnClickListener(this);
        credit_objection = findViewById(R.id.credit_objection);//授权管理
        credit_objection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == credit_query) {//   大众版
            startActivity(new Intent(this, Credit_Reporting_Queries.class));
        } else if (v == credit_management) {//基础版
            getCertification();
        } else if (v == credit_registration) {//深度版
            DialogUtils.showDialogPrompt(this, true, "该功能请到官网操作");
        } else if (v == credit_objection) {//授权管理
            startActivity(new Intent(this, AuthorizationManagment.class));
        } else if (v == iv_querry_finish) {
            finish();
        }
    }

    /**
     * 获取认证结果
     */
    public void getCertification() {


        if (UserInfoUtils.IsLogin(this)) {

            com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
            String json = Util.toJson(baseRequest);//转成json
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---json---" + json);
            BaseObserver<CertifiedResult> baseObserver = new BaseObserver<CertifiedResult>(this) {
                @Override
                protected void onSuccees(String code, CertifiedResult data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {//成功
                        whetherCertified(data.getPersonAuth(), data.getCompanyAuth());
                    }
                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer().
                    whetherCertified(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<CertifiedResult>>bindToLifecycle()))
                    .subscribe(baseObserver);

        }


    }


    /**
     * 是否认证
     *
     * @param personAuth  geren
     * @param companyAuth
     */
    private void whetherCertified(boolean personAuth, boolean companyAuth) {


        String content = "";//提示内容
        String enterpriseText = "";//企业提示内容
        String individualText = "";//个人提示内容
        boolean isEnterprise = companyAuth;
        boolean isIndivdual = personAuth;
        if (isEnterprise) {//企业已认证
            enterpriseText = "企业进入";
            if (isIndivdual) {
                individualText = "个人进入";
                content = "您已认证，请进入";
            } else {
                individualText = "个人认证";
                content = "您还未进行个人认证";
            }
        } else {
            if (isIndivdual) {//个人已认证
                enterpriseText = "企业认证";
                individualText = "个人进入";
                content = "您还未进行企业认证";
            } else {
                content = "您还未认证,请选择个人认证或企业认证";
                enterpriseText = "企业认证";
                individualText = "个人认证";
            }
        }

        final boolean finalIsEnterprise = isEnterprise;
        final boolean finalIsIndivdual = isIndivdual;
        if (finalIsEnterprise) {
            startActivity(new Intent(CreditTransition.this, ZhengXing_Query.class));//进入证信
        } else {
            DialogUtils.showDialogCertification(CreditTransition.this, true, content, enterpriseText, individualText, new DialogUtils.OnCertificationListener() {
                @Override
                public void onEnterprise() {
                    if (finalIsEnterprise) {
                        startActivity(new Intent(CreditTransition.this, CreditBasicEdition.class));//进入证信
                    } else {
                        startActivity(new Intent(CreditTransition.this, Conpany_RenZheng.class));//企业认证
                    }

                }

                @Override
                public void onIndividualTv() {
                    if (finalIsIndivdual) {
                        startActivity(new Intent(CreditTransition.this, CreditBasicEdition.class));//进入证信
                    } else {
                        startActivity(new Intent(CreditTransition.this, Preson_RenZheng.class));//个人认证
                    }
                }
            });
        }

    }
}
