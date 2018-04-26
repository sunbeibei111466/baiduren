package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.pay_for.Pay;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.CreditReportEntity;
import com.yl.baiduren.service.RetrofitHelper;
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
 * Created by sunbeibei on 2018/2/5.
 */

public class Credit_Reporting_Queries extends BaseActivity implements TextWatcher {

    private ImageView zhengxin_finish;
    private EditText creade_name;
    private EditText icode_creade;
    private Button bt_credit_topay;
    private TextView creade_jiaodui;
    private String[] str1 = new String[]{"工商信息", "股东信息", "税务信息", "分支机构"};
    private String[] str2 = new String[]{"固定资产", "负债", "知识产权", "对外投资", "担保信息"};
    private String[] str3 = new String[]{"舆情监测", "失信信息", "经营异常", "企业年报"};
    private String[] str4 = new String[]{"裁决文书", "法院公告", "被执行人", "司法拍卖"};
    private boolean whetherCeck = false;//是否点击校对
    private String strOld=null;//文字改变之前
    private String strNew=null;//文字改变之后
    private int num;//

    @Override
    public int loadWindowLayout() {
        return R.layout.credit_report_query;
    }

    @Override
    public void initViews() {
        zhengxin_finish = findViewById(R.id.zhengxin_finish);//关闭页
        zhengxin_finish.setOnClickListener(listener);
        RadioGroup group_credit = findViewById(R.id.group_credit);
        group_credit.setOnCheckedChangeListener(changeListener);
        creade_name = findViewById(R.id.creade_name);//企业全称
        creade_name.addTextChangedListener(this);
        icode_creade = findViewById(R.id.icode_creade);//查询身份证号
        creade_jiaodui = findViewById(R.id.creade_jiaodui);//校对
        creade_jiaodui.setOnClickListener(listener);
        TextView tv_jine = findViewById(R.id.tv_jine);
        bt_credit_topay = findViewById(R.id.bt_credit_topay);//支付
        bt_credit_topay.setOnClickListener(listener);
//        getReportType();

        MyGridView gv_1 = findViewById(R.id.gv_1);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.credit_report_arrayadapte_item, str1);
        gv_1.setAdapter(arrayAdapter1);

        MyGridView gv_2 = findViewById(R.id.gv_2);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.credit_report_arrayadapte_item, str2);
        gv_2.setAdapter(arrayAdapter2);

        MyGridView gv_3 = findViewById(R.id.gv_3);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, R.layout.credit_report_arrayadapte_item, str3);
        gv_3.setAdapter(arrayAdapter3);

        MyGridView gv_4 = findViewById(R.id.gv_4);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<>(this, R.layout.credit_report_arrayadapte_item, str4);
        gv_4.setAdapter(arrayAdapter4);


    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == zhengxin_finish) {
                finish();
            } else if (v == bt_credit_topay) {//支付
                toPay();
            } else if (v == creade_jiaodui) {//校对
                Intent intent = new Intent();
                intent.setClass(Credit_Reporting_Queries.this, Credit_Reporting_Qurey_Result.class);
                intent.putExtra("ComeOn",1);
                startActivityForResult(intent,1);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            String text=data.getExtras().getString("text");
            whetherCeck = true;//返回成功表示校对过了
            creade_name.setText(text);//第一次设置值 在editText的监听中执行的是改变后的方法 及strNew的值
            creade_name.setText(text);//这个时候设置值时 edittext会先执行改变前的方法，及strOld=text ,然后在执行 strNew=text
        }
    }

    private void toPay() {
        String creadeName = creade_name.getText().toString().trim();
        if (TextUtils.isEmpty(creadeName)) {
            ToastUtil.showShort(this, "请输入企业全称");
            return;
        }

        if (!whetherCeck) {
            ToastUtil.showShort(this, "请先校对");
            return;
        }

        if(!strOld.equals(strNew)){
            whetherCeck=false;
            ToastUtil.showShort(this, "请重新校对");
            return;
        }

//        create_Order();
    }

    private void create_Order() {//生成订单
        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        entity.setType(4);
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
                            UserInfoUtils.setUuid(Credit_Reporting_Queries.this, baseResponse);
                            Intent intent = new Intent();
                            intent.setClass(Credit_Reporting_Queries.this, Pay.class);
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

                            }
                        }
                    });
        }
    }

    private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.enterprise_credit://企业
                    isGone();
                    break;
                case R.id.people_credit://个人
                    isShow();
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void isGone() {
        icode_creade.setVisibility(View.GONE);
        creade_name.setHint("企业名称");
    }

    private void isShow() {
        icode_creade.setVisibility(View.VISIBLE);
        creade_name.setHint("请输入姓名");
    }


    /**
     * 改变之前
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        strOld = s.toString().trim();
        LUtils.e("------旧的字符串-----",strOld);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LUtils.e("------正在改变字符串-----",s.toString().trim());
    }

    /**
     * 改变之后
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        strNew = s.toString().trim();
        LUtils.e("------新的字符串-----",strNew);
        if(num==0){
            num++;
            strOld=strNew;
        }

    }
}