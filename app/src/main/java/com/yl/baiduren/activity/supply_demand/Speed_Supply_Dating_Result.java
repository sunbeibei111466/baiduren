package com.yl.baiduren.activity.supply_demand;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.supply_demend_adapter.Demend_Hall_Adpater;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Collection_Supply_Body;
import com.yl.baiduren.entity.request_body.My_Mach_Body;
import com.yl.baiduren.entity.result.My_Supply_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/1/26.
 */

public class Speed_Supply_Dating_Result extends BaseActivity implements Demend_Hall_Adpater.Item_getId{
    private ImageView speed_result_back;
    private MaterialRefreshLayout refreshLayout;
    private MyRecyclerView recyclerView;
    private Demend_Hall_Adpater adapter;
    private long match_id;
    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private List<My_Supply_Result.DataListBean> dataList;
    @Override
    public int loadWindowLayout() {
        return R.layout.speed_suply_result;
    }

    @Override
    public void initViews() {
        //匹配id
        match_id = getIntent().getLongExtra("match_supply_id", 0);

        speed_result_back = findViewById(R.id.supply_result_back);//关闭
        speed_result_back.setOnClickListener(listener);
        refreshLayout = findViewById(R.id.supply_result_refresh);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = findViewById(R.id.supply_result_recyclerview);
        LinearLayoutManager manager =new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new Demend_Hall_Adpater(this);
        adapter.getItemString(this);

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            refreshLayout.autoRefresh();

        }
    }
    private void requestWork() {
        My_Mach_Body entity = new My_Mach_Body();

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size()!=0){
            entity.setAccessToken(baseRequests.get(0).getAccessToken());
            entity.setPlatform(2);
            entity.setLoginUsername(baseRequests.get(0).getLoginUsername());
            entity.setUid(baseRequests.get(0).getUid());
            entity.setUuid(baseRequests.get(0).getUuid());
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            entity.setSupplyId(match_id);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(this) {

                @Override
                protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(Speed_Supply_Dating_Result.this, baseResponse);
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            hideImageView();
                        } else {
                           if (data.getDataList()!=null&&data.getDataList().size()!=0){
                               hideImageView();
                           }else{
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
                    .supply_Mathes(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        }

    }
    private void collection_Suplly(Long supp_id) {//点击收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(this));
        entity.setNeedId(supp_id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(this) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Speed_Supply_Dating_Result.this, baseResponse);
                    ToastUtil.showShort(Speed_Supply_Dating_Result.this,"收藏成功");
                    refreshLayout.autoRefresh();
                }else {
                    ToastUtil.showShort(Speed_Supply_Dating_Result.this,"收藏失败");
                }

            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .collectNeed(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(baseObserver);

    }
    //抽取的展示数据的方法
    private void displayData(final My_Supply_Result data) {


        if (status == REFRESH) {
            if (dataList!=null){
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
    private View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v==speed_result_back){
                finish();
            }
        }
    };
    private MaterialRefreshListener refreshListener =new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status=REFRESH;
            pn=1;
            requestWork();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status=LOADMORE;
            pn=pn+1;
            requestWork();
        }
    };

    @Override
    public void setItemId(Long supplyId) {
        if (supplyId!=null){
            collection_Suplly(supplyId);
        }

    }
}
