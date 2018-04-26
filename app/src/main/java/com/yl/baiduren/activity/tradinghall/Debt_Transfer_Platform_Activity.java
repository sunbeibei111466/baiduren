package com.yl.baiduren.activity.tradinghall;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_Transfer_Platform_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssigmentDeleteBody;
import com.yl.baiduren.entity.request_body.HallListModeBody;
import com.yl.baiduren.entity.result.HallListMode;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/20.
 * 债权转让平台 ---我的债权
 */

public class Debt_Transfer_Platform_Activity extends BaseActivity implements Debt_Transfer_Platform_Adapter.onDeleteItem {

    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private ImageView iv_company_back;//返回
    private MaterialRefreshLayout refreshLayout;//刷新
    private RecyclerView recyclerView;//容器
    private Button add_claim_company;//添加债权
    private List<HallListMode> hallListModeList;
    private Debt_Transfer_Platform_Adapter adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_transfre_platform;
    }

    @Override
    public void initViews() {
        refreshLayout = findViewById(R.id.company_refresh);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = findViewById(R.id.debt_claim_recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        iv_company_back = findViewById(R.id.iv_company_back);
        iv_company_back.setOnClickListener(listener);
        add_claim_company = findViewById(R.id.add_claim_company);
        add_claim_company.setOnClickListener(listener);
        adapter = new Debt_Transfer_Platform_Adapter(this);
        adapter.setOnDeleteItem(this);
        adapter.setType(2);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        status = REFRESH;
        refreshLayout.autoRefresh();
    }

    /**
     * 查询债权信息列表
     */
    private void initData() {
        if (UserInfoUtils.IsLogin(this)) {
            com.yl.baiduren.data.BaseRequest baseRequest=DataWarehouse.getBaseRequest(this);
            HallListModeBody hallListModeBody = new HallListModeBody();
            hallListModeBody.setPageNo(pn);
            hallListModeBody.setPageSize(ps);
            hallListModeBody.setUserId(baseRequest.uid);
            hallListModeBody.setAccessToken(baseRequest.accessToken);
            hallListModeBody.setUid(baseRequest.uid);
            hallListModeBody.setPlatform(2);
            hallListModeBody.setLoginUsername(baseRequest.loginUsername);
            String json = Util.toJson(hallListModeBody);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---signature---" + signature);
            BaseObserver<List<HallListMode>> observer = new BaseObserver<List<HallListMode>>(this) {
                @Override
                protected void onSuccees(String code, List<HallListMode> data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {

                        if (data.size() != 0) {
                            displayData(data);
                            hideImageView();
                        } else {
                            if (adapter.getHallListModeList() != null && adapter.getHallListModeList().size() != 0) {
                                hideImageView();
                            } else {
                                showImageView();
                            }
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishRefreshLoadMore();
                    }
                }
            };
            observer.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .getClaimsQuery(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<List<HallListMode>>>bindToLifecycle()))
                    .subscribe(observer);
        } else {
            finish();
        }
    }

    //抽取的展示数据的方法
    private void displayData(List<HallListMode> data) {


        if (status == REFRESH) {
            if (hallListModeList != null) {
                hallListModeList.clear();
            }
            hallListModeList = data;
            adapter.setHallListModeList(hallListModeList);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = hallListModeList.size();
            List<HallListMode> dataList2 = data;
            hallListModeList.addAll(dataList2);
            adapter.setHallListModeList(hallListModeList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_company_back) {
                finish();
            } else if (v == add_claim_company) {//新增
                startActivity(new Intent(Debt_Transfer_Platform_Activity.this, AssignmentActivity.class));
            }
        }
    };


    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

            status = REFRESH;
            pn = 1;
            initData();

        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            initData();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }

    @Override
    public void onClickDeleteItem(Long id, final int postion) {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {
            AssigmentDeleteBody assignmentEntity = new AssigmentDeleteBody();
            assignmentEntity.setClaimsId(id);
            assignmentEntity.setUid(baseRequestList.get(0).getUid());
            assignmentEntity.setUuid(baseRequestList.get(0).getUuid());
            assignmentEntity.setPlatform(2);
            assignmentEntity.setLoginUsername(baseRequestList.get(0).getLoginUsername());
            assignmentEntity.setAccessToken(baseRequestList.get(0).getAccessToken());
            String json = Util.toJson(assignmentEntity);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---signature---" + signature);
            RetrofitHelper.getInstance(this).getServer()
                    .getClaimsDelete(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {

                                adapter.getHallListModeList().remove(postion);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}
