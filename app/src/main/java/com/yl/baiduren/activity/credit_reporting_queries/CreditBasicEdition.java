package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.activity.pay_for.Pay;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.CreditBasicEntity;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.result.Authorization_Enterprise_Name;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.CreditReportEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyGridView;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 基础版
 */
public class CreditBasicEdition extends BaseActivity implements View.OnClickListener{

    private ImageView credit_basic_finish;
    private EditText creade_basic_name;//企业名称
    private EditText creade_basic_codeNname;//主体授权码
    private TextView tv_authorization,tv_jine,basic_jiaodui,user_instructions_wt;
    private Button bt_credit_topay;
    private Long creditBillPriceId=0l;
    private TextView tv_explan;
    private String[] str1 = new String[]{"企业基本信息", "股东及出资信息", "主要管理人员", "法定代表人对外投资"
            ,"企业对外投资", "历史变更记录", "分支机构信息", "股权出质历史信息",
            "股权冻结历史信息", "动产抵押信息", "清算信息", "行政处罚历史信息",
            "法院被执行人记录", "法院失信被执行人记录", "法院公告信息", "裁判文书信息",
            "法院开庭公告信息", "工商经营异常名录", "工商行政处罚记录", "税务负面记录"
    };

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_credit_basic_edition;
    }

    @Override
    public void initViews() {
        credit_basic_finish=findViewById(R.id.credit_basic_finish);
        credit_basic_finish.setOnClickListener(this);
        creade_basic_name=findViewById(R.id.creade_basic_name);//企业全称
        creade_basic_codeNname=findViewById(R.id.creade_basic_codeNname);//主体授权码
        tv_authorization=findViewById(R.id.tv_authorization);
        tv_authorization.setOnClickListener(this);
        tv_jine=findViewById(R.id.tv_jine);
        bt_credit_topay=findViewById(R.id.bt_credit_topay);//支付
        bt_credit_topay.setOnClickListener(this);
        basic_jiaodui=findViewById(R.id.basic_jiaodui);
        basic_jiaodui.setOnClickListener(this);
        user_instructions_wt=findViewById(R.id.user_instructions_wt);
        user_instructions_wt.setOnClickListener(this);
        tv_explan=findViewById(R.id.tv_explan);
        MyGridView gv_1 = findViewById(R.id.gv_basic_1);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.credit_report_arrayadapte_item, str1);
        gv_1.setAdapter(arrayAdapter1);

        getReportType();
    }

    @Override
    public void onClick(View v) {
        if(v==credit_basic_finish){
            finish();
        }else if(v==tv_authorization){
            startActivity(new Intent(this,ApplyAuthorization.class));
        }else if(v==bt_credit_topay){//去支付
            create_Order();
        }else if(v==basic_jiaodui){
            String codeNname=creade_basic_codeNname.getText().toString().trim();
            if (TextUtils.isEmpty(codeNname)) {
                ToastUtil.showShort(this, "请输入主体授权码");
                return;
            }
            getNameByAuthCode(codeNname);
        }else if(v==user_instructions_wt){
            Intent intent=new Intent(this, Break_Debt_Activity.class);
            intent.putExtra("type",10);
            intent.putExtra("url", ServiceUrl.H5_HCREDITPROMISETALK);
            startActivity(intent);
        }
    }

    private void create_Order() {//生成订单

        String creadeName = creade_basic_name.getText().toString().trim();
        String codeNname=creade_basic_codeNname.getText().toString().trim();
        if (TextUtils.isEmpty(creadeName)) {
            ToastUtil.showShort(this, "请输入企业全称");
            return;
        }
        if (TextUtils.isEmpty(codeNname)) {
            ToastUtil.showShort(this, "请输入主体授权码");
            return;
        }

        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        entity.setType(7);
        entity.setSearchName(creadeName);
        entity.setGenre(2);
        entity.setAuthCode(codeNname);
        entity.setCreditBillPriceId(creditBillPriceId);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .create_Order(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Create_Order_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Create_Order_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Create_Order_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(CreditBasicEdition.this, baseResponse);
                            Intent intent = new Intent();
                            intent.setClass(CreditBasicEdition.this, Pay.class);
                            intent.putExtra("orderId", data.getId());
                            startActivity(intent);
                        }
                    }
                });
    }


    /**
     * 获取报告类型
     */
    public void getReportType() {
        if (UserInfoUtils.IsLogin(this)) {
            CreditBasicEntity basicEntity=new CreditBasicEntity(DataWarehouse.getBaseRequest(this));
            basicEntity.setType(2);
            String json = Util.toJson(basicEntity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            RetrofitHelper.getInstance(this).getServer()
                    .getReportType(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<CreditReportEntity>>bindToLifecycle()))
                    .subscribe(new BaseObserver<CreditReportEntity>(this) {
                        @Override
                        protected void onSuccees(String code, CreditReportEntity data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                tv_jine.setText("金额:"+data.getPriceStr());
                                creditBillPriceId=data.getId();
//                                String dd="aaaaa"+"/n"+"affffff";
                                tv_explan.setText(data.getCreditReport());

                            }
                        }
                    });

        }
    }



    /**
     * 验证授权码
     */
    public void getNameByAuthCode(String authCode) {
        if (UserInfoUtils.IsLogin(this)) {
            Greate_Order_Body basicEntity=new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
            basicEntity.setAuthCode(authCode);
            String json = Util.toJson(basicEntity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            RetrofitHelper.getInstance(this).getServer()
                    .getNameByAuthCode(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<Authorization_Enterprise_Name>>bindToLifecycle()))
                    .subscribe(new BaseObserver<Authorization_Enterprise_Name>(this) {
                        @Override
                        protected void onSuccees(String code, Authorization_Enterprise_Name data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                creade_basic_name.setText(data.getSearchName());
                            }
                        }
                    });

        }
    }
}
