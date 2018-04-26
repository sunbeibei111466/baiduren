package com.yl.baiduren.fragment.debt_manger_fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_EnterPrise_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_PresonEntity;
import com.yl.baiduren.entity.result.DebtPersonList;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.debtmanagpreson.Debt_Person_Manger.childUserId;


/**
 * Created by sunbeibei on 2017/12/6.
 * 债事企业列表
 */

public class Debt_EnterPrise_Tabulation extends BaseFragment {

    public static int pn = 1;
    public static int ps = 8;

    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Debt_EnterPrise_Adapter adapter;
    private List<DebtPersonList.Mode> dataList;
    private Handler handler = new Handler();

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_enterprise_tabulation;
    }

    @Override
    public void initViews(View rootView) {

        refreshLayout = rootView.findViewById(R.id.refresh_enterprise);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = rootView.findViewById(R.id.debt_enterprise_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Debt_EnterPrise_Adapter(childUserId, getActivity());


    }


    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, 50);
    }


    public void getHttp() {


        //设置基础参数
        if (UserInfoUtils.IsLogin(getActivity())) {
            Debt_PresonEntity debtPresonEntity = new Debt_PresonEntity(DataWarehouse.getBaseRequest(getActivity()));
            debtPresonEntity.setType(1);
            debtPresonEntity.setPageNo(pn);
            debtPresonEntity.setPageSize(ps);
            if (childUserId != 0) {
                debtPresonEntity.setChildUserId(childUserId);
            }
            BaseObserver<DebtPersonList> baseObserver = new BaseObserver<DebtPersonList>(getActivity()) {
                @Override
                protected void onSuccees(String code, DebtPersonList data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            dataNotNullHideImageView();
                        } else {
                            if (adapter.getList() != null && adapter.getList().size() != 0) {
                                dataNotNullHideImageView();
                            }else {
                                dataNullShowImageView();
                            }
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishRefreshLoadMore();
                    }
                }
            };
            baseObserver.setStop(true);
            String json = Util.toJson(debtPresonEntity);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .getDebtPresonList(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<DebtPersonList>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            getActivity().finish();
        }

    }

    //抽取的展示数据的方法
    private void displayData(final DebtPersonList data) {


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
            List<DebtPersonList.Mode> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataList(dataList);
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
            getHttp();


        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            getHttp();

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
