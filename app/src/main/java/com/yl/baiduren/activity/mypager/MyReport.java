package com.yl.baiduren.activity.mypager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.My_Report_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.result.ReportResult;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyReport extends BaseActivity implements View.OnClickListener{

    private ImageView my_report_back;
    private LinearLayout my_reportSearch;
    private MaterialRefreshLayout refreshLayout;
    private RecyclerView report_recycler;
    private My_Report_Adapter adapter;
    private static int status = 0;
    private static int NOMAL = 1;//正场
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private int pn = 1;
    private List<ReportResult.Report> dataList;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_report;
    }

    @Override
    public void initViews() {
        refreshLayout = findViewById(R.id.report_refresh);
        report_recycler = findViewById(R.id.report_recycler);
        report_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        refreshLayout.setMaterialRefreshListener(refreshListener);
        my_reportSearch=findViewById(R.id.my_reportSearch);
        my_reportSearch.setOnClickListener(this);
        my_report_back=findViewById(R.id.my_report_back);
        my_report_back.setOnClickListener(this);
        adapter = new My_Report_Adapter(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            status = REFRESH;
            refreshLayout.autoRefresh();

        }
    }


    private void ask() {
        if (UserInfoUtils.IsLogin(this)) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
            entity.setPageNo(pn);
            int ps = 8;
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<ReportResult> baseObserver = new BaseObserver<ReportResult>(this) {

                @Override
                protected void onSuccees(String code, ReportResult data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(MyReport.this, baseResponse);
                        if (data.getDataList().size() != 0) {
                            displayData(data.getDataList());
                            hideImageView();
                        } else {
                            if (adapter.getReportResults() != null && adapter.getReportResults().size() != 0) {
                                hideImageView();
                            } else {
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
                    .getMyReport(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))

                    .compose(compose(this.<BaseEntity<ReportResult>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }

    private void displayData(List<ReportResult.Report> data) {

        if (status == REFRESH) {
            if (dataList != null) {
                dataList.clear();
            }
            dataList = data;
            adapter.setReportResults(dataList);
            report_recycler.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<ReportResult.Report> dataList2 = data;
            this.dataList.addAll(dataList2);
            adapter.setReportResults(this.dataList);
            report_recycler.setAdapter(adapter);
            report_recycler.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }


    private MaterialRefreshListener refreshListener =new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            pn = 1;
            status = REFRESH;
            ask();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            pn = pn + 1;
            status = LOADMORE;
            ask();
        }
    };

    @Override
    public void onClick(View v) {
        if(v==my_report_back){
            finish();
        }else if(v==my_reportSearch){//搜索
            startActivity(new Intent(this,MyReportSearch.class));
        }
    }
}
