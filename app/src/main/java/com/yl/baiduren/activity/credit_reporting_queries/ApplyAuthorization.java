package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AuthorizeParam;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.result.UserResponse;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 授权申请
 */
public class ApplyAuthorization extends BaseActivity implements View.OnClickListener{

    private ImageView apply_finish;
    private EditText et_apply_name,et_apply_reason,et_apply_agency,et_apply_yzm;
    private TextView et_applicantName,et_apply_crad,user_instructions_apply;
    private Button btn_apply_tijiao,bt_send_apply;
    private String phone=null;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_apply_authorization;
    }

    @Override
    public void initViews() {
        apply_finish=findViewById(R.id.apply_finish);
        apply_finish.setOnClickListener(this);
        et_apply_name=findViewById(R.id.et_apply_name);//被查询企业名称
        et_apply_reason=findViewById(R.id.et_apply_reason);//查询原因
        et_applicantName=findViewById(R.id.et_applicantName);//申请人姓名
        et_apply_crad=findViewById(R.id.et_apply_crad);//申请人身份证号
        et_apply_yzm=findViewById(R.id.et_apply_yzm);//验证码
        bt_send_apply=findViewById(R.id.bt_send_apply);//发送验证码
        bt_send_apply.setOnClickListener(this);
        et_apply_agency=findViewById(R.id.et_apply_agency);//申请人代表机构
        btn_apply_tijiao=findViewById(R.id.btn_apply_tijiao);//提交
        btn_apply_tijiao.setOnClickListener(this);
        user_instructions_apply=findViewById(R.id.user_instructions_apply);
        user_instructions_apply.setOnClickListener(this);
        getInformation();
    }

    @Override
    public void onClick(View v) {
        if(v==apply_finish){
            finish();
        }else if(v==btn_apply_tijiao){//提交
            submit();
        }else if(v==bt_send_apply){
            if(phone!=null){
                new Thread(new CountdownTimer_utils(60, bt_send_apply, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
                getVcode(phone);
            }
        }else if(v==user_instructions_apply){
            Intent intent=new Intent(this, Break_Debt_Activity.class);
            intent.putExtra("type",8);
            intent.putExtra("url", ServiceUrl.H5_HCREDITTALK);
            startActivity(intent);
        }
    }



    private void submit() {
        String et_apply_nameStr=et_apply_name.getText().toString().trim();
        String et_apply_reasonStr=et_apply_reason.getText().toString().trim();
        String et_apply_agencyStr=et_apply_agency.getText().toString().trim();
        String et_apply_yzmStr=et_apply_yzm.getText().toString().trim();
        if(TextUtils.isEmpty(et_apply_nameStr)){
            ToastUtil.showShort(this,"请输入被查询企业名称");
            return;
        }
        if(TextUtils.isEmpty(et_apply_reasonStr)){
            ToastUtil.showShort(this,"请输入查询原因");
            return;
        }

        if(TextUtils.isEmpty(et_apply_yzmStr)){
            ToastUtil.showShort(this,"请输入验证码");
            return;
        }

        if(TextUtils.isEmpty(et_apply_agencyStr)){
            ToastUtil.showShort(this,"申请人代表机构");
            return;
        }

        AuthorizeParam authorizeParam=new AuthorizeParam(DataWarehouse.getBaseRequest(this));
        authorizeParam.setSearchName(et_apply_nameStr);
        authorizeParam.setSendReason(et_apply_reasonStr);
        authorizeParam.setAcceptId(DataWarehouse.getBaseRequest(this).uid);
        authorizeParam.setValidCodeType(7);
        authorizeParam.setMobile(phone);
        authorizeParam.setValidCode(et_apply_yzmStr);
        String json = Util.toJson(authorizeParam);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);

        RetrofitHelper.getInstance(this).getServer().
                authenticate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<AuthorizeParam>>bindToLifecycle()))
                .subscribe(new BaseObserver<AuthorizeParam>(this) {
                    @Override
                    protected void onSuccees(String code, AuthorizeParam data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {//成功
                           ToastUtil.showShort(ApplyAuthorization.this,"提交成功");
                           finish();
                        }
                    }
                });
    }


    private void getInformation(){
        com.yl.baiduren.data.BaseRequest baseRequest=DataWarehouse.getBaseRequest(this);
        String json = Util.toJson(baseRequest);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);

        RetrofitHelper.getInstance(this).getServer().
                getauthenticate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<UserResponse>>bindToLifecycle()))
                .subscribe(new BaseObserver<UserResponse>(this) {
                    @Override
                    protected void onSuccees(String code, UserResponse data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {//成功
                            et_applicantName.setText(data.getPersonName());//申请人姓名
                            et_apply_crad.setText(data.getIdCode());//申请人身份证号
                            if(!TextUtils.isEmpty(data.getCompanyName())){
                                et_apply_agency.setText(data.getCompanyName());//申请人代表机构
                            }else {
                                et_apply_agency.setVisibility(View.GONE);
                            }
                            phone=data.getMobile();
                        }
                    }
                });
    }

    /**
     * 获取验证码
     * @param phone
     */
    private void getVcode(String phone){
        ValidCode validCode = new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(7);
        validCode.setPlatform(2);
        String json = Util.toJson(validCode);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer().
                getValidCode(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            ToastUtil.showShort(ApplyAuthorization.this, "验证码发送成功");
                        }
                    }
                });
    }
}
