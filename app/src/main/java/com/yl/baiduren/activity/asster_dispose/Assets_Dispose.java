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
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.supply_demand.Hall_Supply_Resch;
import com.yl.baiduren.adapter.Assets_Dispose_Adapter;
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
 * 资产展示大厅
 */
public class Assets_Dispose extends BaseActivity {

    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private ImageView dispose_asset_back;
    private ImageView dispose_asset_serch;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Button mine_dispose_asset;
    private Assets_Dispose_Adapter adapter;
    private List<My_Supply_Result.DataListBean> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_assets__dispose;
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
        mine_dispose_asset = findViewById(R.id.mine_dispose_asset);//我的资产
        mine_dispose_asset.setOnClickListener(listener);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new Assets_Dispose_Adapter(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            refreshLayout.autoRefresh();
        }


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mine_dispose_asset) {//我的资产
                if (UserInfoUtils.IsLogin(Assets_Dispose.this)) {
                    startActivity(new Intent(Assets_Dispose.this, MyAssetsDispose.class));
                } else {
                    startActivity(new Intent(Assets_Dispose.this, Login_Activity_Password.class));
                }

            } else if (v == dispose_asset_back) {
                finish();
            } else if (v == dispose_asset_serch) {
                Intent intent = new Intent();
                intent.setClass(Assets_Dispose.this, Hall_Supply_Resch.class);
                intent.putExtra("comePager", Constant.ZC_DT);
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
            BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(Assets_Dispose.this) {

                @Override
                protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(Assets_Dispose.this, baseResponse);
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            hideImageView();
                        } else {
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
            RetrofitHelper.getInstance(Assets_Dispose.this).getServer()
                    .getSupplys(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        }
    }

    private void displayData(My_Supply_Result data) {
        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Supply_Result.DataListBean> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }
}
