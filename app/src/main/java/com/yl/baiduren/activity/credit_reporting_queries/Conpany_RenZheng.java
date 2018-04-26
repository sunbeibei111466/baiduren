package com.yl.baiduren.activity.credit_reporting_queries;

import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AuthenticateParam;
import com.yl.baiduren.entity.request_body.ValidCode;
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

public class Conpany_RenZheng extends BaseActivity implements View.OnClickListener {

    private EditText re_qy_name;
    private EditText rz_qy_area;
    private EditText rz_qy_code;
    private EditText rz_qy_fr;
    private EditText rz_qy_fr_icard;
    private EditText qy_jbr_name;
    private EditText rz_qy_jbr_icde;
    private EditText rz_qy_jbr_phone;
    private EditText yz_qy_code;
    private Button rz_qy_send;
    private GridView qy_grid_photo;
    private Button rz_qy_confirm;
    private UphotoAdapter adapter;
    private List<String> mlist = null;
    private ImageView business_license;
    private ImageView zheng_jian;
    private ImageView code_photos;
    private int type;
    private ImageView zhengxin_finish;
    private TextView rz_qy_jbr_area;
    private EditText rz_qy_jbr_adress;
    private ArrayList<String> imagPath = new ArrayList<>();
    private String qy_name;
    private String qy_area;
    private String qy_code;
    private String qy_fr_name;
    private String qy_fr_icard;
    private String jbr_name;
    private String jbr_icrd;
    private String jbr_phone;
    private String jbr_yzm;
    private String trim;
    private String jbr_adress;
    private String qu_area;

    @Override
    public int loadWindowLayout() {
        return R.layout.company_renzheng;
    }

    @Override
    public void initViews() {
        zhengxin_finish = findViewById(R.id.zhengxin_finish);
        zhengxin_finish.setOnClickListener(this);
        re_qy_name = findViewById(R.id.re_qy_name);//企业名称
        rz_qy_area = findViewById(R.id.rz_qy_area);//企业住所
        rz_qy_code = findViewById(R.id.rz_qy_code);//社会统一代码
        rz_qy_fr = findViewById(R.id.rz_qy_fr);//法人代表
        rz_qy_fr_icard = findViewById(R.id.rz_qy_fr_icard);//法人身份证号
        qy_jbr_name = findViewById(R.id.qy_jbr_name);//经办人姓名
        rz_qy_jbr_icde = findViewById(R.id.rz_qy_jbr_icde);//经办人身份证
        rz_qy_jbr_phone = findViewById(R.id.rz_qy_jbr_phone);//经办人手机号
        yz_qy_code = findViewById(R.id.yz_qy_code);//验证码
        rz_qy_send = findViewById(R.id.rz_qy_send);//发送验证码
        rz_qy_send.setOnClickListener(this);
        business_license = findViewById(R.id.business_license);//营业执照
        business_license.setOnClickListener(this);
        zheng_jian = findViewById(R.id.zheng_jian);
        zheng_jian.setOnClickListener(this);
        code_photos = findViewById(R.id.code_photos);
        code_photos.setOnClickListener(this);
        rz_qy_confirm = findViewById(R.id.rz_qy_confirm);//提交
        rz_qy_confirm.setOnClickListener(this);
        rz_qy_jbr_area = findViewById(R.id.rz_qy_jbr_area);//经办人的所属地
        rz_qy_jbr_area.setOnClickListener(this);
        rz_qy_jbr_adress = findViewById(R.id.rz_qy_jbr_adress);//经办人的详细地址


    }

    private void submit() {
        qy_name = re_qy_name.getText().toString().trim();
        qy_area = rz_qy_area.getText().toString().trim();
        qy_code = rz_qy_code.getText().toString().trim();
        qy_fr_name = rz_qy_fr.getText().toString().trim();
        qy_fr_icard = rz_qy_fr_icard.getText().toString().trim();
        jbr_name = qy_jbr_name.getText().toString().trim();
        jbr_icrd = rz_qy_jbr_icde.getText().toString().trim();
        jbr_phone = rz_qy_jbr_phone.getText().toString().trim();
        jbr_yzm = yz_qy_code.getText().toString().trim();
        String jbr_area = rz_qy_jbr_area.getText().toString().trim();
        jbr_adress = rz_qy_jbr_adress.getText().toString().trim();


        if (TextUtils.isEmpty(qy_name)) {
            ToastUtil.showShort(this, "请输入企业名称");
            return;
        }
        if (TextUtils.isEmpty(qy_area)) {
            ToastUtil.showShort(this, "请输入企业地址");
            return;
        }

        if (TextUtils.isEmpty(qy_code)) {
            ToastUtil.showShort(this, "请输入企业信用代码");
            return;
        }
        if (TextUtils.isEmpty(qy_fr_name)) {
            ToastUtil.showShort(this, "请输入企业法人姓名");
            return;
        }
        if (TextUtils.isEmpty(qy_fr_icard)) {
            ToastUtil.showShort(this, "请输入企业法人身份证号");
            return;
        }

        if (!IdCardUtil.isIDCard(qy_fr_icard)) {
            ToastUtil.showShort(this, "请输入正确的企业法人身份证号 ");
            return;
        }
        if (TextUtils.isEmpty(jbr_name)) {
            ToastUtil.showShort(this, "请输入经办人的姓名");
            return;
        }
        if (TextUtils.isEmpty(jbr_icrd)) {
            ToastUtil.showShort(this, "请输入经办人的身份证号");
            return;
        }
        if (!IdCardUtil.isIDCard(jbr_icrd)) {
            ToastUtil.showShort(this, "请输入正确的经办人身份证号 ");
            return;
        }
        if (TextUtils.isEmpty(jbr_phone)) {
            ToastUtil.showShort(this, "请输入经办人手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(jbr_phone)) {
            ToastUtil.showShort(this, "请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(jbr_yzm)) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(jbr_area)) {
            ToastUtil.showShort(this, "请选择经办人的归属地");
            return;
        }

        if (TextUtils.isEmpty(jbr_adress)) {
            ToastUtil.showShort(this, "请输入经办人的详细地址");
            return;
        }
        if (imagPath.size() == 0 || imagPath.size() < 3) {
            ToastUtil.showShort(this, "请上传企业资料照片");
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
                            ToastUtil.showShort(Conpany_RenZheng.this, "验证码发送成功");
                        }
                    }
                });
    }

    private void requestWork() {
        if (UserInfoUtils.IsLogin(this)) {
            AuthenticateParam entity = new AuthenticateParam(DataWarehouse.getBaseRequest(this));
            entity.setType(2);
            entity.setCompanyName(qy_name);
            entity.setAddress(qy_area);
            entity.setCompanyCode(qy_code);
            entity.setLegalName(qy_fr_name);
            entity.setLegalIdCode(qy_fr_icard);
            entity.setValidCodeType(6);
            entity.setPersonName(jbr_name);
            entity.setPersonIdCode(jbr_icrd);
            entity.setPhoneNumber(jbr_phone);
            entity.setValiCode(jbr_yzm);
            entity.setPersonArea(qu_area);
            entity.setPersonAddress(jbr_adress);
            entity.setImage(Util.listToString(imagPath));
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<String> observer = new BaseObserver<String>(this) {
                @Override
                protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(Conpany_RenZheng.this, baseResponse);
                        ToastUtil.showShort(Conpany_RenZheng.this, "提交成功");
                        finish();


                    }
                }
            };
            observer.setStop(false);
            RetrofitHelper.getInstance(this).getServer()
                    .certification(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(observer);

        } else {
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
                            UserInfoUtils.setUuid(Conpany_RenZheng.this, baseResponse);

                            if (type == 1) {
                                imagPath.add(data.get(0));
                                business_license.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(Conpany_RenZheng.this).load(data.get(0)).into(business_license);
                            } else if (type == 2) {
                                imagPath.add(data.get(0));
                                zheng_jian.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(Conpany_RenZheng.this).load(data.get(0)).into(zheng_jian);
                            } else if (type == 3) {
                                imagPath.add(data.get(0));
                                code_photos.setScaleType(ImageView.ScaleType.FIT_XY);
                                Glide.with(Conpany_RenZheng.this).load(data.get(0)).into(code_photos);
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

    private void call_Phonto() {
        Photo_Select.open_Photo(Conpany_RenZheng.this, 7, callback);
    }

    @Override
    public void onClick(View v) {
        if (v == rz_qy_send) {
           String  jbr_phone = rz_qy_jbr_phone.getText().toString().trim();
            if (TextUtils.isEmpty(jbr_phone)) {
                ToastUtil.showShort(this, "请输入经办人手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(jbr_phone)) {
                ToastUtil.showShort(this, "请输入正确的手机号");
                return;
            }
            new Thread(new CountdownTimer_utils(60, rz_qy_send, getResources().getDrawable(R.drawable.send_gray), getResources().getDrawable(R.drawable.send_ma))).start();
            getHttp(jbr_phone);
        } else if (v == rz_qy_confirm) {//提交
            submit();

        } else if (v == business_license) {
            type = 1;
            call_Phonto();
        } else if (v == zheng_jian) {
            type = 2;
            call_Phonto();
        } else if (v == code_photos) {
            type = 3;
            call_Phonto();

        } else if (v == zhengxin_finish) {
            finish();
        } else if (v == rz_qy_jbr_area) {
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    rz_qy_jbr_area.setText(string);
                    qu_area = quId;

                }
            });
        }

    }
}
