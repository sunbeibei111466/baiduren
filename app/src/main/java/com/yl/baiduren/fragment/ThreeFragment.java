package com.yl.baiduren.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_Angle_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Record_Entity;
import com.yl.baiduren.entity.result.Debt_Angle_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ThreeFragment extends BaseFragment {
    private MaterialRefreshLayout angle_refresh;
    private RecyclerView recyclerView;
    private Debt_Angle_Adapter angle_adapter;
    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private static int status = 1;
    private List<Debt_Angle_Result.Angle> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.fragment_three;
    }

    @Override
    public void initViews(View rootView) {
        angle_refresh = rootView.findViewById(R.id.angle_refresh);//刷新
        angle_refresh.setMaterialRefreshListener(refreshListener);
        recyclerView = rootView.findViewById(R.id.angle_recycle);//列表
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        angle_adapter = new Debt_Angle_Adapter(getActivity());


    }
    @Override
    public void onResume() {
        super.onResume();
        angle_refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                angle_refresh.autoRefresh();
            }
        }, 200);
    }

    private void requstWork() {
        if (UserInfoUtils.IsLogin(getActivity())) {
            My_Record_Entity entity = new My_Record_Entity(DataWarehouse.getBaseRequest(getActivity()));
            entity.setPageNo(pn);
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            BaseObserver baseObserver = new BaseObserver<Debt_Angle_Result>(this.getActivity()) {
                @Override
                protected void onSuccees(String code, Debt_Angle_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        if (data.getDataList().size() != 0) {
                            displayData(data);
                            dataNotNullHideImageView();
                        } else {
                            if (angle_adapter.getList()!= null && angle_adapter.getList().size() != 0) {
                                dataNotNullHideImageView();
                            } else {
                                dataNullShowImageView();
                            }
                        }

                        angle_refresh.finishRefresh();
                        angle_refresh.finishRefreshLoadMore();
                    }
                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this.getActivity()).getServer()
                    .debtAngel(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<Debt_Angle_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            getActivity().finish();
        }
    }
    //抽取的展示数据的方法
    private void displayData(final Debt_Angle_Result data) {


        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            angle_adapter.setList(dataList);
            recyclerView.setAdapter(angle_adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<Debt_Angle_Result.Angle> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            angle_adapter.setList(this.dataList);
            recyclerView.setAdapter(angle_adapter);
            recyclerView.scrollToPosition(size - 1);
            angle_adapter.notifyDataSetChanged();
        }
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = REFRESH;
            pn = 1;
            requstWork();

        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            requstWork();
        }
    };


}
