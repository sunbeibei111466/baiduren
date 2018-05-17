package com.yl.baiduren.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.yl.baiduren.entity.request_body.Login_PasswordEntity;
import com.yl.baiduren.entity.result.LoginPassword;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
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

public class Login_Activity_Password extends BaseActivity implements View.OnClickListener {

    private ImageView back;
    private EditText user_name;
    private EditText password;
    private ImageView is_look;
    private TextView login_code;
    private TextView forget_password;
    private Button login;
    private TextView regiser;
    private boolean islook = true;
    public static int backPage = 0;//返回标识
    private BaseRequest baseRequest;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_login_password;
    }

    @Override
    public void initViews() {
        UserInfoUtils.deleteBaseRequest(this);
        UserInfoUtils.deleteLoginSuccess(this);
        UserInfoUtils.deleteMyPager(this);
        UserInfoUtils.deleteCategory(this);
        //当backPage ==1 时，点返回回到
        backPage = getIntent().getIntExtra("backPage", 0);
        //返回
        back = findViewById(R.id.login_back_password);
        back.setOnClickListener(this);
        //用户名
        user_name = findViewById(R.id.et_user_name);
        //密码
        password = findViewById(R.id.et_user_password);
        //是否可见密码
        is_look = findViewById(R.id.is_look);
        is_look.setOnClickListener(this);
        //手机验证码登录
        login_code = findViewById(R.id.login_phone_yzm);
        login_code.setOnClickListener(this);
        //忘记密码
        forget_password = findViewById(R.id.forget_password2);
        forget_password.setOnClickListener(this);
        //登录
        login = findViewById(R.id.login_password2);
        login.setOnClickListener(this);
        //注册
        regiser = findViewById(R.id.login_regiver2);
        regiser.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.getInstance().setCJ(this);
    }

    private void initEdit() {
        String name = user_name.getText().toString().trim();
        String pd = password.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(this, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pd)) {
            ToastUtil.showShort(this, "请输入密码");
            return;
        }

        Login_PasswordEntity loginPasswordEntity = new Login_PasswordEntity();
        loginPasswordEntity.setLoginName(name);
        loginPasswordEntity.setPassword(pd);
        loginPasswordEntity.setPlatform(2);
        loginPasswordEntity.setLoginUsername(name);
        loginPasswordEntity.setDeviceToken(PushAgent.getInstance(this).getRegistrationId());
        requestNetWork(loginPasswordEntity);
    }


    private void requestNetWork(Login_PasswordEntity loginPasswordEntity) {

        String json = Util.toJson(loginPasswordEntity);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer().
                loginPassword(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<LoginPassword>>bindToLifecycle()))
                .subscribe(new BaseObserver<LoginPassword>(this) {
                    @Override
                    protected void onSuccees(String code, LoginPassword data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {//成功
                            LUtils.e("---用户token--", data.getAccessToken());
                            //存储用户登陆成功信息
                            LoginSuccess success = new LoginSuccess();
                            success.setAccessToken(data.getAccessToken());
                            success.setId(data.getId());
                            success.setIsVip(data.getIsVip());
                            success.setMobile(data.getMobile());
                            success.setRoleType(data.getRoleType());
                            success.setUserName(data.getUserName());
                            success.setUserType(data.getUserType());
                            GreenDaoUtils.getInstance(Login_Activity_Password.this).getLoginSuccessDao().insert(success);

                            //存储用户基本参数

                            BaseRequest request = new BaseRequest();
                            request.setAccessToken(data.getAccessToken());
                            request.setLoginUsername(data.getUserName());
                            request.setUid(Long.valueOf(data.getId()));
                            request.setUuid(baseResponse.getUuid());
                            request.setPlatform(2);
                            request.setTimestamp(baseResponse.getTimestamp());
                            GreenDaoUtils.getInstance(Login_Activity_Password.this).getBaseRequestDao().insert(request);

                            UserInfoUtils.setBaseRequest(Login_Activity_Password.this);
                            if (backPage == 4) {
                                startActivity(new Intent(Login_Activity_Password.this, Debt_Shop_Mall.class));
                            }
                            finish();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v == back) {
            if (backPage == 3) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (backPage == 4) {
                startActivity(new Intent(Login_Activity_Password.this, Debt_Shop_Mall.class));
                finish();
            } else {
                finish();
            }

        } else if (v == is_look) {

            if (islook) {
                is_look.setImageResource(R.mipmap.no_look);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                islook = false;
            } else {
                is_look.setImageResource(R.mipmap.look);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                islook = true;
            }
        } else if (v == login_code) {
            startActivity(new Intent(this, Login_Activity_Yzm.class));
        } else if (v == forget_password) {
            startActivity(new Intent(this, Forget_Password_Activity.class));
        } else if (v == login) {
            initEdit();
        } else if (v == regiser) {
            startActivity(new Intent(this, Regisier_Activity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (backPage == 3) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (backPage == 4) {
            startActivity(new Intent(Login_Activity_Password.this, Debt_Shop_Mall.class));
            finish();
        } else {
            finish();
        }
    }
}
