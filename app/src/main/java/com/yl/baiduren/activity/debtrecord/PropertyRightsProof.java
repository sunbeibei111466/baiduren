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
import com.yl.baiduren.entity.greenentity.PropertyLoanDO;
import com.yl.baiduren.entity.request_body.PropertyLoanUpdataEntity;
import com.yl.baiduren.entity.result.Property_Rights_Result;
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

//产权借贷
public class PropertyRightsProof extends BaseActivity implements View.OnClickListener {

    private ImageView iv_debt4_currency_back;
    private EditText et_property_name, et_property_danwei, et_property_cq_nx, et_property_pgz, et_property_cq_jigou, et_property_zlh, et_property_zl_nx, et_property_zl_pgz, et_property_zl_jigou, et_property_yihuan, et_property_weihuan;//
    private TextView tv_property_cq_ssd;
    private String quCode;
    private Button bt_prooperty_baocun;
    private Long updataId;
    private Property_Rights_Result propertyRightsResult = null;
    private MyGridView gv_property_photo;
    private UphotoAdapter adapter;
    private List<String> mPath;
    private String name;
    private String danwei;
    private String ssd;
    private String cq_nx;
    private String pgz;
    private String jg;
    private String zlh;
    private String zl_nx;
    private String zl_pgz;
    private String zl_pgjg;
    private String jhhj;
    private String wh;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_property_rights_proof;
    }

    @Override
    public void initViews() {
        mPath = new ArrayList<>();
        propertyRightsResult = (Property_Rights_Result) getIntent().getSerializableExtra("propertyRightsResult");
        updataId = getIntent().getLongExtra("updataId", 0);
        iv_debt4_currency_back = findViewById(R.id.iv_debt4_currency_back);
        iv_debt4_currency_back.setOnClickListener(this);
        et_property_name = findViewById(R.id.et_property_name);//标的名称
        et_property_danwei = findViewById(R.id.et_property_danwei);//计量单位
        tv_property_cq_ssd = findViewById(R.id.tv_property_cq_ssd);//产权归属
        et_property_cq_nx = findViewById(R.id.et_property_cq_nx);//产权年限
        et_property_pgz = findViewById(R.id.et_property_pgz);//评估值
        et_property_cq_jigou = findViewById(R.id.et_property_cq_jigou);//评估机构

        et_property_zlh = findViewById(R.id.et_property_zlh);//专利号
        et_property_zl_nx = findViewById(R.id.et_property_zl_nx);//专利年限
        et_property_zl_pgz = findViewById(R.id.et_property_zl_pgz);//评估值
        et_property_zl_jigou = findViewById(R.id.et_property_zl_jigou);//评估机构
        et_property_yihuan = findViewById(R.id.et_property_yihuan);//已还合计
        et_property_weihuan = findViewById(R.id.et_property_weihuan);//未还余额
        bt_prooperty_baocun = findViewById(R.id.bt_prooperty_baocun);
        bt_prooperty_baocun.setOnClickListener(this);
        adapter = new UphotoAdapter(this);
        gv_property_photo = findViewById(R.id.gv_property_photo);
        gv_property_photo.setAdapter(adapter);
        gv_property_photo.setOnItemClickListener(Oitemlistener);

        updataService();
        updata();
    }

    private void updataService() {
        if (propertyRightsResult != null) {
            et_property_name.setText(propertyRightsResult.getName());//标的名称
            et_property_danwei.setText(propertyRightsResult.getUnit());//计量单位
            tv_property_cq_ssd.setText(propertyRightsResult.getOwner());//产权所属地
            et_property_cq_nx.setText(propertyRightsResult.getRightYear() + "");//产权年限
            et_property_pgz.setText(propertyRightsResult.getRightValueStr());//评估值
            et_property_cq_jigou.setText(propertyRightsResult.getRightEvaluation());//评估机构

            et_property_zlh.setText(propertyRightsResult.getPatentNo());//专利号
            et_property_zl_nx.setText(propertyRightsResult.getPatentYear() + "");//专利年限
            et_property_zl_pgz.setText(propertyRightsResult.getPatentValueStr());//评估值
            et_property_zl_jigou.setText(propertyRightsResult.getPatentEvaluation());//评估机构
            et_property_yihuan.setText(propertyRightsResult.getReturnMoneyStr() + "");//已还合计
            et_property_weihuan.setText(propertyRightsResult.getBalanceStr());//未还余额
            if (!TextUtils.isEmpty(propertyRightsResult.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = propertyRightsResult.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_property_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if (updataId != 0) {
            PropertyLoanDO propertyLoanDO = GreenDaoUtils.getInstance(this).getPropertyLoanDODao().load(updataId);
            et_property_name.setText(propertyLoanDO.getName());//标的名称
            et_property_danwei.setText(propertyLoanDO.getUnit());//计量单位
            tv_property_cq_ssd.setText(propertyLoanDO.getOwner());//产权归属
            et_property_cq_nx.setText(propertyLoanDO.getRightYear() + "");//产权年限
            et_property_pgz.setText(propertyLoanDO.getRightValueStr());//评估值
            et_property_cq_jigou.setText(propertyLoanDO.getRightEvaluation());//评估机构

            et_property_zlh.setText(propertyLoanDO.getPatentNo());//专利号
            et_property_zl_nx.setText(propertyLoanDO.getPatentYear() + "");//专利年限
            et_property_zl_pgz.setText(propertyLoanDO.getPatentValueStr());//评估值
            et_property_zl_jigou.setText(propertyLoanDO.getPatentEvaluation());//评估机构
            et_property_yihuan.setText(propertyLoanDO.getReturnMoneyStr() + "");//已还合计
            et_property_weihuan.setText(propertyLoanDO.getBalanceStr());//未还余额

            if (!TextUtils.isEmpty(propertyLoanDO.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = propertyLoanDO.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_property_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == iv_debt4_currency_back) {
            finish();
        } else if (v == bt_prooperty_baocun) {
            //标的名称
            name = et_property_name.getText().toString().trim();
            //计量单位
            danwei = et_property_danwei.getText().toString().trim();
            //产权所属地
            ssd = tv_property_cq_ssd.getText().toString().trim();
            //产权年限
            cq_nx = et_property_cq_nx.getText().toString().trim();
            //评估值
            pgz = et_property_pgz.getText().toString().trim();
            //评估机构
            jg = et_property_cq_jigou.getText().toString().trim();

            //专利号
            zlh = et_property_zlh.getText().toString().trim();
            //专利年限
            zl_nx = et_property_zl_nx.getText().toString().trim();
            //评估值
            zl_pgz = et_property_zl_pgz.getText().toString().trim();
            //评估机构
            zl_pgjg = et_property_zl_jigou.getText().toString().trim();
            //已还合计
            jhhj = et_property_yihuan.getText().toString().trim();
            //未还余额
            wh = et_property_weihuan.getText().toString().trim();
           if (isChanquanEmpty() || isZhuali()){
               baocun();
           }else{
               ToastUtil.showShort(this,"请完善任意一组的信息");
               return;
           }

        }
    }
    private boolean isChanquanEmpty(){//判断产权组
        boolean cq;
        cq = !TextUtils.isEmpty(name) && !TextUtils.isEmpty(danwei) && !TextUtils.isEmpty(ssd) &&
                !TextUtils.isEmpty(cq_nx) && !TextUtils.isEmpty(pgz) && !TextUtils.isEmpty(jg);
        return cq;
    }
    private boolean isZhuali(){//判断专利组
        boolean zl;
        zl = !TextUtils.isEmpty(zlh) && !TextUtils.isEmpty(zl_nx)
                && !TextUtils.isEmpty(zl_pgz) && !TextUtils.isEmpty(zl_pgjg);
        return zl;

    }
    private void baocun() {
        if (TextUtils.isEmpty(jhhj)){
            ToastUtil.showShort(this,"请输入已还合计");
            return;
        }
        if (TextUtils.isEmpty(wh)){
            ToastUtil.showShort(this,"请输入未还余额");
            return;
        }

        if (updataId != 0) {
            PropertyLoanDO propertyLoanDO = GreenDaoUtils.getInstance(this).getPropertyLoanDODao().load(updataId);
            propertyLoanDO.setName(name);
            propertyLoanDO.setUnit(danwei);
            propertyLoanDO.setOwner(ssd);
            propertyLoanDO.setRightYear(Integer.valueOf(cq_nx));
            propertyLoanDO.setRightValueStr(pgz);
            propertyLoanDO.setRightEvaluation(jg);

            propertyLoanDO.setPatentNo(zlh);
            propertyLoanDO.setPatentYear(Integer.valueOf(zl_nx));
            propertyLoanDO.setPatentValueStr(zl_pgz);
            propertyLoanDO.setPatentEvaluation(zl_pgjg);
            propertyLoanDO.setReturnMoneyStr(Long.valueOf(jhhj));
            propertyLoanDO.setBalanceStr(wh);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                propertyLoanDO.setImage(imageUrl);
            } else { //没有传空
                propertyLoanDO.setImage("");
            }

            GreenDaoUtils.getInstance(this).getPropertyLoanDODao().update(propertyLoanDO);
        } else if (propertyRightsResult != null) {
            PropertyLoanUpdataEntity loanUpdataEntity = new PropertyLoanUpdataEntity();
            loanUpdataEntity.setId(propertyRightsResult.getId());
            loanUpdataEntity.setDebtRelationId(propertyRightsResult.getDebtRelationId());
            loanUpdataEntity.setName(name);
            loanUpdataEntity.setUnit(danwei);
            loanUpdataEntity.setOwner(ssd);
            loanUpdataEntity.setRightYear(Integer.valueOf(cq_nx));
            loanUpdataEntity.setRightValueStr(pgz);
            loanUpdataEntity.setRightEvaluation(jg);

            loanUpdataEntity.setPatentNo(zlh);
            loanUpdataEntity.setPatentYear(Integer.valueOf(zl_nx));
            loanUpdataEntity.setPatentValueStr(zl_pgz);
            loanUpdataEntity.setPatentEvaluation(zl_pgjg);

            loanUpdataEntity.setReturnMoneyStr(Long.valueOf(jhhj));
            loanUpdataEntity.setBalanceStr(wh);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                loanUpdataEntity.setImage(imageUrl);
            } else { //没有传空
                loanUpdataEntity.setImage("");
            }
            getHttpUpdata(loanUpdataEntity);
        } else {  //新增
            PropertyLoanDO propertyLoanDO = new PropertyLoanDO();
            propertyLoanDO.setName(name);
            propertyLoanDO.setUnit(danwei);
            propertyLoanDO.setOwner(ssd);
            if (!TextUtils.isEmpty(cq_nx)){
                propertyLoanDO.setRightYear(Integer.valueOf(cq_nx));
            }
            propertyLoanDO.setRightValueStr(pgz);
            propertyLoanDO.setRightEvaluation(jg);

            propertyLoanDO.setPatentNo(zlh);
            if (!TextUtils.isEmpty(zl_nx)){
                propertyLoanDO.setPatentYear(Integer.valueOf(zl_nx));
            }
            propertyLoanDO.setPatentValueStr(zl_pgz);
            propertyLoanDO.setPatentEvaluation(zl_pgjg);
            if (!TextUtils.isEmpty(jhhj)){
                propertyLoanDO.setReturnMoneyStr(Long.valueOf(jhhj));
            }
            propertyLoanDO.setBalanceStr(wh);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                propertyLoanDO.setImage(imageUrl);
            } else { //没有传空
                propertyLoanDO.setImage("");
            }
            GreenDaoUtils.getInstance(this).getPropertyLoanDODao().insert(propertyLoanDO);

        }

        finish();

    }

    private void getHttpUpdata(PropertyLoanUpdataEntity loanUpdataEntity) {

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            loanUpdataEntity.setAccessToken(baseRequests.get(0).getAccessToken());
            loanUpdataEntity.setPlatform(2);
            loanUpdataEntity.setUuid(baseRequests.get(0).getUuid());
            loanUpdataEntity.setUid(baseRequests.get(0).getUid());
            loanUpdataEntity.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(loanUpdataEntity);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getPropertyLoanUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(PropertyRightsProof.this, baseResponse);
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
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(PropertyRightsProof.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(PropertyRightsProof.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(PropertyRightsProof.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(PropertyRightsProof.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_property_photo.setAdapter(adapter);
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
                Photo_Select.open_Photo(PropertyRightsProof.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
