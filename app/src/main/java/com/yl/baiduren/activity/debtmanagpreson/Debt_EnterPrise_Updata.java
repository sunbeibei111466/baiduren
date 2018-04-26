package com.yl.baiduren.activity.debtmanagpreson;

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
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.result.DebtPresonEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
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

public class Debt_EnterPrise_Updata extends BaseActivity implements View.OnClickListener{


    private DebtPresonEntity debtPresonEntity;
    private EditText et_enterprise_name_up,et_enterprise_code_up,et_legal_name_up
            ,et_legal_code_up,et_legal_phone_up,et_legal_detaile_adress_up,et_blong_type_up
            ,et_up_type_up,et_down_type_up;
    private TextView et_enterprise_area_up,et_legal_adress_up;
    private Button enter_prise_save_up;
    private ImageView iv_title_left_up;
    private String qyCode,frCode;
    private MyGridView gv_debt_enter_updata;
    private UphotoAdapter adapter;
    private List<String> mPath=null;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__enter_prise__updata;
    }

    @Override
    public void initViews() {
        mPath=new ArrayList<>();
        debtPresonEntity= (DebtPresonEntity) getIntent().getSerializableExtra("debtPresonEntity");//编辑数据
        iv_title_left_up=findViewById(R.id.iv_title_left_up);
        iv_title_left_up.setOnClickListener(this);
        et_enterprise_name_up=findViewById(R.id.et_enterprise_name_up);//企业全称
        et_enterprise_code_up=findViewById(R.id.et_enterprise_code_up);//企业证号
        et_enterprise_area_up=findViewById(R.id.et_enterprise_area_up);//企业所属地
        et_enterprise_area_up.setOnClickListener(this);
        et_legal_name_up=findViewById(R.id.et_legal_name_up);//法人姓名
        et_legal_code_up=findViewById(R.id.et_legal_code_up);//身份证号
        et_legal_phone_up=findViewById(R.id.et_legal_phone_up);//联系电话
        et_legal_adress_up=findViewById(R.id.et_legal_adress_up);//法人所属地
        et_legal_adress_up.setOnClickListener(this);
        et_legal_detaile_adress_up=findViewById(R.id.et_legal_detaile_adress_up);//详细地址
        et_blong_type_up=findViewById(R.id.et_blong_type_up);//所属行业类型
        et_up_type_up=findViewById(R.id.et_up_type_up);//上游行业类型
        et_down_type_up=findViewById(R.id.et_down_type_up);//下游行业类型
        enter_prise_save_up=findViewById(R.id.enter_prise_save_up);//保存
        enter_prise_save_up.setOnClickListener(this);
        gv_debt_enter_updata=findViewById(R.id.gv_debt_enter_updata);
        gv_debt_enter_updata.setOnItemClickListener(Oitemlistener);
        adapter=new UphotoAdapter(this);
        gv_debt_enter_updata.setAdapter(adapter);
        upData();
    }

    private void upData(){
        if(debtPresonEntity!=null){
            if(!TextUtils.isEmpty(debtPresonEntity.getName())){
                et_enterprise_name_up.setText(debtPresonEntity.getName());//企业全称
            }
            if(!TextUtils.isEmpty(debtPresonEntity.getCompanyCode())){//企业证号
                et_enterprise_code_up.setText(debtPresonEntity.getCompanyCode());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getAreaStr())){//所属地
                et_enterprise_area_up.setText(debtPresonEntity.getAreaStr());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getLegalName())){//法人姓名
                et_legal_name_up.setText(debtPresonEntity.getLegalName());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getIdCode())){//法人身份证号
                et_legal_code_up.setText(debtPresonEntity.getIdCode());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getPhoneNumber())){//法人联系电话
                et_legal_phone_up.setText(debtPresonEntity.getPhoneNumber());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getLegalAreaStr())){//法人所属地
                et_legal_adress_up.setText(debtPresonEntity.getLegalAreaStr());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getAddress())){//详细地址
                et_legal_detaile_adress_up.setText(debtPresonEntity.getAddress());
            }


            if(!TextUtils.isEmpty(debtPresonEntity.getCompanyCategory())){//所属行业类型
                et_blong_type_up.setText(debtPresonEntity.getCompanyCategory());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getCompanyUpCategory())){//上游行业类型
                et_up_type_up.setText(debtPresonEntity.getCompanyUpCategory());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getCompanyDownCategory())){//上游行业类型
                et_down_type_up.setText(debtPresonEntity.getCompanyDownCategory());
            }

            if(!TextUtils.isEmpty(debtPresonEntity.getImg())){//设置图片
                String imageUrl[]=debtPresonEntity.getImg().split(",");
                List<String> urlList=new ArrayList<>(Arrays.asList(imageUrl));
                mPath.addAll(urlList);
                adapter.setPath(mPath);
                gv_debt_enter_updata.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==iv_title_left_up){//返回
            finish();
        }else if(v==et_enterprise_area_up){//企业所属地
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_enterprise_area_up.setText(string);
                    qyCode=quId;
                }
            });
        }else if(v==et_legal_adress_up){//法人所属地
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_legal_adress_up.setText(string);
                    frCode=quId;
                }
            });
        } else if(v==enter_prise_save_up){//保存
            String name=et_enterprise_name_up.getText().toString().trim();//企业全称
            String qyzh=et_enterprise_code_up.getText().toString().trim();//企业证号
            String frName=et_legal_name_up.getText().toString().trim();//法人姓名
            String frCode=et_legal_code_up.getText().toString().trim();//身份证号
            String frPhone=et_legal_phone_up.getText().toString().trim();//联系电话
            String frAddress=et_legal_detaile_adress_up.getText().toString().trim();//详细地址
            String ht_type=et_blong_type_up.getText().toString().trim();//所属行业类型
            String sy_type=et_up_type_up.getText().toString().trim();//上游行业类型
            String xy_type=et_down_type_up.getText().toString().trim();//下游行业类型

            DebtPresonEntity debtPreson=new DebtPresonEntity(DataWarehouse.getBaseRequest(this));
            debtPreson.setId(debtPresonEntity.getId());
            debtPreson.setName(name);
            debtPreson.setCompanyCode(qyzh);
            debtPreson.setArea(qyCode);
            debtPreson.setLegalName(frName);
            debtPreson.setLegalArea(frCode);
            debtPreson.setPhoneNumber(frPhone);
            debtPreson.setAddress(frAddress);
            debtPreson.setCompanyCategory(ht_type);
            debtPreson.setCompanyUpCategory(sy_type);
            debtPreson.setCompanyDownCategory(xy_type);
            if(adapter.getPath()!=null && adapter.getPath().size()!=0){
                String imageUrl=Util.listToString(adapter.getPath());
                debtPreson.setImg(imageUrl);
            }else{
                debtPreson.setImg("");
            }
            getHttp(debtPreson);
        }
    }

    private void getHttp(DebtPresonEntity debtPreson) {
        String json = Util.toJson(debtPreson);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getDebtPersonUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            finish();
                        }
                    }
                });
    }


    private AdapterView.OnItemClickListener Oitemlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == parent.getCount() - 1) {
                Photo_Select.open_Photo(Debt_EnterPrise_Updata.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };

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

    private void getHttpImage(List<File> resultList) {

        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(Debt_EnterPrise_Updata.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(Debt_EnterPrise_Updata.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(Debt_EnterPrise_Updata.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(Debt_EnterPrise_Updata.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_debt_enter_updata.setAdapter(adapter);
                        }
                    }
                });
    }
}
