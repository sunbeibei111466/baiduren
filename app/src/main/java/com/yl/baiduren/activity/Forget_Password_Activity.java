package com.yl.baiduren.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.ForgetPassword;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/1.
 */

public class Forget_Password_Activity extends BaseActivity implements View.OnClickListener {

    private EditText phone_num;
    private EditText code;
    private Button send_code;
    private EditText set_password;
    private EditText confrim;
    private Button complte;
    private ImageView iv_title_left;

    @Override
    public int loadWindowLayout() {
        return R.layout.for_password_activity;
    }

    @Override
    public void initViews() {
        iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_left.setOnClickListener(this);
        //手机号
        phone_num = findViewById(R.id.set_et_phone);
        //验证码
        code = findViewById(R.id.et_yzm2);
        //发送验证码
        send_code = findViewById(R.id.send_yzm2);
        send_code.setOnClickListener(this);
        //新密码
        set_password = findViewById(R.id.set_new_password);
        //确认新密码
        confrim = findViewById(R.id.confit__new_password);
        //完成
        complte = findViewById(R.id.complete);
        complte.setOnClickListener(this);

    }

    private void initEdit() {
        String phone = phone_num.getText().toString();
        String yzm = code.getText().toString();
        String new_password = set_password.getText().toString();
        String con_password = confrim.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(this, "请输入手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone)){
            ToastUtil.showShort(this,"请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(yzm)) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(new_password)) {
            ToastUtil.showShort(this, "请输入新的密码");
            return;
        }
        if (TextUtils.isEmpty(con_password)) {
            ToastUtil.showShort(this, "请再次输入密码");
            return;
        }
        if (!con_password.equals(new_password)) {
            ToastUtil.showShort(this, "两次输入密码不一致");
            return;
        }

        ForgetPassword forgetPassword=new ForgetPassword();
        forgetPassword.setMobile(phone);
        forgetPassword.setValidCode(yzm);
        forgetPassword.setPassword(new_password);
        forgetPassword.setValidCodeType(4);
        forgetPassword.setPlatform(2);
        getPassword(forgetPassword);

    }



    @Override
    public void onClick(View v) {
        if (v == send_code) {//发送验证码
            String phone = phone_num.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShort(this, "请输入手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }

            new Thread(new CountdownTimer_utils(60, send_code, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(phone);

        } else if (v == complte) {//完成
            initEdit();

        } else if (v == iv_title_left) {
            finish();
        }
    }

    private void getHttp(String phone) {
        ValidCode validCode=new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(4);
        validCode.setPlatform(2);
        String json = Util.toJson(validCode);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer().
                getValidCode(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if(code.equals("1")){
                            ToastUtil.showShort(Forget_Password_Activity.this,"验证码发送成功");
                        }
                    }
                });
    }

    private void getPassword(ForgetPassword forgetPassword) {

        String json = Util.toJson(forgetPassword);//转成json
        LUtils.e("--JSON----",json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer().
                getForgetPassword(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if(code.equals("1")){
                            UserInfoUtils.setUuid(Forget_Password_Activity.this,baseResponse);
                            ToastUtil.showShort(Forget_Password_Activity.this,"修改密码成功");
                            finish();
                        }
                    }
                });
    }
}
