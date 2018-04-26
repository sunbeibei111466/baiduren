package com.yl.baiduren.activity.mypager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.My_Child_Accout_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.IDeleteCall;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Delete_Child_Accort;
import com.yl.baiduren.entity.request_body.My_Child_Accout_Lie;
import com.yl.baiduren.entity.result.My_Child_Accort_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/19.
 * 我的子账号列表
 */

public class My_Child_Account extends BaseActivity implements IDeleteCall {

    private RecyclerView child_recyclerview;
    private MaterialRefreshLayout refresh_child;
    private ImageView iv_child_back;
    private Button add_child_usrname;
    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private My_Child_Accout_Adapter accout_adapter;
    private List<My_Child_Accort_Result.DataListBean> dataListBeanList;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_child_accout;
    }

    @Override
    public void initViews() {
        iv_child_back = findViewById(R.id.iv_child_back);
        iv_child_back.setOnClickListener(listener);
        refresh_child = findViewById(R.id.refresh_child);
        refresh_child.setMaterialRefreshListener(refreshListener);
        child_recyclerview = findViewById(R.id.child_recyclerview);
        add_child_usrname = findViewById(R.id.add_child_usrname);
        add_child_usrname.setOnClickListener(listener);
        child_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        accout_adapter = new My_Child_Accout_Adapter(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            refresh_child.autoRefresh();
        }
    }


    private void requsetWork() {
        My_Child_Accout_Lie entity = new My_Child_Accout_Lie(DataWarehouse.getBaseRequest(this));
        entity.setPageNo(pn);
        entity.setPageSize(ps);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<My_Child_Accort_Result> observer = new BaseObserver<My_Child_Accort_Result>(this) {

            @Override
            protected void onSuccees(String code, My_Child_Accort_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(My_Child_Account.this, baseResponse);
                    if (data.getDataListBeanList().size() != 0) {
                        displayData(data);
                        hideImageView();
                    } else {
                        if (accout_adapter.getList() != null && accout_adapter.getList().size() != 0) {
                            hideImageView();
                        } else {
                            showImageView();
                        }
                    }
                    refresh_child.finishRefresh();
                    refresh_child.finishRefreshLoadMore();
                }
            }
        };
        observer.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .getChildUser(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Child_Accort_Result>>bindToLifecycle()))
                .subscribe(observer);
}

    //取的展示数据的方法
    private void displayData(My_Child_Accort_Result data) {

        if (status == REFRESH) {

            if (dataListBeanList != null) {
                dataListBeanList.clear();
            }
            dataListBeanList = data.getDataListBeanList();
            accout_adapter.setDataResult(dataListBeanList);
            child_recyclerview.setAdapter(accout_adapter);
        }
        if (status == LOADMORE) {
            int size = dataListBeanList.size();
            List<My_Child_Accort_Result.DataListBean> dataListBeanList2 = data.getDataListBeanList();
            this.dataListBeanList.addAll(dataListBeanList2);
            accout_adapter.setDataResult(dataListBeanList);
            child_recyclerview.setAdapter(accout_adapter);
            child_recyclerview.scrollToPosition(size - 1);
            accout_adapter.notifyDataSetChanged();
        }

        refresh_child.finishRefresh();
        refresh_child.finishRefreshLoadMore();
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = REFRESH;
            pn = 1;
            requsetWork();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            requsetWork();
        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == add_child_usrname) {
                startActivity(new Intent(My_Child_Account.this, Add_Child_Account.class));
            } else if (v == iv_child_back) {
                finish();
            }

        }
    };

    @Override
    public void onDelete(Long id, int type) {//删除子账号列表
        if (id != null) {
            delete_child(id);
        }

    }

    private void delete_child(Long id) {
        Delete_Child_Accort entity = new Delete_Child_Accort(DataWarehouse.getBaseRequest(this));
        entity.setUserId(id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .deleteChildUsers(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {


                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(My_Child_Account.this, baseResponse);
                            ToastUtil.showShort(My_Child_Account.this, "删除子账号成功");
                        }

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
