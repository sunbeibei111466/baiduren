package com.yl.baiduren.activity.mypager;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.UpdataPassword;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/21.
 */

public class Change_Password extends BaseActivity {
    private Button complete_passwrod;
    private EditText set_old_password, et_new_password, comfre_password;
    private int myChild;//不为0时修改子账号密码
    private Long childUserId = 0l;
    private ImageView iv_title_left;

    @Override
    public int loadWindowLayout() {
        return R.layout.change_password;
    }

    @Override
    public void initViews() {
        myChild = getIntent().getIntExtra("myChild", 0);
        childUserId = getIntent().getLongExtra("childUserId", 0);
        complete_passwrod = findViewById(R.id.complete_passwrod);
        iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_left.setOnClickListener(onClickListener);
        complete_passwrod.setOnClickListener(onClickListener);
        LinearLayout user_mima_ll = findViewById(R.id.user_mima_ll);
        set_old_password = findViewById(R.id.set_old_password);//旧密码
        et_new_password = findViewById(R.id.et_new_password);//新密码
        comfre_password = findViewById(R.id.comfre_password);//确认密码
        if (myChild != 0) {//修改子账号密码时 隐藏
            user_mima_ll.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == complete_passwrod) {
                String newPass = et_new_password.getText().toString().trim();
                String comfrePass = comfre_password.getText().toString().trim();
                String oldPass = "";
                if (myChild == 0) {
                    oldPass = set_old_password.getText().toString().trim();
                    if (TextUtils.isEmpty(oldPass)) {
                        ToastUtil.showShort(Change_Password.this, "请输入旧密码");
                        return;
                    }
                }

                if (TextUtils.isEmpty(newPass)) {
                    ToastUtil.showShort(Change_Password.this, "请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(comfrePass)) {
                    ToastUtil.showShort(Change_Password.this, "请输入确认密码");
                    return;
                }

                if (!newPass.equals(comfrePass)) {
                    ToastUtil.showShort(Change_Password.this, "俩次新密码请一致");
                    return;
                }
                UpdataPassword updataPassword = new UpdataPassword(DataWarehouse.getBaseRequest(Change_Password.this));
                if (myChild == 0) { //修改主账户
                    updataPassword.setOldPassword(oldPass);
                } else {//修改子账户 密码时set 子账户id
                    if (childUserId != 0) {
                        updataPassword.setChildUserId(childUserId);
                    }
                }
                updataPassword.setPassword(newPass);
                getNetWork(updataPassword);
            } else if (v == iv_title_left) {
                finish();
            }


        }
    };

    private void getNetWork(UpdataPassword updataPassword) {

        String json = Util.toJson(updataPassword);//转成json
        LUtils.e("---json----", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(Change_Password.this).getServer().
                updataUserPassword(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(Change_Password.this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            ToastUtil.showShort(Change_Password.this, "修改密码成功");
                            finish();

                        }

                    }
                });

    }
}
