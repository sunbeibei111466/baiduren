package com.yl.baiduren.activity.asster_dispose;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.credit_reporting_queries.CreditReportPage;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.result.ReportPrice;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/1/23.
 * 购买报告
 */

public class Purchase_Report extends BaseActivity {

    private ImageView purchase_back;
    private RelativeLayout credit_relative;
    private RelativeLayout assessment_relative;
    private int stauts = 0;
    private int type = 0;
    private TextView content_report, content_appraisal;
    private Long id;
    private Button btn_skip;

    @Override
    public int loadWindowLayout() {
        return R.layout.purchase_report;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);
        String from = getIntent().getStringExtra("from");
        purchase_back = findViewById(R.id.purchase_back);//关闭页
        purchase_back.setOnClickListener(listener);
        credit_relative = findViewById(R.id.credit_relative);//信用报告布局
        credit_relative.setOnClickListener(listener);
        assessment_relative = findViewById(R.id.assessment_relative);//评估报告布局
        assessment_relative.setOnClickListener(listener);
        TextView xinyong_baog = findViewById(R.id.xinyong_baog);
        content_report = findViewById(R.id.content_report);//信用报告
        TextView tv_pinggu_baog = findViewById(R.id.tv_pinggu_baog);
        content_appraisal = findViewById(R.id.content_appraisal);//评估报告
        btn_skip = findViewById(R.id.btn_skip);//跳过
        btn_skip.setOnClickListener(listener);
        if (!from.equals("addassets")){
            btn_skip.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHttp();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == purchase_back) { //关闭
                if (id != 0) {
                    startActivity(new Intent(Purchase_Report.this, MyAsstesDisposeDetials.class).putExtra("id", id));
                    finish();
                } else {
                    finish();
                }

            } else if (v == credit_relative) {//信用报告
                startActivity(new Intent(Purchase_Report.this, CreditReportPage.class));

            } else if (v == assessment_relative) {//评估报告
            }else if(v==btn_skip){
                startActivity(new Intent(Purchase_Report.this, MyAsstesDisposeDetials.class).putExtra("id", id));
                finish();
            }

        }
    };


    public void getHttp() {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();

        if (baseRequestList.size() != 0) {
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setAccessToken(baseRequestList.get(0).getAccessToken());
            baseRequest.setPlatform(2);
            baseRequest.setUid(baseRequestList.get(0).getUid());
            baseRequest.setUuid(baseRequestList.get(0).getUuid());
            baseRequest.setLoginUsername(baseRequestList.get(0).getLoginUsername());
            String json = Util.toJson(baseRequest);//转成json
            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getReportPrice(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))

                      .compose(compose(this.<BaseEntity<ReportPrice>>bindToLifecycle()))
                    .subscribe(new BaseObserver<ReportPrice>(this) {
                        @Override
                        protected void onSuccees(String code, ReportPrice data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                if (data != null) {
                                    ReportPrice.XyReportPriceBean xyReportPrice = data.getXyReportPrice();
                                    ReportPrice.PgReportPriceBean pgReportPrice = data.getPgReportPrice();
//                                    xinyong_baog.setText("¥" + xyReportPrice.getPriceStr() + "/份");
                                    content_report.setText(xyReportPrice.getExplain());
//                                    tv_pinggu_baog.setText("¥" + pgReportPrice.getPriceStr() + "/份");
                                    content_appraisal.setText(pgReportPrice.getExplain());

                                }

                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (id != 0) {
            startActivity(new Intent(Purchase_Report.this, MyAsstesDisposeDetials.class).putExtra("id", id));
            finish();
        } else {
            finish();
        }

    }
}
