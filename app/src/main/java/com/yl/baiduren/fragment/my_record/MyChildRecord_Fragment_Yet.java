package com.yl.baiduren.fragment.my_record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.MyChildRecord_Fragmnet_Yet_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Record_Entity;
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

import static com.yl.baiduren.activity.debtbunesshall.MyChildRecord_Activity.childUserId;


/**
 * Created by sunbeibei on 2017/12/15.
 * 子账号未摘牌备案列表
 */

public class MyChildRecord_Fragment_Yet extends BaseFragment {

    private MaterialRefreshLayout layout;
    private RecyclerView recyclerView;

    private int pn = 1;

    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private List<My_Record_Result.DataListBean> dataList;
    private MyChildRecord_Fragmnet_Yet_Adapter adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_record_fragment_yet;
    }

    @Override
    public void initViews(View rootView) {
        layout = rootView.findViewById(R.id.rcord_yet_refresh);
        layout.setMaterialRefreshListener(refreshListener);
        recyclerView = rootView.findViewById(R.id.record_yet_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new MyChildRecord_Fragmnet_Yet_Adapter(getActivity(), childUserId);
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
        My_Record_Entity entity = new My_Record_Entity(DataWarehouse.getBaseRequest(getActivity()));
        if (UserInfoUtils.IsLogin(getActivity())) {
            entity.setSettled(false);
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            if (childUserId != 0) { //子账号id不为0时,查询子账号列表
                entity.setChildUserId(childUserId);
            }
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .getDebtList(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                    .subscribe(new BaseObserver<My_Record_Result>(getActivity()) {


                        @Override
                        protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {

                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(getActivity(), baseResponse);
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
                    });
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
            if (data.getDataList().size() != 0) {
                dataList = data.getDataList();
                adapter.setDataList(dataList);
                recyclerView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();

        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Record_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
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
}


