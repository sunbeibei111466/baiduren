package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.adapter.Crd_Demand_Adapter;
import com.yl.baiduren.adapter.DebtCreditorsDemandRecyclerAdapter;
import com.yl.baiduren.adapter.SpaceItemDecoration;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.Debt_Details_Item_Moldel;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.greenentity.DemandDO;
import com.yl.baiduren.entity.request_body.Debt2;
import com.yl.baiduren.entity.request_body.DebtRelationVo2;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CollapseView_1;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.RectLightShape;

/**
 * 债权人需求页面
 */
public class Debt_Too_CreditorsDemand extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_debt2_demand_list;
    private Button bt_debt2;
    private ImageView iv_debt2_back;
    private MyRecyclerView recyclerView;
    private Long addDebtId;//新增Id
    private HighLight mHightLight;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__too__debtor_demand;
    }

    @Override
    public void initViews() {

        addDebtId = getIntent().getLongExtra("id", 0);
        ll_debt2_demand_list = findViewById(R.id.ll_debt2_demand_list);//recyclerView
        bt_debt2 = findViewById(R.id.bt_debt2);//保存按钮
        bt_debt2.setOnClickListener(this);
        iv_debt2_back = findViewById(R.id.iv_debt2_back);//返回
        iv_debt2_back.setOnClickListener(this);
        recyclerView = findViewById(R.id.crd_my_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ArrayList<Debt_Details_Item_Moldel> list2 = new ArrayList<>();
        List<CategoryDo> categoryDoList=GreenDaoUtils.getInstance(Debt_Too_CreditorsDemand.this).getCategoryDoDao().loadAll();
        if(categoryDoList.size()==0) {//数据库中没有时，请求
            getType();//获取需求类别
        }else{
            initViewData(categoryDoList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
          列表展示数据
         */
        List<DemandDO> demandDOList = GreenDaoUtils.getInstance(this).getDemandDODao().loadAll();
        if (demandDOList != null && demandDOList.size() != 0) {
            Crd_Demand_Adapter adapter = new Crd_Demand_Adapter(this, demandDOList, null, 1);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == bt_debt2) {
            preserve();
        } else if (view == iv_debt2_back) {
            ActivityCollector.back(Debt_Too_CreditorsDemand.this, addDebtId);
        }
    }

    public void getType() {

        //设置基础参数
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests != null && baseRequests.size() != 0) {
            LUtils.e("----获取第二页数据----");
            final BaseRequest baseRequest = new BaseRequest();
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
                                UserInfoUtils.setUuid(Debt_Too_CreditorsDemand.this,baseRequest);
                                List<CategoryDo> categoryDoList=data.getList();
                                if(categoryDoList.size()!=0){//数据路中没有时，添加
                                    for(int i=0;i<categoryDoList.size();i++){
                                        CategoryDo categoryDo=new CategoryDo();
                                        categoryDo.setId(categoryDoList.get(i).getId());
                                        categoryDo.setImage(categoryDoList.get(i).getImage());
                                        categoryDo.setName(categoryDoList.get(i).getName());
                                        GreenDaoUtils.getInstance(Debt_Too_CreditorsDemand.this).getCategoryDoDao().insert(categoryDo);
                                    }
                                    LUtils.e("------新增-----");
                                    initViewData(categoryDoList);
                                }
                            }
                        }
                    });
        } else {
            startActivity(new Intent(this, Login_Activity_Password.class));
        }
    }


    public void initViewData(List<CategoryDo> list) {
        CollapseView_1 collapseView = geneCollapseView_1("请选择需求类别");
        RecyclerView recycler = new RecyclerView(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(gridLayoutManager);
        DebtCreditorsDemandRecyclerAdapter myRecyclerAdapter = new DebtCreditorsDemandRecyclerAdapter(this, list);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.addItemDecoration(new SpaceItemDecoration(3));
        collapseView.setContent(recycler);
        collapseView.expand();
        ll_debt2_demand_list.addView(collapseView);
        //显示新手指引
        if (Util.isFirstApp(this,3)) {
            LUtils.e("--3333---TRUE---");
            initGuidel();
        }
    }

    private void initGuidel() {
        mHightLight = new HighLight(Debt_Too_CreditorsDemand.this);
        mHightLight.anchor(this.getWindow().getDecorView())//如果是在Activity上增加引导层，不需要设置此方法
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
//                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {

                    @Override
                    public void onLayouted() {
                        mHightLight.addHighLight(R.id.view_line_demand, R.layout.debt_guidel_demand, onPosCallback, new RectLightShape());
                        //然后显示高亮布局
                        mHightLight.show();
                    }
                })
                .setClickCallback(new HighLightInterface.OnClickCallback() {
                    @Override
                    public void onClick() {
                        mHightLight.remove();
                    }
                });

    }
    /**
     * 位置回掉
     */
    private HighLight.OnPosCallback onPosCallback = new HighLight.OnPosCallback() {
        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.bottomMargin = rectF.top-(rectF.top-1);
        }
    };

    /**
     * 保存数据
     */
    private void preserve() {

        List<DemandDO> demandDOList = GreenDaoUtils.getInstance(this).getDemandDODao().loadAll();
        if (demandDOList != null && demandDOList.size() != 0) {


            List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
            Debt2 debt2 = new Debt2();
            DebtRelationVo2 debtRelationVo2 = new DebtRelationVo2();
            debtRelationVo2.setDebtRelationId(addDebtId); //从债事详情进来添加数据
            debtRelationVo2.setDemand(demandDOList);
            debt2.setProcess(2);
            debt2.setDebtRelationVo2(debtRelationVo2);
            debt2.setAccessToken(baseRequests.get(0).getAccessToken());
            debt2.setPlatform(2);
            debt2.setLoginUsername(baseRequests.get(0).getLoginUsername());
            debt2.setUuid(baseRequests.get(0).getUuid());
            debt2.setUid(baseRequests.get(0).getUid());

            String json = Util.toJson(debt2);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---signature---" + signature);
            RetrofitHelper.getInstance(this).getServer()
                    .debt2(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                ActivityCollector.back(Debt_Too_CreditorsDemand.this, addDebtId);
                                //删除需求表所有数据
                                GreenDaoUtils.getInstance(Debt_Too_CreditorsDemand.this).getDemandDODao().deleteAll();
                                finish();
                            }
                        }
                    });
        } else {
            ToastUtil.showShort(this, "请添加数据");
        }


    }


    @Override
    public void onBackPressed() {//此时的返回返回到债事列表页
        super.onBackPressed();
        ActivityCollector.back(Debt_Too_CreditorsDemand.this, addDebtId);
    }


    /**
     * 一级菜单
     */
    private CollapseView_1 geneCollapseView_1(String title) {
        CollapseView_1 collapseView = new CollapseView_1(this);
        collapseView.setTitle(title);
        return collapseView;
    }


}
