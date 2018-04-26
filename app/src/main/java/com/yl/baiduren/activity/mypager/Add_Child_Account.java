package com.yl.baiduren.activity.mypager;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.Login_Activity_Yzm;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.request_body.AddChildModel;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.result.LoginPassword;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/19.
 */

public class Add_Child_Account extends BaseActivity implements View.OnClickListener{

    private EditText child_natural_name,add_child_phone,add_child_password,child_conmit_password
            ,add_child_name,add_child_code,et_natural_adress,et_child_text;
    private TextView add_child_area;
    private String quCode;
    private Button child_regiver,child_send_yzm;
    private ImageView add_child_back;

    @Override
    public int loadWindowLayout() {
        return R.layout.add_child_account;
    }

    @Override
    public void initViews() {
        add_child_back = findViewById(R.id.add_child_back);//关闭页面
        add_child_back.setOnClickListener(this);
        child_natural_name=findViewById(R.id.child_natural_name);//用户名
        add_child_phone=findViewById(R.id.add_child_phone);//手机号
        et_child_text=findViewById(R.id.et_child_text);//验证码
        add_child_password=findViewById(R.id.add_child_password);//密码
        child_conmit_password=findViewById(R.id.child_conmit_password);//确认密码
        add_child_name=findViewById(R.id.add_child_name);//真实姓名
        add_child_code=findViewById(R.id.add_child_code);//身份证号
        add_child_area=findViewById(R.id.add_child_area);//所属地
        add_child_area.setOnClickListener(this);
        et_natural_adress=findViewById(R.id.et_child_adress);//详细地址
        child_regiver=findViewById(R.id.child_regiver);//注册
        child_regiver.setOnClickListener(this);
        child_send_yzm=findViewById(R.id.child_send_yzm);//验证码按钮
        child_send_yzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==add_child_area){
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    add_child_area.setText(string);
                    quCode=quId;
                }
            });
        }else if (v==add_child_back){
            finish();
        } else if(v==child_send_yzm){//发送验证码
            String phone=add_child_phone.getText().toString().trim();//手机号
            if (TextUtil.isEmpty(phone)){
                ToastUtil.showShort(this,"请输入手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }
            new Thread(new CountdownTimer_utils(60, child_send_yzm, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(phone);

        } else if(v==child_regiver){//子账号注册
            String yhm=child_natural_name.getText().toString().trim();//用户名
            String phone=add_child_phone.getText().toString().trim();//手机号
            String yzm=et_child_text.getText().toString().trim();//验证码
            String password=add_child_password.getText().toString().trim();//密码
            String conmit_mima=child_conmit_password.getText().toString().trim();//确认密码
            String name=add_child_name.getText().toString().trim();//真实姓名
            String cord=add_child_code.getText().toString().trim();//身份证号
            String ssd=add_child_area.getText().toString().trim();//所属地
            String xxdz=et_natural_adress.getText().toString().trim();//详细地址
            if (TextUtil.isEmpty(yhm)){
                ToastUtil.showShort(this,"请输入用户名");
                return;
            }
            if (TextUtil.isEmpty(phone)){
                ToastUtil.showShort(this,"请输入手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }
            if (TextUtil.isEmpty(yzm)){
                ToastUtil.showShort(this,"请输入验证码");
                return;
            }
            if (TextUtil.isEmpty(password)){
                ToastUtil.showShort(this,"请设置密码");
                return;
            }
            if (TextUtil.isEmpty(conmit_mima)){
                ToastUtil.showShort(this,"请再次确认密码");
                return;
            }

            if(!password.equals(conmit_mima)){
                ToastUtil.showShort(this,"请确认密码一致");
                return;
            }
            if (TextUtil.isEmpty(name)){
                ToastUtil.showShort(this,"请输入真实姓名");
                return;
            }
            if (TextUtil.isEmpty(cord)){
                ToastUtil.showShort(this,"请输入身份证号");
                return;
            }
            if (!IdCardUtil.isIDCard(cord)){
                ToastUtil.showShort(this,"请输入正确的身份证号");
                return;
            }
            if (TextUtil.isEmpty(ssd)){
                ToastUtil.showShort(this,"请选择所属地");
                return;
            }
            if (TextUtil.isEmpty(xxdz)) {
                ToastUtil.showShort(this, "请输入详细地址");
                return;
            }
            List<BaseRequest> baseRequestList=GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
            AddChildModel addChildModel=new AddChildModel();
            addChildModel.setUserName(yhm);
            addChildModel.setMobile(phone);
            addChildModel.setValidCode(yzm);
            addChildModel.setValidCodeType(5);
            addChildModel.setPassword(password);
            addChildModel.setRealName(name);
            addChildModel.setIdCard(cord);
            addChildModel.setArea(quCode);
            addChildModel.setAddress(xxdz);
            addChildModel.setAccessToken(baseRequestList.get(0).getAccessToken());
            addChildModel.setLoginUsername(baseRequestList.get(0).getLoginUsername());
            addChildModel.setPlatform(2);
            addChildModel.setUid(baseRequestList.get(0).getUid());
            addChildModel.setUuid(baseRequestList.get(0).getUuid());
            addChild(addChildModel);
        }
    }

    private void getHttp(String phone) {
        ValidCode validCode = new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(5);
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
                            ToastUtil.showShort(Add_Child_Account.this, "验证码发送成功");
                        }
                    }
                });
    }

    private void addChild(AddChildModel addChildModel) {

        String json = Util.toJson(addChildModel);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        RetrofitHelper.getInstance(this).getServer().
                addChild(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
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
