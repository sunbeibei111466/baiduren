package com.yl.baiduren.fragment.my_supply_demend_match_speed;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.supply_demend_match_speed.Supply_Match_Speed_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
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
 * Created by sunbeibei on 2018/1/19.
 */

public class My_Supply_Match_Speed_Fragment extends BaseFragment {

    private MaterialRefreshLayout match_speed_refresh;
    private RecyclerView match_speed_recycleview;
    private Supply_Match_Speed_Adapter adapter;
    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private List<My_Supply_Result.DataListBean> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_supply_match_speed_fragment;
    }

    @Override
    public void initViews(View rootView) {
        match_speed_refresh = rootView.findViewById(R.id.match_speed_refresh);
        match_speed_refresh.setMaterialRefreshListener(refreshListener);
        match_speed_recycleview = rootView.findViewById(R.id.match_speed_recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        match_speed_recycleview.setLayoutManager(manager);
        adapter = new Supply_Match_Speed_Adapter(getActivity());


    }

    @Override
    public void onResume() {
        super.onResume();
        match_speed_refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                match_speed_refresh.autoRefresh();
            }
        }, 200);
    }

    private void requestWork() {

        if (UserInfoUtils.IsLogin(getActivity())) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(getActivity()));
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            entity.setType(1);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(getActivity()) {

                @Override
                protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(getActivity(), baseResponse);
                        if (data.getDataList().size()!=0){

                             displayData(data);
                             dataNotNullHideImageView();
                        }else{
                            if (adapter.getList()!=null&&adapter.getList().size()!=0){
                                dataNotNullHideImageView();
                            }else{
                                dataNullShowImageView();
                            }
                        }

                        match_speed_refresh.finishRefresh();
                        match_speed_refresh.finishRefreshLoadMore();

                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .mineSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            getActivity().finish();
        }
    }

    //抽取的展示数据的方法
    private void displayData(final My_Supply_Result data) {


        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            match_speed_recycleview.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Supply_Result.DataListBean> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            match_speed_recycleview.setAdapter(adapter);
            match_speed_recycleview.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }


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
}
