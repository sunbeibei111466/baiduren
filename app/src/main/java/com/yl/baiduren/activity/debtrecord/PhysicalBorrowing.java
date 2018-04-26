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
import com.yl.baiduren.entity.greenentity.GoodLoanDO;
import com.yl.baiduren.entity.request_body.GoodLoanUpdataEntity;
import com.yl.baiduren.entity.result.PhysicalBorrowing_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;
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
 * 事物借贷
 */
public class PhysicalBorrowing extends BaseActivity implements View.OnClickListener{

    private ImageView iv_debt4_currency_back;
    private EditText et_physical_name,et_physical_num,etphysical_danwei
            ,et_physical_jiazhi,et_physical_jigou,et_physical_zjl,et_physical_nx
            ,et_physical_th_num,et_physical_wh_jine;
    private TextView tv_physical_jy_time;
    private Button bt_physical_baocun;
    private Long updataId;
    private PhysicalBorrowing_Details_Result physicalBorrowingDetailsResult=null;//实物借贷编辑数据
    private MyGridView gv_physical_photo;
    private UphotoAdapter adapter;
    private List<String> mPath;
    @Override
    public int loadWindowLayout() {
        return R.layout.activity_physical_borrowing;
    }

    @Override
    public void initViews() {
        mPath=new ArrayList<>();
        physicalBorrowingDetailsResult= (PhysicalBorrowing_Details_Result) getIntent().getSerializableExtra("physicalBorrowingDetailsResult");
        updataId=getIntent().getLongExtra("updataId",0);
        iv_debt4_currency_back=findViewById(R.id.iv_debt4_currency_back);//返回
        iv_debt4_currency_back.setOnClickListener(this);
        et_physical_name=findViewById(R.id.et_physical_name);//物品名称
        etphysical_danwei=findViewById(R.id.etphysical_danwei);//计量单位
        et_physical_num=findViewById(R.id.et_physical_num);//数量
        et_physical_jiazhi=findViewById(R.id.et_physical_jiazhi);//评估价值
        et_physical_jigou=findViewById(R.id.et_physical_jigou);//评估机构
        et_physical_zjl=findViewById(R.id.et_physical_zjl);//折旧率
        tv_physical_jy_time=findViewById(R.id.tv_physical_jy_time);//借用时间
        tv_physical_jy_time.setOnClickListener(this);
        et_physical_nx=findViewById(R.id.et_physical_nx);//使用年限
        et_physical_th_num=findViewById(R.id.et_physical_th_num);//退还数量
        et_physical_wh_jine=findViewById(R.id.et_physical_wh_jine);//未还金额
        bt_physical_baocun=findViewById(R.id.bt_physical_baocun);//保存
        bt_physical_baocun.setOnClickListener(this);
        adapter=new UphotoAdapter(this);
        gv_physical_photo=findViewById(R.id.gv_physical_photo);
        gv_physical_photo.setAdapter(adapter);
        gv_physical_photo.setOnItemClickListener(Oitemlistener);
        updataService();
        updata();
    }

    private void updataService() {
        if(physicalBorrowingDetailsResult!=null){
            et_physical_name.setText(physicalBorrowingDetailsResult.getName());//物品名称
            etphysical_danwei.setText(physicalBorrowingDetailsResult.getUnit());//计量单位
            et_physical_num.setText(physicalBorrowingDetailsResult.getNum()+"");//数量
            et_physical_jiazhi.setText(physicalBorrowingDetailsResult.getValueStr());//评估价值
            et_physical_jigou.setText(physicalBorrowingDetailsResult.getEvaluation());//评估机构
            BigDecimal bigDecimal=new BigDecimal(physicalBorrowingDetailsResult.getDepreciation());
            Double d=bigDecimal.multiply(new BigDecimal("100")).doubleValue();
            et_physical_zjl.setText(String.valueOf(d));//折旧率
            tv_physical_jy_time.setText(Util.LongParseString(physicalBorrowingDetailsResult.getBorrowTime()));//借用时间
            et_physical_nx.setText(physicalBorrowingDetailsResult.getUseLife()+"");//使用年限
            et_physical_th_num.setText(physicalBorrowingDetailsResult.getReturnNum()+"");//退还数量
            et_physical_wh_jine.setText(physicalBorrowingDetailsResult.getBalanceStr()+"");//未还金额

            if (!TextUtils.isEmpty(physicalBorrowingDetailsResult.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = physicalBorrowingDetailsResult.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_physical_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if(updataId!=0){
            GoodLoanDO goodLoanDO = GreenDaoUtils.getInstance(this).getGoodLoanDODao().load(updataId);
            et_physical_name.setText(goodLoanDO.getName());//物品名称
            etphysical_danwei.setText(goodLoanDO.getUnit());//计量单位
            et_physical_num.setText(goodLoanDO.getNum()+"");//数量
            et_physical_jiazhi.setText(goodLoanDO.getValueStr());//评估价值
            et_physical_jigou.setText(goodLoanDO.getEvaluation());//评估机构
            BigDecimal bigDecimal=new BigDecimal(goodLoanDO.getDepreciation());
            Double d=bigDecimal.multiply(new BigDecimal("100")).doubleValue();
            et_physical_zjl.setText(String.valueOf(d));//折旧率
            tv_physical_jy_time.setText(Util.LongParseString(goodLoanDO.getBorrowTime()));//借用时间
            et_physical_nx.setText(goodLoanDO.getUseLife()+"");//使用年限
            et_physical_th_num.setText(goodLoanDO.getReturnNum()+"");//退还数量
            et_physical_wh_jine.setText(goodLoanDO.getBalanceStr()+"");//未还金额

            if (!TextUtils.isEmpty(goodLoanDO.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = goodLoanDO.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_physical_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==iv_debt4_currency_back){
            finish();
        }else if(v==tv_physical_jy_time){//借用时间
            Timer_Select.getTimer(this, Timer_Select.getTime(tv_physical_jy_time), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    tv_physical_jy_time.setText(time.split(" ")[0]);
                }
            });
        }else if(v==bt_physical_baocun){//保存
            preserve();
        }
    }

    private void preserve() {

        String name=et_physical_name.getText().toString().trim();//物品名称
        String unit=etphysical_danwei.getText().toString().trim();//计量单位
        String num=et_physical_num.getText().toString().trim();//数量
        String rightValueStr=et_physical_jiazhi.getText().toString().trim();//评估价值
        String evaluation=et_physical_jigou.getText().toString().trim();//评估机构
        String zjl=et_physical_zjl.getText().toString().trim();//折旧率
        String time=tv_physical_jy_time.getText().toString().trim();//借用日期
        String nx=et_physical_nx.getText().toString().trim();//使用年限
        String th_num=et_physical_th_num.getText().toString().trim();//退还数量
        String wh_jine=et_physical_wh_jine.getText().toString().trim();//未还金额
        if (TextUtils.isEmpty(name)){
            ToastUtil.showShort(this,"请输入物品名称");
            return;
        }
        if (TextUtils.isEmpty(unit)){
            ToastUtil.showShort(this,"请输入计量单位");
            return;
        }
        if (TextUtils.isEmpty(num)){
            ToastUtil.showShort(this,"请输入物品数量");
            return;
        }
        if (TextUtils.isEmpty(zjl)){
            ToastUtil.showShort(this,"请输入折旧率");
            return;
        }
        Double dou=Double.valueOf(zjl);
        if (dou==0||dou>100){
            ToastUtil.showShort(this,"折旧率不能小于0%或者大于100%");
            return;
        }
        if (TextUtils.isEmpty(rightValueStr)){
            ToastUtil.showShort(this,"请输入评估价值");
            return;
        }
        if (TextUtils.isEmpty(evaluation)){
            ToastUtil.showShort(this,"请输入评估机构");
            return;
        }

        if (TextUtils.isEmpty(time)){
            ToastUtil.showShort(this,"请选择借用日期");
            return;
        }
        if (TextUtils.isEmpty(nx)){
            ToastUtil.showShort(this,"请输入使用年限");
            return;
        }
        if (TextUtils.isEmpty(th_num)){
            ToastUtil.showShort(this,"请输入退换数量");
            return;
        }
        if (TextUtils.isEmpty(wh_jine)){
            ToastUtil.showShort(this,"请输入未还余额");
            return;
        }

        if(updataId!=0){
            GoodLoanDO goodLoanDO = GreenDaoUtils.getInstance(this).getGoodLoanDODao().load(updataId);
            goodLoanDO.setName(name);
            goodLoanDO.setUnit(unit);
            goodLoanDO.setNum(Integer.valueOf(num));
            goodLoanDO.setValueStr(rightValueStr);
            goodLoanDO.setEvaluation(evaluation);
            BigDecimal bigDecimal=new BigDecimal(zjl);
            BigDecimal bigDecima2=new BigDecimal("100");
            BigDecimal d=bigDecimal.divide(bigDecima2);
            goodLoanDO.setDepreciation(d.doubleValue());//折旧率
            goodLoanDO.setBorrowTime(Util.strParsedata(time));//借用日期
            goodLoanDO.setUseLife(Integer.valueOf(nx));//年限
            goodLoanDO.setReturnNum(Long.valueOf(th_num));
            goodLoanDO.setBalanceStr(Long.valueOf(wh_jine));
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                goodLoanDO.setImage(imageUrl);
            } else { //没有传空
                goodLoanDO.setImage("");
            }

            GreenDaoUtils.getInstance(this).getGoodLoanDODao().update(goodLoanDO);
        }else if(physicalBorrowingDetailsResult!=null){
            GoodLoanUpdataEntity goodLoanUpdataEntity=new GoodLoanUpdataEntity();
            goodLoanUpdataEntity.setId(physicalBorrowingDetailsResult.getId());
            goodLoanUpdataEntity.setDebtRelationId(physicalBorrowingDetailsResult.getDebtRelationId());
            goodLoanUpdataEntity.setName(name);
            goodLoanUpdataEntity.setUnit(unit);
            goodLoanUpdataEntity.setNum(Integer.valueOf(num));
            goodLoanUpdataEntity.setValueStr(rightValueStr);
            goodLoanUpdataEntity.setEvaluation(evaluation);
            BigDecimal bigDecimal=new BigDecimal(zjl);
            BigDecimal bigDecima2=new BigDecimal("100");
            BigDecimal d=bigDecimal.divide(bigDecima2);
            goodLoanUpdataEntity.setDepreciation(d.doubleValue());//折旧率
            goodLoanUpdataEntity.setBorrowTime(Util.strParsedata(time));//借用日期
            goodLoanUpdataEntity.setUseLife(Integer.valueOf(nx));//年限
            goodLoanUpdataEntity.setReturnNum(Long.valueOf(th_num));
            goodLoanUpdataEntity.setBalanceStr(wh_jine);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                goodLoanUpdataEntity.setImage(imageUrl);
            } else { //没有传空
                goodLoanUpdataEntity.setImage("");
            }

            getHttpUpdata(goodLoanUpdataEntity);
        }
        else {
            GoodLoanDO goodLoanDO=new GoodLoanDO();
            goodLoanDO.setName(name);
            goodLoanDO.setUnit(unit);
            goodLoanDO.setNum(Integer.valueOf(num));
            goodLoanDO.setValueStr(rightValueStr);
            goodLoanDO.setEvaluation(evaluation);
            BigDecimal bigDecimal=new BigDecimal(zjl);
            BigDecimal bigDecima2=new BigDecimal("100");
            BigDecimal d=bigDecimal.divide(bigDecima2);
            goodLoanDO.setDepreciation(d.doubleValue());//折旧率
            goodLoanDO.setBorrowTime(Util.strParsedata(time));//借用日期
            goodLoanDO.setUseLife(Integer.valueOf(nx));//年限
            goodLoanDO.setReturnNum(Long.valueOf(th_num));
            goodLoanDO.setBalanceStr(Long.valueOf(wh_jine));
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                goodLoanDO.setImage(imageUrl);
            } else { //没有传空
                goodLoanDO.setImage("");
            }
            GreenDaoUtils.getInstance(this).getGoodLoanDODao().insert(goodLoanDO);
        }

        finish();
    }

    public void getHttpUpdata(GoodLoanUpdataEntity goodLoanUpdataEntity) {
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            LUtils.e("----事物借贷-----基本参数-------");
            goodLoanUpdataEntity.setAccessToken(baseRequests.get(0).getAccessToken());
            goodLoanUpdataEntity.setPlatform(2);
            goodLoanUpdataEntity.setUuid(baseRequests.get(0).getUuid());
            goodLoanUpdataEntity.setUid(baseRequests.get(0).getUid());
            goodLoanUpdataEntity.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(goodLoanUpdataEntity);//转成json
            LUtils.e("----事物借贷--json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getGoodLoanUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(PhysicalBorrowing.this,baseResponse);
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
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(PhysicalBorrowing.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(PhysicalBorrowing.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(PhysicalBorrowing.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(PhysicalBorrowing.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_physical_photo.setAdapter(adapter);
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
                Photo_Select.open_Photo(PhysicalBorrowing.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
