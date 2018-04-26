package com.yl.baiduren.activity.mypager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.My_Bill_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.request_body.My_Bill_Details_Body;
import com.yl.baiduren.entity.result.My_Bill_Result;
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
 * Created by sunbeibei on 2017/12/20.
 * 我的 账单
 */

public class My_Bill extends BaseActivity implements My_Bill_Adapter.IDeleteItem {

    private ImageView my_bill_back;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private android.os.Handler handler = new android.os.Handler();
    private List<My_Bill_Result.DataListBean> dataList;
    private My_Bill_Adapter adapter;


    @Override
    public int loadWindowLayout() {
        return R.layout.my_bill;
    }

    @Override
    public void initViews() {
        my_bill_back = findViewById(R.id.my_bill_back);
        my_bill_back.setOnClickListener(listener);
        refreshLayout = findViewById(R.id.my_bill_refresh);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = findViewById(R.id.my_bill_recylcer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new My_Bill_Adapter(this);
        adapter.setiDeleteItem(this);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            status = REFRESH;
            refreshLayout.autoRefresh();

        }
    }

    private void requsetWorkBill() {

        if (UserInfoUtils.IsLogin(this)) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<My_Bill_Result> baseObserver = new BaseObserver<My_Bill_Result>(this) {

                @Override
                protected void onSuccees(String code, My_Bill_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(My_Bill.this, baseResponse);
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
                    .getMyBill(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))

                    .compose(compose(this.<BaseEntity<My_Bill_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }

    }

    //抽取的展示数据的方法
    private void displayData(final My_Bill_Result data) {


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
            List<My_Bill_Result.DataListBean> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == my_bill_back) {
                finish();
            }

        }
    };
    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            pn = 1;
            status = REFRESH;
            requsetWorkBill();


        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            pn = pn + 1;
            status = LOADMORE;
            requsetWorkBill();

        }
    };

    /**
     * 删除账单
     *
     * @param id
     */
    @Override
    public void onClickDeleteItem(Long id, final int position) {

        Log.e("-----删除---id-----", id + "");
        My_Bill_Details_Body body = new My_Bill_Details_Body(DataWarehouse.getBaseRequest(this));
        body.setId(id);
        String json = Util.toJson(body);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .delete_Bill(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(My_Bill.this, baseResponse);
                            ToastUtil.showShort(My_Bill.this, "订单删除成功");
                            adapter.getList().remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
