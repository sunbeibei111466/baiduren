package com.yl.baiduren.activity.debtmanagpreson;

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
import com.greendao.DebtPersonDao;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.DebtSearchTextAdapter;
import com.yl.baiduren.adapter.Debt_Preson_Adatper;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.DebtPerson;
import com.yl.baiduren.entity.request_body.DebtPersonSearch;
import com.yl.baiduren.entity.result.DebtPersonList;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/7.
 */

public class Debt_Search_Person extends BaseActivity implements View.OnClickListener, DebtSearchTextAdapter.IClickItem {

    public static int pn = 1;
    public static int ps = 20;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private ImageView iv_title_left;
    private EditText et_content;
    private Button btn_suo;
    private ImageView delete;
    private String content;
    private RecyclerView serch_recyclview, serch_textList;
    private MaterialRefreshLayout search_refresh;
    private DebtSearchTextAdapter debtSearchTextAdapter;
    private Debt_Preson_Adatper adatper;
    private List<DebtPersonList.Mode> dataList;


    @Override
    public int loadWindowLayout() {
        return R.layout.debt_search_preson;
    }

    @Override
    public void initViews() {
//        personList=new ArrayList<>();
        String threePage = getIntent().getStringExtra("threePage");
        int currentItem = getIntent().getIntExtra("currentItem", 2);
        iv_title_left = findViewById(R.id.iv_title_left);//关闭页面
        iv_title_left.setOnClickListener(this);
        et_content = findViewById(R.id.et_content);//输入搜索内容
        btn_suo = findViewById(R.id.btn_sousuo);//点击搜索
        btn_suo.setOnClickListener(this);
        delete = findViewById(R.id.delete);//删除搜索记录
        delete.setOnClickListener(this);
        serch_textList = findViewById(R.id.serch_textList);
        serch_textList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        debtSearchTextAdapter = new DebtSearchTextAdapter(this);
        serch_recyclview = findViewById(R.id.serch_recyclview);
        serch_recyclview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        search_refresh = findViewById(R.id.search_refresh);//
        search_refresh.setMaterialRefreshListener(refreshListener);
        adatper = new Debt_Preson_Adatper(0l, Debt_Search_Person.this);
        adatper.setThreePage(threePage);
        setBqData();

    }


    private void getHttp() {
        content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showShort(this, "请输入搜索内容");
            return;
        }


        DebtPersonSearch debtPersonSearch = new DebtPersonSearch(DataWarehouse.getBaseRequest(this));
        debtPersonSearch.setPageNo(pn);
        debtPersonSearch.setPageSize(ps);
        debtPersonSearch.setCondition(content);
        String json = Util.toJson(debtPersonSearch);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

        RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
        Observable<BaseEntity<DebtPersonList>> observable = null;
        observable = retrofitService.getMyDebt(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        observable.compose(compose(this.<BaseEntity<DebtPersonList>>bindToLifecycle()))
                .subscribe(new BaseObserver<DebtPersonList>(this) {
                    @Override
                    protected void onSuccees(String code, DebtPersonList data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            Util.closeKeyboard(Debt_Search_Person.this);
                            UserInfoUtils.setUuid(Debt_Search_Person.this, baseResponse);
                            //查询数据库，没有时添加
                            QueryBuilder queryBuilder = GreenDaoUtils.getInstance(Debt_Search_Person.this).getDebtPersonDao().queryBuilder();
                            List<DebtPerson> debtPersonList = queryBuilder.where(DebtPersonDao.Properties.Text.eq(content)).list();
                            if (debtPersonList.size() == 0) { //查询后如果为空，添加
                                DebtPerson debtPerson = new DebtPerson();
                                debtPerson.setText(content);
                                GreenDaoUtils.getInstance(Debt_Search_Person.this).getDebtPersonDao().insert(debtPerson);
                            }

                            setBqData();
                            if (adatper.getList() != null) {
                                adatper.getList().clear();
                                adatper.notifyDataSetChanged();
                            }

                            //列表数据
                            if (data.getDataList().size() != 0) {
                                displayData(data);
                                hideImageView();
                            } else {
                                if (adatper.getList() != null && adatper.getList().size() != 0) {
                                    hideImageView();
                                } else {
                                    showImageView();
                                }
                            }
                            et_content.setText("");
                            search_refresh.finishRefresh();
                            search_refresh.finishRefreshLoadMore();
                        }
                    }
                });

    }

    //抽取的展示数据的方法
    private void displayData(final DebtPersonList data) {
        if (status == NORMAL) {
            dataList = data.getDataList();
            adatper.setDataList(dataList);
            serch_recyclview.setAdapter(adatper);
        }

        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data.getDataList();
            adatper.setDataList(dataList);
            serch_recyclview.setAdapter(adatper);
            adatper.notifyDataSetChanged();

        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<DebtPersonList.Mode> dataList2 = data.getDataList();
            this.dataList.addAll(dataList2);
            adatper.setDataList(this.dataList);
            serch_recyclview.setAdapter(adatper);
            serch_recyclview.scrollToPosition(size - 1);
            adatper.notifyDataSetChanged();
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
    public void onClick(View v) {
        if (v == iv_title_left) {
            finish();
        } else if (v == btn_suo) {//点击搜索
            getHttp();
        } else if (v == delete) {//删除搜索历史纪录
            debtSearchTextAdapter.getDebtPersonList().clear();
            debtSearchTextAdapter.notifyDataSetChanged();
            GreenDaoUtils.getInstance(this).getDebtPersonDao().deleteAll();

        }

    }

    /**
     * 设置标签数据
     */
    private void setBqData() {
        //标签数据
        List<DebtPerson> debtPerson = GreenDaoUtils.getInstance(Debt_Search_Person.this).getDebtPersonDao().loadAll();
        if (debtPerson.size() != 0) {
            debtSearchTextAdapter.setDebtPersonList(debtPerson);
            debtSearchTextAdapter.setClickItem(Debt_Search_Person.this);
            serch_textList.setAdapter(debtSearchTextAdapter);
            debtSearchTextAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }

    /**
     * 点击item 设置输入框内容
     *
     * @param text
     */
    @Override
    public void onClickItem(String text) {
        et_content.setText(text);
    }
}
