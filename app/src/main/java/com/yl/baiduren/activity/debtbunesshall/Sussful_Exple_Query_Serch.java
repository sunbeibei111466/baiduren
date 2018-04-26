package com.yl.baiduren.activity.debtbunesshall;

import android.os.Handler;
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
import com.greendao.Sussful_Exple_QueryDao;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.My_Sussful_Adapter;
import com.yl.baiduren.adapter.Sufful_Exple_Histery_Serch_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.Sussful_Exple_Query;
import com.yl.baiduren.entity.request_body.Sussful_Exmple_Serch;
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

/**
 * Created by sunbeibei on 2018/1/5.
 */

public class Sussful_Exple_Query_Serch extends BaseActivity implements Sufful_Exple_Histery_Serch_Adapter.Item_Click{

    private ImageView sussful_finish;
    private EditText et_sufful_content;
    private Button btn_sussful_sousuo;
    private ImageView sufful_delete;
    private MyRecyclerView sufful_serch_textList;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView serch_sufful_recyclview;
    private String query_content;
    private int pn = 1;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 0;
    private List<My_Record_Result.DataListBean> dataList;
    private My_Sussful_Adapter adapter;
    private Handler handler =new Handler();
    private Sufful_Exple_Histery_Serch_Adapter adapter1;

    @Override
    public int loadWindowLayout() {
        return R.layout.sufful_exple_query_serch;
    }



    @Override
    public void initViews() {
        sussful_finish = findViewById(R.id.sussful_finish);//关闭页面
        sussful_finish.setOnClickListener(listener);
        et_sufful_content = findViewById(R.id.et_sufful_content);//输入搜查内容
        btn_sussful_sousuo = findViewById(R.id.btn_sussful_sousuo);//搜索按钮
        btn_sussful_sousuo.setOnClickListener(listener);
        sufful_delete = findViewById(R.id.sufful_delete);//清空历史记录
        sufful_delete.setOnClickListener(listener);
        sufful_serch_textList = findViewById(R.id.sufful_serch_textList);//历史搜索记录列表
        sufful_serch_textList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
        refreshLayout = findViewById(R.id.search_sufful_refresh);//刷新加载
        refreshLayout.setMaterialRefreshListener(refreshListener);
        serch_sufful_recyclview = findViewById(R.id.serch_sufful_recyclview);//结果列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        serch_sufful_recyclview.setLayoutManager(manager);
        adapter =new  My_Sussful_Adapter(this);
        adapter1 = new Sufful_Exple_Histery_Serch_Adapter(this);
        setHistry_Data();
    }

    private void setHistry_Data(){//加载标签
        List<Sussful_Exple_Query> sussful_exple_queries = GreenDaoUtils.getInstance(this).getSussful_Exple_QueryDao().loadAll();
        LUtils.e(sussful_exple_queries.size()+"===============");
        if (sussful_exple_queries.size()!=0){
            adapter1.setList(sussful_exple_queries);
            adapter1.getItemString(this);
            adapter1.notifyDataSetChanged();
            sufful_serch_textList.setAdapter(adapter1);


        }

    }

    private void requestWorkQuery() {


        query_content = et_sufful_content.getText().toString().trim();
        if (TextUtils.isEmpty(query_content)) {
            ToastUtil.showShort(this, "搜索内容不能为空");
            return;
        }
        Sussful_Exmple_Serch querry = new Sussful_Exmple_Serch(DataWarehouse.getBaseRequest(this));
        querry.setPageNo(pn);
        int ps = 8;
        querry.setPageSize(ps);
        querry.setExactParam(query_content);
        querry.setSearchType(3);
        String json = Util.toJson(querry);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver baseObserver = new BaseObserver<My_Record_Result>(this) {
            @Override
            protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    Util.closeKeyboard(Sussful_Exple_Query_Serch.this);
                    UserInfoUtils.setUuid(Sussful_Exple_Query_Serch.this, baseResponse);
                    insert_DbSqul();

                    if(data.getDataList().size()!=0){
                        displayData(data);
                        hideImageView();
                    } else {
                        if(adapter.getDataList()!=null&&adapter.getDataList().size()!=0){
                            hideImageView();
                        }else {
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
                .getByAccurate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }
    private  void insert_DbSqul(){//查询数据库没有则添加
        QueryBuilder<Sussful_Exple_Query>queryBuilder = GreenDaoUtils.getInstance(Sussful_Exple_Query_Serch.this).getSussful_Exple_QueryDao().queryBuilder();
        List<Sussful_Exple_Query> list = queryBuilder.where(Sussful_Exple_QueryDao.Properties.Querysufull.eq(query_content)).list();
        if (list.size()==0){//如果没有查询到则添加
            Sussful_Exple_Query query =new Sussful_Exple_Query();
            query.setQuerysufull(query_content);
            GreenDaoUtils.getInstance(this).getSussful_Exple_QueryDao().insert(query);
            setHistry_Data();
        }

    }
    //抽取的展示数据的方法
    private void displayData(final My_Record_Result data) {
        if (status==NORMAL){
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            serch_sufful_recyclview.setAdapter(adapter);
        }


        if (status == REFRESH) {
            dataList.clear();
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            serch_sufful_recyclview.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Record_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            serch_sufful_recyclview.setAdapter(adapter);
            serch_sufful_recyclview.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_sussful_sousuo) {
                status=NORMAL;
                   pn=1;
                if (dataList!=null){
                    dataList.clear();
                    adapter.notifyDataSetChanged();
                }
                requestWorkQuery();
            }else if (v==sufful_delete){//删除全部记录

                adapter1.getList().clear();
                adapter1.notifyDataSetChanged();
                GreenDaoUtils.getInstance(Sussful_Exple_Query_Serch.this).getSussful_Exple_QueryDao().deleteAll();

            }else if (v==sussful_finish){
                finish();
            }

        }
    };
    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status=REFRESH;
            pn=1;
            requestWorkQuery();



        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status=LOADMORE;
            pn=pn+1;
            requestWorkQuery();

        }
    };

    @Override
    public void setItemString(String text) {
        et_sufful_content.setText(text);
    }
}
