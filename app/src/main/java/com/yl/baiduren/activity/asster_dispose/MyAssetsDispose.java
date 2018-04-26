package com.yl.baiduren.activity.asster_dispose;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.supply_demand.Hall_Supply_Resch;
import com.yl.baiduren.adapter.MyAssets_Dispose_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssetsDisposeList;
import com.yl.baiduren.entity.result.My_Supply_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的资产处置
 */
public class MyAssetsDispose extends BaseActivity {

    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private ImageView dispose_asset_back;
    private ImageView dispose_asset_serch;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Button mine_add_asset;
    private MyAssets_Dispose_Adapter adapter;
    private List<My_Supply_Result.DataListBean> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_assets_dispose;
    }

    @Override
    public void initViews() {
        dispose_asset_back = findViewById(R.id.dispose_asset_back);//关闭页
        dispose_asset_back.setOnClickListener(listener);
        dispose_asset_serch = findViewById(R.id.dispose_asset_serch);//搜索
        dispose_asset_serch.setOnClickListener(listener);
        refreshLayout = findViewById(R.id.dispose_asset_asset_refresh);//刷新
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = findViewById(R.id.dispose_asset_recycleview);//列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mine_add_asset = findViewById(R.id.mine_add_asset);//新增资产
        mine_add_asset.setOnClickListener(listener);
        adapter = new MyAssets_Dispose_Adapter(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, 200);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mine_add_asset) {//新增资产
                startActivity(new Intent(MyAssetsDispose.this, AddAssetsDispose.class));
            } else if (v == dispose_asset_back) {
                finish();
            } else if (v == dispose_asset_serch) {
                Intent intent = new Intent();
                intent.setClass(MyAssetsDispose.this, Hall_Supply_Resch.class);
                intent.putExtra("comePager", Constant.MY_ZC);
                startActivity(intent);
            }
        }
    };

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = REFRESH;
            pn = 1;
            requestWork();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            requestWork();
        }
    };

    private void requestWork() {

        if (UserInfoUtils.IsLogin(this)) {
            AssetsDisposeList entity = new AssetsDisposeList(DataWarehouse.getBaseRequest(this));
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            entity.setType(3);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(MyAssetsDispose.this) {

                @Override
                protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(MyAssetsDispose.this, baseResponse);
                        if (data.getDataList().size() != 0) {
                            refreshLayout.setBackgroundColor(getResources().getColor(R.color.f5f5f5));
                            displayData(data);
                            hideImageView();
                        } else {
                            refreshLayout.setBackgroundColor(getResources().getColor(R.color.white));
                            if (adapter.getList() != null && adapter.getList().size() != 0) {
                                hideImageView();
                            }else {
                                showImageView();
                            }
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishRefreshLoadMore();


                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(MyAssetsDispose.this).getServer()
                    .getMySupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }

    private void displayData(My_Supply_Result data) {
        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setDataListBeans(dataList);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Supply_Result.DataListBean> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataListBeans(this.dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }
}
