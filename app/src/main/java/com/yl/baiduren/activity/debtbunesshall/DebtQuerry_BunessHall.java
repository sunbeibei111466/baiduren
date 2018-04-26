package com.yl.baiduren.activity.debtbunesshall;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.greendao.Hall_QuerryDao;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_Claimed_Adapter;
import com.yl.baiduren.adapter.HallQuerryTextAdapter;
import com.yl.baiduren.adapter.Yet_Debt_Claimed_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.Hall_Querry;
import com.yl.baiduren.entity.request_body.BunessHallQuerry;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.support.v7.widget.RecyclerView.*;

public class DebtQuerry_BunessHall extends BaseActivity implements View.OnClickListener, HallQuerryTextAdapter.IClickItem {

    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private ImageView iv_querry_hall_finish, delete_hall;
    private EditText et_hall_content;
    private Button btn_hall_sousuo;
    private MaterialRefreshLayout search_hall_refresh;
    private RecyclerView serch_hall_recyclview;
    private MyRecyclerView hall_serch_textList;
    private int item;
    private String content;
    private List<My_Record_Result.DataListBean> dataList;
    private Yet_Debt_Claimed_Adapter adapter;
    private Debt_Claimed_Adapter adapterClaimed;
    private HallQuerryTextAdapter hallQuerryTextAdapter;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt_querry__buness_hall;
    }

    @Override
    public void initViews() {
        item = getIntent().getIntExtra("item", 2);
        iv_querry_hall_finish = findViewById(R.id.iv_querry_hall_finish);
        iv_querry_hall_finish.setOnClickListener(this);
        et_hall_content = findViewById(R.id.et_hall_content);//搜索内容
        btn_hall_sousuo = findViewById(R.id.btn_hall_sousuo);//搜索按钮
        btn_hall_sousuo.setOnClickListener(this);
        delete_hall = findViewById(R.id.delete_hall);//删除历史记录
        delete_hall.setOnClickListener(this);
        search_hall_refresh = findViewById(R.id.search_hall_refresh);//刷新控件
        search_hall_refresh.setMaterialRefreshListener(refreshListener);
        hall_serch_textList = findViewById(R.id.hall_serch_textList);//标签  recyclerView
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        hall_serch_textList.setLayoutManager(layoutManager);
        hall_serch_textList.setHasFixedSize(true);
        hall_serch_textList.addOnScrollListener(scrollListener);
        serch_hall_recyclview = findViewById(R.id.serch_hall_recyclview);//recyclerView
        serch_hall_recyclview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Yet_Debt_Claimed_Adapter(this);//未摘牌适配器
        adapterClaimed = new Debt_Claimed_Adapter(DebtQuerry_BunessHall.this);//已摘牌适配器
        hallQuerryTextAdapter = new HallQuerryTextAdapter(this);//标签适配器
        setData();

    }

    private OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] firstVisibleItem = null;
            firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
            if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            layoutManager.invalidateSpanAssignments();
        }
    };


    @Override
    public void onClick(View v) {
        if (v == iv_querry_hall_finish) {//返回
            finish();
        } else if (v == btn_hall_sousuo) {//搜索按钮
            status = NORMAL;
            pn=1;
            if (dataList!=null){
                dataList.clear();
                adapterClaimed.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
            getHttp();
        } else if (v == delete_hall) {//删除历史纪录
            hallQuerryTextAdapter.getHallQuerryList().clear();
            hallQuerryTextAdapter.notifyDataSetChanged();
            GreenDaoUtils.getInstance(this).getHall_QuerryDao().deleteAll();
        }
    }


    public void getHttp() {

        content = et_hall_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShort(this, "搜索内容不能为空");
            return;
        }
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {

            BunessHallQuerry bunessHallQuerry = new BunessHallQuerry();
            bunessHallQuerry.setPageNo(pn);
            bunessHallQuerry.setPageSize(ps);
            bunessHallQuerry.setSearchType(1);
            bunessHallQuerry.setExactParam(content);
            bunessHallQuerry.setAccessToken(baseRequestList.get(0).getAccessToken());
            bunessHallQuerry.setLoginUsername(baseRequestList.get(0).getLoginUsername());
            bunessHallQuerry.setPlatform(2);
            bunessHallQuerry.setUid(baseRequestList.get(0).getUid());
            bunessHallQuerry.setUuid(baseRequestList.get(0).getUuid());
            if (item == 0) {//未摘牌
                bunessHallQuerry.setSettled(false);
            } else if (item == 1) {//摘牌
                bunessHallQuerry.setSettled(true);
            }


            String json = Util.toJson(bunessHallQuerry);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver baseObserver = new BaseObserver<My_Record_Result>(this) {
                @Override
                protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        Util.closeKeyboard(DebtQuerry_BunessHall.this);
                        UserInfoUtils.setUuid(DebtQuerry_BunessHall.this, baseResponse);
                        ifData(data);
                    }
                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .getByAccurate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }

    private void ifData(My_Record_Result data) {
        //查询数据库，没有时添加
        QueryBuilder queryBuilder = GreenDaoUtils.getInstance(DebtQuerry_BunessHall.this).getHall_QuerryDao().queryBuilder();
        List<Hall_Querry> hallQuerries = queryBuilder.where(Hall_QuerryDao.Properties.QuerryText.eq(content)).list();
        if (hallQuerries.size() == 0) { //查询后如果为空，添加
            Hall_Querry hallQuerry = new Hall_Querry();
            hallQuerry.setQuerryText(content);
            GreenDaoUtils.getInstance(DebtQuerry_BunessHall.this).getHall_QuerryDao().insert(hallQuerry);
        }

        setData();


        if (data.getDataList().size() != 0) {
            displayData(data);
            hideImageView();
        } else {
            if (item==0){
                if(adapter.getDataList()!=null&&adapter.getDataList().size()!=0){
                    hideImageView();
                }else {
                    showImageView();
                }
            }else{
                if(adapterClaimed.getList()!=null&&adapterClaimed.getList().size()!=0){
                    hideImageView();
                }else {
                    showImageView();
                }
            }

        }
        search_hall_refresh.finishRefresh();
        search_hall_refresh.finishRefreshLoadMore();
    }

    /**
     * 抽取的展示数据的方法  未摘牌
     */

    private void displayData(final My_Record_Result data) {
        if (status == NORMAL) {
            dataList = data.getDataList();
            if (item == 0) {
                adapter.setDataRecordList(dataList);
                serch_hall_recyclview.setAdapter(adapter);
            } else if (item == 1) {
                adapterClaimed.setList(dataList);
                serch_hall_recyclview.setAdapter(adapterClaimed);
            }

        }

        if (status == REFRESH) {
            dataList.clear();
            dataList = data.getDataList();
            if (item == 0) {
                adapter.setDataRecordList(dataList);
                serch_hall_recyclview.setAdapter(adapter);
            } else if (item == 1) {
                adapterClaimed.setList(dataList);
                serch_hall_recyclview.setAdapter(adapterClaimed);
            }

        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Record_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            if (item == 0) {
                adapter.setDataRecordList(this.dataList);
                serch_hall_recyclview.setAdapter(adapter);
                serch_hall_recyclview.scrollToPosition(size - 1);
                adapter.notifyDataSetChanged();
            } else if (item == 1) {
                adapterClaimed.setList(dataList);
                serch_hall_recyclview.setAdapter(adapterClaimed);
                serch_hall_recyclview.scrollToPosition(size - 1);
                adapterClaimed.notifyDataSetChanged();
            }

        }
    }


    //设置输入框内容
    @Override
    public void onClickItem(String text) {
        et_hall_content.setText(text);
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

    /**
     * 设置标签数据
     */
    private void setData() {
        //加载历史记录
        List<Hall_Querry> hallQuerries = GreenDaoUtils.getInstance(this).getHall_QuerryDao().loadAll();
        if (hallQuerries.size() != 0) {
            hallQuerryTextAdapter.setHallQuerryList(hallQuerries);
            hallQuerryTextAdapter.setClickItem(this);
            hallQuerryTextAdapter.notifyDataSetChanged();
            hall_serch_textList.setAdapter(hallQuerryTextAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }


}
