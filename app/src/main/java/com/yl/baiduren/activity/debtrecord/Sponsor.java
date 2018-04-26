package com.yl.baiduren.activity.debtrecord;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.SponsorDO;
import com.yl.baiduren.entity.request_body.SponsorEntity;
import com.yl.baiduren.entity.result.Sponsor_Details_Relult;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Sponsor extends BaseActivity implements View.OnClickListener {

    private EditText et_sponsor_name, et_sponsor_code, et_sponsor_phone, et_sponsor_xxdz;
    private TextView tv_sponsor_ssd;
    private Button bt_sponsor_baocun;
    private ImageView iv_debt4_sponsor_back;
    private String quCode;
    private Long updataId;
    private Sponsor_Details_Relult sponsorDetailsRelult;
    private MyGridView gv_sponsor_photo;
    private UphotoAdapter adapter;
    private List<String> mPath;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_sponsor;
    }

    @Override
    public void initViews() {
        mPath = new ArrayList<>();
        sponsorDetailsRelult = (Sponsor_Details_Relult) getIntent().getSerializableExtra("sponsorDetailsRelult");//担保人编辑数据
        updataId = getIntent().getLongExtra("updataId", 0);
        et_sponsor_name = findViewById(R.id.et_sponsor_name);//担保人真实姓名
        et_sponsor_code = findViewById(R.id.et_sponsor_code);//身份证号
        et_sponsor_phone = findViewById(R.id.et_sponsor_phone);//手机号
        tv_sponsor_ssd = findViewById(R.id.tv_sponsor_ssd);//所属地
        tv_sponsor_ssd.setOnClickListener(this);
        et_sponsor_xxdz = findViewById(R.id.et_sponsor_xxdz);
        bt_sponsor_baocun = findViewById(R.id.bt_sponsor_baocun);
        bt_sponsor_baocun.setOnClickListener(this);
        iv_debt4_sponsor_back = findViewById(R.id.iv_debt4_sponsor_back);
        iv_debt4_sponsor_back.setOnClickListener(this);
        adapter = new UphotoAdapter(this);
        gv_sponsor_photo = findViewById(R.id.gv_sponsor_photo);
        gv_sponsor_photo.setAdapter(adapter);
        gv_sponsor_photo.setOnItemClickListener(Oitemlistener);
        updataService();
        updata();
    }

    private void updataService() {
        if (sponsorDetailsRelult != null) {
            et_sponsor_name.setText(sponsorDetailsRelult.getName());//担保人真实姓名
            et_sponsor_code.setText(sponsorDetailsRelult.getIdCode());//身份证号
            et_sponsor_phone.setText(sponsorDetailsRelult.getPhoneNumber());//手机号
            tv_sponsor_ssd.setText(sponsorDetailsRelult.getAreaName());//所属地
            et_sponsor_xxdz.setText(sponsorDetailsRelult.getAddress());
            if (!TextUtils.isEmpty(sponsorDetailsRelult.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = sponsorDetailsRelult.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_sponsor_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if (updataId != 0) {
            SponsorDO sponsorDO = GreenDaoUtils.getInstance(this).getSponsorDODao().load(updataId);
            et_sponsor_name.setText(sponsorDO.getName());//担保人真实姓名
            et_sponsor_code.setText(sponsorDO.getIdCode());//身份证号
            et_sponsor_phone.setText(sponsorDO.getPhoneNumber());//手机号
            tv_sponsor_ssd.setText(sponsorDO.getDizhi());//所属地
            et_sponsor_xxdz.setText(sponsorDO.getAddress());
            if (!TextUtils.isEmpty(sponsorDO.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = sponsorDO.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_sponsor_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == iv_debt4_sponsor_back) {
            finish();
        } else if (v == tv_sponsor_ssd) {
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    tv_sponsor_ssd.setText(string);
                    quCode = quId;
                }
            });
        } else if (v == bt_sponsor_baocun) {//保存
            baocun();
        }
    }

    private void baocun() {

        String name = et_sponsor_name.getText().toString().trim();//担保人真实姓名
        String code = et_sponsor_code.getText().toString().trim();//身份证号
        String phone = et_sponsor_phone.getText().toString().trim();//手机号
        String dizhi = tv_sponsor_ssd.getText().toString().trim();//所属地
        String xxdz = et_sponsor_xxdz.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort(this,"请输入担保人的姓名");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showShort(this,"请输入担保人的身份证号");
            return;
        }
        if(!IdCardUtil.isIDCard(code)){
            ToastUtil.showShort(this,"请输入正确的身份证号");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showShort(this,"请输入手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone)){
            ToastUtil.showShort(this,"请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(dizhi)){
            ToastUtil.showShort(this,"请选择担保人所属地");
        }
        if(TextUtils.isEmpty(xxdz)){
            ToastUtil.showShort(this,"请输入担保人的详细地址");
            return;
        }


        if (updataId != 0) {
            SponsorDO sponsorDO = GreenDaoUtils.getInstance(this).getSponsorDODao().load(updataId);
            sponsorDO.setName(name);
            sponsorDO.setIdCode(code);
            if (!TextUtils.isEmpty(quCode)) {
                sponsorDO.setArea(quCode);
            } else {
                sponsorDO.setArea(sponsorDO.getArea());
            }
            sponsorDO.setDizhi(dizhi);
            sponsorDO.setAddress(xxdz);
            sponsorDO.setPhoneNumber(phone);
            sponsorDO.setImage("");
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                sponsorDO.setImage(imageUrl);
            } else { //没有传空
                sponsorDO.setImage("");
            }
            GreenDaoUtils.getInstance(this).getSponsorDODao().update(sponsorDO);
        } else if (sponsorDetailsRelult != null) {
            SponsorEntity sponsorEntity = new SponsorEntity();
            sponsorEntity.setId(sponsorDetailsRelult.getId());
            sponsorEntity.setDebtRelationId(sponsorDetailsRelult.getDebtRelationId());
            sponsorEntity.setName(name);
            sponsorEntity.setIdCode(code);
            if (!TextUtils.isEmpty(quCode)) {
                sponsorEntity.setArea(quCode);
            } else {
                sponsorEntity.setArea(sponsorDetailsRelult.getArea());
            }
            sponsorEntity.setAddress(xxdz);
            sponsorEntity.setPhoneNumber(phone);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                sponsorEntity.setImage(imageUrl);
            } else { //没有传空
                sponsorEntity.setImage("");
            }
            getHttpUpdata(sponsorEntity);
        } else {
            SponsorDO sponsorDO = new SponsorDO();
            sponsorDO.setName(name);
            sponsorDO.setIdCode(code);
            sponsorDO.setArea(quCode);
            sponsorDO.setDizhi(dizhi);
            sponsorDO.setAddress(xxdz);
            sponsorDO.setPhoneNumber(phone);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                sponsorDO.setImage(imageUrl);
            } else { //没有传空
                sponsorDO.setImage("");
            }
            GreenDaoUtils.getInstance(this).getSponsorDODao().insert(sponsorDO);
        }

        finish();
    }

    public void getHttpUpdata(SponsorEntity sponsorEntity) {
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            sponsorEntity.setAccessToken(baseRequests.get(0).getAccessToken());
            sponsorEntity.setPlatform(2);
            sponsorEntity.setUuid(baseRequests.get(0).getUuid());
            sponsorEntity.setUid(baseRequests.get(0).getUid());
            sponsorEntity.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(sponsorEntity);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getSponsorUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(Sponsor.this, baseResponse);
                                finish();
                            }
                        }
                    });
        }
    }


    private void getHttpImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(Sponsor.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(Sponsor.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(Sponsor.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(Sponsor.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_sponsor_photo.setAdapter(adapter);
                        }
                    }
                });
    }

    /**
     * 获取本地图片回掉
     */
    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList.size() != 0) {
                getHttpImage(ImageUtils.comperssImage(resultList));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }

    };


    private AdapterView.OnItemClickListener Oitemlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == parent.getCount() - 1) {
                Photo_Select.open_Photo(Sponsor.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
