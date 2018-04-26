package com.yl.baiduren.activity.supply_demand;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Assets_Dispose_Adapter;
import com.yl.baiduren.adapter.Debt_Type_Adapter;
import com.yl.baiduren.adapter.MyAssets_Dispose_Adapter;
import com.yl.baiduren.adapter.Serch_Type_Adapter;
import com.yl.baiduren.adapter.my_collection_adapter.My_Demend_Collection_Adapter;
import com.yl.baiduren.adapter.my_collection_adapter.My_Supply_Collection_Adapter;
import com.yl.baiduren.adapter.my_supply_demend_apapger.My_Demend_Adapter;
import com.yl.baiduren.adapter.my_supply_demend_apapger.My_Supply_Adpater;
import com.yl.baiduren.adapter.supply_demend_adapter.Demend_Hall_Adpater;
import com.yl.baiduren.adapter.supply_demend_adapter.Supply_Hall_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Collection_Supply_Body;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.result.Debt_Type_Result;
import com.yl.baiduren.entity.result.My_Supply_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/1/23.
 */

public class Hall_Supply_Resch extends BaseActivity implements RadioGroup.OnCheckedChangeListener, Supply_Hall_Adapter.Item_getId, Demend_Hall_Adpater.Item_getId, My_Demend_Collection_Adapter.CancleColection, My_Supply_Collection_Adapter.CancleColection {

    private ImageView supply_recher_finish;
    private Button btn_recher_sousuo;
    private MyRecyclerView supply_serch_textList;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private int pn = 1;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 0;
    private Serch_Type_Adapter serch_type_adapter;
    private int type = 0;  //0 供应 1 常量
    private String comePager;
    private Supply_Hall_Adapter supply_hall_adapter;
    private List<My_Supply_Result.DataListBean> dataList;
    private My_Supply_Adpater my_supply_adpater;
    private My_Supply_Collection_Adapter my_supply_collection_adapter;
    private Demend_Hall_Adpater demend_hall_adpater;
    private My_Demend_Adapter my_demend_adapter;
    private My_Demend_Collection_Adapter my_demend_collection_adapter;
    private Assets_Dispose_Adapter assets_dispose_adapter;
    private MyAssets_Dispose_Adapter myAssets_dispose_adapter;
    private MyRecyclerView supply_serch_texttype;
    private Debt_Type_Adapter debt_type_adapter;


    @Override
    public int loadWindowLayout() {
        return R.layout.hall_supply_recher;
    }

    @Override
    public void initViews() {
        comePager = getIntent().getStringExtra("comePager");
        RadioGroup group = findViewById(R.id.group_pa);
        group.setOnCheckedChangeListener(this);
        LinearLayout debt_type_line = findViewById(R.id.debt_type_line);
        supply_recher_finish = findViewById(R.id.supply_recher_finish);//关闭页
        supply_recher_finish.setOnClickListener(listener);
        btn_recher_sousuo = findViewById(R.id.btn_recher_sousuo);//搜索
        btn_recher_sousuo.setOnClickListener(listener);
        supply_serch_texttype = findViewById(R.id.supply_serch_texttype);//债事类型选框
        debt_type_adapter = new Debt_Type_Adapter(this);
        supply_serch_texttype.setLayoutManager(new GridLayoutManager(this, 4));
        supply_serch_textList = findViewById(R.id.supply_serch_textList);//记录列表
        supply_serch_textList.setLayoutManager(new GridLayoutManager(this, 4));
        serch_type_adapter = new Serch_Type_Adapter(this);
        refreshLayout = findViewById(R.id.search_supply_refresh);//内容刷新
        refreshLayout.setMaterialRefreshListener(refreshListener);
        recyclerView = findViewById(R.id.serch_supply_recyclview);//内容列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        supply_hall_adapter = new Supply_Hall_Adapter(this);//大厅供应适配器
        supply_hall_adapter.getItemString(this);
        my_supply_adpater = new My_Supply_Adpater(this);//我的供应适配器
        my_supply_collection_adapter = new My_Supply_Collection_Adapter(this);//我的收藏供应适配器
        my_supply_collection_adapter.Item_Cancle(this);
        demend_hall_adpater = new Demend_Hall_Adpater(this);//大厅需求适配器
        demend_hall_adpater.getItemString(this);
        my_demend_adapter = new My_Demend_Adapter(this);//我的需求适配器
        my_demend_collection_adapter = new My_Demend_Collection_Adapter(this);//我的收藏需求适配器
        my_demend_collection_adapter.Item_Cancle(this);
        assets_dispose_adapter = new Assets_Dispose_Adapter(this);//资产大厅适配器
        myAssets_dispose_adapter = new MyAssets_Dispose_Adapter(this);//我的资产适配器
        if (comePager != null) {//从资产大厅或者我的资产跳转
            if (comePager.equals(Constant.ZC_DT) || comePager.equals(Constant.MY_ZC)) {
                group.setVisibility(View.GONE);
            }else if(comePager.equals(Constant.GX_DT)||comePager.equals(Constant.MY_GX_DT)||comePager.equals(Constant.MY_SC_DT)){//从供需大厅，我的供需，供需收藏
                debt_type_line.setVisibility(View.GONE);
            }

        }

        getType();


    }

    /**
     * 获取搜索类型
     */
    public void getType() {//请求供应数据类型
        com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
        String json = Util.toJson(baseRequest);//转成json

        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getAlltype(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Debt_Type_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Debt_Type_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Debt_Type_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            List<Debt_Type_Result.CategoryDO> ass_list = data.getClist();//资产类别
                            List<Debt_Type_Result.TypesDO> tlist = data.getTlist();//债事类别
                            if (ass_list.size()!=0&&tlist.size()!=0){
                                debt_type_adapter.setList(tlist);
                                supply_serch_texttype.setAdapter(debt_type_adapter);
                                serch_type_adapter.setList(ass_list);
                                supply_serch_textList.setAdapter(serch_type_adapter);
                            }

                        }
                    }
                });

    }



    private void requestWork() {//大厅供应
        My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
        entity.setPageNo(pn);
        int ps = 8;
        entity.setPageSize(ps);
        if (comePager.equals(Constant.ZC_DT) || comePager.equals(Constant.MY_ZC)) {//从资产处置跳转
            entity.setType(3);
        } else {
            entity.setType(1);
        }
        if (debt_type_adapter.getSelectedItem().size()!=0){
            entity.settIds(debt_type_adapter.getSelectedItem());
        }
        if (serch_type_adapter.getSelectedItem().size()!=0){
            entity.setcIds(serch_type_adapter.getSelectedItem());
        }

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<My_Supply_Result> baseObserver = new BaseObserver<My_Supply_Result>(this) {

            @Override
            protected void onSuccees(String code, My_Supply_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Hall_Supply_Resch.this, baseResponse);

                    if (data.getDataList().size() != 0) {
                        displayData(data);
                        hideImageView();
                    } else {
                        isShowContent();
                    }
                    displayData(data);
                    refreshLayout.finishRefresh();
                    refreshLayout.finishRefreshLoadMore();

                }
            }

            @Override
            protected void onCodeError(String code, String mess) throws Exception {
                super.onCodeError(code, mess);
                refreshLayout.finishRefresh();
                refreshLayout.finishRefreshLoadMore();
                ToastUtil.showShort(Hall_Supply_Resch.this, mess);
            }
        };
        RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
        Observable<BaseEntity<My_Supply_Result>> observable = null;
        if (comePager.equals(Constant.ZC_DT)) {//资产大厅
            observable = retrofitService.getHallSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (comePager.equals(Constant.MY_ZC)) {//我的资产
            observable = retrofitService.mineSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));

        } else if (comePager.equals(Constant.GX_DT)) {//供应大厅
            if (type == 0) {
                observable = retrofitService.getHallSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            } else if (type == 1) {
                observable = retrofitService.getHall_Demend(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            }
        } else if (comePager.equals(Constant.MY_GX_DT)) {//我的供应
            if (type == 0) {
                observable = retrofitService.mineSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            } else if (type == 1) {
                observable = retrofitService.getMyNeed(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            }
        } else if (comePager.equals(Constant.MY_SC_DT)) {//我的收藏
            if (type == 0) {
                observable = retrofitService.getMycollectSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            } else if (type == 1) {
                observable = retrofitService.getMyCollectNeeds(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            }
        }

        observable.compose(compose(this.<BaseEntity<My_Supply_Result>>bindToLifecycle())).subscribe(baseObserver);

    }

    private void isShowContent() {
        if (comePager.equals(Constant.ZC_DT)) {//资产大厅
            if (assets_dispose_adapter.getList() != null && assets_dispose_adapter.getList().size() != 0) {
                hideImageView();
            } else {
                showImageView();
            }


        } else if (comePager.equals(Constant.MY_ZC)) {//我的资产
            if (myAssets_dispose_adapter.getList() != null && myAssets_dispose_adapter.getList().size() != 0) {
                hideImageView();
            } else {
                showImageView();
            }

        } else if (comePager.equals(Constant.GX_DT)) {//供应大厅
            if (type == 0) {
                if (supply_hall_adapter.getList() != null && supply_hall_adapter.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }
            } else if (type == 1) {
                if (demend_hall_adpater.getList() != null && demend_hall_adpater.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }
            }
        } else if (comePager.equals(Constant.MY_GX_DT)) {//我的供应
            if (type == 0) {
                if (my_supply_adpater.getList() != null && my_supply_adpater.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }

            } else if (type == 1) {
                if (my_demend_adapter.getList() != null && my_demend_adapter.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }
            }
        } else if (comePager.equals(Constant.MY_SC_DT)) {//我的收藏
            if (type == 0) {
                if (my_supply_collection_adapter.getList() != null && my_supply_collection_adapter.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }
            } else if (type == 1) {
                if (my_demend_collection_adapter.getList() != null && my_demend_collection_adapter.getList().size() != 0) {
                    hideImageView();
                } else {
                    showImageView();
                }
            }
        }
    }

    private void collection_Suplly(Long supp_id) {//点击收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(this));
        if (type == 0) {
            entity.setSupplyId(supp_id);
        } else if (type == 1) {
            entity.setNeedId(supp_id);
        }

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(this) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Hall_Supply_Resch.this, baseResponse);
                    ToastUtil.showShort(Hall_Supply_Resch.this, "收藏成功");
                    refreshLayout.autoRefresh();
                } else {
                    ToastUtil.showShort(Hall_Supply_Resch.this, "收藏失败");
                }

            }
        };
        baseObserver.setStop(true);
        RetrofitService server = RetrofitHelper.getInstance(Hall_Supply_Resch.this).getServer();
        Observable<BaseEntity<String>> baseEntityObservable = null;
        if (type == 0) {

            baseEntityObservable = server.collectSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 1) {
            baseEntityObservable = server.collectNeed(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        }

        baseEntityObservable.compose(compose(this.<BaseEntity<String>>bindToLifecycle())).subscribe(baseObserver);

    }

    private void cancle_Collection_Suplly(Long supp_id) {//点击取消收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(this));
        if (type == 0) {
            entity.setSupplyId(supp_id);
        } else if (type == 1) {
            entity.setNeedId(supp_id);
        }
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(this) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Hall_Supply_Resch.this, baseResponse);
                    ToastUtil.showShort(Hall_Supply_Resch.this, "取消成功");
                    refreshLayout.autoRefresh();
                } else {
                    ToastUtil.showShort(Hall_Supply_Resch.this, "取消失败");
                }

            }
        };

        RetrofitService cancleserver = RetrofitHelper.getInstance(this).getServer();
        Observable<BaseEntity<String>> canclebaseEntityObservable = null;
        if (type == 0) {
            canclebaseEntityObservable = cancleserver.cancleCollectSupply(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 1) {
            canclebaseEntityObservable = cancleserver.cancleCollectNeeds(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        }

        canclebaseEntityObservable.compose(compose(this.<BaseEntity<String>>bindToLifecycle())).subscribe(baseObserver);
    }


    //抽取的展示数据的方法
    private void displayData(final My_Supply_Result data) {

        if (status == NORMAL) {
            dataList = data.getDataList();
            if (comePager.equals(Constant.ZC_DT)) {//资产大厅
                assets_dispose_adapter.setDataList(dataList);
                recyclerView.setAdapter(assets_dispose_adapter);
            } else if (comePager.equals(Constant.MY_ZC)) {
                myAssets_dispose_adapter.setDataListBeans(dataList);
                recyclerView.setAdapter(myAssets_dispose_adapter);
            } else if (comePager.equals(Constant.GX_DT)) {//大厅供需
                if (type == 0) {//供应端
                    supply_hall_adapter.setDataList(dataList);
                    recyclerView.setAdapter(supply_hall_adapter);
                } else if (type == 1) {//需求端
                    demend_hall_adpater.setDataList(dataList);
                    recyclerView.setAdapter(demend_hall_adpater);
                }
            } else if (comePager.equals(Constant.MY_GX_DT)) {
                if (type == 0) {//我的供应
                    my_supply_adpater.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_adpater);
                } else if (type == 1) {//我的需求
                    my_demend_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_adapter);
                }
            } else if (comePager.equals(Constant.MY_SC_DT)) {//我的收藏大厅
                if (type == 0) {//收藏供应
                    my_supply_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_collection_adapter);
                } else if (type == 1) {
                    my_demend_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_collection_adapter);
                }
            }

        }
        if (status == REFRESH) {
            dataList.clear();
            dataList = data.getDataList();
            if (comePager.equals(Constant.ZC_DT)) {//资产大厅
                assets_dispose_adapter.setDataList(dataList);
                recyclerView.setAdapter(assets_dispose_adapter);
            } else if (comePager.equals(Constant.MY_ZC)) {//我的资产
                myAssets_dispose_adapter.setDataListBeans(dataList);
                recyclerView.setAdapter(myAssets_dispose_adapter);
            } else if (comePager.equals(Constant.GX_DT)) {//大厅供需
                if (type == 0) {//供应端
                    supply_hall_adapter.setDataList(dataList);
                    recyclerView.setAdapter(supply_hall_adapter);
                } else if (type == 1) {//需求端
                    demend_hall_adpater.setDataList(dataList);
                    recyclerView.setAdapter(demend_hall_adpater);
                }
            } else if (comePager.equals(Constant.MY_GX_DT)) {
                if (type == 0) {//我的供应
                    my_supply_adpater.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_adpater);
                } else if (type == 1) {//我的需求
                    my_demend_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_adapter);
                }
            } else if (comePager.equals(Constant.MY_SC_DT)) {//我的收藏大厅
                if (type == 0) {//收藏供应
                    my_supply_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_collection_adapter);
                } else if (type == 1) {
                    my_demend_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_collection_adapter);
                }
            }

        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Supply_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            if (comePager.equals(Constant.ZC_DT)) {//资产大厅
                assets_dispose_adapter.setDataList(dataList);
                recyclerView.setAdapter(assets_dispose_adapter);
                recyclerView.scrollToPosition(size - 1);
                assets_dispose_adapter.notifyDataSetChanged();
            } else if (comePager.equals(Constant.MY_ZC)) {//我的资产
                myAssets_dispose_adapter.setDataListBeans(dataList);
                recyclerView.setAdapter(myAssets_dispose_adapter);
                recyclerView.scrollToPosition(size - 1);
                myAssets_dispose_adapter.notifyDataSetChanged();
            } else if (comePager.equals(Constant.GX_DT)) {//大厅供需
                if (type == 0) {//供应端
                    supply_hall_adapter.setDataList(dataList);
                    recyclerView.setAdapter(supply_hall_adapter);
                    recyclerView.scrollToPosition(size - 1);
                    supply_hall_adapter.notifyDataSetChanged();
                } else if (type == 1) {//需求端
                    demend_hall_adpater.setDataList(dataList);
                    recyclerView.setAdapter(demend_hall_adpater);
                    recyclerView.scrollToPosition(size - 1);
                    demend_hall_adpater.notifyDataSetChanged();
                }
            } else if (comePager.equals(Constant.MY_GX_DT)) {
                if (type == 0) {//我的供应
                    my_supply_adpater.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_adpater);
                    recyclerView.scrollToPosition(size - 1);
                    my_supply_adpater.notifyDataSetChanged();
                } else if (type == 1) {//我的需求
                    my_demend_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_adapter);
                    recyclerView.scrollToPosition(size - 1);
                    my_demend_adapter.notifyDataSetChanged();
                }
            } else if (comePager.equals(Constant.MY_SC_DT)) {//我的收藏大厅
                if (type == 0) {//收藏供应
                    my_supply_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_supply_collection_adapter);
                    recyclerView.scrollToPosition(size - 1);
                    my_supply_collection_adapter.notifyDataSetChanged();
                } else if (type == 1) {//收藏需求
                    my_demend_collection_adapter.setDataList(dataList);
                    recyclerView.setAdapter(my_demend_collection_adapter);
                    recyclerView.scrollToPosition(size - 1);
                    my_demend_collection_adapter.notifyDataSetChanged();
                }
            }

        }
    }


    //点击按钮监听
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_recher_sousuo) {
                pn=1;
                status = NORMAL;
                if (debt_type_adapter.getSelectedItem().size()==0&&serch_type_adapter.getSelectedItem().size() == 0){
                    ToastUtil.showShort(Hall_Supply_Resch.this, "请选择搜索类型");
                    return;
                }

                if (dataList != null) {
                    dataList.clear();
                    supply_hall_adapter.notifyDataSetChanged();
                    my_supply_adpater.notifyDataSetChanged();
                    my_supply_collection_adapter.notifyDataSetChanged();
                    demend_hall_adpater.notifyDataSetChanged();
                    my_demend_adapter.notifyDataSetChanged();
                    my_demend_collection_adapter.notifyDataSetChanged();
                    assets_dispose_adapter.notifyDataSetChanged();
                    myAssets_dispose_adapter.notifyDataSetChanged();
                }
                requestWork();
            } else if (v == supply_recher_finish) {
                finish();
            }

        }
    };
    //刷新列表监听
    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = NORMAL;
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.hall_radio1://供应端
                type = 0;
                break;
            case R.id.hall_radio2://需求端
                type = 1;
                break;
        }

    }

    @Override
    public void setItemId(Long supplyId) {//大厅点击收藏
        if (supplyId != null) {
            collection_Suplly(supplyId);
        }


    }

    @Override
    public void setCancle(Long cancle_id) {//我的收藏点击取消收藏
        if (cancle_id != null) {
            cancle_Collection_Suplly(cancle_id);
        }

    }
}
