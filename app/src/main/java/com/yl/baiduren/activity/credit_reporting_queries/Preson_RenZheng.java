package com.yl.baiduren.activity.credit_reporting_queries;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Sussful_Exmple;
import com.yl.baiduren.activity.mypager.ChangePhone;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AuthenticateParam;
import com.yl.baiduren.entity.request_body.MyDeliing_Body;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.CountdownTimer_utils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/3/27.
 */

public class Preson_RenZheng extends BaseActivity implements View.OnClickListener{

    private EditText rezheng_name;
    private EditText rz_code;
    private EditText rz_phone;
    private EditText yz_code;
    private Button rz_send;
    private TextView rz_area;
    private EditText rz_adress;
    private GridView grid_photo;
    private Button rz_confirm;
    private UphotoAdapter adapter;
    private List<String> mlist = null;
    private ImageView iCode_positive;
    private ImageView iCode_reverse;
    private boolean which;
    private ImageView zhengxin_finish;
    private String name;
    private String iCard;
    private String phone_num;
    private String code;
    private String adress;
    private String qUarea;
    private ArrayList<String>imagPath=new ArrayList<>();

    @Override
    public int loadWindowLayout() {
        return R.layout.preson_renzheng;
    }

    @Override
    public void initViews() {
        zhengxin_finish = findViewById(R.id.zhengxin_finish);
        zhengxin_finish.setOnClickListener(this);
        rezheng_name = findViewById(R.id.rezheng_name);//认证姓名
        rz_code = findViewById(R.id.rz_code);//认证身份证号
        rz_phone = findViewById(R.id.rz_phone);//认证手机号
        yz_code = findViewById(R.id.yz_code);//验证码
        rz_send = findViewById(R.id.rz_send);//发送验证码
        rz_send.setOnClickListener(this);
        rz_area = findViewById(R.id.rz_area);//认证人所属地
        rz_area.setOnClickListener(this);
        rz_adress = findViewById(R.id.rz_adress);//详细地址
        rz_confirm = findViewById(R.id.rz_confirm);//认证提交
        rz_confirm.setOnClickListener(this);
        iCode_positive = findViewById(R.id.iCode_positive);//身份证正面
        iCode_positive.setOnClickListener(this);
        iCode_reverse = findViewById(R.id.iCode_reverse);//身份证反面
        iCode_reverse.setOnClickListener(this);




    }
    private void submit(){
        name = rezheng_name.getText().toString().trim();
        iCard = rz_code.getText().toString().trim();
        phone_num = rz_phone.getText().toString().trim();
        code = yz_code.getText().toString().trim();
        String area = rz_area.getText().toString().trim();
        adress = rz_adress.getText().toString().trim();
        if (name.isEmpty()){
            ToastUtil.showShort(this,"请输入您的真实姓名");
            return;
        }
        if (iCard.isEmpty()){
            ToastUtil.showShort(this,"请输入您的身份证号");
            return;
        }
        if (!IdCardUtil.isIDCard(iCard)) {
            ToastUtil.showShort(this, "请输入正确的身份证号 ");
            return;
        }
        if (TextUtils.isEmpty(phone_num)) {
            ToastUtil.showShort(this, "请输入您的的手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone_num)) {
            ToastUtil.showShort(this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(this, "请输入您的验证码");
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtil.showShort(this, "请选择债事人的归属地");
            return;
        }
        if (TextUtils.isEmpty(adress)){
            ToastUtil.showShort(this, "请输入您的详细地址");
            return;
        }
        if (imagPath.size()==0||imagPath.size()<2){
            ToastUtil.showShort(this, "请上传您的身份证正反照");
            return;

        }
        requestWork();


    }
    private void getHttp(String phone) {
        ValidCode validCode = new ValidCode();
        validCode.setMobile(phone);
        validCode.setValidCodeType(6);
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
                            ToastUtil.showShort(Preson_RenZheng.this, "验证码发送成功");
                        }
                    }
                });
    }
    private void requestWork() {
        if(UserInfoUtils.IsLogin(this)){
            AuthenticateParam entity = new AuthenticateParam(DataWarehouse.getBaseRequest(this));
            entity.setType(1);
            entity.setValiCode(code);
            entity.setValidCodeType(6);
            entity.setPersonName(name);
            entity.setPersonIdCode(iCard);
            entity.setPhoneNumber(phone_num);
            entity.setPersonArea(qUarea);
            entity.setPersonAddress(adress);
            entity.setImage(Util.listToString(imagPath));
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<String> observer = new BaseObserver<String >(this) {
                @Override
                protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(Preson_RenZheng.this,baseResponse);
                        ToastUtil.showShort(Preson_RenZheng.this,"提交成功");
                        finish();



                    }
                }
            };
            observer.setStop(false);
            RetrofitHelper.getInstance(this).getServer()
                    .certification(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(observer);

        }else {
            finish();
        }
    }
    /**
     * 上传图片，获取网络地址
     *
     * @param resultList
     */
    private void loadImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        RetrofitHelper.getInstance(this).getServer()
                .getImage("ssss", UserInfoUtils.getUid(this), partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(Preson_RenZheng.this, baseResponse);
                            if (which){
                                imagPath.add(data.get(0));
                                iCode_positive.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(Preson_RenZheng.this).load(data.get(0)).into(iCode_positive);
                            }else{
                                imagPath.add(data.get(0));
                                iCode_reverse.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(Preson_RenZheng.this).load(data.get(0)).into(iCode_reverse);
                            }

                        }
                    }
                });
    }
    /**
     * 图片选择回掉
     */
    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null && resultList.size() != 0) {
                loadImage(ImageUtils.comperssImage(resultList));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    public void onClick(View v) {
        if (v==rz_send){
            String phone = rz_phone.getText().toString().trim();//新手机号
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShort(this, "请输入手机号码");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }
            new Thread(new CountdownTimer_utils(60, rz_send, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(phone);
        }else if(v==rz_area){//认证人归属地
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    rz_area.setText(string);
                    qUarea=quId;


                }
            });
        }else if(v==rz_confirm) {//提交
            submit();

        }else if(v==iCode_positive){
            which=true;
            Photo_Select.open_Photo(Preson_RenZheng.this, 7, callback);
        }else if (v==iCode_reverse){
            which=false;
            Photo_Select.open_Photo(Preson_RenZheng.this, 7, callback);
        }else if (v==zhengxin_finish){
            finish();
        }
    }
}
