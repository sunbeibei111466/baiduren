package com.yl.baiduren.activity.credit_reporting_queries;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.Reture_Price;
import com.yl.baiduren.entity.result.CreditReportEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 信用报告
 */
public class CreditReportPage extends BaseActivity {

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_credit_report;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        MyRecyclerView rv_credit_report_page = findViewById(R.id.rv_credit_report_page);
        rv_credit_report_page.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        credit_reportingAdapter = new Credit_ReportingAdapter(this);
        TextView content_report = findViewById(R.id.content_report);
//        getReportType();
    }

    /**
     * 获取报告类型
     */
    public void getReportType() {
        if (UserInfoUtils.IsLogin(this)) {
            com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
            String json = Util.toJson(baseRequest);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            RetrofitHelper.getInstance(this).getServer()
                    .getReportType(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<CreditReportEntity>>bindToLifecycle()))
                    .subscribe(new BaseObserver<CreditReportEntity>(this) {
                        @Override
                        protected void onSuccees(String code, CreditReportEntity data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
//                                credit_reportingAdapter.setCreditReports(data.getData());
//                                rv_credit_report_page.setAdapter(credit_reportingAdapter);
//                                content_report.setText(data.getCreditReport());
                            }
                        }
                    });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPrice(Reture_Price price) {
        if (price != null) {
            Long type = price.getCreditId();
            String creditName = price.getCreditName();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
