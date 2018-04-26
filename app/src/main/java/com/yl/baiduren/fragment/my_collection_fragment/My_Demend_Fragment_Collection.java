package com.yl.baiduren.fragment.my_collection_fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.my_collection_adapter.My_Demend_Collection_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Collection_Supply_Body;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.result.My_Supply_Result;
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
 * Created by sunbeibei on 2018/1/19.
 * 我的需求端收藏
 */

public class My_Demend_Fragment_Collection extends BaseFragment implements My_Demend_Collection_Adapter.CancleColection {

    private MaterialRefreshLayout my_collect_demend_refresh;
    private RecyclerView my_collect_demend_recycleview;
    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private List<My_Supply_Result.DataListBean> dataList;
    private My_Demend_Collection_Adapter adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_demend_fragment_collecton;
    }

    @Override
    public void initViews(View rootView) {
        my_collect_demend_refresh = rootView.findViewById(R.id.my_collect_demend_refresh);
        my_collect_demend_refresh.setMaterialRefreshListener(refreshListener);
        my_collect_demend_recycleview = rootView.findViewById(R.id.my_collect_demend_recycleview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        my_collect_demend_recycleview.setLayoutManager(manager);
        adapter = new My_Demend_Collection_Adapter(getActivity());
        adapter.Item_Cancle(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        my_collect_demend_refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                my_collect_demend_refresh.autoRefresh();
            }
        }, 200);
    }

    private void requestWork() {

        if (UserInfoUtils.IsLogin(getActivity())) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(getActivity()));
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(getActivity()) {

                @Override
                protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(getActivity(), baseResponse);

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
                        my_collect_demend_refresh.finishRefresh();
                        my_collect_demend_refresh.finishRefreshLoadMore();

                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .getMyCollectNeeds(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            getActivity().finish();
        }
    }

    private void cancle_Collection_Needs(Long supp_id) {//点击取消收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(getActivity()));
        entity.setNeedId(supp_id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(getActivity()) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(getActivity(), baseResponse);
                    ToastUtil.showShort(getActivity(), "取消成功");
                    my_collect_demend_refresh.autoRefresh();
                } else {
                    ToastUtil.showShort(getActivity(), "取消失败");
                }

            }
        };
        RetrofitHelper.getInstance(getActivity()).getServer()
                .cancleCollectNeeds(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    //抽取的展示数据的方法
    private void displayData(final My_Supply_Result data) {


        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            my_collect_demend_recycleview.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Supply_Result.DataListBean> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            my_collect_demend_recycleview.setAdapter(adapter);
            my_collect_demend_recycleview.scrollToPosition(size - 1);
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

    @Override
    public void setCancle(Long cancle_id) {
        if (cancle_id != null) {
            cancle_Collection_Needs(cancle_id);
        }


    }
}
