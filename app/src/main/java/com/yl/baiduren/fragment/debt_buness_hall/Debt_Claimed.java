package com.yl.baiduren.fragment.debt_buness_hall;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_Claimed_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.Debt_Listener;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Record_Entity;
import com.yl.baiduren.entity.request_body.QuerryEntity;
import com.yl.baiduren.entity.result.My_Record_Result;
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
 * Created by sunbeibei on 2017/12/7.
 * 已认领债事
 */

public class Debt_Claimed extends BaseFragment implements Debt_Listener {
    private int pn = 1;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private List<My_Record_Result.DataListBean> dataList;
    private MaterialRefreshLayout layout;
    private RecyclerView recyclerView;
    private Debt_Claimed_Adapter adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_claimed;
    }

    @Override
    public void initViews(View rootView) {

        layout = rootView.findViewById(R.id.claimed_refresh);
        layout.setMaterialRefreshListener(refreshListener);
        recyclerView = rootView.findViewById(R.id.claimed_recyclview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new Debt_Claimed_Adapter(getActivity());


    }

    @Override
    public void onResume() {
        super.onResume();
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.autoRefresh();
            }
        }, 200);
    }

    private void requestWord() {

        if (UserInfoUtils.IsLogin(getActivity())) {
            My_Record_Entity entity = new My_Record_Entity(DataWarehouse.getBaseRequest(getActivity()));
            entity.setSettled(true);
            entity.setPageNo(pn);
            entity.setPageSize(8);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            BaseObserver<My_Record_Result> baseObserver = new BaseObserver<My_Record_Result>(getActivity()) {
                @Override
                protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            dataNotNullHideImageView();
                        } else {
                            if (adapter.getList() != null && adapter.getList().size() != 0) {
                                dataNotNullHideImageView();
                            } else {
                                dataNullShowImageView();
                            }
                        }

                        layout.finishRefresh();
                        layout.finishRefreshLoadMore();
                    }
                }
            };
            baseObserver.setStop(true);//不显示进度条
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .getRecordDebtList(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            getActivity().finish();
        }


    }

    //抽取的展示数据的方法
    private void displayData(final My_Record_Result data) {


        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setList(dataList);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Record_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            adapter.setList(this.dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }


    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = REFRESH;
            pn = 1;
            requestWord();

        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            requestWord();

        }
    };


    @Override
    public void onTimeListener(Long start, Long end, boolean isSettled) {

        LUtils.e("---时间查询----");
        QuerryEntity querryEntity = new QuerryEntity(DataWarehouse.getBaseRequest(getActivity()));
        querryEntity.setMinCreateTime(start);
        querryEntity.setMaxCreateTime(end);
        querryEntity.setPageNo(pn);
        querryEntity.setPageSize(100);
        querryEntity.setSettled(isSettled);//是否摘牌
        status = REFRESH;
        getHttpData(querryEntity);
    }

    @Override
    public void onTypeListener(List<Long> types, boolean isSettled) {
        LUtils.e("----类型查询----");
        QuerryEntity querryEntity = new QuerryEntity(DataWarehouse.getBaseRequest(getActivity()));
        querryEntity.settIds(types);
        querryEntity.setPageNo(pn);
        querryEntity.setPageSize(100);
        querryEntity.setSettled(isSettled);//是否摘牌
        status = REFRESH;
        getHttpData(querryEntity);
    }

    @Override
    public void onAreaListener(String quCode, boolean isSettled) {
        LUtils.e("----地址查询----");
        QuerryEntity querryEntity = new QuerryEntity(DataWarehouse.getBaseRequest(getActivity()));
        querryEntity.setAreaCode(quCode);
        querryEntity.setPageNo(pn);
        querryEntity.setPageSize(100);
        querryEntity.setSettled(isSettled);//是否摘牌
        status = REFRESH;
        getHttpData(querryEntity);
    }

    @Override
    public void onSaiXuanListener(Long createTimeMin, Long createTimeMax, String areaCode, Long amount, Double jiezai, Integer assertId, Integer demandId, boolean isSettled) {
        QuerryEntity querryEntity = new QuerryEntity(DataWarehouse.getBaseRequest(getActivity()));
        querryEntity.setMinCreateTime(createTimeMin);
        querryEntity.setMaxCreateTime(createTimeMax);
        querryEntity.setAreaCode(areaCode);
        querryEntity.setAmount(amount);
        querryEntity.setCommission(jiezai);
        querryEntity.setAssetCategoryId(assertId);
        querryEntity.setDemandCategoryId(demandId);
        querryEntity.setPageNo(pn);
        querryEntity.setPageSize(100);
        querryEntity.setSettled(isSettled);//是否摘牌
        status = REFRESH;
        getHttpData(querryEntity);
    }


    /**
     * 查询
     *
     * @param querryEntity
     */
    public void getHttpData(QuerryEntity querryEntity) {

        String json = Util.toJson(querryEntity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this.getActivity()).getServer()
                .getByConditions(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<My_Record_Result>(this.getActivity()) {


                    @Override
                    protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            if (dataList != null) {
                                dataList.clear();
                                adapter.notifyDataSetChanged();
                            }
                            if (data.getDataList().size() != 0) {
                                displayData(data);
                                dataNotNullHideImageView();
                            } else {
                                if (adapter.getList() != null && adapter.getList().size() != 0) {
                                    dataNotNullHideImageView();
                                } else {
                                    dataNullShowImageView();
                                }
                            }
                        }
                        layout.finishRefresh();
                        layout.finishRefreshLoadMore();
                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {
                        super.onCodeError(code, mess);
                        layout.finishRefresh();
                        layout.finishRefreshLoadMore();
                    }
                });
    }

}
