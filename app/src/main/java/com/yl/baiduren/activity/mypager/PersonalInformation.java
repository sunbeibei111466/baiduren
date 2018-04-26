package com.yl.baiduren.activity.mypager;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Personalinfo;
import com.yl.baiduren.entity.result.MyPager;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//个人资料
public class PersonalInformation extends BaseActivity implements View.OnClickListener {

    private ImageView iv_personal_title_left, iv_user_image;
    private TextView iv_personal_title_right, et_personal_address, tv_personal_phone, tv_personal_phone_by;
    private EditText ed_personal_nicheng, et_personal_name, et_personal_code, et_personal_xx_address;
    private Button bt_esc;
    private LinearLayout ll_updataPass, ll_updataPhone;
    private String xQuCode;//现地区Code
    private String yQuCode;//原地区Code
    private LinearLayout ll_updataPhone_by;
    private TextView tv_userName_preson;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_personal_information;
    }

    @Override
    public void initViews() {
        iv_personal_title_left = findViewById(R.id.iv_personal_title_left);//返回
        iv_personal_title_left.setOnClickListener(this);
        iv_personal_title_right = findViewById(R.id.iv_personal_title_right);//编辑
        iv_personal_title_right.setOnClickListener(this);
        iv_user_image = findViewById(R.id.iv_user_image);//用户头像
        iv_user_image.setOnClickListener(this);
        tv_userName_preson = findViewById(R.id.tv_userName_preson);//用户名
        ed_personal_nicheng = findViewById(R.id.ed_personal_nicheng);//昵称
        tv_personal_phone = findViewById(R.id.tv_personal_phone);//手机号
        ll_updataPhone_by = findViewById(R.id.ll_updataPhone_by);//备用手机号 父布局
        ll_updataPhone_by.setOnClickListener(this);
        tv_personal_phone_by = findViewById(R.id.tv_personal_phone_by);//备用手机号
        et_personal_name = findViewById(R.id.et_personal_name);//真实姓名
        et_personal_code = findViewById(R.id.et_personal_code);//身份证号码
        et_personal_address = findViewById(R.id.et_personal_address);//所属地
        et_personal_address.setOnClickListener(this);
        et_personal_xx_address = findViewById(R.id.et_personal_xx_address);//详细地址
        ll_updataPass = findViewById(R.id.ll_updataPass);//修改密码
        ll_updataPass.setOnClickListener(this);
        ll_updataPhone = findViewById(R.id.ll_updataPhone);//修改手机号
        ll_updataPhone.setOnClickListener(this);
        bt_esc = findViewById(R.id.bt_esc);//退出登录
        bt_esc.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsetInfo();
    }

    /**
     * 获取用户数据
     */
    private void getUsetInfo() {
        if (UserInfoUtils.IsLogin(PersonalInformation.this)) {

            List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(PersonalInformation.this).getBaseRequestDao().loadAll();
            if (baseRequestList.size() != 0) {

                final com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
                String json = Util.toJson(baseRequest);//转成json
                LUtils.e("---json----", json);
                String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
                BaseObserver baseObserver = new BaseObserver<MyPager>(PersonalInformation.this) {
                    @Override
                    protected void onSuccees(String code, MyPager data, BaseRequest baseResponse) throws Exception {
                        UserInfoUtils.setUuid(PersonalInformation.this, baseResponse);
                        initText(data);
                    }
                };
                baseObserver.setStop(true);
                RetrofitHelper.getInstance(PersonalInformation.this).getServer().
                        getUserInfo(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                        .compose(compose(this.<BaseEntity<MyPager>>bindToLifecycle()))
                        .subscribe(baseObserver);
            }
        }
    }

    /**
     * 设置数据
     * @param data
     */
    private void initText(MyPager data) {
        if (data != null) {
            if (!TextUtil.isEmpty(data.getUserName())){
                tv_userName_preson.setText(data.getUserName());
            }
            if (!TextUtils.isEmpty(data.getNickName())) {//设置昵称
                ed_personal_nicheng.setText(data.getNickName());
            }

            if (!TextUtils.isEmpty(data.getMobile())) {//手机号
                tv_personal_phone.setText(data.getMobile());
            }
            if (!TextUtils.isEmpty(data.getRealName())) {//真实姓名
                et_personal_name.setText(data.getRealName());
            }
            if (!TextUtils.isEmpty(data.getIdCard())) {//身份证号码
                et_personal_code.setText(data.getIdCard());
            }
            if (!TextUtils.isEmpty(data.getAreaStr())) {//所属地
                et_personal_address.setText(data.getAreaStr());
                xQuCode = data.getArea();
            }
            if (!TextUtils.isEmpty(data.getAddress())) {//详细地址
                et_personal_xx_address.setText(data.getAddress());
            }
            if (!TextUtils.isEmpty(data.getStandbyMobile())) {//备用手机号
                tv_personal_phone_by.setText(data.getStandbyMobile());
            }

            Glide.with(PersonalInformation.this).
                    load(data.getImage()).bitmapTransform(new CropCircleTransformation(PersonalInformation.this))
                    .placeholder(R.mipmap.my_header)
                    .error(R.mipmap.my_header)
                    .crossFade(1000)
                    .into(iv_user_image);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == iv_personal_title_left) {//返回
            finish();
        } else if (v == iv_personal_title_right) {//编辑
            updata();
        } else if (v == ll_updataPass) {//修改密码
            startActivity(new Intent(PersonalInformation.this, Change_Password.class));
        } else if (v == ll_updataPhone) {//修改手机号
            startActivity(new Intent(PersonalInformation.this, ChangePhone.class).putExtra("index", 1));
        } else if (v == bt_esc) {//退出登陆
            UserInfoUtils.deleteBaseRequest(this);
            UserInfoUtils.deleteLoginSuccess(this);
            UserInfoUtils.deleteMyPager(this);
            Util.delFolder(Constant.COMPERSS_IMAGE);
            LUtils.e("---删除基本信息--我的也数据---");
            finish();
        } else if (v == et_personal_address) {
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_personal_address.setText(string);
                    xQuCode = quId;
                }
            });
        } else if (v == ll_updataPhone_by) {
            startActivity(new Intent(PersonalInformation.this, ChangePhone.class).putExtra("index", 2));
        } else if (v == iv_user_image) {//设置用户头像
            Photo_Select.open_Photo(PersonalInformation.this, 7, call);
        }
    }

    private void updata() {

        boolean isUpdata = false;
        if (iv_personal_title_right.getText().toString().equals("编辑")) {
            iv_personal_title_right.setText("完成");
            ed_personal_nicheng.setEnabled(true);
            et_personal_name.setEnabled(true);
            //真实姓名
            et_personal_code.setEnabled(true);
            //身份证号码
            et_personal_address.setEnabled(true);
            //所属地
            et_personal_address.setClickable(true);
            et_personal_xx_address.setEnabled(true);
            ll_updataPass.setVisibility(View.GONE);
            ll_updataPhone.setVisibility(View.GONE);
            ll_updataPhone_by.setVisibility(View.GONE);
            bt_esc.setVisibility(View.GONE);//隐藏退出按钮
            isUpdata = true;
        } else {

            if (TextUtils.isEmpty(ed_personal_nicheng.getText().toString().trim())) {
                ToastUtil.showShort(this, "昵称不能为空");
                return;
            }

            iv_personal_title_right.setText("编辑");
            ed_personal_nicheng.setEnabled(false);
            et_personal_name.setEnabled(false);
            //真实姓名
            et_personal_code.setEnabled(false);
            //身份证号码
            et_personal_address.setEnabled(false);
            //所属地
            et_personal_xx_address.setEnabled(false);
            ll_updataPass.setVisibility(View.VISIBLE);
            ll_updataPhone.setVisibility(View.VISIBLE);
            bt_esc.setVisibility(View.VISIBLE);//退出按钮
            isUpdata = false;

            String nic = ed_personal_nicheng.getText().toString();
            String name = et_personal_name.getText().toString();
            //真实姓名
            String cord = et_personal_code.getText().toString();
            String ssd = et_personal_address.getText().toString();
            //所属地
            String xxdz = et_personal_xx_address.getText().toString();

            Personalinfo personalinfo = new Personalinfo(DataWarehouse.getBaseRequest(this));
            personalinfo.setNickName(nic);
            personalinfo.setRealName(name);
            personalinfo.setIdCard(cord);
            personalinfo.setAddress(xxdz);
            if (!TextUtils.isEmpty(xQuCode)) {
                personalinfo.setArea(xQuCode);//不同，修改过
            } else {
                personalinfo.setArea(null);
            }

            updataGetNet(personalinfo);
        }

    }

    private void updataGetNet(Personalinfo personalinfo) {

        String json = Util.toJson(personalinfo);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        RetrofitHelper.getInstance(this).getServer().
                updataUserInfo(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
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


    private GalleryFinal.OnHanlderResultCallback call = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

            if (resultList.size() != 0) {
                updataUserImage(ImageUtils.comperssImage(resultList));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    /**
     * 设置头像
     *
     * @param resultList
     */
    private void updataUserImage(List<File> resultList) {

        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(PersonalInformation.this, baseResponse);
                            Glide.with(PersonalInformation.this).
                                    load(data.get(0).toString()).bitmapTransform(new CropCircleTransformation(PersonalInformation.this))
                                    .crossFade(1000)
                                    .into(iv_user_image);
                            Personalinfo personalinfo = new Personalinfo(DataWarehouse.getBaseRequest(PersonalInformation.this));
                            personalinfo.setImage(data.get(0).toString());
                            updataGetNet(personalinfo);
                        }
                    }
                });
    }


}
