package com.yl.baiduren.activity.debtrecord;

import android.graphics.RectF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Crd_Demand_Adapter;
import com.yl.baiduren.adapter.DebThreeDebtorAssetsAdapter;
import com.yl.baiduren.adapter.SpaceItemDecoration;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.Debt_Details_Item_Moldel;
import com.yl.baiduren.entity.greenentity.AssetDO;
import com.yl.baiduren.entity.greenentity.CategoryDo;
import com.yl.baiduren.entity.request_body.Debt3;
import com.yl.baiduren.entity.request_body.DebtRelationVo3;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
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
 * 债务人资产页面
 */
public class Debt_Three_DebtorAssets extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_debt3_demand_list;
    private Button bt_debt3;
    private ImageView iv_debt3_back;
    private MyRecyclerView recyclerView;
    private Long addDebtId;
    private HighLight mHightLight;


    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt_three_creditors_assets;
    }

    @Override
    public void initViews() {
        addDebtId = getIntent().getLongExtra("id", 0);//债事Id
        ll_debt3_demand_list = findViewById(R.id.ll_debt3_demand_list);
        bt_debt3 = findViewById(R.id.bt_debt3);
        bt_debt3.setOnClickListener(this);
        iv_debt3_back = findViewById(R.id.iv_debt3_back);
        iv_debt3_back.setOnClickListener(this);
        ArrayList<Debt_Details_Item_Moldel> mlist = new ArrayList<>();
        recyclerView = findViewById(R.id.my_assic_recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        List<CategoryDo> categoryDoList=GreenDaoUtils.getInstance(Debt_Three_DebtorAssets.this).getCategoryDoDao().loadAll();
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
        List<AssetDO> assetDOList = GreenDaoUtils.getInstance(this).getAssetDODao().loadAll();
        if (assetDOList != null && assetDOList.size() != 0) {
            Crd_Demand_Adapter adapter = new Crd_Demand_Adapter(this, null, assetDOList, 2);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }


    public void getType() {

        //设置基础参数
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
                            if(list.size()!=0){//数据路中没有时，添加
                                for(int i=0;i<list.size();i++){
                                    CategoryDo categoryDo=new CategoryDo();
                                    categoryDo.setId(list.get(i).getId());
                                    categoryDo.setImage(list.get(i).getImage());
                                    categoryDo.setName(list.get(i).getName());
                                    GreenDaoUtils.getInstance(Debt_Three_DebtorAssets.this).getCategoryDoDao().insert(categoryDo);
                                }
                                initViewData(list);
                            }
                        }
                    }
                });


    }


    public void initViewData(List<CategoryDo> list) {
        CollapseView_1 collapseView = geneCollapseView_1("请选择资产类别");
        RecyclerView recycler = new RecyclerView(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(gridLayoutManager);
        DebThreeDebtorAssetsAdapter myRecyclerAdapter = new DebThreeDebtorAssetsAdapter(this, list);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.addItemDecoration(new SpaceItemDecoration(5));
        collapseView.setContent(recycler);
        collapseView.expand();//默认展开
        ll_debt3_demand_list.addView(collapseView);
        //显示新手指引
        if (Util.isFirstApp(this,4)) {
            initGuidel();
        }
    }

    private void initGuidel() {
        mHightLight = new HighLight(Debt_Three_DebtorAssets.this);
        mHightLight.anchor(this.getWindow().getDecorView())//如果是在Activity上增加引导层，不需要设置此方法
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
//                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {

                    @Override
                    public void onLayouted() {
                        mHightLight.addHighLight(R.id.view_line_asstes, R.layout.debt_guidel_assets, onPosCallback, new RectLightShape());
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
     * 一级菜单
     */
    private CollapseView_1 geneCollapseView_1(String title) {
        CollapseView_1 collapseView = new CollapseView_1(this);
        collapseView.setTitle(title);
        return collapseView;
    }

    @Override
    public void onClick(View view) {
        if (view == bt_debt3) {
            tijiao();
        } else if (view == iv_debt3_back) {
            ActivityCollector.back(this, addDebtId);
        }
    }

    private void tijiao() {

        List<AssetDO> assetDOList = GreenDaoUtils.getInstance(this).getAssetDODao().loadAll();
        if (assetDOList != null && assetDOList.size() != 0) {
            List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
            Debt3 debt3 = new Debt3();
            DebtRelationVo3 debtRelationVo3 = new DebtRelationVo3();
            debtRelationVo3.setDebtRelationId(addDebtId);
            debtRelationVo3.setAsset(assetDOList);
            debt3.setProcess(3);//步骤
            debt3.setDebtRelationVo3(debtRelationVo3);
            debt3.setAccessToken(baseRequests.get(0).getAccessToken());
            debt3.setPlatform(2);
            debt3.setLoginUsername(baseRequests.get(0).getLoginUsername());
            debt3.setUuid(baseRequests.get(0).getUuid());
            debt3.setUid(baseRequests.get(0).getUid());

            String json = Util.toJson(debt3);//转成json
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
                                LUtils.e("---第3步数据返回不为空--");
                                if (addDebtId != 0) {
                                    ActivityCollector.back(Debt_Three_DebtorAssets.this, addDebtId);
                                    //删除资产表所有数据
                                    GreenDaoUtils.getInstance(Debt_Three_DebtorAssets.this).getAssetDODao().deleteAll();
                                    finish();
                                }
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
        ActivityCollector.back(this, addDebtId);
    }


}
