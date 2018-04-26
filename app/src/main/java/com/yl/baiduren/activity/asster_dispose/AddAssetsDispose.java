package com.yl.baiduren.activity.asster_dispose;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.AddAsstes_Type_Adapter;
import com.yl.baiduren.adapter.SpaceItemDecoration;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.Upload_Supply_CallBack;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.AssetsnDetailesInformation;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.request_body.SupplyDO;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
import com.yl.baiduren.entity.result.ReportResult;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
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
import com.yl.baiduren.view.CollapseView_1;
import com.yl.baiduren.view.MyGridView;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * 新增资产处置
 */
public class AddAssetsDispose extends BaseActivity implements View.OnClickListener, Upload_Supply_CallBack,RadioGroup.OnCheckedChangeListener {

    private LinearLayout ll_debt1_10;
    private Button bt_add;//上传
    private ImageView upload_back;
    private TextView tv_assict_type;
    private EditText upload_assict;
    private EditText upload_num;
    private EditText upload_guzhi,tv_enterprise_name;
    private EditText upload_jigou;
    private TextView upload_area;
    private LinearLayout upload_supply_content;
    private MyGridView upload_assic;
    private Button bt_upload,bt_debt1_examine;
    private ArrayList<String> mlist;
    private UphotoAdapter adapter;
    private Long assId;
    private String quCode;
    private Supply_Demend_Details_Result demendDetailsResult = null;
    private RadioGroup rg_type;
    private RadioButton rb_gr,rb_qy;
    private int genre=0;
    private LinearLayout ll_debt1_0;
    private boolean whetherCeck = false;//是否点击校对
    private TextView select_report;
    private String company_name;
    private Integer reporid=null;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_add_assets_dispose;
    }

    @Override
    public void initViews() {
        demendDetailsResult = (Supply_Demend_Details_Result) getIntent().getSerializableExtra("demendDetailsResult");

        ll_debt1_10 = findViewById(R.id.ll_debt1_10);//详细描述
        ll_debt1_10.setOnClickListener(this);
        upload_supply_content = findViewById(R.id.upload_supply_content);//选择资产
        ll_debt1_0=findViewById(R.id.ll_debt1_0);
        View view_xian0 = findViewById(R.id.view_xian0);
        tv_enterprise_name=findViewById(R.id.tv_enterprise_name);//企业名称
        select_report = findViewById(R.id.select_report);//选择报告
        select_report.setOnClickListener(this);
        upload_back = findViewById(R.id.add_dispose_fish);//关闭页
        upload_back.setOnClickListener(this);
        tv_assict_type = findViewById(R.id.tv_assict_type);//资产类型
        upload_assict = findViewById(R.id.tv_assict_name);//供应资产名称
        upload_num = findViewById(R.id.et_asstes_num); //供应资产数量
        upload_guzhi = findViewById(R.id.et_asster_guzhi);//估值
        upload_jigou = findViewById(R.id.et_asster_jigou);//评估机构
        upload_area = findViewById(R.id.tv_asstes_area);//归属地
        upload_area.setOnClickListener(this);
        upload_assic = findViewById(R.id.gv_asster_upload_photo);//图片上传
        bt_upload = findViewById(R.id.bt_upload);//上传
        bt_upload.setOnClickListener(this);
        rb_gr=findViewById(R.id.rb_gr);//个人
        rb_qy=findViewById(R.id.rb_qy);//企业
        rg_type=findViewById(R.id.rg_type);
        rg_type.setOnCheckedChangeListener(this);
        mlist = new ArrayList<>();
        adapter = new UphotoAdapter(this);
        upload_assic.setAdapter(adapter);
        upload_assic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {
                    Photo_Select.open_Photo(AddAssetsDispose.this, parent.getCount() - 1, callback);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        List<CategoryDo> categoryDoList = GreenDaoUtils.getInstance(AddAssetsDispose.this).getCategoryDoDao().loadAll();
        if (categoryDoList.size() == 0) {//数据库中没有时，请求
            getType();//获取需求类别
        } else {
            initViewData(categoryDoList);//如果有数据则直接
        }
        updata();

    }

    private void updata() {
        if (demendDetailsResult != null) {

            if(demendDetailsResult.getGenre()==1){//个人
                rb_gr.setChecked(true);
            }else {//企业
                rb_qy.setChecked(true);
            }
            rb_gr.setClickable(false);
            rb_qy.setClickable(false);
            tv_assict_type.setText(demendDetailsResult.getCategoryName());
            assId = demendDetailsResult.getCategoryId();
            upload_assict.setText(demendDetailsResult.getName());
            upload_num.setText(demendDetailsResult.getNum() + "");
            tv_enterprise_name.setText(demendDetailsResult.getCategoryName());
            upload_guzhi.setText(demendDetailsResult.getValueStr());
            upload_jigou.setText(demendDetailsResult.getEvaluation());
            upload_area.setText(demendDetailsResult.getAreaName());
            quCode = demendDetailsResult.getArea();
            AssetsnDetailesInformation info = new AssetsnDetailesInformation();
            info.setText(demendDetailsResult.getDescribe());
            GreenDaoUtils.getInstance(AddAssetsDispose.this).getAssetsnDetailesInformationDao().insert(info);
            LUtils.e("-------**********---------" + demendDetailsResult.getDescribe());
            String[] strings = demendDetailsResult.getImg().split(",");
            mlist.addAll(new ArrayList<>(Arrays.asList(strings)));
            adapter.setPath(mlist);
            upload_assic.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(rg_type==group){
            if(checkedId==R.id.rb_gr){
                genre=1;
              /* *//* view_xian0.setVisibility(View.GONE);*/
                ll_debt1_0.setVisibility(View.GONE);
            }else {
                genre=2;
              /* *//* view_xian0.setVisibility(View.VISIBLE);*/
                ll_debt1_0.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == ll_debt1_10) {
            startActivity(new Intent(this, AddInfo.class));
        } else if (v == upload_back) {
            finish();
        } else if (v == bt_upload) {//上传
            upload();
        } else if (v == upload_area) {
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    quCode = quId;
                    upload_area.setText(string);
                }
            });
        }else if (v==select_report){//选择报告
            ask();
        }

    }

    private void ask() {
        if (UserInfoUtils.IsLogin(this)) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
            int pn = 1;
            entity.setPageNo(pn);
            int ps = 100;
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<ReportResult> baseObserver = new BaseObserver<ReportResult>(this) {

                @Override
                protected void onSuccees(String code, ReportResult data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(AddAssetsDispose.this, baseResponse);
                        List<ReportResult.Report> dataList = data.getDataList();
                        if (dataList.size()!=0){
                            PopupWindeUtils.queryPopupWinde2(AddAssetsDispose.this, dataList, new PopupWindeUtils.OnClickListView() {
                                @Override
                                public void onStringClick(Integer id, String name) {
                                    select_report.setText(name);
                                    reporid=id;

                                }
                            });
                        }else{
                            ToastUtil.showShort(AddAssetsDispose.this,"您还没有购买报告");
                        }

                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .getMyReport(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))

                    .compose(compose(this.<BaseEntity<ReportResult>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }


    private void upload() {

        if(genre==0){
            ToastUtil.showShort(this, "请选择主体类型");
            return;
        }

        if(genre==2){//企业
            company_name = tv_enterprise_name.getText().toString().trim();
            if (TextUtil.isEmpty(company_name)) {
                ToastUtil.showShort(this, "请输入企业名称");
                return;
            }


        }



        if (assId==null) {
            ToastUtil.showShort(this, "请选择资产类型");
            return;
        }
        if (TextUtil.isEmpty(upload_assict.getText().toString().trim())) {
            ToastUtil.showShort(this, "请输入资产名称");
            return;
        }
        if (TextUtil.isEmpty(upload_num.getText().toString().trim())) {
            ToastUtil.showShort(this, "请输入资产数量");
            return;
        }

        if (TextUtil.isEmpty(upload_guzhi.getText().toString().trim())) {
            ToastUtil.showShort(this, "请输入资产估值");
            return;
        }

        if (TextUtil.isEmpty(upload_jigou.getText().toString().trim())) {
            ToastUtil.showShort(this, "请输入评估机构");
            return;
        }
        if (TextUtil.isEmpty(upload_area.getText().toString().trim())) {
            ToastUtil.showShort(this, "请输入归属地");
            return;
        }
        List<AssetsnDetailesInformation> information = GreenDaoUtils.
                getInstance(AddAssetsDispose.this).getAssetsnDetailesInformationDao().loadAll();
        if (information.size() == 0) {
            ToastUtil.showShort(this, "请输入详细信息");
            return;
        }

        String name = upload_assict.getText().toString().trim();
        String num = upload_num.getText().toString().trim();
        String guzhi = upload_guzhi.getText().toString().trim();
        String jg = upload_jigou.getText().toString().trim();

        SupplyDO supplyDO = new SupplyDO(DataWarehouse.getBaseRequest(this));
        if (demendDetailsResult != null) {
            supplyDO.setId(demendDetailsResult.getId());
        }
        supplyDO.setType(3);
        supplyDO.setCategoryId(assId);
        supplyDO.setName(name);
        supplyDO.setNum(Integer.valueOf(num));
        supplyDO.setValueStr(guzhi);
        supplyDO.setEvaluation(jg);
        supplyDO.setArea(quCode);
        supplyDO.setGenre(genre);
        supplyDO.setCompanyName(company_name);
        supplyDO.setReportId(reporid);
        supplyDO.setDescribe(information.get(0).getText());
        if (adapter.getPath() != null && adapter.getPath().size() != 0) {
            String imageUrl = Util.listToString(adapter.getPath());
            supplyDO.setImg(imageUrl);
        } else {
            supplyDO.setImg("");
        }
        getHttpUpLoad(supplyDO);
    }

    private void getHttpUpLoad(SupplyDO supplyDO) {

        if (UserInfoUtils.IsLogin(this)) {
            String json = Util.toJson(supplyDO);//转成json

            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
            Observable<BaseEntity<Long>> observable = null;
            if (demendDetailsResult != null) {
                observable = retrofitService.updateSupply_Asstes(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            } else {
                observable = retrofitService.insertSupply_Asstes(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            }

            observable.compose(compose(this.<BaseEntity<Long>>bindToLifecycle()))
                    .subscribe(new BaseObserver<Long>(this) {
                        @Override
                        protected void onSuccees(String code, Long data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                LUtils.e("------id-----" + data);
                                if (demendDetailsResult == null) {
                                    ToastUtil.showShort(AddAssetsDispose.this, "添加供应成功");
                                    startActivity(new Intent(AddAssetsDispose.this, MyAsstesDisposeDetials.class).putExtra("id", data).putExtra("from","addassets"));
                                    finish();
                                } else {
                                    ToastUtil.showShort(AddAssetsDispose.this, "修改供应成功");
                                    startActivity(new Intent(AddAssetsDispose.this, MyAsstesDisposeDetials.class).putExtra("id", demendDetailsResult.getId()));
                                }
                                GreenDaoUtils.getInstance(AddAssetsDispose.this).getAssetsnDetailesInformationDao().deleteAll();
                            }
                        }
                    });
        }
    }

    public void getType() {//请求供应数据类型

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        LUtils.e("----获取第三页数据----");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setPlatform(2);
        baseRequest.setUuid(baseRequests.get(0).getUuid());
        baseRequest.setUid(baseRequests.get(0).getUid());
        baseRequest.setLoginUsername(baseRequests.get(0).getLoginUsername());
        baseRequest.setAccessToken(baseRequests.get(0).getAccessToken());

        String json = Util.toJson(baseRequest);//转成json

        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getCreditorsDemand(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Debt2_CreditorsDemand>>bindToLifecycle()))
                .subscribe(new BaseObserver<Debt2_CreditorsDemand>(this) {
                    @Override
                    protected void onSuccees(String code, Debt2_CreditorsDemand data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            List<CategoryDo> list = data.getList();
                            if (list.size() != 0) {//数据路中没有时，添加
                                for (int i = 0; i < list.size(); i++) {
                                    CategoryDo categoryDo = new CategoryDo();
                                    categoryDo.setId(list.get(i).getId());
                                    categoryDo.setImage(list.get(i).getImage());
                                    categoryDo.setName(list.get(i).getName());
                                    GreenDaoUtils.getInstance(AddAssetsDispose.this).getCategoryDoDao().insert(categoryDo);
                                }
                                initViewData(list);
                            }
                        }
                    }
                });

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
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(AddAssetsDispose.this, baseResponse);

                            mlist.addAll(data);
                            adapter.setPath(mlist);
                            upload_assic.setAdapter(adapter);
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
                loadImage(ImageUtils.comperssImage(resultList));//获取图片地址
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    public void initViewData(List<CategoryDo> list) {
        CollapseView_1 collapseView = geneCollapseView_1();
        RecyclerView recycler = new RecyclerView(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(gridLayoutManager);
        AddAsstes_Type_Adapter myRecyclerAdapter = new AddAsstes_Type_Adapter(this, list);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.addItemDecoration(new SpaceItemDecoration(5));
        collapseView.setContent(recycler);
        collapseView.expand();//默认展开
        upload_supply_content.addView(collapseView);

    }

    /**
     * 一级菜单
     */
    private CollapseView_1 geneCollapseView_1() {
        CollapseView_1 collapseView = new CollapseView_1(this);
        collapseView.setTitle("请选择供应的类别");
        return collapseView;
    }


    @Override
    public void getType(String name, Long assid) {
        if (!TextUtil.isEmpty(name)) {
            tv_assict_type.setText(name);
            assId = assid;
        }
    }


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==2){
            String text=data.getExtras().getString("text");
            whetherCeck = true;//返回成功表示校对过了
            tv_enterprise_name.setText(text);//第一次设置值 在editText的监听中执行的是改变后的方法 及strNew的值
            tv_enterprise_name.setText(text);//这个时候设置值时 edittext会先执行改变前的方法，及strOld=text ,然后在执行 strNew=text
        }
    }*/
}
