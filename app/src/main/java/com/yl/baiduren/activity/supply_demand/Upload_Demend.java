package com.yl.baiduren.activity.supply_demand;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.SpaceItemDecoration;
import com.yl.baiduren.adapter.Upload_Demend_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.Upload_Supply_CallBack;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.request_body.Upload_Supply_Body;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
import com.yl.baiduren.entity.result.My_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CollapseView_1;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 上传需求
 */

public class Upload_Demend extends BaseActivity implements Upload_Supply_CallBack {

    private ImageView upload_demend_back;
    private EditText upload_demend_assict;
    private EditText upload_demend_num;
    private EditText upload_demend_guzhi;
    private EditText upload_demend_jigou;
    private TextView upload_demend_area;
    private Button bt_upload_demends;
    private String debtQuCode;
    private LinearLayout upload_demend_content;
    private TextView upload_demend;
    private Long dememnd_id;
    private My_Demend_Details_Result myDemendDetailsResult;

    @Override
    public int loadWindowLayout() {
        return R.layout.upload_demend;
    }

    @Override
    public void initViews() {
        myDemendDetailsResult = (My_Demend_Details_Result) getIntent().getSerializableExtra("myDemendDetailsResult");
        upload_demend_content = findViewById(R.id.upload_demend_content);//上传需求类别父布局
        upload_demend_back = findViewById(R.id.upload_demend_back);//关闭
        upload_demend_back.setOnClickListener(listener);
        upload_demend = findViewById(R.id.upload_demend);//资产类型
        upload_demend_assict = findViewById(R.id.upload_demend_assict);//资产名称
        upload_demend_num = findViewById(R.id.upload_demend_num);//资产数量
        upload_demend_guzhi = findViewById(R.id.upload_demend_guzhi);//资产估值
        upload_demend_area = findViewById(R.id.upload_demend_area);//归属地
        upload_demend_area.setOnClickListener(listener);
        bt_upload_demends = findViewById(R.id.bt_upload_demend);//上传
        bt_upload_demends.setOnClickListener(listener);
        List<CategoryDo> categoryDoList = GreenDaoUtils.getInstance(this).getCategoryDoDao().loadAll();
        if (categoryDoList.size() == 0) {//数据库中没有时，请求
            getType();//获取需求类别
        } else {
            initViewData(categoryDoList);//如果有数据则直接set
        }

        updata();
    }

    private void updata() {
        if (myDemendDetailsResult != null) {
            upload_demend.setText(myDemendDetailsResult.getCategoryName());
            upload_demend_assict.setText(myDemendDetailsResult.getName());
            upload_demend_num.setText(myDemendDetailsResult.getNum() + "");
            upload_demend_guzhi.setText(myDemendDetailsResult.getValueStr());
            upload_demend_area.setText(myDemendDetailsResult.getAreaName());
            debtQuCode = myDemendDetailsResult.getArea();
            dememnd_id = myDemendDetailsResult.getCategoryId();
        }
    }

    public void getType() {//请求供应数据类型
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
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
                                    GreenDaoUtils.getInstance(Upload_Demend.this).getCategoryDoDao().insert(categoryDo);
                                }
                                initViewData(list);
                            }
                        }
                    }
                });


    }

    /*上传供应*/
    private void upLoadDemnd() {
        String name = upload_demend_assict.getText().toString().trim();
        String num = upload_demend_num.getText().toString().trim();//数量
        String guzhi = upload_demend_guzhi.getText().toString().trim();//估值
        String area = upload_demend_area.getText().toString().trim();//归属地
        if (dememnd_id==null){
            ToastUtil.showShort(this, "请选择需求资产名称");
            return;
        }

        if (TextUtil.isEmpty(num)) {
            ToastUtil.showShort(this, "请输入需求数量");
            return;
        }
        if (TextUtil.isEmpty(guzhi)) {
            ToastUtil.showShort(this, "请输入估值");
            return;
        }
        if (TextUtil.isEmpty(area)) {
            ToastUtil.showShort(this, "请选择归属地");
            return;
        }


        Upload_Supply_Body body = new Upload_Supply_Body(DataWarehouse.getBaseRequest(this));

        body.setArea(debtQuCode);
        if (myDemendDetailsResult != null) {
            body.setId(myDemendDetailsResult.getId());
        }
        body.setCategoryId(dememnd_id);
        body.setName(name);
        body.setValueStr(guzhi);
        body.setNum(Integer.parseInt(num));

        String json = Util.toJson(body);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

        BaseObserver baseObserver = new BaseObserver<String>(this) {
            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    ToastUtil.showShort(Upload_Demend.this, "上传需求成功");
                    finish();
                }
            }
        };

        RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
        if (myDemendDetailsResult != null) {
            retrofitService.updataDemend(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            retrofitService.upload_Demend(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(baseObserver);
        }

    }

    public void initViewData(List<CategoryDo> list) {
        CollapseView_1 collapseView = geneCollapseView_1("请选择需求的类别");
        RecyclerView recycler = new RecyclerView(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(gridLayoutManager);
        Upload_Demend_Adapter myRecyclerAdapter = new Upload_Demend_Adapter(this, list);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.addItemDecoration(new SpaceItemDecoration(5));
        collapseView.setContent(recycler);
        collapseView.expand();//默认展开
        upload_demend_content.addView(collapseView);

    }

    /**
     * 一级菜单
     */
    private CollapseView_1 geneCollapseView_1(String title) {
        CollapseView_1 collapseView = new CollapseView_1(this);
        collapseView.setTitle(title);
        return collapseView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == upload_demend_back) {
                finish();
            } else if (v == upload_demend_area) {//归属地
                PopupWindeUtils.showPopupWinde(Upload_Demend.this, new PopupWindeUtils.OnAreaDataIdListener() {
                    @Override
                    public void areaId(String shengId, String shiId, String quId, String string) {
                        upload_demend_area.setText(string);
                        debtQuCode = quId;
                    }
                });

            } else if (v == bt_upload_demends) {//上传
                upLoadDemnd();
            }

        }
    };

    @Override
    public void getType(String name, Long assid) {
        dememnd_id = assid;
        upload_demend.setText(name);

    }
}
