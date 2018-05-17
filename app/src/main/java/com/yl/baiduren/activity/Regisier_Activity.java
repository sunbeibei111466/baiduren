package com.yl.baiduren.activity;

import android.content.Intent;
import android.telephony.IccOpenLogicalChannelResponse;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.RegisierEntity;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
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

public class Regisier_Activity extends BaseActivity implements View.OnClickListener {

    private ImageView title_finish;
    private EditText user_name;
    private EditText phone;
    private EditText yzm_code;
    private Button send;
    private EditText set_password;
    private EditText comfit_password;
    private Button resiger;
    private TextView regisiter_agreement;

    @Override
    public int loadWindowLayout() {
        return R.layout.regisier_activity;
    }

    @Override
    public void initViews() {
        //返回
        title_finish = findViewById(R.id.iv_title_left);
        title_finish.setOnClickListener(this);
        //用户名
        user_name = findViewById(R.id.set_et_user);
        //手机号
        phone = findViewById(R.id.et_phone);
        //验证码
        yzm_code = findViewById(R.id.et_yzm);

        //发送验证码
        send = findViewById(R.id.send_yzm);
        send.setOnClickListener(this);
        //设置密码
        set_password = findViewById(R.id.set_password);
        //确认密码
        comfit_password = findViewById(R.id.confit_password);
        regisiter_agreement = findViewById(R.id.regisiter_agreement);//用户注册协议
        regisiter_agreement.setOnClickListener(this);
        //注册
        resiger = findViewById(R.id.regiver);
        resiger.setOnClickListener(this);

        LUtils.e("---------友盟--------" + PushAgent.getInstance(this).getRegistrationId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.getInstance().setCJ(this);
    }

    private void isEdit() {
        String name = user_name.getText().toString();
        byte[] b = name.getBytes();
        LUtils.e("字节的大小" + b.length);
        String code = yzm_code.getText().toString();
        String phone_num = phone.getText().toString();
        String getpassword = set_password.getText().toString();
        String comfit_mima = comfit_password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(this, "请输入用户名");
            return;
        }
        if (b.length > 21) {
            ToastUtil.showShort(this, "用户名应小于7个字符");
            return;
        }
        if (TextUtils.isEmpty(phone_num)) {
            ToastUtil.showShort(this, "请输入手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone_num)) {
            ToastUtil.showShort(this, "请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(getpassword)) {
            ToastUtil.showShort(this, "请设置密码");
            return;
        }
        if (TextUtils.isEmpty(comfit_mima)) {
            ToastUtil.showShort(this, "请再次确认密码");
            return;
        }
        if (!comfit_mima.equals(getpassword)) {
            ToastUtil.showShort(this, "两次输入密码不一致");
            return;
        }

        RegisierEntity regisierEntity = new RegisierEntity();
        regisierEntity.setUserName(name);
        regisierEntity.setMobile(phone_num);
        regisierEntity.setValidCode(code);
        regisierEntity.setPassword(getpassword);
        regisierEntity.setValidCodeType(1);
        regisierEntity.setPlatform(2);
        regisierEntity.setDeviceToken(PushAgent.getInstance(this).getRegistrationId());
        requestNetWork(regisierEntity);

    }

    private void requestNetWork(RegisierEntity regisierEntity) {

        String json = Util.toJson(regisierEntity);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        RetrofitHelper.getInstance(this).getServer().
                regisierPhonr(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Regisier_Activity.this, baseResponse);
                            ToastUtil.showShort(Regisier_Activity.this, "注册成功");
                            finish();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        if (v == title_finish) {
            finish();
        } else if (v == send) {
            String p = phone.getText().toString().trim();
            if (!IdCardUtil.matchPhone(p)) {
                ToastUtil.showShort(this, "请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(p)) {
                ToastUtil.showShort(this, "请输入手机号");
                return;
            }
            CountdownTimer_utils utils = new CountdownTimer_utils(60, send, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma));
            Thread thread = new Thread(utils);
            thread.start();
            getHttp(p);
        } else if (v == resiger) {

            isEdit();

        } else if (v == regisiter_agreement) {//用户注册协议
            Intent intent = new Intent();
            intent.setClass(this, Break_Debt_Activity.class);
            intent.putExtra("url", ServiceUrl.H5_REGISIER_AGREEMENT);
            intent.putExtra("type", 3);
            startActivity(intent);


        }

    }

    private void getHttp(String p) {

        ValidCode validCode = new ValidCode();
        validCode.setMobile(p);
        validCode.setValidCodeType(1);
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
                            ToastUtil.showShort(Regisier_Activity.this, "验证码发送成功");
                        }
                    }
                });
    }
}
