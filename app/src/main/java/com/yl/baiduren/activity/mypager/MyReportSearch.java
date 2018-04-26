package com.yl.baiduren.activity.mypager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.utils.ToastUtil;

public class MyReportSearch extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String[] items = new String[]{
            "全部", "资产", "征信"
    };
    private ImageView my_report_back;
    private Button btn_sussful_sousuo;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_report_search;
    }

    @Override
    public void initViews() {
        my_report_back = findViewById(R.id.my_report_back);
        my_report_back.setOnClickListener(this);
        MaterialRefreshLayout refreshLayout = findViewById(R.id.find_refresh);
        btn_sussful_sousuo = findViewById(R.id.btn_sussful_sousuo);
        btn_sussful_sousuo.setOnClickListener(this);
        refreshLayout.setMaterialRefreshListener(refreshListener);
        RecyclerView find_recycler = findViewById(R.id.find_recycler);
        find_recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Spinner spinner_report = findViewById(R.id.spinner_report);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_report.setAdapter(adapter);
        spinner_report.setOnItemSelectedListener(this);
        spinner_report.setSelection(0);
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectName = items[position];
        ToastUtil.showShort(MyReportSearch.this, selectName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == my_report_back) {
            finish();
        } else if (v == btn_sussful_sousuo) {//搜索

        }
    }
}
