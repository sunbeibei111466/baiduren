package com.yl.baiduren.activity.supply_demand;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.SpaceItemDecoration;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.adapter.Upload_Supply_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.Upload_Supply_CallBack;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.request_body.Upload_Supply_Body;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/1/19.
 * 上传供应
 */

public class UploadSupply extends BaseActivity implements Upload_Supply_CallBack {

    private ImageView upload_back;
    private TextView upload_assict;
    private EditText upload_num;
    private EditText upload_guzhi;
    private EditText upload_jigou;
    private TextView upload_area;
    private LinearLayout upload_supply_content;
    private MyGridView upload_assic;
    private Button bt_upload;
    private ArrayList<String> mlist;
    private UphotoAdapter adapter;
    private String debtQuCode;
    private String assicName;
    private Long assicId;
    private EditText supplly_name;
    private Supply_Demend_Details_Result demendDetailsResult;

    @Override
    public int loadWindowLayout() {
        return R.layout.upload_supply;
    }

    @Override
    public void initViews() {
        demendDetailsResult = (Supply_Demend_Details_Result) getIntent().getSerializableExtra("demendDetailsResult");
        upload_supply_content = findViewById(R.id.upload_supply_content);//选择资产
        upload_back = findViewById(R.id.upload_back);//关闭页
        upload_back.setOnClickListener(listener);
        upload_assict = findViewById(R.id.upload_assict);//供应资产类型
        upload_num = findViewById(R.id.upload_num); //供应资产数量
        upload_guzhi = findViewById(R.id.upload_guzhi);//估值
        upload_jigou = findViewById(R.id.upload_jigou);//评估机构
        upload_area = findViewById(R.id.upload_area);//归属地
        upload_area.setOnClickListener(listener);
        supplly_name = findViewById(R.id.supplly_name);//可供应资产名称
        upload_assic = findViewById(R.id.upload_photo);//图片上传
        bt_upload = findViewById(R.id.bt_add);//上传
        bt_upload.setOnClickListener(listener);
        mlist = new ArrayList<>();
        adapter = new UphotoAdapter(this);
        upload_assic.setAdapter(adapter);
        upload_assic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {
                    Photo_Select.open_Photo(UploadSupply.this, parent.getCount() - 1, callback);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        List<CategoryDo> categoryDoList = GreenDaoUtils.getInstance(UploadSupply.this).getCategoryDoDao().loadAll();
        if (categoryDoList.size() == 0) {//数据库中没有时，请求
            getType();//获取需求类别
        } else {
            initViewData(categoryDoList);//如果有数据则直接set
        }

        updata();
    }

    private void updata() {
        if (demendDetailsResult != null) {
            upload_assict.setText(demendDetailsResult.getCategoryName());
            supplly_name.setText(demendDetailsResult.getName());
            upload_num.setText(demendDetailsResult.getNum() + "");
            upload_guzhi.setText(demendDetailsResult.getValueStr());
            upload_jigou.setText(demendDetailsResult.getEvaluation());
            upload_area.setText(demendDetailsResult.getAreaName());
            debtQuCode = demendDetailsResult.getArea();
            if (!TextUtil.isEmpty(demendDetailsResult.getImg())) {
                String[] strings = demendDetailsResult.getImg().split(",");
                mlist.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mlist);
            }
            assicId = demendDetailsResult.getCategoryId();
        }
    }

    public void getType() {//请求供应数据类型
        com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
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
                                    GreenDaoUtils.getInstance(UploadSupply.this).getCategoryDoDao().insert(categoryDo);
                                }
                                initViewData(list);
                            }
                        }
                    }
                });


    }

    public void initViewData(List<CategoryDo> list) {
        CollapseView_1 collapseView = geneCollapseView_1("请选择供应的类别");
        RecyclerView recycler = new RecyclerView(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(gridLayoutManager);
        Upload_Supply_Adapter myRecyclerAdapter = new Upload_Supply_Adapter(this, list);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.addItemDecoration(new SpaceItemDecoration(5));
        collapseView.setContent(recycler);
        collapseView.expand();//默认展开
        upload_supply_content.addView(collapseView);

    }

    /**
     * 一级菜单
     */
    private CollapseView_1 geneCollapseView_1(String title) {
        CollapseView_1 collapseView = new CollapseView_1(this);
        collapseView.setTitle(title);
        return collapseView;
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
                            UserInfoUtils.setUuid(UploadSupply.this, baseResponse);

                            mlist.addAll(data);
                            adapter.setPath(mlist);
                            upload_assic.setAdapter(adapter);
                        }
                    }
                });
    }

    /*上传供应*/
    private void upLoadSupply() {
        String name = supplly_name.getText().toString().trim();
        String num = upload_num.getText().toString().trim();//数量
        String guzhi = upload_guzhi.getText().toString().trim();//估值
        String jigou = upload_jigou.getText().toString().trim();//机构
        String area = upload_area.getText().toString().trim();//归属地
        if(TextUtils.isEmpty(upload_assict.getText().toString().trim())){
            ToastUtil.showShort(this, "请输入供应类型");
            return;
        }

        if (TextUtil.isEmpty(name)) {
            ToastUtil.showShort(this, "请输入供应资产名称");
            return;
        }
        if (TextUtil.isEmpty(num)) {
            ToastUtil.showShort(this, "请输入供应数量");
            return;
        }
        if (TextUtil.isEmpty(guzhi)) {
            ToastUtil.showShort(this, "请输入估值");
            return;
        }
        if (TextUtil.isEmpty(jigou)) {
            ToastUtil.showShort(this, "请输入评估机构");
            return;
        }
        if (TextUtil.isEmpty(area)) {
            ToastUtil.showShort(this, "请选择归属地");
            return;
        }


        Upload_Supply_Body body = new Upload_Supply_Body(DataWarehouse.getBaseRequest(this));
            body.setArea(debtQuCode);
            if (demendDetailsResult != null) {
                body.setId(demendDetailsResult.getId());
            } else {
                body.setType(1);
            }
            body.setCategoryId(assicId);
            body.setName(name);
            body.setValueStr(guzhi);
            body.setEvaluation(jigou);
            body.setNum(Integer.parseInt(num));
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {
                String imageUrl = Util.listToString(adapter.getPath());
                body.setImg(imageUrl);
            } else {
                body.setImg("");
            }

            String json = Util.toJson(body);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            BaseObserver baseObserver = new BaseObserver<String>(this) {
                @Override
                protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        ToastUtil.showShort(UploadSupply.this, "上传供应成功");
                        finish();
                    }
                }
            };
            baseObserver.setStop(true);
            RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
            if (demendDetailsResult != null) {//编辑
                retrofitService.updataSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                        .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                        .subscribe(baseObserver);
            } else {//新增
                retrofitService.upLoadSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                        .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                        .subscribe(baseObserver);
            }

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
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == upload_back) {
                finish();
            } else if (v == bt_upload) {//上传
                upLoadSupply();
            } else if (v == upload_area) {//资产归属地
                PopupWindeUtils.showPopupWinde(UploadSupply.this, new PopupWindeUtils.OnAreaDataIdListener() {
                    @Override
                    public void areaId(String shengId, String shiId, String quId, String string) {
                        upload_area.setText(string);
                        debtQuCode = quId;
                    }
                });
            }

        }
    };

    @Override
    public void getType(String name, Long assid) {//资产名，id回调
        assicId = assid;

        if (!TextUtil.isEmpty(name)) {
            upload_assict.setText(name);
        }
    }
}
