package com.yl.baiduren.activity.debtrecord;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.MortgageDO;
import com.yl.baiduren.entity.request_body.AssetrMortgageEntity;
import com.yl.baiduren.entity.result.Assert_Mortgae_Details_Relut;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
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

//资产抵押
public class AssetrMortgage extends BaseActivity implements View.OnClickListener{

    private EditText et_assetr_name, et_assetr_num, et_asster_jiazhi, et_assert_jigou;
    private Button bt_debt4_asstre;
    private Long updataId;
    private Assert_Mortgae_Details_Relut assertMortgaeDetailsRelut = null;
    private MyGridView gv_assic_mortgage_photo;
    private UphotoAdapter uphotoAdapter;
    private List<String> mPath;
    private ImageView iv_debt4_currency_back;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_assetr_mortgage;
    }

    @Override
    public void initViews() {
        assertMortgaeDetailsRelut = (Assert_Mortgae_Details_Relut) getIntent().getSerializableExtra("assertMortgaeDetailsRelut");
        updataId = getIntent().getLongExtra("updataId", 0);
        mPath = new ArrayList<>();
        uphotoAdapter = new UphotoAdapter(this);
        iv_debt4_currency_back = findViewById(R.id.iv_debt4_currency_back);
        iv_debt4_currency_back.setOnClickListener(this);
        gv_assic_mortgage_photo = findViewById(R.id.gv_assic_mortgage_photo);
        gv_assic_mortgage_photo.setAdapter(uphotoAdapter);
        gv_assic_mortgage_photo.setOnItemClickListener(Oitemlistener);
        et_assetr_name = findViewById(R.id.et_assetr_name);//资产名称
        et_assetr_num = findViewById(R.id.et_assetr_num);//数量
        et_asster_jiazhi = findViewById(R.id.et_asster_jiazhi);//估值
        et_assert_jigou = findViewById(R.id.et_assert_jigou);//机构
        bt_debt4_asstre = findViewById(R.id.bt_debt4_asstre);
        bt_debt4_asstre.setOnClickListener(this);


        updataSrevice();
        updata();
    }

    private void updataSrevice() {//编辑资产抵押
        if (assertMortgaeDetailsRelut != null) {
            et_assetr_name.setText(assertMortgaeDetailsRelut.getName());//资产名称
            et_assetr_num.setText(assertMortgaeDetailsRelut.getNum() + "");//数量
            et_asster_jiazhi.setText(assertMortgaeDetailsRelut.getAmountStr());//估值
            et_assert_jigou.setText(assertMortgaeDetailsRelut.getEvaluation());//机构
            if (!TextUtils.isEmpty(assertMortgaeDetailsRelut.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = assertMortgaeDetailsRelut.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                uphotoAdapter.setPath(mPath);
                gv_assic_mortgage_photo.setAdapter(uphotoAdapter);
                uphotoAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if (updataId != 0) {
            MortgageDO mortgageDO = GreenDaoUtils.getInstance(this).getMortgageDODao().load(updataId);
            et_assetr_name.setText(mortgageDO.getName());//资产名称
            et_assetr_num.setText(mortgageDO.getNum() + "");//数量
            et_asster_jiazhi.setText(mortgageDO.getAmountStr());//估值
            et_assert_jigou.setText(mortgageDO.getEvaluation());//机构
            if (!TextUtils.isEmpty(mortgageDO.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = mortgageDO.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                uphotoAdapter.setPath(mPath);
                gv_assic_mortgage_photo.setAdapter(uphotoAdapter);
                uphotoAdapter.notifyDataSetChanged();
            }
        }
    }

    private void baocun() {
        String name = et_assetr_name.getText().toString().trim();//资产名称
        String num = et_assetr_num.getText().toString().trim();//数量
        String jiazhi = et_asster_jiazhi.getText().toString().trim();//估值
        String jigou = et_assert_jigou.getText().toString().trim();//机构
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort(this,"请输入资产名称");
            return;
        }
        if (TextUtils.isEmpty(num)){
            ToastUtil.showShort(this,"请输入资产数量");
            return;
        }
        if (TextUtils.isEmpty(jiazhi)){
            ToastUtil.showShort(this,"请输入评估价值");
            return;
        }
        if (TextUtils.isEmpty(jigou)){
            ToastUtil.showShort(this,"请输入评估机构");
            return;
        }

        if (updataId != 0) { //本地编辑
            MortgageDO mortgageDO = GreenDaoUtils.getInstance(this).getMortgageDODao().load(updataId);
            mortgageDO.setName(name);
            mortgageDO.setNum(Integer.valueOf(num));
            mortgageDO.setAmountStr(jiazhi);
            mortgageDO.setEvaluation(jigou);
            if (uphotoAdapter.getPath() != null && uphotoAdapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(uphotoAdapter.getPath());
                mortgageDO.setImage(imageUrl);
            } else { //没有传空
                mortgageDO.setImage("");
            }
            GreenDaoUtils.getInstance(this).getMortgageDODao().update(mortgageDO);
        } else if (assertMortgaeDetailsRelut != null) {//获取网络数据进行编辑
            AssetrMortgageEntity assetrMortgageEntity = new AssetrMortgageEntity();
            assetrMortgageEntity.setId(assertMortgaeDetailsRelut.getId());
            assetrMortgageEntity.setDebtRelationId(assertMortgaeDetailsRelut.getDebtRelationId());
            assetrMortgageEntity.setName(name);
            assetrMortgageEntity.setNum(Integer.valueOf(num));
            assetrMortgageEntity.setAmountStr(jiazhi);
            assetrMortgageEntity.setEvaluation(jigou);
            if (uphotoAdapter.getPath() != null && uphotoAdapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(uphotoAdapter.getPath());
                assetrMortgageEntity.setImage(imageUrl);
            } else { //没有传空
                assetrMortgageEntity.setImage("");
            }
            assetrMortgageEntity.setImage("");//图片
            getHttpUpdata(assetrMortgageEntity);
        } else {
            MortgageDO mortgageDO = new MortgageDO();
            mortgageDO.setName(name);
            mortgageDO.setNum(Integer.valueOf(num));
            mortgageDO.setAmountStr(jiazhi);
            mortgageDO.setEvaluation(jigou);
            if (uphotoAdapter.getPath() != null && uphotoAdapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(uphotoAdapter.getPath());
                mortgageDO.setImage(imageUrl);
            } else { //没有传空
                mortgageDO.setImage("");
            }

            GreenDaoUtils.getInstance(this).getMortgageDODao().insert(mortgageDO);

        }
        finish();
    }

    private void getHttpUpdata(AssetrMortgageEntity assetrMortgageEntity) {

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            assetrMortgageEntity.setAccessToken(baseRequests.get(0).getAccessToken());
            assetrMortgageEntity.setPlatform(2);
            assetrMortgageEntity.setUuid(baseRequests.get(0).getUuid());
            assetrMortgageEntity.setUid(baseRequests.get(0).getUid());
            assetrMortgageEntity.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(assetrMortgageEntity);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getAssetMortgageUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(AssetrMortgage.this, baseResponse);
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
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(AssetrMortgage.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(AssetrMortgage.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(AssetrMortgage.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(AssetrMortgage.this, baseResponse);
                            mPath.addAll(data);
                            uphotoAdapter.setPath(mPath);
                            gv_assic_mortgage_photo.setAdapter(uphotoAdapter);
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
                Photo_Select.open_Photo(AssetrMortgage.this, parent.getCount() - 1, callback);
                uphotoAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v==iv_debt4_currency_back){
            finish();
        }else if (v==bt_debt4_asstre){
            baocun();
        }
    }
}
