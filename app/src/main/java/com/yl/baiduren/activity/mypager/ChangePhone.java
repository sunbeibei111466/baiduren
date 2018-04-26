package com.yl.baiduren.activity.mypager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.Forget_Password_Activity;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.IDeleteCall;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Personalinfo;
import com.yl.baiduren.entity.request_body.UpdataPhone;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.result.MyPager;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 更换手机号
 */
public class ChangePhone extends BaseActivity implements View.OnClickListener {

    private Button bt_change_send, bt_changePhone;
    private ImageView iv_phone_title_left;
    private EditText et_change_phone_new, et_debt1_zw_name;
    private int index;
    private Long childUserId;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initViews() {
        index = getIntent().getIntExtra("index", 0);
        childUserId = getIntent().getLongExtra("childUserId", 0);
        String phone = getIntent().getStringExtra("phone");
        bt_change_send = findViewById(R.id.bt_change_send);//发送验证码
        bt_change_send.setOnClickListener(this);
        iv_phone_title_left = findViewById(R.id.iv_phone_title_left);//返回
        iv_phone_title_left.setOnClickListener(this);
        bt_changePhone = findViewById(R.id.bt_changePhone);//保存
        bt_changePhone.setOnClickListener(this);
        TextView tv_change_phone_ole = findViewById(R.id.tv_change_phone_ole);
        et_change_phone_new = findViewById(R.id.et_change_phone_new);//新手机号
        et_debt1_zw_name = findViewById(R.id.et_debt1_zw_name);//验证码
        List<MyPager> myPagerList = GreenDaoUtils.getInstance(this).getMyPagerDao().loadAll();
        if (myPagerList.size() != 0) { //自动填充手机号
            if (index == 1) {//修改手机号
                tv_change_phone_ole.setText(myPagerList.get(0).getMobile());
            } else if (index == 2) {//备注手机号
                if (!TextUtils.isEmpty(myPagerList.get(0).getStandbyMobile())) {
                    tv_change_phone_ole.setText(myPagerList.get(0).getStandbyMobile());
                }
            } else if (index == 3) {//修改子账号手机号
                tv_change_phone_ole.setText(phone);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bt_change_send) {
            String phone = et_change_phone_new.getText().toString().trim();//新手机号
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShort(this, "请输入手机号码");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }
            new Thread(new CountdownTimer_utils(60, bt_change_send, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(phone);
        } else if (v == iv_phone_title_left) {
            finish();
        } else if (v == bt_changePhone) {
            String phone = et_change_phone_new.getText().toString().trim();//新手机号
            String yzm = et_debt1_zw_name.getText().toString().trim();//验证码
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShort(this, "请输入手机号码");
                return;
            }

            if(!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号码");

                return;
            }
            if (TextUtils.isEmpty(yzm)) {
                ToastUtil.showShort(this, "请输入验证码");
                return;
            }

            UpdataPhone updataPhone = new UpdataPhone(DataWarehouse.getBaseRequest(this));
            if (index == 1) {
                updataPhone.setMobile(phone);
            } else if (index == 2) {
                updataPhone.setStandbyMobile(phone);
            } else if (index == 3) { //修改子账号手机号
                updataPhone.setMobile(phone);
                updataPhone.setChildUserId(childUserId);
            }
            updataPhone.setValidCode(yzm);
            updataPhone.setValidCodeType(2);
            updataGetNet(updataPhone);
        }
    }

    private void getHttp(String phone) {
        ValidCode validCode = new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(2);
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
                            ToastUtil.showShort(ChangePhone.this, "验证码发送成功");
                        }
                    }
                });
    }


    private void updataGetNet(UpdataPhone updataPhone) {

        String json = Util.toJson(updataPhone);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        RetrofitHelper.getInstance(this).getServer().
                updataUserPhone(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {//成功
                            finish();
                        }
                    }
                });
    }
}
