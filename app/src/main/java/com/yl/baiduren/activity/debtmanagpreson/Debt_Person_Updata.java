package com.yl.baiduren.activity.debtmanagpreson;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsAssets;
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

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Debt_Person_Updata extends BaseActivity implements View.OnClickListener{

    private ImageView iv_title_left_up;
    private EditText et_natural_name_up,et_natural_code__up,et_natural_phone__up,et_natural_adress_up;
    private TextView et_natural_area_up;
    private String quCode;
    private Button person_save_up;
    private DebtPresonEntity debtPresonEntity=null;
    private MyGridView gv_debt_person_updata_photo;
    private UphotoAdapter adapter;
    private List<String> mPath=null;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__person__updata;
    }

    @Override
    public void initViews() {
        mPath=new ArrayList<>();
        debtPresonEntity= (DebtPresonEntity) getIntent().getSerializableExtra("debtPresonEntity");
        iv_title_left_up=findViewById(R.id.iv_title_left_up);
        iv_title_left_up.setOnClickListener(this);
        et_natural_name_up=findViewById(R.id.et_natural_name_up);//真实姓名
        et_natural_code__up=findViewById(R.id.et_natural_code__up);//身份证号
        et_natural_phone__up=findViewById(R.id.et_natural_phone__up);//联系电话
        et_natural_area_up=findViewById(R.id.et_natural_area_up);//所属地
        et_natural_area_up.setOnClickListener(this);
        et_natural_adress_up=findViewById(R.id.et_natural_adress_up);//详细地址
        person_save_up=findViewById(R.id.person_save_up);//保存
        person_save_up.setOnClickListener(this);
        gv_debt_person_updata_photo=findViewById(R.id.gv_debt_person_updata_photo);//图片容器
        gv_debt_person_updata_photo.setOnItemClickListener(Oitemlistener);
        adapter=new UphotoAdapter(this);//图片适配器
        gv_debt_person_updata_photo.setAdapter(adapter);
        upData();
    }


    private void upData(){
        if(debtPresonEntity!=null){//设置编辑数据源
            if (!TextUtils.isEmpty(debtPresonEntity.getName())) {
                et_natural_name_up.setText(debtPresonEntity.getName());//真实姓名
            }
            if (!TextUtils.isEmpty(debtPresonEntity.getIdCode())) {//身份证件号
                et_natural_code__up.setText(debtPresonEntity.getIdCode());
            }
            if (!TextUtils.isEmpty(debtPresonEntity.getPhoneNumber())) {//联系电话
                et_natural_phone__up.setText(debtPresonEntity.getPhoneNumber());
            }
            if (!TextUtils.isEmpty(debtPresonEntity.getAreaStr())) {//所属地
                et_natural_area_up.setText(debtPresonEntity.getAreaStr());
            }
            if (!TextUtils.isEmpty(debtPresonEntity.getAddress())) {//详细地址
                et_natural_adress_up.setText(debtPresonEntity.getAddress());
            }
            if (!TextUtils.isEmpty(debtPresonEntity.getImg())) {//设置图片
                String imageUrl[]=debtPresonEntity.getImg().split(",");
                List<String> urlList=new ArrayList<>(Arrays.asList(imageUrl));
                mPath.addAll(urlList);
                adapter.setPath(mPath);
                gv_debt_person_updata_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }else{
            LUtils.e("---空----");
        }
    }

    @Override
    public void onClick(View v) {
        if(v==iv_title_left_up){
            finish();
        }else if(v==et_natural_area_up){
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_natural_area_up.setText(string);
                    quCode=quId;
                }
            });
        }else if(v==person_save_up){//保存
            String name=et_natural_name_up.getText().toString().trim();//真实姓名
            String idCord=et_natural_code__up.getText().toString().trim();//身份证号
            String phone=et_natural_phone__up.getText().toString().trim();//联系电话
            String address=et_natural_adress_up.getText().toString().trim();//详细地址
            if (TextUtil.isEmpty(name)){
                ToastUtil.showShort(this,"请输入债事人的姓名");
                return;
            }
            if (TextUtil.isEmpty(idCord)){
                ToastUtil.showShort(this,"请输入债事人的身份证号");
                return;
            }
            if (!IdCardUtil.isIDCard(idCord)){
                ToastUtil.showShort(this,"请输入正确的身份证号");
                return;
            }
            if (TextUtil.isEmpty(phone)){
                ToastUtil.showShort(this,"请输入债事人的手机号");
                return;
            }
            if (!IdCardUtil.matchPhone(phone)){
                ToastUtil.showShort(this,"请输入正确的手机号");
                return;
            }
            if (TextUtil.isEmpty(address)){
                ToastUtil.showShort(this,"请输入详细地址");
                return;
            }

            DebtPresonEntity debtPreson=new DebtPresonEntity(DataWarehouse.getBaseRequest(this));
            debtPreson.setId(debtPresonEntity.getId());
            debtPreson.setName(name);
            debtPreson.setIdCode(idCord);
            debtPreson.setPhoneNumber(phone);
            debtPreson.setArea(quCode);
            debtPreson.setAddress(address);
            if(adapter.getPath()!=null && adapter.getPath().size()!=0){
                String path=Util.listToString(adapter.getPath());
                debtPreson.setImg(path);
            }else{
                debtPreson.setImg("");
            }

            getHttp(debtPreson);
        }
    }


    private void getHttp(DebtPresonEntity debtPresonEntity){
        String json = Util.toJson(debtPresonEntity);
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
                Photo_Select.open_Photo(Debt_Person_Updata.this, parent.getCount() - 1, callback);
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
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(Debt_Person_Updata.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(Debt_Person_Updata.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(Debt_Person_Updata.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(Debt_Person_Updata.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_debt_person_updata_photo.setAdapter(adapter);
                        }
                    }
                });
    }
}
