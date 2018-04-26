package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.asster_dispose.AddAssetsDispose;
import com.yl.baiduren.adapter.crdeit_query_result_adapter.Credit_Reporting_Query_Enterprise_Adapter;
import com.yl.baiduren.adapter.crdeit_query_result_adapter.Creit_Reporting_Query_People_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Report_Query_Body;
import com.yl.baiduren.entity.result.Report_Result;
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
 * Created by sunbeibei on 2018/2/5.
 */

public class Credit_Reporting_Qurey_Result extends BaseActivity implements Credit_Reporting_Query_Enterprise_Adapter.IonClickItem {

    private ImageView iv_title_left;
    private TextView result_num;

    private RecyclerView credit_recyclview;
    private Credit_Reporting_Query_Enterprise_Adapter enterprise_adapter;
    private int ComeOn = 0;
    private String companyname;

    @Override
    public int loadWindowLayout() {
        return R.layout.credit_reporting_qurey_result;
    }


    @Override
    public void initViews() {
        ComeOn = getIntent().getIntExtra("ComeOn", 0);
        companyname = getIntent().getStringExtra("companyname");//企业名称
        iv_title_left = findViewById(R.id.iv_title_left);//关闭页
        iv_title_left.setOnClickListener(listener);
        result_num = findViewById(R.id.result_num);//搜索结果数量
        credit_recyclview = findViewById(R.id.credit_recyclview);//结果列表
        credit_recyclview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        enterprise_adapter = new Credit_Reporting_Query_Enterprise_Adapter(this);//企业
        enterprise_adapter.setIonClickItem(this);
        Creit_Reporting_Query_People_Adapter reporting_query_people_adapter = new Creit_Reporting_Query_People_Adapter(this);
        requestWord();

    }

    private void requestWord() {

        if (UserInfoUtils.IsLogin(this)) {
            Report_Query_Body entity = new Report_Query_Body(DataWarehouse.getBaseRequest(this));
            if (!TextUtils.isEmpty(companyname)) {
                entity.setEntName(companyname);
            }
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            BaseObserver baseObserver = new BaseObserver<Report_Result>(this) {
                @Override
                protected void onSuccees(String code, Report_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {

                        Report_Result.DataBean data1 = data.getData();
                        List<String> entNameList = data1.getEntNameList();
                        String str1 = "匹配到";
                        String str2 = "";
                        String str3 = "个公司";

                        if (entNameList.size() != 0) {
                            hideImageView();
                            str2 = "<font color='#2595ff'>" + entNameList.size() + "</font>";
                            enterprise_adapter.setEntNameList(entNameList);
                            credit_recyclview.setAdapter(enterprise_adapter);
                        } else {
                            str2 = "<font color='#2595ff'>" + 0 + "</font>";
                            showImageView();
                        }
                        String text = str1 + str2 + str3;
                        result_num.setText(Html.fromHtml(text));
                    }
                }
            };
            RetrofitHelper.getInstance(this).getServer()
                    .report_Query(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<Report_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            finish();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_title_left) {
                finish();
            }
        }
    };

    @Override
    public void onClickItem(String text) {

        Intent intent = new Intent();
        if (ComeOn == 1) {//征信
            intent.setClass(Credit_Reporting_Qurey_Result.this, Credit_Reporting_Queries.class);
            intent.putExtra("text", text);
            setResult(1, intent);
        } else {
            intent.setClass(Credit_Reporting_Qurey_Result.this, AddAssetsDispose.class);
            intent.putExtra("text", text);
            setResult(2, intent);
        }
        finish();
    }
}
