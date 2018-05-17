package com.yl.baiduren.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.yl.baiduren.MainActivity;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Shop_Mall;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.LoginSuccess;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.request_body.ValidCodeLogin;
import com.yl.baiduren.entity.result.LoginPassword;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.Login_Activity_Password.backPage;


/**
 * Created by sunbeibei on 2017/11/30.
 */

public class Login_Activity_Yzm extends BaseActivity implements View.OnClickListener {

    private ImageView login_back;
    private EditText login_phone;
    private EditText et_code;
    private Button send;
    private Button login;
    private TextView regisier;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_login_yzm;
    }

    @Override
    public void initViews() {
        //删除基础信息
        GreenDaoUtils.getInstance(this).getBaseRequestDao().deleteAll();
        GreenDaoUtils.getInstance(this).getLoginSuccessDao().deleteAll();
        GreenDaoUtils.getInstance(this).getMyPagerDao().deleteAll();
        login_back = findViewById(R.id.login_back);//返回
        login_back.setOnClickListener(this);
        //输入手机号
        login_phone = findViewById(R.id.et_login_phone);
        //输入验证码
        et_code = findViewById(R.id.et_login_code);
        //发送验证码
        send = findViewById(R.id.send_ma);
        send.setOnClickListener(this);
        //登录
        login = findViewById(R.id.login_yzm);
        login.setOnClickListener(this);
        //注册
        regisier = findViewById(R.id.login_regiver);
        regisier.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.getInstance().setCJ(this);
    }

    private void initEdit() {
        String phone = login_phone.getText().toString();
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(this, "请输入手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone)) {
            ToastUtil.showShort(this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }

        ValidCodeLogin validCodeLogin = new ValidCodeLogin();
        validCodeLogin.setMobile(phone);
        validCodeLogin.setValidCode(code);
        validCodeLogin.setValidCodeType(3);
        validCodeLogin.setPlatform(2);
        validCodeLogin.setDeviceToken(PushAgent.getInstance(this).getRegistrationId());
        getHttpLogin(validCodeLogin);
    }

    @Override
    public void onClick(View v) {
        if (v == login_back) {
            finish();
        } else if (v == send) {//发送验证码
            String phone = login_phone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShort(this, "请输入手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)) {
                ToastUtil.showShort(this, "请输入正确的手机号");
                return;
            }
            new Thread(new CountdownTimer_utils(60, send, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(phone);

        } else if (v == login) {
            initEdit();
        } else if (v == regisier) {
            startActivity(new Intent(this, Regisier_Activity.class));
        }
    }

    private void getHttp(String phone) {
        ValidCode validCode = new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(3);
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
                            ToastUtil.showShort(Login_Activity_Yzm.this, "验证码发送成功");
                        }
                    }
                });
    }

    public void getHttpLogin(ValidCodeLogin validCodeLogin) {
        String json = Util.toJson(validCodeLogin);//转成json
        LUtils.e("--json---", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer().
                getMobileLogin(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<LoginPassword>>bindToLifecycle()))
                .subscribe(new BaseObserver<LoginPassword>(this) {
                    @Override
                    protected void onSuccees(String code, LoginPassword data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {

                            LoginSuccess success = new LoginSuccess();
                            success.setAccessToken(data.getAccessToken());
                            success.setId(data.getId());
                            success.setIsVip(data.getIsVip());
                            success.setMobile(data.getMobile());
                            success.setRoleType(data.getRoleType());
                            success.setUserName(data.getUserName());
                            success.setUserType(data.getUserType());
                            GreenDaoUtils.getInstance(Login_Activity_Yzm.this).getLoginSuccessDao().insert(success);
                            UserInfoUtils.setUuid(Login_Activity_Yzm.this, baseResponse);

                            BaseRequest request = new BaseRequest();
                            request.setAccessToken(data.getAccessToken());
                            request.setLoginUsername(data.getUserName());
                            request.setUid(Long.valueOf(data.getId()));
                            request.setUuid(baseResponse.getUuid());
                             request.setPlatform(2);
                            GreenDaoUtils.getInstance(Login_Activity_Yzm.this).getBaseRequestDao().insert(request);
                            UserInfoUtils.setBaseRequest(Login_Activity_Yzm.this);
                            startActivity(new Intent(Login_Activity_Yzm.this, MainActivity.class).putExtra("currMenu", 3));
                            if (backPage == 4) {
                                startActivity(new Intent(Login_Activity_Yzm.this, Debt_Shop_Mall.class));
                            }
                            finish();
                        }

                    }
                });
    }
}
