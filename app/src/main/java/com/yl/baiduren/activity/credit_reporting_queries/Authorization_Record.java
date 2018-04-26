package com.yl.baiduren.activity.credit_reporting_queries;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Authorization_Recoud_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.result.Authouization_Apply_List;
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
 * 授权记录
 */

public class Authorization_Record extends BaseActivity implements View.OnClickListener{

    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;

    private ImageView recoud_finish;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView record_recyclview;
    private Authorization_Recoud_Adapter adapter;
    private List<Authouization_Apply_List.AuthorizeResponse> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.authorization_recoud_activity;
    }

    @Override
    public void initViews() {
        recoud_finish = findViewById(R.id.recoud_finish);
        recoud_finish.setOnClickListener(this);
        refreshLayout = findViewById(R.id.recoud_refresh);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        record_recyclview = findViewById(R.id.record_recyclview);
        record_recyclview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Authorization_Recoud_Adapter(this);




    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            status = REFRESH;
            refreshLayout.autoRefresh();
        }
    }
    private void requsetWork() {

        if (UserInfoUtils.IsLogin(this)) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
            entity.setPageNo(pn);
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<Authouization_Apply_List> baseObserver = new BaseObserver<Authouization_Apply_List>(this) {

                @Override
                protected void onSuccees(String code, Authouization_Apply_List data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(Authorization_Record.this, baseResponse);
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            hideImageView();
                        } else {
                            if (adapter.getList() != null && adapter.getList().size() != 0) {
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
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .querry_Authorize_List(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<Authouization_Apply_List>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }

    //抽取的展示数据的方法
    private void displayData(final Authouization_Apply_List data) {


        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            record_recyclview.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<Authouization_Apply_List.AuthorizeResponse> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            record_recyclview.setAdapter(adapter);
            record_recyclview.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }

    private MaterialRefreshListener refreshListener =new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status=REFRESH;
            pn=1;
            requsetWork();

        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status=LOADMORE;
            pn=pn+1;
            requsetWork();
        }
    };

    @Override
    public void onClick(View v) {
        if (v==recoud_finish){
            finish();
        }

    }
}
