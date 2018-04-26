package com.yl.baiduren.activity.debtrecord;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.yl.baiduren.entity.greenentity.AssetDO;
import com.yl.baiduren.entity.request_body.AssetUpdata;
import com.yl.baiduren.entity.result.Asset_Details_Result;
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
import com.yl.baiduren.view.MyGridView;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 债务人资产
 */
public class Debt_CreditorsAssets extends BaseActivity implements View.OnClickListener {

    private MyGridView gridView;
    private UphotoAdapter adapter;
    private List<String> mPath = new ArrayList<>();
    private ImageView iv_debt2_demand_back;
    private TextView et_debt3_demand_type_three, et_debt3_demand_diqu_three, et_debt3_assets_zonge_three;
    private EditText et_debt3_demand_name_three, et_debt3_demand_jine_three, et_debt3_assets_shulian_three, et_debt3_assets_jigou_three, et_debt3_asstes_pmiaoshu_three;
    private String quCode;//区Code
    private Button bt_assets_tijiao;
    private Long updataAssetsId;
    private String assetsName;
    private String assetsId;
    private Asset_Details_Result assetDetailsResult = null;
    private String gz, num;


    /**/
    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__debtor_assets;
    }

    @Override
    public void initViews() {
        assetDetailsResult = (Asset_Details_Result) getIntent().getSerializableExtra("assetDetailsResult");//带过来进行编辑的
        updataAssetsId = getIntent().getLongExtra("updataAssetrId", 0);
        assetsName = getIntent().getStringExtra("assetsName");
        assetsId = getIntent().getStringExtra("assetsId");

        iv_debt2_demand_back = findViewById(R.id.iv_debt2_demand_back);//返回
        iv_debt2_demand_back.setOnClickListener(this);
        gridView = findViewById(R.id.gv_assic_photo);
        adapter = new UphotoAdapter(this);//图片适配器
        gridView.setOnItemClickListener(Oitemlistener);
        gridView.setAdapter(adapter);

        et_debt3_demand_type_three = findViewById(R.id.et_debt3_demand_type_three);//资产
        et_debt3_demand_type_three.setText(assetsName);
        et_debt3_demand_name_three = findViewById(R.id.et_debt3_demand_name_three);//资产名称
        et_debt3_demand_jine_three = findViewById(R.id.et_debt3_demand_jine_three);//估值
        et_debt3_demand_jine_three.addTextChangedListener(textWatcher);
        et_debt3_assets_shulian_three = findViewById(R.id.et_debt3_assets_shulian_three);//数量
        et_debt3_assets_shulian_three.addTextChangedListener(textWatcher1);
        et_debt3_assets_zonge_three = findViewById(R.id.et_debt3_assets_zonge_three);//估值总额
        et_debt3_assets_jigou_three = findViewById(R.id.et_debt3_assets_jigou_three);//机构
        et_debt3_demand_diqu_three = findViewById(R.id.et_debt3_demand_diqu_three);//地区
        et_debt3_demand_diqu_three.setOnClickListener(this);
        et_debt3_asstes_pmiaoshu_three = findViewById(R.id.et_debt3_asstes_pmiaoshu_three);//资产描述
        bt_assets_tijiao = findViewById(R.id.bt_assets_tijiao);
        bt_assets_tijiao.setOnClickListener(this);//保存

        updataService();
        updata();
    }

    private void updataService() {
        if (assetDetailsResult != null) {
            et_debt3_demand_type_three.setText(assetDetailsResult.getCategoryName());
            et_debt3_demand_name_three.setText(assetDetailsResult.getName());
            et_debt3_demand_jine_three.setText(assetDetailsResult.getPriceStr().toString());
            et_debt3_assets_shulian_three.setText(assetDetailsResult.getNum() + "");
            et_debt3_assets_zonge_three.setText(assetDetailsResult.getTotalStr().toString());
            et_debt3_assets_jigou_three.setText(assetDetailsResult.getEvaluation().toString());
            et_debt3_demand_diqu_three.setText(assetDetailsResult.getAreaName().toString());
            et_debt3_asstes_pmiaoshu_three.setText(assetDetailsResult.getRemark().toString());
            if (!TextUtils.isEmpty(assetDetailsResult.getImgUrl())) {
                LUtils.e("----从数据库取处--Imagg-------", assetDetailsResult.getImgUrl());
                //将String 转成数组，在转成集合
                String[] strings = assetDetailsResult.getImgUrl().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if (updataAssetsId != 0) {
            AssetDO assetDO = GreenDaoUtils.getInstance(this).getAssetDODao().load(updataAssetsId);
            et_debt3_demand_type_three.setText(assetDO.getCategoryName());
            et_debt3_demand_name_three.setText(assetDO.getName());
            et_debt3_demand_jine_three.setText(assetDO.getPriceStr());
            et_debt3_assets_shulian_three.setText(assetDO.getNum() + "");
            et_debt3_assets_zonge_three.setText(assetDO.getTotalStr());
            et_debt3_assets_jigou_three.setText(assetDO.getEvaluation());
            et_debt3_demand_diqu_three.setText(assetDO.getDizhi());
            et_debt3_asstes_pmiaoshu_three.setText(assetDO.getRemark());
            if (!TextUtils.isEmpty(assetDO.getImgUrl())) {
                LUtils.e("----从数据库取处--Imagg-------", assetDO.getImgUrl());
                //将String 转成数组，在转成集合
                String[] strings = assetDO.getImgUrl().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void baocun() {
        String name = et_debt3_demand_name_three.getText().toString().trim();
        String jine = et_debt3_demand_jine_three.getText().toString().trim();
        String shuliang = et_debt3_assets_shulian_three.getText().toString().trim();
        String zonge = et_debt3_assets_zonge_three.getText().toString().trim();
        String jigou = et_debt3_assets_jigou_three.getText().toString().trim();
        String diqu = et_debt3_demand_diqu_three.getText().toString().trim();
        String miaoshu = et_debt3_asstes_pmiaoshu_three.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(this, "请输入资产名称");
            return;
        }
        if (TextUtils.isEmpty(jine)) {
            ToastUtil.showShort(this, "请输入估值单价");
            return;
        }
        if (TextUtils.isEmpty(shuliang)) {
            ToastUtil.showShort(this, "请输入资产数量");
            return;
        }
        if (TextUtils.isEmpty(jigou)) {
            ToastUtil.showShort(this, "请输入评估机构");
            return;
        }
        if (TextUtils.isEmpty(diqu)) {
            ToastUtil.showShort(this, "请选择资产所在地区");
            return;
        }


        if (updataAssetsId != 0) {//本地修改

            AssetDO assetDO = GreenDaoUtils.getInstance(this).getAssetDODao().load(updataAssetsId);
            assetDO.setName(name);
            assetDO.setCategoryName(assetDO.getCategoryName());
            assetDO.setCategoryId(assetDO.getCategoryId());
            assetDO.setPriceStr(jine);
            assetDO.setEvaluation(jigou);
            assetDO.setNum(Integer.valueOf(shuliang));
            if (!TextUtils.isEmpty(quCode)) {
                assetDO.setArea(quCode);
            } else {
                assetDO.setArea(assetDO.getArea());
            }
            assetDO.setDizhi(diqu);
            assetDO.setRemark(miaoshu);
            assetDO.setTotalStr(zonge);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {
                assetDO.setImgUrl("");//先清空
                if (adapter.getPath().size() != 0) {
                    String imageUrl = Util.listToString(adapter.getPath());
                    assetDO.setImgUrl(imageUrl);
                }
            }
            GreenDaoUtils.getInstance(this).getAssetDODao().update(assetDO);
        } else if (assetDetailsResult != null) {
            AssetUpdata assetUpdata = new AssetUpdata();
            assetUpdata.setId(assetDetailsResult.getId());
            assetUpdata.setDebtRelationId(assetDetailsResult.getDebtRelationId());
            assetUpdata.setCategoryId(assetDetailsResult.getCategoryId());
            assetUpdata.setName(name);
            assetUpdata.setPriceStr(jine);
            assetUpdata.setEvaluation(jigou);
            assetUpdata.setNum(Integer.valueOf(shuliang));
            if (!TextUtils.isEmpty(quCode)) {
                assetUpdata.setArea(quCode);
            } else {
                assetUpdata.setArea(assetDetailsResult.getArea());
            }
            assetUpdata.setRemark(miaoshu);
            assetUpdata.setTotalStr(zonge);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {
                String imageUrl = Util.listToString(adapter.getPath());
                assetUpdata.setImgUrl(imageUrl);
            } else {
                assetUpdata.setImgUrl("");
            }

            getHttpUpdata(assetUpdata);
        } else {//'本地新增
            AssetDO assetDO = new AssetDO();
            assetDO.setName(name);
            assetDO.setCategoryName(assetsName);
            assetDO.setCategoryId(Integer.valueOf(assetsId));
            assetDO.setPriceStr(jine);
            assetDO.setNum(Integer.valueOf(shuliang));
            assetDO.setTotalStr(zonge);
            assetDO.setEvaluation(jigou);
            assetDO.setArea(quCode);
            assetDO.setDizhi(diqu);
            assetDO.setRemark(miaoshu);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                assetDO.setImgUrl(imageUrl);
            } else { //没有传空
                assetDO.setImgUrl("");
            }
            GreenDaoUtils.getInstance(this).getAssetDODao().insert(assetDO);
        }
        finish();

//            LoadImage loadImage=new LoadImage();
//            List<File> fileList=new ArrayList<>();
//            for(int i=0;i<mpath.size();i++){
//                File file=new File(mpath.get(i));
//                fileList.add(file);
//            }
//            loadImage.setFile(fileList);
//            getHttpImage(loadImage);


    }

    private void getHttpUpdata(AssetUpdata assetUpdata) {
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            assetUpdata.setAccessToken(baseRequests.get(0).getAccessToken());
            assetUpdata.setPlatform(2);
            assetUpdata.setUuid(baseRequests.get(0).getUuid());
            assetUpdata.setUid(baseRequests.get(0).getUid());
            assetUpdata.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(assetUpdata);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getAssetUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(Debt_CreditorsAssets.this, baseResponse);
                                finish();
                            }
                        }
                    });
        }
    }


    /**
     * 图片上传
     *
     * @param resultList 图片
     */
    private void getHttpImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(Debt_CreditorsAssets.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(Debt_CreditorsAssets.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(Debt_CreditorsAssets.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(Debt_CreditorsAssets.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gridView.setAdapter(adapter);
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
                Photo_Select.open_Photo(Debt_CreditorsAssets.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onClick(View v) {
        if (v == et_debt3_demand_diqu_three) {//选择地址
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_debt3_demand_diqu_three.setText(string);
                    quCode = quId;
                }
            });
        } else if (v == iv_debt2_demand_back) {
            finish();
        } else if (v == bt_assets_tijiao) {//保存
            baocun();
        }
    }

    //估值
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gz = s.toString();
            if (!TextUtils.isEmpty(gz) && !TextUtils.isEmpty(num)) {
                BigDecimal bigDecimal = new BigDecimal(gz);
                BigDecimal bigDecima2 = new BigDecimal(num);
                String zz = String.valueOf(bigDecimal.multiply(bigDecima2).doubleValue());
                et_debt3_assets_zonge_three.setText(zz);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //数量监听
    private TextWatcher textWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            num = s.toString();
            if (!TextUtils.isEmpty(gz) && !TextUtils.isEmpty(num)) {
                BigDecimal bigDecimal = new BigDecimal(gz);
                BigDecimal bigDecima2 = new BigDecimal(num);
                String zz = String.valueOf(bigDecimal.multiply(bigDecima2).doubleValue());
                et_debt3_assets_zonge_three.setText(zz);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}